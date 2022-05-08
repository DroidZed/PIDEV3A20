<?php

namespace App\Controller;

use App\Entity\TblPost;
use App\Entity\TblReaction;
use Doctrine\ORM\EntityManagerInterface;
use phpDocumentor\Reflection\Types\Boolean;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route ("/reaction")
 */

class ReactionController extends AbstractController
{
    /**
     * @Route("/", name="app_reaction")
     */
    public function index(): Response
    {
        return $this->render('reaction/index.html.twig', [
            'controller_name' => 'ReactionController',
        ]);
    }

    /**
     * @Route("/like/{idpost}", name="app_like" , methods={"GET"})
     */
    public function like(TblPost $post , EntityManagerInterface $entityManager): Response
        {

            ////////////////
            $eventsGetter = $entityManager
                ->getRepository(TblPost::class)
                ->findBy(array('typepost'=>'Event', 'statuspost'=>1));

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


            ////////////////
            ///
            ///
            $event = null ;
            foreach ($events as $eventElem) {
                if ($eventElem['event']->getIdPost() == 14) {
                    $event = $eventElem ;
                }
            }

            if ($event['isLiked'] == true) {
                $like = $entityManager->getRepository(TblReaction::class)->findOneBy(
                    [
                        'idpost' => $event['event']->getIdPost() ,
                        'iduser' => 2 ,
                    ]) ;

            $entityManager->remove($like);
            $entityManager->flush () ;

            return $this->json([
                'code' => 200 ,
                'message' => 'Like bien supprimé' ,
                'likes' => $entityManager->getRepository(TblReaction::class)->count(
                    ['idpost' => $event['event']->getIdPost()
                    ]) ], 200) ;
            }

            //if it's not liked then create like

            $like = new TblReaction() ;
            $like->setReacteddtm(new \DateTime('now')) ;
            $like->setIdpost($event['event']) ;
            //$like->setIduser(2) ;
            //$like->setIdtypereact(1) ;

            $entityManager->persist($like);
            $entityManager->flush();

            return $this->json([
                'code' => 200 ,
                'message' => 'Like bien ajouté' ,
                'likes' => $entityManager->getRepository(TblReaction::class)->count(
                    ['idpost' => $event['event']->getIdPost()
                    ]) ], 200) ;


            return $this->json(['code' => 200 , 'message' => 'Ca marche bien'] , 200) ;
        }


}
