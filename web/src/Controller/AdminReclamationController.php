<?php

namespace App\Controller;

use App\Entity\TblReclamation;
use App\Entity\TblTypecomplaint;
use App\Form\ConsulterRecType;
use App\Repository\ReclamationRepository;
use App\Repository\TypeComplaintRepository;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/admin")
 */
class AdminReclamationController extends AbstractController
{
    /**
     * @Route("/consulter", name="consulter", methods={"GET"})
     */
    public function consulter(ReclamationRepository $reclamationRepository,Request $request,PaginatorInterface $paginator): Response
    {
        $donnes=$reclamationRepository->findAll();
        $rec=$paginator->paginate(
            $donnes,
            $request->query->getInt('page',1),4

        );
        return $this->render('backTemplate/listReclamations.html.twig', [
            'tbl_reclamations' => $rec,
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
}
