<?php

namespace App\Controller;

use App\Entity\TblCategory;
use App\Entity\TblProduct;
use App\Form\CategoryType;
use App\Form\ProductType;
use App\Repository\CategoryRepository;
use App\Repository\ProductRepository;
use App\Repository\UserRepository;
use App\Services\SendEmailService;
use App\Services\GetUser;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Routing\RouterInterface;

class ProductController extends AbstractController
{
    /**
     * @var RouterInterface
     */
    private $router;
    private $getUser;

    /**
     * @param RouterInterface $router
     * @param GetUser $getUser
     */
    public function __construct(RouterInterface $router,GetUser $getUser)
    {
        $this->router = $router;
        $this->getUser=$getUser;

    }

    /**
     * @Route("/member/myproducts", name="myProducts")
     */
    public function displayProduct(): Response
    {
        $products = $this->getDoctrine()->getManager()->getRepository(TblProduct::class)->findBy(
            ["iduser" => $this->getUser->Get_User()]
        );
        return $this->render('frontTemplate/products/index.html.twig', [
            'b'=>$products
        ]);
    }

    /**
     * @Route("/productDetail/{idproduct}" , name="productDetail" , methods={"GET"})
     */
    public function productDetail(int $idproduct, ProductRepository $productRepository): Response
    {
        $product = $productRepository->find($idproduct);
        return $this->render('frontTemplate/products/productDetail.html.twig', [
            'product'=>$product,
        ]);
    }

    /**
     * @Route("/member/addProduct", name="addProduct")
     */
    public function addProduct(Request $request , UserRepository $userRepository ,  SendEmailService $mailer): Response
    {
        $product = new TblProduct();
        $form =$this->createForm(ProductType::class,$product);

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()){
            $em = $this->getDoctrine()->getManager();
            $product->setCreateddtm(new \DateTime());
            $product->setIduser($this->getUser->Get_User());
            $message="votre produit";
            $message2="est mis en ligne";
            $mailMessage = $message.' '.$product->getNameproduct().' '.$message2;

            $mailer->sendEmail($this->getUser->Get_User()->getEmail(), 'Product Online',$mailMessage);
            $em ->persist($product);//Hedhi nzidou
            $em ->flush();
            return $this->redirectToRoute( 'myProducts');
        }
        $message="failed";
        return $this->render('frontTemplate/products/createProduct.html.twig',[ 'f'=>$form->createView()]);
    }

    /**
     * @Route("/membre/deleteProduct/{id}", name="deleteProduct")
     */
    public function deleteProduct(TblProduct  $product): Response
    {
        $em = $this->getDoctrine()->getManager();
        $em->remove($product);
        $em->flush();
        return  $this->redirectToRoute('myProducts');
    }

    /**
     * @Route("/membre/editProduct/{id}", name="editProduct")
     */
    public function editCategory(Request $request, $id): Response
    {

        $product = $this->getDoctrine()->getManager()->getRepository(TblProduct::class)->find($id);
        $form = $this->createForm(ProductType::class,$product);

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()){
            $em = $this->getDoctrine()->getManager();
            $em ->flush();

            return $this->redirectToRoute( 'myProducts');
        }
        return $this->render('frontTemplate/products/updateProduct.html.twig',[ 'f'=>$form->createView()]);
    }

    /**
     * @Route("/shop", name="allProducts")
     */
    public function index(ProductRepository $productRepository, CategoryRepository $categoryRepository): Response
    {

        return $this->render('frontTemplate/products/ListProduct.html.twig', [
            'products' => $productRepository->findNonZero(),
            'categories' => $categoryRepository->findAll()
        ]);
    }


}
