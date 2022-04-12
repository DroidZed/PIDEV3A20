<?php

namespace App\Controller;

use App\Repository\ProductRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
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
     * @Route("/cart", name="goToCart")
     */
    public function index(): Response
    {
        return $this->render('cart/index.html.twig', [
            'controller_name' => 'CartController',
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
