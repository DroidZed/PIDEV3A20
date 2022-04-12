<?php

namespace App\Controller;

use App\Entity\TblVideogame;
use App\Form\JeuVideoType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\Request;

class JeuVideoController extends AbstractController
{
    /**
     * @Route("/Jeu", name="display_Vg")
     */
    public function index(): Response
    {

        $jeux = $this->getDoctrine()->getManager()->getRepository(TblVideogame::class)->findAll();
        return $this->render('jeu_video/index.html.twig', [
            'b'=>$jeux
        ]);
    }

    /**
     * @Route("/", name="display_Accueil")
     */
    public function indexFront(): Response
    {
        return $this->render('indexFront.html');
    }

    /**
     * @Route("/addJeu", name="addJeu")
     */
    public function addJeu(Request $request): Response
    {
        $JV = new  TblVideogame();

        $form = $this->createForm(JeuVideoType::class, $JV);

        $form->handleRequest($request);

        if($form->isSubmitted() &&  $form->isValid() )
        {
            $file = $form->get('imagevg')->getData();
            $fileName = md5(uniqid()).'.'.$file->guessExtension();
            try {
                $file->move(
                    $this->getParameter('images_directory'),
                    $fileName
                );
            } catch (FileException $e){

            }
            $em = $this->getDoctrine()->getManager();
            $JV->setImagevg($fileName);
            $em->persist($JV); //add
            $em->flush();

            return $this->redirectToRoute('display_Vg');
        }

        return $this->render('jeu_video/createJV.html.twig', ['f'=>$form->createView()]);

    }

    /**
     * @Route("/removeJeu/{id}", name="supp_Vg")
     */
    public function suppressionJeu(TblVideogame $JV): Response
    {
        $em = $this->getDoctrine()->getManager();
        $em->remove($JV);
        $em->flush();

        return $this->redirectToRoute('display_Vg');

    }

    /**
     * @Route("/modifJeu/{id}", name="modifJeu")
     */
    public function modifJeu(Request $request, $id): Response
    {
        $JV = $this->getDoctrine()->getManager()->getRepository(TblVideogame::class)->find($id);

        $form = $this->createForm(JeuVideoType::class, $JV);

        $form->handleRequest($request);

        if($form->isSubmitted() &&  $form->isValid() )
        {
            $file = $form->get('imagevg')->getData();
            $fileName = md5(uniqid()).'.'.$file->guessExtension();
            try {
                $file->move(
                    $this->getParameter('images_directory'),
                    $fileName
                );
            } catch (FileException $e){

            }
            $em = $this->getDoctrine()->getManager();
            $JV->setImagevg($fileName);
            $em->flush();

            return $this->redirectToRoute('display_Vg');
        }

        return $this->render('jeu_video/updateJV.html.twig', ['f'=>$form->createView()]);

    }



}
