<?php

namespace App\Controller;

use App\Entity\TblCategory;
use App\Form\CategoryType;
use App\Repository\CategoryRepository;
use Symfony\Component\HttpFoundation\Request;
use phpDocumentor\Reflection\Types\This;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
class CategoryController extends AbstractController
{

    /**
     * @Route("/admin/category", name="displayCategory", methods={"GET"})
     * @param Request|null $req
     * @param CategoryRepository $categoryRepository
     * @return Response
     */
    public function index(?Request $req, CategoryRepository $categoryRepository): Response
    {

        $page = $req->get("page") ? $req->get("page") : 1;

        $categories = $categoryRepository->getCategoriesPaginated($page);

        $maxPages = ceil($categories->count() / 5);

        return $this->render('backTemplate/category/index.html.twig', [
            'b' => $categories,
            'maxPages' => $maxPages,
            'currPage' => $page
        ]);
    }


    /**
     * @Route("/admin/addCategory", name="addCategory")
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
        return $this->render('backTemplate/category/createCategory.html.twig',[ 'f'=>$form->createView()]);
    }

    //Suppresion
    /**
     * @Route("/admin/deleteCategory/{id}", name="deleteCategory")
     */
    public function deleteCategory(TblCategory  $category): Response
    {
      $em = $this->getDoctrine()->getManager();
      $em->remove($category);
      $em->flush();
      return  $this->redirectToRoute('displayCategory');
    }

    /**
     * @Route("/admin/editCategory/{id}", name="editCategory")
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
        return $this->render('backTemplate/category/updateCategory.html.twig',[ 'f'=>$form->createView()]);
    }
}
