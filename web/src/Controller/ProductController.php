<?php

namespace App\Controller;

use App\Entity\TblCategory;
use App\Entity\TblProduct;
use App\Form\CategoryType;
use App\Form\ProductType;
use App\Repository\CategoryRepository;
use App\Repository\ProductRepository;
use App\Repository\UserRepository;
use App\services\MailerService;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class ProductController extends AbstractController
{
    /**
     * @Route("/product", name="displayProduct")
     */
    public function displayProduct(): Response
    {
        $products = $this->getDoctrine()->getManager()->getRepository(TblProduct::class)->findAll();
        return $this->render('products/index.html.twig', [
            'b'=>$products
        ]);
    }

    /**
     * @Route("/productDetail/{idproduct}" , name="productDetail" , methods={"GET"})
     */
    public function productDetail(int $idproduct, ProductRepository $productRepository): Response
    {
        $product = $productRepository->find($idproduct);
        return $this->render('products/productDetail.html.twig', [
            'product'=>$product,
        ]);
    }

    /**
     * @Route("/addProduct", name="addProduct")
     */
    public function addProduct(Request $request , UserRepository $userRepository , MailerService $mailer): Response
    {
        $product = new TblProduct();
        $form =$this->createForm(ProductType::class,$product);

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()){
            $em = $this->getDoctrine()->getManager();
            $product->setCreateddtm(new \DateTime());
            $product->setIduser($userRepository->find(1));
            $message="votre produit";
            $message2="est mis en ligne";
            $mailMessage = $message.' '.$product->getNameproduct().' '.$message2;

            $mailer->sendEmail('rayen.bakali@gmail.com', 'Product Online',$mailMessage);
            $em ->persist($product);//Hedhi nzidou
            $em ->flush();
            return $this->redirectToRoute( 'displayProduct');
        }
        $message="failed";
        return $this->render('products/createProduct.html.twig',[ 'f'=>$form->createView()]);
    }

    /**
     * @Route("/deleteProduct/{id}", name="deleteProduct")
     */
    public function deleteProduct(TblProduct  $product): Response
    {
        $em = $this->getDoctrine()->getManager();
        $em->remove($product);
        $em->flush();
        return  $this->redirectToRoute('displayProduct');
    }

    /**
     * @Route("/editProduct/{id}", name="editProduct")
     */
    public function editCategory(Request $request, $id): Response
    {

        $product = $this->getDoctrine()->getManager()->getRepository(TblProduct::class)->find($id);
        $form = $this->createForm(ProductType::class,$product);

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()){
            $em = $this->getDoctrine()->getManager();
            $em ->flush();

            return $this->redirectToRoute( 'displayProduct');
        }
        return $this->render('products/updateProduct.html.twig',[ 'f'=>$form->createView()]);
    }

    /**
     * @Route("/allProducts", name="allProducts")
     */
    public function index(ProductRepository $productRepository, CategoryRepository $categoryRepository): Response
    {

        return $this->render('products/ListProduct.html.twig', [
            'products' => $productRepository->findAll(),
            'categories' => $categoryRepository->findAll()
        ]);
    }


}
