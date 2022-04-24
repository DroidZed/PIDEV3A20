<?php

namespace App\Controller;

use App\Entity\CartItem;
use App\Repository\ProductRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
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
    public function index(ProductRepository $productsRepository): Response
    {
        $cartItems = $this->session->get('cartItems', []);

        return $this->render('frontTemplate/cart/index.html.twig',
            [
                'cart' => $cartItems,
            ]);
    }

    /**
     * @Route(""/membre/cart/add", name="addToCart")
     */
    public function new(Request $req, ProductRepository $productsRepository): Response
    {
        $idProd = $req->get('idProd');
        $quantity = $req->get('quantity');

        $items = $this->getFromCart();

        forEach ($items as $k => $v )
        {

        }

        $cartItem = new CartItem(1); // TODO: integrate user session !

        $product = $productsRepository->find($idProd);

        $cartItem->setProduct($product)->setQuantity($quantity);

        $items += [$product->getNameproduct() => $cartItem];

        $this->session->set('cartItems', $items);

        return $this->redirectToRoute('listProds');
    }

    /**
     * @return mixed | CartItem[]
     */
    public function getFromCart()
    {
        return $this->session->get('cartItems', []);
    }

    /**
     * @Route(""/membre/cart/remove", name="removeFromCart")
     */
    public function del(Request $req): RedirectResponse
    {
        $items = $this->getFromCart();

        unset($items[$req->get("nameProd")]);

        $this->session->set('cartItems', $items);

        return $this->redirectToRoute('goToCart');
    }
}
