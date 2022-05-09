<?php

namespace App\Controller;

use App\Entity\TblOption;
use App\Entity\TblPost;
use App\Form\TblOptionType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/option")
 */
class OptionController extends AbstractController
{
    /**
     * @Route("/", name="app_option_index", methods={"GET"})
     */
    public function index(EntityManagerInterface $entityManager): Response
    {
        $tblOptions = $entityManager
            ->getRepository(TblOption::class)
            ->findAll();

        return $this->render('backTemplate/option/index.html.twig', [
            'tbl_options' => $tblOptions,
        ]);
    }

    /**
     * @Route("/new/{idpost}", name="app_option_new", methods={"GET", "POST"})
     */
    public function new(Request $request, EntityManagerInterface $entityManager , int $idpost): Response
    {
        $tblOption = new TblOption();
        $post = $this->getDoctrine()->getRepository(TblPost::class)->find($idpost) ;

        $form = $this->createForm(TblOptionType::class, $tblOption);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $tblOption->setCreateddtm(new \DateTime()) ;
            $tblOption->setIdpost($post) ;

            $entityManager->persist($tblOption);
            $entityManager->flush();

            return $this->redirectToRoute('app_quiz_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('backTemplate/option/new.html.twig', [
            'tbl_option' => $tblOption,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idoption}", name="app_option_show", methods={"GET"})
     */
    public function show(TblOption $tblOption): Response
    {
        return $this->render('backTemplate/option/show.html.twig', [
            'tbl_option' => $tblOption,
        ]);
    }


    /**
     * @Route("/showOptions/{idpost}", name="app_option_showOptions", methods={"GET"})
     */
    public function showQuizOptions (int $idpost ,  EntityManagerInterface $entityManager): Response
    {

        $optionRepository = $entityManager->getRepository(TblOption::class) ;
        $options = $optionRepository->findBy(['idpost' => $idpost]) ;

        return $this->render('backTemplate/option/show.html.twig', [
            'options' => $options,
        ]);
    }


    public function getOptionsPerPost (int $idpost , EntityManagerInterface $entityManager) : Response
    {

        $optionRepository = $entityManager->getRepository(TblOption::class) ;
        $options = $optionRepository->findBy(['idpost' => $idpost]) ;


        return $options ;
    }



    /**
     * @Route("/{idoption}/edit", name="app_option_edit", methods={"GET", "POST"})
     */
    public function edit(Request $request, TblOption $tblOption, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(TblOptionType::class, $tblOption);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_quiz_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('backTemplate/option/edit.html.twig', [
            'tbl_option' => $tblOption,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idoption}", name="app_option_delete", methods={"POST"})
     */
    public function delete(Request $request, TblOption $tblOption, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$tblOption->getIdoption(), $request->request->get('_token'))) {
            $entityManager->remove($tblOption);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_quiz_index', [], Response::HTTP_SEE_OTHER);
    }
}
