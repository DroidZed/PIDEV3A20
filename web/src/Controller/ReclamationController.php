<?php

namespace App\Controller;

use App\Entity\TblReclamation;
use App\Entity\TblStateuser;
use App\Entity\TblTypecomplaint;
use App\Entity\User;
use App\Form\ConsulterRecType;
use App\Form\ModererType;
use App\Form\TblReclamationType;
use App\Repository\ReclamationRepository;
use App\Repository\TypeComplaintRepository;
use App\Repository\UserRepository;
use App\Services\GetUser;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Routing\RouterInterface;


class ReclamationController extends AbstractController
{
    /**
     * @var RouterInterface
     */
    private $router;
    private $getUser;

    /**
     * @param RouterInterface $router
     * @param GetUser $getUser
     */
    public function __construct(RouterInterface $router,GetUser $getUser)
    {
        $this->router = $router;
        $this->getUser=$getUser;

    }
    /**
     * @Route("/membre/reclamation/show", name="app_reclamation_index", methods={"GET"})
     */
    public function index(ReclamationRepository $reclamationRepository): Response
    {
        $user=new User();
        $user = $this->getUser->Get_User();

        return $this->render('reclamation/index.html.twig', [
            'tbl_reclamations' => $reclamationRepository->findBy(['iduser'=>$user->getId()]),


        ]);
    }

    /**
     * @Route("/membre/reclamation/new", name="passerRec", methods={"GET", "POST"})
     */
    public function new(Request $request, ReclamationRepository $reclamationRepository): Response
    {
        $user=new User();
        $user = $this->getUser->Get_User();

        $tblReclamation = new TblReclamation();
        $form = $this->createForm(TblReclamationType::class, $tblReclamation);
        $form->handleRequest($request);

        $tblReclamation->setIduser($user);
        if ($form->isSubmitted() && $form->isValid()) {
            $reclamationRepository->add($tblReclamation);
            return $this->redirectToRoute('app_reclamation_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('reclamation/new.html.twig', [
            'tbl_reclamation' => $tblReclamation,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/membre/reclamation/{idcomplaint}", name="app_reclamation_show", methods={"GET"})
     */
    public function show(TblReclamation $tblReclamation): Response
    {
        return $this->render('reclamation/show.html.twig', [
            'tbl_reclamation' => $tblReclamation,
        ]);
    }


    /**
     * @Route("/membre/reclamation/{idcomplaint}/edit", name="app_reclamation_edit", methods={"GET", "POST"})
     */
    public function edit(Request $request, TblReclamation $tblReclamation, ReclamationRepository $reclamationRepository): Response
    {
        $form = $this->createForm(TblReclamationType::class, $tblReclamation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $reclamationRepository->add($tblReclamation);
            return $this->redirectToRoute('app_reclamation_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('reclamation/edit.html.twig', [
            'tbl_reclamation' => $tblReclamation,
            'form' => $form->createView(),
        ]);
    }


    /**
     * @Route("/membre/reclamation/{idcomplaint}", name="app_reclamation_delete", methods={"POST"})
     */
    public function delete(Request $request, TblReclamation $tblReclamation, ReclamationRepository $reclamationRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$tblReclamation->getIdcomplaint(), $request->request->get('_token'))) {
            $reclamationRepository->remove($tblReclamation);
        }

        return $this->redirectToRoute('app_reclamation_index', [], Response::HTTP_SEE_OTHER);
    }

    }
