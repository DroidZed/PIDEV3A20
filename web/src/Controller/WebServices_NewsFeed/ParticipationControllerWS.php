<?php

namespace App\Controller\WebServices_NewsFeed;

use App\Entity\TblParticipation;
use App\Entity\TblPost;
use App\Repository\PayTypeRepository;
use App\Repository\PostRepository;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\UserRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;



/**
 * @Route ("/wsparticipations")
 */

class ParticipationControllerWS
{


    /**
     * @Route ("/getParticipations" , name="AllParticipationsJSON" , methods={"GET"})
     */

    public function allParticipations (NormalizerInterface $normalizer , EntityManagerInterface $entityManager): JsonResponse
    {
        $participationRepository = $entityManager->getRepository(TblParticipation::class) ;


        $participations = $participationRepository->findAll() ;

        $jsonContent = $normalizer->normalize($participations , 'json' , ['groups' => 'participation:read'] ) ;

        return new JsonResponse($jsonContent) ;
    }


    /**
     * @Route ("/participate" , name ="addEventJSON" ,  methods={"POST"})
     */

    public function addParticipation (Request $request , NormalizerInterface $normalizer , EntityManagerInterface $entityManager , UserRepository $userRepository , PostRepository $postRepository , PayTypeRepository $payTypeRepository) : JsonResponse {

        $data = json_decode($request->getContent() , true) ;
        $user = $userRepository->find($data['idUser']) ;
        $post = $postRepository->find($data['idPost']) ;
        $payType = $payTypeRepository->find($data['idPayType']) ;

        $participation = new TblParticipation() ;
        $participation->setBookeddtm(new \DateTime("now")) ;
        $participation->setIduser($user) ;
        $participation->setIdpost($post) ;
        $participation->setRank(0) ;
        $participation->setResult(0) ;
        $participation->setIdpaytype($payType) ;

        $entityManager->persist($participation);
        $entityManager->flush();

        return new JsonResponse( "Participation added successfully .".json_encode($data)) ;

    }

}