<?php

namespace App\Controller;

use App\Entity\TblCategory;
use App\Form\CategoryType;
use Symfony\Component\HttpFoundation\Request;
use phpDocumentor\Reflection\Types\This;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
class CategoryController extends AbstractController
{


    /**
     * @Route("/", name="displayCategory")
     */
    public function index(): Response
    {
        $categories = $this->getDoctrine()->getManager()->getRepository(TblCategory::class)->findAll();
        return $this->render('category/index.html.twig', [
            'b'=>$categories
        ]);
    }

    /**
     * @Route("/addCategory", name="addCategory")
     */
    public function addCategory(Request $request): Response
    {
        $category = new TblCategory();
        $form =$this->createForm(CategoryType::class,$category);

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()){
            $em = $this->getDoctrine()->getManager();
            $em ->persist($category);//Hedhi nzidou
            $em ->flush();
            return $this->redirectToRoute( 'displayCategory');
        }
        return $this->render('category/createCategory.html.twig',[ 'f'=>$form->createView()]);
    }

    //Suppresion
    /**
     * @Route("/deleteCategory/{id}", name="deleteCategory")
     */
    public function deleteCategory(TblCategory  $category): Response
    {
      $em = $this->getDoctrine()->getManager();
      $em->remove($category);
      $em->flush();
      return  $this->redirectToRoute('displayCategory');
    }

    /**
     * @Route("/editCategory/{id}", name="editCategory")
     */
    public function editCategory(Request $request, $id): Response
    {

        $category = $this->getDoctrine()->getManager()->getRepository(TblCategory::class)->find($id);
        $form = $this->createForm(CategoryType::class,$category);

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()){
            $em = $this->getDoctrine()->getManager();
            $em ->flush();
            return $this->redirectToRoute( 'displayCategory');
        }
        return $this->render('category/updateCategory.html.twig',[ 'f'=>$form->createView()]);
    }
}
