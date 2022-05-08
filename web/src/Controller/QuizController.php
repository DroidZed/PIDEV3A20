<?php

namespace App\Controller;

use App\Entity\TblOption;
use App\Form\QuizType;
use App\Form\TblPostType;
use App\Repository\OptionRepository;
use Symfony\Component\HttpFoundation\RequestStack;
use Symfony\Component\Routing\Annotation\Route ;
use App\Entity\TblPost;
use App\Form\EventType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;

/**
 * @Route("/quiz")
 */
class QuizController extends AbstractController
{

    /**
     * @Route("/", name="app_quiz_index", methods={"GET"})
     */
    public function index(EntityManagerInterface $entityManager): Response
    {

        $quizes = $entityManager
            ->getRepository(TblPost::class)
            ->findBytypepost('Quiz');

        foreach ($quizes as $quiz) {
            if ($quiz->getCorrectanswer() == null ) {
                $quiz->setStatuspost(0) ;
            }
        }
        return $this->render('backTemplate/quiz/index.html.twig', [
            'quizes' => $quizes,
        ]);
    }

    /**
     * @Route("/new", name="app_quiz_new", methods={"GET", "POST"})
     */
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $quiz = new TblPost();
        $form = $this->createForm(TblPostType::class, $quiz);
        $form->handleRequest($request);



        if ($form->isSubmitted() && $form->isValid()) {

            $file = $request->files->get('tbl_post')['photopost'];
            $uploads_directory = $this->getParameter('uploads_directory') ;

            $filename = null ;
            if ($file != null) {
                $filename = md5(uniqid()). '.' . $file->guessExtension() ;
                $file->move(
                    $uploads_directory ,
                    $filename
                );
            }
            $quiz->setTypepost('Quiz') ;
            $quiz->setPhotopost($filename) ;
            $quiz->setPosteddtm(new \DateTime('now')) ;


            $entityManager->persist($quiz);
            $entityManager->flush();
            $this->addFlash('success' , 'Quiz added with success !');

            $id = $quiz->getIdpost();
            return $this->redirectToRoute('app_option_new', array('idpost' => $id) ,Response::HTTP_SEE_OTHER );
        }
        return $this->render('backTemplate/quiz/new.html.twig', [
            'quiz' => $quiz,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idpost}", name="app_quiz_show", methods={"GET"})
     */
    public function show(TblPost $quiz): Response
    {
        return $this->render('backTemplate/quiz/show.html.twig', [
            'quiz' => $quiz,
        ]);
    }




    /**
     * @Route("/{idpost}/edit", name="app_quiz_edit", methods={"GET", "POST"})
     */
    public function edit(Request $request, TblPost $quiz , EntityManagerInterface $entityManager): Response
    {

        $form = $this->createForm(QuizType::class, $quiz);
        $form->handleRequest($request);
        $photoQuiz = $quiz->getPhotopost() ;


        if ($form->isSubmitted() && $form->isValid()) {

            $file = $request->files->get('quiz')['photopost'];


            if ($file == null) {
                $quiz->setPhotopost($photoQuiz) ;
            }
            else {
                $uploads_directory = $this->getParameter('uploads_directory') ;

                $filename = md5(uniqid()). '.' . $file->guessExtension() ;
                $file->move(
                    $uploads_directory ,
                    $filename
                );

                $quiz->setPhotopost($filename) ;
            }



            $entityManager->flush();

            $this->addFlash('success' , 'Quiz updated with success !');

            return $this->redirectToRoute('app_quiz_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('backTemplate/quiz/edit.html.twig', [
            'quiz' => $quiz,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idpost}", name="app_quiz_delete", methods={"POST"})
     */
    public function delete(Request $request, TblPost $quiz, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$quiz->getIdpost(), $request->request->get('_token'))) {
            $entityManager->remove($quiz);
            $entityManager->flush();
        }

        $this->addFlash('success' , 'Quiz deleted with success !');
        return $this->redirectToRoute('app_quiz_index', [], Response::HTTP_SEE_OTHER);
    }
}
