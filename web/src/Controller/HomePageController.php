<?php

namespace App\Controller;

use App\Entity\TblOption;
use App\Entity\TblPost;
use App\Entity\TblReaction;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route ;


class HomePageController extends AbstractController
{
    /**
     * @Route("/", name="app_homepage_index", methods={"GET","POST"})
     */
    public function index(EntityManagerInterface $entityManager): Response
    {

        $eventsGetter = $entityManager
            ->getRepository(TblPost::class)
            ->findBy(array('typepost'=>'Event', 'statuspost'=>1));



        $postsGetter = $entityManager
            ->getRepository(TblPost::class)
            ->findBy(array('typepost'=>'Post', 'statuspost'=>1));


        $quizzes = $entityManager
            ->getRepository(TblPost::class)
            ->findBy(array('typepost'=>'Quiz', 'statuspost'=>1));


        //Get Likes per Post
        foreach ($postsGetter as $post) {
            $likesPost ['post']= $post ;


            $nbLikesPost = count($entityManager
                ->getRepository(TblReaction::class)
                ->findBy(array('idpost' => $post->getIdPost())));

            $likesPost ['nbLikes'] = $nbLikesPost ;
            $likesPerUserPost = $entityManager
                ->getRepository(TblReaction::class)
                ->findOneBy(array('idpost' => $post->getIdPost() , 'iduser' => 2)) ;

            if ($likesPerUserPost != null) {
                $likesPost ['isLiked'] =  true;
            }else {
                $likesPost ['isLiked'] = false ;
            }


            $posts [] = $likesPost ;
        }

        //Get likes per Event
        foreach ($eventsGetter as $event) {
            $likesEvent ['event']= $event ;
            $nbLikesEvent = count($entityManager
                ->getRepository(TblReaction::class)
                ->findBy(array('idpost' => $event->getIdPost())));

            $likesEvent ['nbLikes'] = $nbLikesEvent ;
            $likesPerUserEvent = $entityManager
                ->getRepository(TblReaction::class)
                ->findOneBy(array('idpost' => $event->getIdPost() , 'iduser' => 2)) ;

            if ($likesPerUserEvent != null) {
                $likesEvent ['isLiked'] =  true;
            }else {
                $likesEvent ['isLiked'] = false ;
            }

            $events [] = $likesEvent ;
        }


        $rss = simplexml_load_file("https://esportsinsider.com/feed/");


        foreach ($quizzes as $quiz) {
            $quizzesAndOptions [] = $entityManager
                ->getRepository(TblOption::class)
                ->findBy(['idpost' => $quiz->getIdPost()]);
        }

        if ($rss == null) {
            return $this->render('frontTemplate/homePage.html.twig', [
                'events' => $events ,
                'quizzesOutput' => $quizzesAndOptions,
                'posts' => $posts ,
            ]);
        }

        return $this->render('frontTemplate/homePage.html.twig', [
            'events' => $events ,
            'quizzesOutput' => $quizzesAndOptions,
            'posts' => $posts ,
            'rss' => $rss->channel->item ,
        ]);
    }






}