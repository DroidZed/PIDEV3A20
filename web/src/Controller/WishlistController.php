<?php

namespace App\Controller;

use App\Entity\JsonResponseDAO;
use App\Entity\TblWishlist;
use App\Repository\ProductRepository;
use App\Repository\UserRepository;
use App\Repository\WishListRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Exception\ExceptionInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;


class WishlistController extends AbstractController
{
    /**
     * @Route("/membre/wishlist/{userId}", name="app_tbl_wishlist_show", methods={"GET"})
     * @param int $userId
     * @param WishListRepository $wishListRepository
     * @param UserRepository $userRepo
     * @return Response
     */
    public function show(int $userId, WishListRepository $wishListRepository, UserRepository $userRepo): Response
    {
        return $this->render('frontTemplate/wishlist/list.html.twig', [
            'wishlist' => $wishListRepository->findBy(["iduser" => $userRepo->find($userId)]),
        ]);
    }

    /**
     * @Route("/membre/wishlist/addProduct", name="addToWishlist", methods={"GET", "POST"})
     * @param Request $request
     * @param WishListRepository $wishListRepository
     * @param ProductRepository $productRepository
     * @param UserRepository $userRepository
     * @return Response
     */
    public function add(Request            $request,
                        WishListRepository $wishListRepository,
                        ProductRepository  $productRepository,
                        UserRepository     $userRepository): Response
    {

        $idUser = $request->get("idUser");
        $idProduct = $request->get("idProduct");
        $prod = $productRepository->find($idProduct);
        $user = $userRepository->find($idUser);

        if ($wishListRepository->findOneBy([
            "iduser" => $user,
            "idproduct" => $prod
        ])) {
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
     * @Route("/membre/wishlist/remProduct", name="app_tbl_wishlist_delete", methods={"GET"})
     * @param Request $request
     * @param WishListRepository $wishListRepository
     * @param UserRepository $userRepository
     * @param ProductRepository $productRepository
     * @return Response
     */
    public function remove(Request            $request,
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


    /**
     * @Route("/membre/wishlist/add", name="addToWishlistJson", methods={"POST"})
     * @param Request $request
     * @param WishListRepository $wishListRepository
     * @param ProductRepository $productRepository
     * @param UserRepository $userRepository
     * @return JsonResponse
     */
    public function addJson(Request            $request,
                            WishListRepository $wishListRepository,
                            ProductRepository  $productRepository,
                            UserRepository     $userRepository): JsonResponse
    {
        $ids = json_decode($request->getContent(), true);

        $prod = $productRepository->find($ids["idProduct"]);

        if ($prod == null) {
            return $this->json(new JsonResponseDAO("Product not found !"), Response::HTTP_BAD_REQUEST);
        }

        $user = $userRepository->find($ids["idUser"]);

        if ($user == null) {
            return $this->json(new JsonResponseDAO("User not found !"), Response::HTTP_BAD_REQUEST);
        }

        if ($wishListRepository->findOneBy([
            "iduser" => $user,
            "idproduct" => $prod
        ])) {
            return $this->json(new JsonResponseDAO("Product already in wishlist !"), Response::HTTP_BAD_REQUEST);
        }

        $item = new TblWishlist();

        $item->setIdproduct($prod);
        $item->setIdUser($user);

        $em = $this->getDoctrine()->getManager();

        $em->persist($item);
        $em->flush();

        return $this->json(new JsonResponseDAO("Product added !"));
    }

    /**
     * @Route("/membre/wishlist/get/{userId}", name="wishlist", methods={"GET"})
     * @param int $userId
     * @param NormalizerInterface $normalizer
     * @param WishListRepository $wishListRepository
     * @param UserRepository $userRepo
     * @return JsonResponse
     * @throws ExceptionInterface
     */
    public function getAllOfUser(
        int                 $userId,
        NormalizerInterface $normalizer,
        WishListRepository  $wishListRepository,
        UserRepository      $userRepo): JsonResponse
    {
        return new JsonResponse(
            $normalizer->normalize(
                $wishListRepository->findBy(
                    [
                        "iduser" => $userRepo->find($userId)
                    ]
                ),
                'json',
                ['groups' => 'wishlist:items']
            )
        );
    }

    /**
     * @Route("/membre/wishlist/rem", name="rem", methods={"DELETE"})
     * @param Request $request
     * @param WishListRepository $wishListRepository
     * @return JsonResponse
     */
    public function rem(Request            $request,
                        WishListRepository $wishListRepository): JsonResponse
    {
        $item = $wishListRepository->find($request->get("idWishlist"));

        $em = $this->getDoctrine()->getManager();

        $em->remove($item);

        $em->flush();

        return $this->json(new JsonResponseDAO("Product removed !"));
    }

}
