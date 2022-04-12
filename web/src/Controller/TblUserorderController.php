<?php

namespace App\Controller;

use App\Entity\TblUserorder;
use App\Form\PlaceOrderType;
use App\Repository\UserOrderRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/tbl/userorder")
 */
class TblUserorderController extends AbstractController
{
    /**
     * @Route("/", name="myOrders", methods={"GET"})
     */
    public function userOrders(UserOrderRepository $userOrderRepository): Response
    {
        return $this->render('tbl_userorder/list.html.twig', [
            'ordersList' => $userOrderRepository->findAll(),
        ]);
    }

    /**
     * @Route("/new", name="checkOut", methods={"GET", "POST"})
     */
    public function new(Request $request, UserOrderRepository $userOrderRepository): Response
    {
        $tblUserorder = new TblUserorder();
        $form = $this->createForm(PlaceOrderType::class, $tblUserorder);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $userOrderRepository->add($tblUserorder);
            return $this->redirectToRoute('myOrders', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('tbl_userorder/new.html.twig', [
            'tbl_userorder' => $tblUserorder,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/admin", name="adminOrders", methods={"GET"})
     */
    public function adminSide(UserOrderRepository $userOrderRepository): Response
    {
        return $this->render('backTemplate/listOrders.html.twig', [
            'ordersList' => $userOrderRepository->findAll()
        ]);
    }

    /**
     * @Route("/{numberorder}/edit", name="app_tbl_userorder_edit", methods={"GET", "POST"})
     */
    public function edit(Request $request, TblUserorder $tblUserorder, UserOrderRepository $userOrderRepository): Response
    {
        $form = $this->createForm(TblUserorderType::class, $tblUserorder);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $userOrderRepository->add($tblUserorder);
            return $this->redirectToRoute('app_tbl_userorder_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('tbl_userorder/edit.html.twig', [
            'tbl_userorder' => $tblUserorder,
            'form' => $form->createView(),
        ]);
    }
}
