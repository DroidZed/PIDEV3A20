<?php

namespace App\Controller;

use App\Entity\TblDomain;
use App\Form\DomainType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;

class DomainController extends AbstractController
{
    /**
     * @Route("/", name="display_Domain")
     */
    public function index(): Response
    {
        $Domain = $this->getDoctrine()->getManager()->getRepository(TblDomain::class)->findAll();
        return $this->render('domain/index.html.twig', [
            'b'=>$Domain
        ]);
    }


    /**
     * @Route("/addDomain", name="addDomain")
     */
    public function addDomain(Request $request): Response
    {
        $Domain = new  TblDomain();

        $form = $this->createForm(DomainType::class, $Domain);

        $form->handleRequest($request);

        if($form->isSubmitted() &&  $form->isValid() )
        {

            $em = $this->getDoctrine()->getManager();
            $em->persist($Domain); //add
            $em->flush();

            return $this->redirectToRoute('display_Domain');
        }

        return $this->render('domain/createDomain.html.twig', ['f'=>$form->createView()]);

    }

    /**
     * @Route("/removeDomain/{id}", name="supp_Domain")
     */
    public function suppressionDomain(TblDomain $Domain): Response
    {
        $em = $this->getDoctrine()->getManager();
        $em->remove($Domain);
        $em->flush();

        return $this->redirectToRoute('display_Domain');

    }

    /**
     * @Route("/modifDomain/{id}", name="modifDomain")
     */
    public function modifDomain(Request $request, $id): Response
    {
        $Domain = $this->getDoctrine()->getManager()->getRepository(TblDomain::class)->find($id);

        $form = $this->createForm(DomainType::class, $Domain);

        $form->handleRequest($request);

        if($form->isSubmitted() &&  $form->isValid() )
        {
            $em = $this->getDoctrine()->getManager();
            $em->flush();

            return $this->redirectToRoute('display_Domain');
        }

        return $this->render('domain/updateDomain.html.twig', ['f'=>$form->createView()]);

    }


}
