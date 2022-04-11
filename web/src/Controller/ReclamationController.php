<?php

namespace App\Controller;

use App\Entity\TblReclamation;
use App\Entity\TblTypecomplaint;
use App\Entity\User;
use App\Form\ConsulterRecType;
use App\Form\TblReclamationType;
use App\Repository\ReclamationRepository;
use App\Repository\TypeComplaintRepository;
use App\Services\GetUser;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Routing\RouterInterface;

/**
 * @Route("/reclamation")
 */
class ReclamationController extends AbstractController
{
    /**
     * @var \Symfony\Component\Routing\RouterInterface
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
     * @Route("/reclamation", name="app_reclamation_index", methods={"GET"})
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
     * @Route("/new", name="passerRec", methods={"GET", "POST"})
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
     * @Route("/{idcomplaint}", name="app_reclamation_show", methods={"GET"})
     */
    public function show(TblReclamation $tblReclamation): Response
    {
        return $this->render('backTemplate/admin/reclamationShowOne.html.twig', [
            'tbl_reclamation' => $tblReclamation,
        ]);
    }
    /**
     * @Route("reclamation/consulter", name="consulter", methods={"GET"})
     */
    public function consulter(ReclamationRepository $reclamationRepository): Response
    {
        return $this->render('backTemplate/listReclamations.html.twig', [
            'tbl_reclamations' => $reclamationRepository->findAll(),
        ]);
    }

    /**
     * @Route("/{idcomplaint}/edit", name="app_reclamation_edit", methods={"GET", "POST"})
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
     * @Route("/{idcomplaint}/traiter", name="traiter", methods={"GET", "POST"})
     */
    public function traiter(Request $request, TblReclamation $tblReclamation, ReclamationRepository $reclamationRepository, TypeComplaintRepository $type): Response
    {
        $entityManager = $this->getDoctrine()->getManager();

        $form = $this->createForm(ConsulterRecType::class, $tblReclamation);
        $typ = new TblTypecomplaint();
        $typpe= $form->get('typecomplaint')->getData();
        $typ=$type->findOneBy(['idtype'=>$typpe]);
        $tblReclamation->setTypecomplaint($typ);

        $form->handleRequest($request);


        if ($form->isSubmitted() && $form->isValid()) {
            $typpe= $form->get('typecomplaint')->getData();
            $typ=$type->findBy(['nametype'=>$typpe]);

            $tblReclamation->setStatuscomplaint('TRAITEE');

            $reclamationRepository->add($tblReclamation);
            return $this->redirectToRoute('consulter', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('backTemplate/admin/consulter.html.twig', [
            'tbl_reclamation' => $tblReclamation,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idcomplaint}", name="app_reclamation_delete", methods={"POST"})
     */
    public function delete(Request $request, TblReclamation $tblReclamation, ReclamationRepository $reclamationRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$tblReclamation->getIdcomplaint(), $request->request->get('_token'))) {
            $reclamationRepository->remove($tblReclamation);
        }

        return $this->redirectToRoute('app_reclamation_index', [], Response::HTTP_SEE_OTHER);
    }
}
