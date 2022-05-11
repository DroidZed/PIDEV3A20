<?php

namespace App\Controller\WebServices_Product;

use App\Repository\CategoryRepository;
use App\Repository\ProductRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Exception\ExceptionInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;


/**
 * @Route("/category")
 */
class CategoryResourceController extends AbstractController
{


    /**
     * @Route("/get", name="allCategoriesJson", methods={"GET"})
     * @throws ExceptionInterface
     */
    public function index(CategoryRepository $categoryRepository, NormalizerInterface $normalizer): JsonResponse
    {

        return $this->json($normalizer->normalize(
            $categoryRepository->findAll(),
            'json',
            ['groups' => 'category']
        )
        );
    }
}