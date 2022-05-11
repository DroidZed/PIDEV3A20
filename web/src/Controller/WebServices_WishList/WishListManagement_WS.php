<?php

namespace App\Controller\WebServices_WishList;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Serializer\Exception\ExceptionInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use App\Entity\JsonResponseDAO;
use App\Entity\TblWishlist;
use App\Repository\ProductRepository;
use App\Repository\UserRepository;
use App\Repository\WishListRepository;
use App\Services\GetUser;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/wishlist")
 */
class WishListManagement_WS extends AbstractController
{

    /**
     * @Route("/add", name="addToWishlistJson", methods={"POST"})
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
            return $this->json(new JsonResponseDAO("Product already in wishlist !"));
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
     * @Route("/get/{userId}", name="wishlist", methods={"GET"})
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
     * @Route("/rem", name="rem", methods={"DELETE"})
     * @param Request $request
     * @param WishListRepository $wishListRepository
     * @return JsonResponse
     */
    public function rem(Request            $request,
                        WishListRepository $wishListRepository): JsonResponse
    {
        $item = $wishListRepository->find($request->get("idWishlist"));

        if ($item == null) {
            return $this->json(new JsonResponseDAO("Product not found !"));
        }

        $em = $this->getDoctrine()->getManager();

        $em->remove($item);

        $em->flush();

        return $this->json(new JsonResponseDAO("Product removed !"));
    }
}