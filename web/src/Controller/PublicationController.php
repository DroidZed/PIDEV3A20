<?php

namespace App\Controller;

use App\Entity\TblPublication;
use App\Entity\TblVideogame;
use App\Form\JeuVideoType;
use App\Form\PublicationType;
use Doctrine\Persistence\ObjectManager;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\Request;
use DateTimeInterface;
use Datetime;
use App\Repository\TblPublicationRepository;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;


class PublicationController extends AbstractController
{
    /**
     * @Route("/publication/{id}", name="display_Pub")
     */
    public function index($id): Response
    {
        $publications = $this->getDoctrine()->getManager()->getRepository(TblPublication::class)->findBy([
            'idgv'=> $id,
        ]);
        return $this->render('publication/index.html.twig', [
            'b'=>$publications
        ]);
    }

    /**
     * @Route("/addPub/{id}", name="addPub")
     */
    public function addPub(Request $request, $id): Response
    {
        $pub = new  TblPublication();
        $Vg = $this->getDoctrine()->getManager()->getRepository(TblVideogame::class)->find($id);
        $form = $this->createForm(PublicationType::class, $pub);
        $form->handleRequest($request);


        if($form->isSubmitted() &&  $form->isValid() )
        {
            $pub->setIdgv($Vg);
            $pub->setDatepub(new \DateTime());
            $em = $this->getDoctrine()->getManager();
            $em->persist($pub); //add
            $em->flush();

            return $this->redirectToRoute('display_Pub', ['id'=>$id]);
        }

        return $this->render('publication/createPub.html.twig', ['f'=>$form->createView()]);
    }

    /**
     * @Route("/removePub/{id}", name="supp_Pub")
     */
    public function suppressionPub(TblPublication $pub, $id): Response
    {
        $em = $this->getDoctrine()->getManager();
        $em->remove($pub);
        $em->flush();

        return $this->redirectToRoute('display_Vg');

    }

    /**
     * @Route("/modifPub/{id}", name="modifPub")
     */
    public function modifPub(Request $request, $id): Response
    {
        $JV = $this->getDoctrine()->getManager()->getRepository(TblPublication::class)->find($id);

        $form = $this->createForm(PublicationType::class, $JV);

        $form->handleRequest($request);

        if($form->isSubmitted() &&  $form->isValid() )
        {

            $em = $this->getDoctrine()->getManager();
            $em->flush();

            return $this->redirectToRoute('display_Vg');
        }

        return $this->render('publication/createPub.html.twig', ['f'=>$form->createView()]);

    }

    /**
     * @Route("/publication/{id}/croi", name="croi")
     * @return Response
     */

    public function orderLikesDesc($id): Response
    {
        $order = 1;
        $pub = $this->getDoctrine()->getRepository(TblPublication::class)->triLikesASC($id);

        return $this->render('publication/index.html.twig', [
            'b'=>$pub,
            'order'=> $order
        ]);
    }







}
