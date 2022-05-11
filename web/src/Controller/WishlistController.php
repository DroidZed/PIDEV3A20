<?php

namespace App\Controller;

use App\Entity\JsonResponseDAO;
use App\Entity\TblWishlist;
use App\Repository\ProductRepository;
use App\Repository\UserRepository;
use App\Repository\WishListRepository;
use App\Services\GetUser;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Routing\RouterInterface;


class WishlistController extends AbstractController
{

    /**
     * @var RouterInterface
     */
    private $router;
    private $getUser;

    /**
     * @param RouterInterface $router
     * @param GetUser $getUser
     */
    public function __construct(RouterInterface $router, GetUser $getUser)
    {
        $this->router = $router;
        $this->getUser = $getUser;

    }

    /**
     * @Route("/membre/wishlist", name="app_tbl_wishlist_show", methods={"GET"})
     * @param WishListRepository $wishListRepository
     * @return Response
     */
    public function show(WishListRepository $wishListRepository): Response
    {
        return $this->render('frontTemplate/wishlist/list.html.twig', [
            'wishlist' => $wishListRepository->findBy(["iduser" => $this->getUser->Get_User()]),
        ]);
    }

    /**
     * @Route("/membre/wishlist/addProduct", name="addToWishlist", methods={"GET", "POST"})
     * @param Request $request
     * @param WishListRepository $wishListRepository
     * @param ProductRepository $productRepository
     * @return Response
     */
    public function add(Request            $request,
                        WishListRepository $wishListRepository,
                        ProductRepository  $productRepository): Response
    {

        $idProduct = $request->get("idProduct");
        $prod = $productRepository->find($idProduct);
        $user = $this->getUser->Get_User();

        if ($wishListRepository->findOneBy([
            "iduser" => $user,
            "idproduct" => $prod
        ])) {
            return $this->redirectToRoute('app_tbl_wishlist_show');
        }

        $item = new TblWishlist();

        $item->setIdproduct($prod);
        $item->setIdUser($user);

        $em = $this->getDoctrine()->getManager();

        $em->persist($item);
        $em->flush();

        return $this->redirectToRoute('app_tbl_wishlist_show');
    }

    /**
     * @Route("/membre/wishlist/remProduct", name="app_tbl_wishlist_delete", methods={"GET"})
     * @param Request $request
     * @param WishListRepository $wishListRepository
     * @param ProductRepository $productRepository
     * @return Response
     */
    public function remove(Request            $request,
                           WishListRepository $wishListRepository,
                           ProductRepository  $productRepository): Response
    {

        $idProduct = $request->get("idProduct");

        $user = $this->getUser->Get_User();
        $item = $wishListRepository->findOneBy([
            "idproduct" => $productRepository->find($idProduct),
            "iduser" => $user
        ]);

        $em = $this->getDoctrine()->getManager();

        $em->remove($item);

        $em->flush();

        return $this->redirectToRoute('app_tbl_wishlist_show');
    }
}
