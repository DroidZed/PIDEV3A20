<?php

namespace App\Controller;

use App\Entity\TblCommentaire;
use App\Entity\TblPublication;
use App\Form\CommentaireType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class CommentaireController extends AbstractController
{
    /**
     * @Route("/commentaire/{id}", name="display_Com")
     */
    public function index($id): Response
    {

        $com = $this->getDoctrine()->getManager()->getRepository(TblCommentaire::class)->findBy([
            'idpub'=> $id,
        ]);
        return $this->render('commentaire/index.html.twig', [
            'b'=>$com
        ]);
    }

    /**
     * @Route("/addCom/{id}", name="addCom")
     */
    public function addCom(Request $request, $id): Response
    {
        $com = new  TblCommentaire();
        $pub = $this->getDoctrine()->getManager()->getRepository(TblPublication::class)->find($id);

        $form = $this->createForm(CommentaireType::class, $com);

        $form->handleRequest($request);


        if($form->isSubmitted() &&  $form->isValid() )
        {
            $com->setIdpub($pub);
            $com->setDatecom(new \DateTime());
            $em = $this->getDoctrine()->getManager();
            $em->persist($com); //add
            $em->flush();

            return $this->redirectToRoute('display_Com', ['id'=>$id]);
        }

        return $this->render('commentaire/createCom.html.twig', ['f'=>$form->createView()]);
    }

    /**
     * @Route("/removeCom/{id}", name="supp_Com")
     */
    public function suppressionJeu(TblCommentaire $com, $id): Response
    {
        $em = $this->getDoctrine()->getManager();
        $em->remove($com);
        $em->flush();

        return $this->redirectToRoute('display_Com', ['id'=>$id]);

    }

    /**
     * @Route("/modifCom/{id}", name="modifCom")
     */
    public function modifCom(Request $request, $id): Response
    {
        $pub = $this->getDoctrine()->getManager()->getRepository(TblCommentaire::class)->find($id);

        $form = $this->createForm(CommentaireType::class, $pub);

        $form->handleRequest($request);

        if($form->isSubmitted() &&  $form->isValid() )
        {

            $em = $this->getDoctrine()->getManager();
            $em->flush();

            return $this->redirectToRoute('display_Vg');
        }

        return $this->render('commentaire/createCom.html.twig', ['f'=>$form->createView()]);

    }
}
