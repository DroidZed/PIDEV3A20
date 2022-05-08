<?php

namespace App\Controller;

use App\Entity\TblAnswerpost;
use App\Entity\TblOption;
use App\Entity\TblPost;
use App\Entity\TblUser;
use App\Form\AnswerQuizType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/answer")
 */


class AnswerQuizController extends AbstractController
{


    /**
     * @Route("/", name="app_answer_index")
     */
    public function index()
    {

    }

    /**
     * @Route("/showAnswers/{idpost}", name="app_answer_showAnswers", methods={"GET"})
     */
    public function showQuizAnswersStats (int $idpost ,  EntityManagerInterface $entityManager): Response
    {

        $availableAnswers = null ;
        $countAnswersPerOption = null ;
        $answersList = null ;


        $answersRepository = $entityManager->getRepository(TblAnswerpost::class) ;
        $postRepository = $entityManager->getRepository(TblPost::class) ;


        // Data
        $availableAnswers = $answersRepository->getAnswersPerPost($idpost) ;
        $answers = $answersRepository->getAllAnswersPerPost($idpost) ;
        $answersList = $answersRepository->findBy(['idpost' => $idpost]) ;

        $countAnswersPerOption = null ;

        foreach ($availableAnswers as $avAnswer) {
            $countAnswersPerOption []  = $answersRepository->getCountAnswersPerOption($idpost,$avAnswer["answer"]) ;
        }


        $countAnswers = count($answers) ;

        $correctAnswer  = $postRepository->findOneBy(['idpost' => $idpost])->getCorrectanswer() ;

        if ($availableAnswers == null && $answers == null && $countAnswersPerOption == null) {
            return $this->render('backTemplate/answer_quiz/show.html.twig', [
                'availableAnswers' => $availableAnswers ,
                'countAnswersPerOption' => $countAnswersPerOption ,
                'answersList' => $answersList ,
                'answers' => $answers ,
                'correctAnswer' => $correctAnswer ,
                'countAnswers' => $countAnswers ,
            ]);
        }

        return $this->render('backTemplate/answer_quiz/show.html.twig', [
            'availableAnswers' => json_encode($this->arrayFlatten($availableAnswers)),
            'correctAnswer' => $correctAnswer ,
            'countAnswers' => $countAnswers ,
            'answers' => json_encode($this->arrayFlatten($answers)) ,
            'countAnswersPerOption' => json_encode($this->arrayFlatten($countAnswersPerOption)),
            'answersList' => $answersList ,
        ]);
    }



    function arrayFlatten(array $array) {
        $flatten = array();
        array_walk_recursive($array, function($value) use(&$flatten) {
            $flatten[] = $value;
        });

        return $flatten;
    }

    /**
     * @Route("/addAnswer/{idpost}" , name="app_answer_new", methods={"POST"})
     */

    public function new (Request $request ,EntityManagerInterface $entityManager , TblPost $quiz) {
        $data = json_decode($request->getContent(), true);
        $answer = $data["answer"];
        $newAnswer = new TblAnswerpost();
        $newAnswer->setAnswereddtm(new \DateTime()) ;
        $newAnswer->setIdpost($quiz) ;
        $newAnswer->setAnswer($answer);
        $entityManager->persist($newAnswer);
        $entityManager->flush();
        return new JsonResponse(['status' => 'ok']);
    }



}
