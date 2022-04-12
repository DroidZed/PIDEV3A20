<?php

namespace App\Controller;

use App\Entity\TblCandidacy;
use App\Entity\TblOffer;
use App\Entity\TblUser;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

use App\Form\OfferType;
use Symfony\Component\HttpFoundation\Request;


/**
 * @Route("/offer")
 */

class OfferController extends AbstractController
{

    /**
     * @Route("/", name="display_Offer")
     */
    public function index(): Response
    {
        $user =$this->getDoctrine()->getManager()->getRepository(TblUser::class)->find(1);
        $Offers = $this->getDoctrine()->getManager()->getRepository(TblOffer::class)->findAll();
        $candidacys = $this->getDoctrine()->getManager()->getRepository(TblCandidacy::class)->findBy([
            'iduser' => $user,
            ]
        );
        $test =false;
        return $this->render('offer/index.html.twig', [
            'Offers'=>$Offers,
            'candidacys'=>$candidacys,
            'test' => $test

        ]);
    }

    /**
     * @Route("/Admin", name="display_Offer_Admin")
     */
    public function indexAdmin(): Response
    {
        $Offer = $this->getDoctrine()->getManager()->getRepository(TblOffer::class)->findAll();
        return $this->render('offer/indexAdmin.html.twig', [
            'b'=>$Offer
        ]);
    }


    /**
     * @Route("/add", name="addOffer")
     */
    public function addOffer(Request $request): Response
    {
        $OFFER = new  TblOffer();

        $form = $this->createForm(OfferType::class, $OFFER);

        $form->handleRequest($request);

        if($form->isSubmitted() &&  $form->isValid() )
        {
            $OFFER->setIduser($this->getDoctrine()->getManager()->getRepository(TblUser::class)->find(1));
            $em = $this->getDoctrine()->getManager();
            $em->persist($OFFER); //add
            $em->flush();

            return $this->redirectToRoute('display_Offer');
        }

        return $this->render('offer/createOffer.html.twig', ['f'=>$form->createView()]);

    }

    /**
     * @Route("/remove/{id}", name="supp_Offer")
     */
    public function suppressionDomain(TblOffer $OFFER): Response
    {
        $em = $this->getDoctrine()->getManager();
        $em->remove($OFFER);
        $em->flush();

        return $this->redirectToRoute('display_Offer');

    }

    /**
     * @Route("/modif/{id}", name="modifOffer")
     */
    public function modifOffer(Request $request, $id): Response
    {
        $OFFER = $this->getDoctrine()->getManager()->getRepository(TblOffer::class)->find($id);

        $form = $this->createForm(OfferType::class, $OFFER);

        $form->handleRequest($request);

        if($form->isSubmitted() &&  $form->isValid() )
        {
            $em = $this->getDoctrine()->getManager();
            $em->flush();

            return $this->redirectToRoute('display_Offer');
        }

        return $this->render('offer/updateOffer.html.twig', ['f'=>$form->createView()]);

    }





}
