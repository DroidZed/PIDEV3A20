<?php

namespace App\Controller;

use App\Repository\ProductRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/products")
 */
class ProductsController extends AbstractController
{

    private SessionInterface $session;

    public function __construct(SessionInterface $session)
    {
        $this->session = $session;
    }

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

    /**
     * @Route("/addToCart", name="addToCart")
     */
    public function addToCart(Request $req, ProductRepository $productsRepository): Response
    {
        $prod = $productsRepository->find($req->get('idProd'));

        // gets an attribute by name, an empty array is given if the item doesn't exist
        $items = $this->session->get('cartItems', []);

        $items += [sizeOf($items) => $prod];

        // stores an attribute in the session for later reuse
        $this->session->set('cartItems', $items);

        return $this->redirectToRoute('listProds');
    }
}
