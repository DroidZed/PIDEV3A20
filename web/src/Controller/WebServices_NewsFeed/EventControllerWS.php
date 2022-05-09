<?php

namespace App\Controller\WebServices_NewsFeed;


use App\Entity\TblPost;
use App\Entity\TblUser;
use App\Repository\UserRepository;
use Doctrine\ORM\EntityManagerInterface;
use PHPUnit\Util\Json;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Validator\Constraints\Date;

/**
 * @Route ("/wsevents")
 */
class EventControllerWS extends AbstractController
{

    /**
     * @Route ("/getEvents" , name="AllEventsJSON" , methods={"GET"})
     */

    public function allEvents (NormalizerInterface $normalizer , EntityManagerInterface $entityManager , UserRepository $userRepository): JsonResponse
    {
        $postRepository = $entityManager->getRepository(TblPost::class) ;


        $events = $postRepository->findBy(['typepost' => 'Event']) ;

        $jsonContent = $normalizer->normalize($events , 'json' , ['groups' => 'post:read'] ) ;

        return new JsonResponse($jsonContent) ;
    }

    /**
     * @Route ("/getEvent/{idpost}" , name="getEventJSON" , methods={"GET"})
     */

    public function getEvent (Request $request , $idpost , NormalizerInterface $normalizer , EntityManagerInterface $entityManager) : JsonResponse {
        $postRepository = $entityManager->getRepository(TblPost::class) ;
        $events = $postRepository->find($idpost) ;



        $jsonContent = $normalizer->normalize($events , 'json' , ['groups' => 'post:read'] ) ;

        return new JsonResponse ($jsonContent) ;

    }

    /**
     * @Route ("/new" , name ="addEventJSON")
     */

    public function addEvent (Request $request , NormalizerInterface $normalizer , EntityManagerInterface $entityManager , UserRepository $userRepository) : JsonResponse {
        /*$event = new TblPost() ;
        $event->setTitlepost($request->get("titlepost")) ;
        $event->setDescpost($request->get("descpost")) ;
        //$event->setIduser() ;
        $event->setTypepost($request->get("typepost")) ;
        $event->setPosteddtm(new \DateTime('now'));
        $event->setStatuspost($request->get("statuspost")) ;

        $entityManager->persist($event);
        $entityManager->flush();

        $jsonContent = $normalizer->normalize($event , 'json' , ['groups' => 'post:read'] ) ;
        return new Response(json_encode($jsonContent)) ;*/

        // http://127.0.0.1:8000/wsevents/new?titlepost=testJSON&descpost=testJSON&iduser=2&typepost=Event&statuspost=1

        $data = json_decode($request->getContent() , true) ;
        $user = $userRepository->find($data['idUser']) ;

       $event = new TblPost() ;
       $event->setPosteddtm(new \DateTime("now")) ;
       $event->setTitlepost($data['titlePost']) ;
       $event->setDescpost($data['descPost']) ;
       $event->setIduser($user) ;
       $event->setTypepost($data['typePost']) ;
       $event->setStatuspost($data['statusPost']) ;

       $entityManager->persist($event);
       $entityManager->flush();

        return new JsonResponse( "Event added successfully .".json_encode($data)) ;

    }


    /**
     * @Route ("/deleteEvent/{idpost}" , name="deleteEventJSON")
     */

    public function deleteEvent (Request $request , NormalizerInterface $normalizer , EntityManagerInterface $entityManager , $idpost) :JsonResponse {
        $event = $entityManager->getRepository(TblPost::class)->find($idpost) ;
        $entityManager->remove($event);
        $entityManager->flush() ;

        $jsonContent = $normalizer->normalize($event , 'json' , ['groups' => 'post:read']) ;

        return new JsonResponse("Event deleted successfully .".json_encode($jsonContent)) ;
    }


    /**
     * @Route ("/updateEvent/{idpost}" , name="updateEventJSON")
     */

    public function updateEvent (Request $request , NormalizerInterface $normalizer , EntityManagerInterface $entityManager , $idpost) : JsonResponse {
        $data = json_decode($request->getContent() , true) ;

        $event = $entityManager->getRepository(TblPost::class)->find($idpost) ;
        $event->setTitlepost($data['titlePost']) ;
        $event->setDescpost($data['descPost']) ;
        $event->setTypepost($data['typePost']) ;
        $event->setStatuspost($data['statusPost']) ;

        $entityManager->flush() ;
        return new JsonResponse( "Event updated successfully .") ;
    }

}