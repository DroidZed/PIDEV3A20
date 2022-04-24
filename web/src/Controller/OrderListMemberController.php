<?php

namespace App\Controller;

use App\Entity\TblOrderline;
use App\Entity\TblProduct;
use App\Entity\TblUserorder;
use App\Form\PlaceOrderType;
use App\Repository\OrderLineRepository;
use App\Repository\UserOrderRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;


class OrderListMemberController extends AbstractController
{
    /**
     * @Route("/myOrders", name="myOrders", methods={"GET"})
     */
    public function index(UserOrderRepository $userOrderRepository): Response
    {
        return $this->render('frontTemplate/orders/index.html.twig', [
            'ordersList' => $userOrderRepository->findAll(),
        ]);
    }

    /**
     * @Route("/checkout", name="checkOut", methods={"GET", "POST"})
     */
    public function new(Request             $request,
                        UserOrderRepository $userOrderRepository,
                        OrderLineRepository $orderLineRepository,
                        CartController      $cartController): Response
    {
        $userOrder = new TblUserorder();
        $form = $this->createForm(PlaceOrderType::class, $userOrder);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $savedOrder = $userOrderRepository->save($userOrder);

            $cartItems = $cartController->getFromCart();

            foreach ($cartItems as $k => $v) {
                if ($v instanceof TblProduct) {
                    $temp = new TblOrderline();
                    $temp->setIdproduct($v);
                    $temp->setNumberorder($userOrderRepository->find($savedOrder));
                    $countOf = 0;
                    for ($i = 0; $i < count($cartItems); $i++) {
                        if ($cartItems[$i] == $cartItems[$i + 1])
                            $countOf++;
                    }
                    $temp->setQuantordline($countOf);
                    $orderLineRepository->add($temp);
                }
            }

            return $this->redirectToRoute('test', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('frontTemplate/orders/new.html.twig', [
            'orders' => $userOrder,
            'form' => $form->createView(),
        ]);
    }
}
