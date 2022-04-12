<?php

namespace App\Controller;

use App\Entity\TblOrderline;
use App\Form\TblOrderlineType;
use App\Repository\OrderLineRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/tbl/orderline")
 */
class TblOrderlineController extends AbstractController
{
    /**
     * @Route("/", name="app_tbl_orderline_index", methods={"GET"})
     */
    public function index(OrderLineRepository $orderLineRepository): Response
    {
        return $this->render('tbl_orderline/list.html.twig', [
            'tbl_orderlines' => $orderLineRepository->findAll(),
        ]);
    }

    /**
     * @Route("/new", name="app_tbl_orderline_new", methods={"GET", "POST"})
     */
    public function new(Request $request, OrderLineRepository $orderLineRepository): Response
    {
        $tblOrderline = new TblOrderline();
        $form = $this->createForm(TblOrderlineType::class, $tblOrderline);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $orderLineRepository->add($tblOrderline);
            return $this->redirectToRoute('app_tbl_orderline_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('tbl_orderline/new.html.twig', [
            'tbl_orderline' => $tblOrderline,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idorderline}", name="app_tbl_orderline_show", methods={"GET"})
     */
    public function show(TblOrderline $tblOrderline): Response
    {
        return $this->render('tbl_orderline/show.html.twig', [
            'tbl_orderline' => $tblOrderline,
        ]);
    }

    /**
     * @Route("/{idorderline}/edit", name="app_tbl_orderline_edit", methods={"GET", "POST"})
     */
    public function edit(Request $request, TblOrderline $tblOrderline, OrderLineRepository $orderLineRepository): Response
    {
        $form = $this->createForm(TblOrderlineType::class, $tblOrderline);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $orderLineRepository->add($tblOrderline);
            return $this->redirectToRoute('app_tbl_orderline_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('tbl_orderline/edit.html.twig', [
            'tbl_orderline' => $tblOrderline,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idorderline}", name="app_tbl_orderline_delete", methods={"POST"})
     */
    public function delete(Request $request, TblOrderline $tblOrderline, OrderLineRepository $orderLineRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$tblOrderline->getIdorderline(), $request->request->get('_token'))) {
            $orderLineRepository->remove($tblOrderline);
        }

        return $this->redirectToRoute('app_tbl_orderline_index', [], Response::HTTP_SEE_OTHER);
    }
}
