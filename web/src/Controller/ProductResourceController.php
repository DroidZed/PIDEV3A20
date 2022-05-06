<?php

namespace App\Controller;

use App\Repository\ProductRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Exception\ExceptionInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

class ProductResourceController extends AbstractController
{
    /**
     * @Route("/get/product/{idproduct}" , name="singleProduct" , methods={"GET"})
     * @throws ExceptionInterface
     */
    public function productDetail(int $idproduct, ProductRepository $productRepository, NormalizerInterface $normalizer): JsonResponse
    {
        return new JsonResponse($normalizer->normalize(
            $productRepository->find($idproduct),
            'json',
            ['groups' => 'products']
        )
        );
    }


    /**
     * @Route("/get/products", name="allProducts", methods={"GET"})
     * @throws ExceptionInterface
     */
    public function index(ProductRepository $productRepository, NormalizerInterface $normalizer): JsonResponse
    {

        return new JsonResponse($normalizer->normalize(
            $productRepository->findAll(),
            'json',
            ['groups' => 'products']
        )
        );
    }

}