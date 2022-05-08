<?php

namespace App\Controller;

use App\Entity\TblParticipation;
use App\Entity\TblPaytype;
use App\Entity\TblPost;
use App\Entity\TblUser;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route ("/participation")
 */
class ParticipationEventController extends AbstractController
{
    /**
     * @Route("/", name="app_participation_event")
     */
    public function index(): Response
    {
        return $this->render('participation_event/index.html.twig', [
            'controller_name' => 'ParticipationEventController',
        ]);
    }

    /**
     * @Route("/{idpost}", name="app_participation_new" , methods={"GET"})
     */
    public function new(EntityManagerInterface $entityManager , TblPost $idpost): Response
    {

        $user = new TblUser() ;
        $paytype = new TblPaytype() ;

        $participation = new TblParticipation();
        $participation->setBookeddtm(new \DateTime()) ;
        $participation->setRank(0) ;
        $participation->setResult(0) ;
        $participation->setIdpost($idpost) ;
        $participation->setIdUser($user) ;
        $participation->setIdpaytype($paytype) ;

        $entityManager->persist($participation);
        $entityManager->flush();

        return $this->redirectToRoute('app_event_showFront', [], Response::HTTP_SEE_OTHER);
    }



    /**
     * @Route("participation/{idpost}", name="app_participation_show" ,methods={"GET"})
     */
    public function show(EntityManagerInterface $entityManager , TblPost $idpost): Response
    {
        $participationRepository = $entityManager->getRepository(TblParticipation::class) ;
        $participations = $participationRepository->findBy(['idpost' => $idpost]) ;

        return $this->render('backTemplate/participation/show.html.twig', [
            'participations' => $participations,
        ]);

    }





}
