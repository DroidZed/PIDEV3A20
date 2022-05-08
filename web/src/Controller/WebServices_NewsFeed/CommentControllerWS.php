<?php

namespace App\Controller\WebServices_NewsFeed;

use App\Entity\TblComment;
use App\Repository\PostRepository;
use App\Repository\UserRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route ("/wscomments")
 */


class CommentControllerWS
{

    /**
     * @Route ("/comment" , name ="addCommentJSON" ,  methods={"POST"})
     */

    public function addComment (Request $request , NormalizerInterface $normalizer , EntityManagerInterface $entityManager ,UserRepository  $userRepository ,PostRepository $postRepository) : JsonResponse {

        $data = json_decode($request->getContent() , true) ;
        $post = $postRepository->find($data['idPost']) ;
        $user = $userRepository->find($data['idUser']) ;

        $comment = new TblComment() ;
        $comment->setPosteddtm(new \DateTime("now")) ;
        $comment->setIduser($user) ;
        $comment->setIdpost($post) ;
        $comment->setComment($data['comment']) ;

        $entityManager->persist($comment);
        $entityManager->flush();

        return new JsonResponse( "Comment added successfully .".json_encode($data)) ;

    }

}