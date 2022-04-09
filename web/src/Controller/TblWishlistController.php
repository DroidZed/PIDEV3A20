<?php

namespace App\Controller;

use App\Entity\TblWishlist;
use App\Form\TblWishlistType;
use App\Repository\WishListRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/tbl/wishlist")
 */
class TblWishlistController extends AbstractController
{
    /**
     * @Route("/", name="app_tbl_wishlist_index", methods={"GET"})
     */
    public function index(WishListRepository $wishListRepository): Response
    {
        return $this->render('wishlist/index.html.twig', [
            'tbl_wishlists' => $wishListRepository->findAll(),
        ]);
    }

    /**
     * @Route("/new", name="app_tbl_wishlist_new", methods={"GET", "POST"})
     */
    public function new(Request $request, WishListRepository $wishListRepository): Response
    {
        $tblWishlist = new TblWishlist();
        $form = $this->createForm(TblWishlistType::class, $tblWishlist);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $wishListRepository->add($tblWishlist);
            return $this->redirectToRoute('app_tbl_wishlist_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('wishlist/new.html.twig', [
            'wishlist' => $tblWishlist,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idwishlist}", name="app_tbl_wishlist_show", methods={"GET"})
     */
    public function show(TblWishlist $tblWishlist): Response
    {
        return $this->render('wishlist/show.html.twig', [
            'wishlist' => $tblWishlist,
        ]);
    }

    /**
     * @Route("/{idwishlist}/edit", name="app_tbl_wishlist_edit", methods={"GET", "POST"})
     */
    public function edit(Request $request, TblWishlist $tblWishlist, WishListRepository $wishListRepository): Response
    {
        $form = $this->createForm(TblWishlistType::class, $tblWishlist);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $wishListRepository->add($tblWishlist);
            return $this->redirectToRoute('app_tbl_wishlist_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('wishlist/edit.html.twig', [
            'wishlist' => $tblWishlist,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idwishlist}", name="app_tbl_wishlist_delete", methods={"POST"})
     */
    public function delete(Request $request, TblWishlist $tblWishlist, WishListRepository $wishListRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$tblWishlist->getIdwishlist(), $request->request->get('_token'))) {
            $wishListRepository->remove($tblWishlist);
        }

        return $this->redirectToRoute('app_tbl_wishlist_index', [], Response::HTTP_SEE_OTHER);
    }
}
