<?php

namespace App\Controller;

use App\Entity\CartItem;
use App\Entity\TblPromo;
use App\Repository\ProductRepository;
use App\Repository\PromoRepository;
use DateTime;
use Exception;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;

class CartController extends AbstractController
{
    private SessionInterface $session;

    public function __construct(SessionInterface $session)
    {
        $this->session = $session;
    }

    /**
     * @Route("/membre/cart", name="goToCart")
     */
    public function index(): Response
    {
        return $this->render('frontTemplate/cart/index.html.twig',
            [
                'cart' => $this->getFromCart(),
            ]);
    }

    /**
     * @return mixed | CartItem[]
     */
    public function getFromCart()
    {
        return $this->session->get('cartItems', []);
    }

    /**
     * @Route("/membre/cart/add", name="addToCart")
     */
    public function new(Request $req, ProductRepository $productsRepository): Response
    {
        $idProd = $req->get('idProd');
        $product = $productsRepository->find($idProd);
        $productName = $product->getNameproduct();

        $items = $this->getFromCart();


        if (array_key_exists($productName, $items)) {
            $items[$productName]->setQuantity($items[$productName]->getQuantity() + 1);

            $this->session->set('cartItems', $items);

            return $this->redirectToRoute('goToCart');
        }


        $cartItem = new CartItem(1); // TODO: integrate user session !

        $cartItem->setProduct($product)->setQuantity(1);

        $items += [$productName => $cartItem];

        $this->session->set('cartItems', $items);

        return $this->redirectToRoute('goToCart');
    }

    /**
     * @param Request $req
     * @param ProductRepository $productsRepository
     * @param PromoRepository $codePromoRepo
     * @return JsonResponse
     * @Route("/membre/cart/updatePrice", name="updatePrice")
     * @throws Exception
     */
    public function updatePrice(Request $req,
                                ProductRepository $productsRepository,
                                PromoRepository $codePromoRepo): JsonResponse
    {
        $data = json_decode($req->getContent(), true);

        $promoCode = $data['promoCode'];
        $promoEntity = $codePromoRepo->findOneBy(["codepromo" => $promoCode]);

        if ($promoEntity == null) {
            return $this->json(
                ["err" => "Invalid code !"],
                Response::HTTP_NOT_FOUND
            );
        }

        if ($promoEntity->getStatuspromo() == 0) {
            return $this->json(
                ["err" => "Invalid code !"],
                Response::HTTP_NOT_FOUND
            );
        }

        $product = $productsRepository->find($promoEntity->getIdproduct());

        $productName = $product->getNameproduct();

        $items = $this->getFromCart();

        if (!array_key_exists($productName, $items)) {

            return $this->json(
                ["err" => "Invalid code !"],
                Response::HTTP_NOT_FOUND
            );
        }

        $product = $items[$productName]->getProduct();

        $newPrice = $product->getPriceproduct() - (($promoEntity->getDiscountpromo() * $product->getPriceproduct()) / 100);

        $items[$productName]->getProduct()->setPriceproduct($newPrice);

        $total = 0;

        foreach ($items as $item) {
            $total = $total + ($item->getQuantity() * $item->getProduct()->getPriceproduct());
        }

        $promoEntity->setStatuspromo(0);

        $codePromoRepo->update();

        return $this->json(
            ["subTotal" => $total,
                "newPrice" => $newPrice,
                "idProd" => $product->getIdproduct()
            ],
            Response::HTTP_OK
        );

    }

    /**
     * @Route("/membre/cart/remove", name="removeFromCart")
     */
    public function del(Request $req, ProductRepository $productsRepository): RedirectResponse
    {
        $isDecrease = $req->get('dec');
        $idProd = $req->get('idProd');
        $product = $productsRepository->find($idProd);
        $productName = $product->getNameproduct();
        $items = $this->getFromCart();

        if (array_key_exists($productName, $items) && $isDecrease == 1) {
            $items[$productName]->setQuantity($items[$productName]->getQuantity() - 1);

            $this->session->set('cartItems', $items);

            return $this->redirectToRoute('goToCart');
        }

        unset($items[$productName]);

        $this->session->set('cartItems', $items);

        return $this->redirectToRoute('goToCart');
    }
}
