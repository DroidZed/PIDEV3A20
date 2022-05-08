<?php

namespace App\Controller;

use App\Entity\TblBadge;
use App\Form\TblBadgeType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @Route("/badge")
 */
class BadgeController extends AbstractController
{
    /**
     * @Route("/", name="app_badge_index", methods={"GET"})
     */
    public function index(EntityManagerInterface $entityManager): Response
    {
        $tblBadges = $entityManager
            ->getRepository(TblBadge::class)
            ->findAll();

        return $this->render('backTemplate/badge/index.html.twig', [
            'tbl_badges' => $tblBadges,
        ]);
    }

    /**
     * @Route("/new", name="app_badge_new", methods={"GET", "POST"})
     */
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $tblBadge = new TblBadge();
        $form = $this->createForm(TblBadgeType::class, $tblBadge);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $file = $request->files->get('tbl_badge')['photobadge'];



            $uploads_directory = $this->getParameter('uploads_directory') ;

            $filename = null ;
            if ($file != null) {
                $filename = md5(uniqid()). '.' . $file->guessExtension() ;
                $file->move(
                    $uploads_directory ,
                    $filename
                );
            }

            $tblBadge->setPhotobadge($filename) ;

            $entityManager->persist($tblBadge);
            $entityManager->flush();

            $this->addFlash('success' , 'Badge added with success !');
            return $this->redirectToRoute('app_badge_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('backTemplate/badge/new.html.twig', [
            'tbl_badge' => $tblBadge,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idbadge}", name="app_badge_show", methods={"GET"})
     */
    public function show(TblBadge $tblBadge): Response
    {
        return $this->render('backTemplate/badge/show.html.twig', [
            'tbl_badge' => $tblBadge,
        ]);
    }

    /**
     * @Route("/{idbadge}/edit", name="app_badge_edit", methods={"GET", "POST"})
     */
    public function edit(Request $request, TblBadge $tblBadge, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(TblBadgeType::class, $tblBadge);
        $form->handleRequest($request);
        $photoBadge = $tblBadge->getPhotobadge() ;



        if ($form->isSubmitted() && $form->isValid()) {
            $tblBadge->setPhotobadge($photoBadge) ;$tblBadge->setPhotobadge($photoBadge) ;

                $file = $request->files->get('tbl_badge')['photobadge'];
                $uploads_directory = $this->getParameter('uploads_directory') ;

                $filename = null ;
                if ($file != null) {
                    $filename = md5(uniqid()). '.' . $file->guessExtension() ;
                    $file->move(
                        $uploads_directory ,
                        $filename
                    );
                }
                $tblBadge->setPhotobadge($filename) ;


            $entityManager->flush();

            $this->addFlash('success' , 'Badge edited with success !');
            return $this->redirectToRoute('app_badge_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('backTemplate/badge/edit.html.twig', [
            'tbl_badge' => $tblBadge,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idbadge}", name="app_badge_delete", methods={"POST"})
     */
    public function delete(Request $request, TblBadge $tblBadge, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$tblBadge->getIdbadge(), $request->request->get('_token'))) {
            $entityManager->remove($tblBadge);
            $entityManager->flush();
        }
        $this->addFlash('success' , 'Badge deleted with success !');
        return $this->redirectToRoute('app_badge_index', [], Response::HTTP_SEE_OTHER);
    }
}
