<?php

namespace App\Controller;

use App\Repository\ProductRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/products")
 */
class ProductsController extends AbstractController
{
    /**
     * @Route("/list", name="listProds", methods={"GET"})
     */
    public function index(ProductRepository $productRepository): Response
    {
        return $this->render('frontTemplate/products/list.html.twig', [
            'prods' => $productRepository->findAll(),
        ]);
    }

    /**
     * @Route("/show/{idP}", name="detailProd", methods={"GET"})
     */
    public function showOne(int $idP, ProductRepository $productRepository): Response
    {
        return $this->render('frontTemplate/products/show.html.twig', [
            'prod' => $productRepository->find($idP),
        ]);
    }
}
