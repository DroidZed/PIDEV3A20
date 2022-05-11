<?php

namespace App\Controller;

use App\Entity\TblComment;
use App\Entity\TblPost;
use App\Entity\User;
use App\Form\CommentType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route ;


/**
 * @Route ("/comment")
 */
class CommentController extends AbstractController
{

    /**
     * @param Request $request
     * @param EntityManagerInterface $entityManager
     * @param TblPost $event
     * @return Response
     * @Route ("/{idpost}" , name="app_comment_new" , methods={"POST" , "GET"})
     */

    public function new(Request $request ,EntityManagerInterface $entityManager , TblPost $event): Response
    {

        $repositoryUser = $entityManager->getRepository(User::class) ;

        //Comment part
        $comments = $entityManager
            ->getRepository(TblComment::class)
            ->findBy(array('idpost' => $event->getIdpost()));

        $commenters = null ;
        foreach ($comments as $comment ) {
            $user = $repositoryUser->find($comment->getIdUser()) ;
            $commenters[] = $user ;
        }


        $tblComment = new TblComment();
        $form = $this->createForm(CommentType::class, $tblComment);

        if ($form->isSubmitted() && $form->isValid()) {
            $tblComment->setPosteddtm()(new \DateTime()) ;
            $tblComment->setIdpost($event) ;
            $tblComment->setIduser(1) ;
            $tblComment->setComment("ahhahahha") ;

            $entityManager->persist($tblComment);
            $entityManager->flush();
        }

        return $this->render('frontTemplate/eventFront/comment/new.html.twig', [
            'comments' => $comments ,
            'commenters' => $commenters ,
            'form' => $form->createView(),
        ]);

    }



}