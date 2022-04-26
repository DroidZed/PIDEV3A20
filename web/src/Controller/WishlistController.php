<?php

namespace App\Controller;

use App\Entity\TblWishlist;
use App\Repository\ProductRepository;
use App\Repository\UserRepository;
use App\Repository\WishListRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/tbl/wishlist")
 */
class WishlistController extends AbstractController
{
    /**
     * @Route("/{userId}", name="app_tbl_wishlist_show", methods={"GET"})
     */
    public function show(int $userId, WishListRepository $wishListRepository, UserRepository $userRepo): Response
    {
        return $this->render('frontTemplate/wishlist/list.html.twig', [
            'wishlist' => $wishListRepository->findBy(["iduser" => $userRepo->find($userId)]),
        ]);
    }

    /**
     * @Route("/{idwishlist}/addProduct", name="addToWishlist", methods={"GET", "POST"})
     */
    public function addToWishList(int $idwishlist, Request $request,
                                  WishListRepository $wishListRepository,
                                  ProductRepository $productRepository,
                                  UserRepository  $userRepository): Response
    {

        $idUser = $request->get("idUser");
        $idProduct = $request->get("idProduct");
        $prod = $productRepository->find($idProduct);
        $user = $userRepository->find($idUser);

        if ($wishListRepository->findOneBy([
            "iduser" => $user,
            "idproduct" => $prod
        ]))
        {
            return $this->redirectToRoute('app_tbl_wishlist_show', [
                "userId" => $idUser
            ], Response::HTTP_SEE_OTHER);
        }


        $item = new TblWishlist();

        $item->setIdproduct($prod);
        $item->setIdUser($user);

        $em = $this->getDoctrine()->getManager();

        $em->persist($item);
        $em->flush();

        return $this->redirectToRoute('app_tbl_wishlist_show', [
            "userId" => $idUser
        ], Response::HTTP_SEE_OTHER);
    }

    /**
     * @Route("/{idwishlist}/remProduct", name="app_tbl_wishlist_delete", methods={"GET", "POST"})
     */
    public function removeProductFromWishList(Request            $request,
                                              WishListRepository $wishListRepository,
                                              UserRepository     $userRepository,
                                              ProductRepository  $productRepository): Response
    {

        $idUser = $request->get("idUser");
        $idProduct = $request->get("idProduct");

        $item = $wishListRepository->findOneBy([
            "idproduct" => $productRepository->find($idProduct),
            "iduser" => $userRepository->find($idUser)
        ]);

        $em = $this->getDoctrine()->getManager();

        $em->remove($item);

        $em->flush();

        return $this->redirectToRoute('app_tbl_wishlist_show', ["userId" => $idUser], Response::HTTP_SEE_OTHER);
    }
}
