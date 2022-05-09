<?php

namespace App\Controller;

use App\Entity\TblPost;
use App\Form\TblPostType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/post")
 */
class PostController extends AbstractController
{
    /**
     * @Route("/", name="app_post_index", methods={"GET"})
     */
    public function index(EntityManagerInterface $entityManager): Response
    {
        $tblPosts = $entityManager
            ->getRepository(TblPost::class)
            ->findBytypepost('Post');

        return $this->render('backTemplate/post/index.html.twig', [
            'tbl_posts' => $tblPosts,
        ]);
    }

    /**
     * @Route("/new", name="app_post_new", methods={"GET", "POST"})
     */
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $tblPost = new TblPost();
        $form = $this->createForm(TblPostType::class, $tblPost);
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


            $tblPost->setTypepost('Post') ;
            $tblPost->setPhotopost($filename) ;
            $tblPost->setPosteddtm(new \DateTime('now')) ;

            $entityManager->persist($tblPost);
            $entityManager->flush();

            $this->addFlash('success' , 'Post added with success !');

            return $this->redirectToRoute('app_post_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('backTemplate/post/new.html.twig', [
            'tbl_post' => $tblPost,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idpost}", name="app_post_show", methods={"GET"})
     */
    public function show(TblPost $tblPost): Response
    {
        return $this->render('backTemplate/post/show.html.twig', [
            'tbl_post' => $tblPost,
        ]);
    }

    /**
     * @Route("/{idpost}/edit", name="app_post_edit", methods={"GET", "POST"})
     */
    public function edit(Request $request, TblPost $tblPost, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(TblPostType::class, $tblPost);
        $form->handleRequest($request);
        $photoPost = $tblPost->getPhotopost() ;

        if ($form->isSubmitted() && $form->isValid()) {

            $file = $request->files->get('tbl_post')['photopost'];

            if ($file == null) {
                $tblPost->setPhotopost($photoPost) ;
            }
            else {
                $uploads_directory = $this->getParameter('uploads_directory') ;

                $filename = md5(uniqid()). '.' . $file->guessExtension() ;
                $file->move(
                    $uploads_directory ,
                    $filename
                );

                $tblPost->setPhotopost($filename) ;
            }

            $entityManager->flush();
            $this->addFlash('success' , 'Post updated with success !');

            return $this->redirectToRoute('app_post_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('backTemplate/post/edit.html.twig', [
            'tbl_post' => $tblPost,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idpost}", name="app_post_delete", methods={"POST"})
     */
    public function delete(Request $request, TblPost $tblPost, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$tblPost->getIdpost(), $request->request->get('_token'))) {
            $entityManager->remove($tblPost);
            $entityManager->flush();
            $this->addFlash('success' , 'Post deleted with success !');
        }

        return $this->redirectToRoute('app_post_index', [], Response::HTTP_SEE_OTHER);
    }
}
