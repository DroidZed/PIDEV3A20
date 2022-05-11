<?php

namespace App\Controller;

use App\Entity\TblComment;
use App\Entity\TblParticipation;
use App\Controller\CommentController ;
use App\Entity\TblPost;
use App\Entity\User;
use App\Form\CommentType;
use Doctrine\ORM\EntityManagerInterface;
use Doctrine\ORM\Mapping\Entity;
use http\Env\Request;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route ;


class EventFrontController extends AbstractController
{

    /**
     * @Route("/member/eventFront/{idpost}", name="app_event_showFront", methods={"POST" , "GET"})
     */
    public function show (TblPost $event , EntityManagerInterface $entityManager): Response
    {

        $repositoryPost = $entityManager->getRepository(TblParticipation::class) ;
        $repositoryUser = $entityManager->getRepository(User::class) ;
        $participations = $repositoryPost->findBy(array('idpost' => $event->getIdpost())) ;
        $nbparticipations = count($participations) ;

        $participants = [] ;
        foreach ($participations as $participation) {
            $user = $repositoryUser->find($participation->getIdUser()) ;
            $participants[] = $user ;
        }


        return $this->render('frontTemplate/eventFront/show.html.twig', [
            'nbparticipations' => $nbparticipations ,
            'event' => $event,
            'participants' => $participants ,

        ]);
    }





}