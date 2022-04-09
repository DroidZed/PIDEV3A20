<?php

namespace App\Controller;

use App\Entity\TblUserorder;
use App\Form\TblUserorderType;
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
     * @Route("/", name="app_tbl_userorder_index", methods={"GET"})
     */
    public function index(UserOrderRepository $userOrderRepository): Response
    {
        return $this->render('tbl_userorder/index.html.twig', [
            'tbl_userorders' => $userOrderRepository->findAll(),
        ]);
    }

    /**
     * @Route("/new", name="app_tbl_userorder_new", methods={"GET", "POST"})
     */
    public function new(Request $request, UserOrderRepository $userOrderRepository): Response
    {
        $tblUserorder = new TblUserorder();
        $form = $this->createForm(TblUserorderType::class, $tblUserorder);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $userOrderRepository->add($tblUserorder);
            return $this->redirectToRoute('app_tbl_userorder_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('tbl_userorder/new.html.twig', [
            'tbl_userorder' => $tblUserorder,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{numberorder}", name="app_tbl_userorder_show", methods={"GET"})
     */
    public function show(TblUserorder $tblUserorder): Response
    {
        return $this->render('tbl_userorder/show.html.twig', [
            'tbl_userorder' => $tblUserorder,
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

    /**
     * @Route("/{numberorder}", name="app_tbl_userorder_delete", methods={"POST"})
     */
    public function delete(Request $request, TblUserorder $tblUserorder, UserOrderRepository $userOrderRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$tblUserorder->getNumberorder(), $request->request->get('_token'))) {
            $userOrderRepository->remove($tblUserorder);
        }

        return $this->redirectToRoute('app_tbl_userorder_index', [], Response::HTTP_SEE_OTHER);
    }
}
