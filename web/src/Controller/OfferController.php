<?php

namespace App\Controller;

use App\Entity\TblCandidacy;
use App\Entity\TblDomain;
use App\Entity\TblOffer;
use App\Entity\TblUser;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

use App\Form\OfferType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;


/**
 * @Route("/offer")
 */

class OfferController extends AbstractController
{

    /**
     * @Route("/", name="display_Offer")
     * @param Request $request
     * @param PaginatorInterface $paginator
     * @return Response
     */
    public function index(Request $request, PaginatorInterface $paginator): Response
    {

        $user =$this->getDoctrine()->getManager()->getRepository(TblUser::class)->find(1);
        $donnees = $this->getDoctrine()->getManager()->getRepository(TblOffer::class)->findAll();
        $candidacys = $this->getDoctrine()->getManager()->getRepository(TblCandidacy::class)->findBy([
                'iduser' => $user,
            ]
        );

        $donnees = $paginator->paginate(
            $donnees,
            $request->query->getInt('page', 1),
            5
        );
        $test =false;
        return $this->render('offer/index.html.twig', [
            'Offers'=>$donnees,
            'candidacys'=>$candidacys,
            'test' => $test
        ]);
        /*$user =$this->getDoctrine()->getManager()->getRepository(TblUser::class)->find(1);
        $Offers = $this->getDoctrine()->getManager()->getRepository(TblOffer::class)->findAll();
        $candidacys = $this->getDoctrine()->getManager()->getRepository(TblCandidacy::class)->findBy([
            'iduser' => $user,
            ]
        );
        $test =false;
        return $this->render('offer/index.html.twig', [
            'Offers'=>$Offers,
            'candidacys'=>$candidacys,
            'test' => $test,

        ]);*/
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

            return $this->redirectToRoute('display_Offer_Admin');
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

        return $this->redirectToRoute('display_Offer_Admin');

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

            return $this->redirectToRoute('display_Offer_Admin');
        }

        return $this->render('offer/updateOffer.html.twig', ['f'=>$form->createView()]);

    }


    /**
     * @Route("/r/search_recc", name="search_recc", methods={"GET"})
     */
    public function search_rec(Request $request, NormalizerInterface $Normalizer): Response
    {

        $requestString = $request->get('searchValue');
        $requestString3 = $request->get('orderid');

        $em = $this->getDoctrine()->getManager();
        if ($requestString3 == 'DESC') {
            $query = $em->createQuery(
                'SELECT r FROM App\Entity\TblOffer r   where r.typeoffer like :suj OR r.regionoffer like :suj  order by r.salaryoffer DESC '
            );
            $query->setParameter('suj', $requestString . '%');
        } else {
            $query = $em->createQuery(
                'SELECT r FROM App\Entity\TblOffer r   where r.typeoffer like :suj OR r.regionoffer like :suj  order by r.salaryoffer ASC '
            );
            $query->setParameter('suj', $requestString . '%');
        }
        $offre =$query->getResult();

        $jsoncontentc = $Normalizer->normalize($offre, 'json', ['groups' => 'posts:read']);
        $jsonc = json_encode($jsoncontentc);
        if ($jsonc == "[]") {
            return new Response(null);
        } else {
            return new Response($jsonc);
        }
    }


    //*****MOBILE

    /**
     * @Route("/mobile/aff", name="affmoboffredom")
     */
    public function affmoboffredom(Request $request,NormalizerInterface $normalizer)
    {

        $dom=$this->getDoctrine()->getRepository(TblDomain::class)->find($request->get("id"));
        $med=$this->getDoctrine()->getRepository(TblOffer::class)->findBy([
            'iddomain' => $dom
        ]);
        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(1);
        $normalizer->setCircularReferenceHandler(function ($med) {
            return $med->getId();
        });
        $encoders = [new JsonEncoder()];
        $normalizers = array($normalizer);
        $serializer = new Serializer($normalizers,$encoders);
        $formatted = $serializer->normalize($med);
        return new JsonResponse($formatted);
    }



}
