<?php

namespace App\Controller\WebServices_Product;

use App\Entity\JsonResponseDAO;
use App\Entity\TblProduct;
use App\Repository\CategoryRepository;
use App\Repository\ProductRepository;
use App\Repository\UserRepository;
use App\Services\SendEmailService;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Exception\ExceptionInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;


/**
 * @Route("/product");
 */
class ProductResourceController extends AbstractController
{
    /**
     * @Route("/get/{idproduct}" , name="singleProduct" , methods={"GET"})
     * @throws ExceptionInterface
     */
    public function productDetail(int $idproduct, ProductRepository $productRepository, NormalizerInterface $normalizer): JsonResponse
    {
        return $this->json($normalizer->normalize(
            $productRepository->find($idproduct),
            'json',
            ['groups' => 'products']
        )
        );
    }

    /**
     * @Route("/add", name="addProduct", methods={"POST"})
     */
    public function addProduct(Request $request,
                               ProductRepository $productRepository,
                               UserRepository $userRepository,
                               SendEmailService $mailer,
                               CategoryRepository $categoryRepository): JsonResponse
    {
        $product = new TblProduct();

        $data = json_decode($request->getContent(), true);

        if ($productRepository->findBy(["nameproduct" => $data["nameProduct"]])) {
            return $this->json(new JsonResponseDAO("Product already exists !"));
        }

        $product->setNameproduct($data["nameProduct"]);
        $product->setQtyproduct($data["qtyProd"]);
        $product->setPriceproduct($data["priceProduct"]);
        $product->setIdcategory($categoryRepository->findOneBy([ "nameCategory" => $data["nameCategory"]]));
        $product->setIduser($userRepository->find($data["idUser"]));

        $em = $this->getDoctrine()->getManager();
        $product->setCreateddtm(new \DateTime());

        $message = "votre produit";
        $message2 = "est mis en ligne";
        $mailMessage = $message . ' ' . $product->getNameproduct() . ' ' . $message2;

        $mailer->sendEmail('rayen.bakali@gmail.com', 'Product Online', $mailMessage);
        $em->persist($product);//Hedhi nzidou
        $em->flush();

        return $this->json(new JsonResponseDAO("Product saved !"));

    }


    /**
     * @Route("/get", name="allProductsJson", methods={"GET"})
     * @throws ExceptionInterface
     */
    public function index(ProductRepository $productRepository, NormalizerInterface $normalizer): JsonResponse
    {

        return $this->json($normalizer->normalize(
            $productRepository->findAll(),
            'json',
            ['groups' => 'products']
        )
        );
    }


    /**
     * @Route("/del/{id}", name="deleteProduct", methods={"DELETE"})
     */
    public function deleteProduct(int $id, ProductRepository $productRepository): Response
    {
        $em = $this->getDoctrine()->getManager();
        $product = $productRepository->find($id);
        if ($product == null) {
            return $this->json(new JsonResponseDAO("Product Not Found !"));
        }
        $em->remove($product);
        $em->flush();
        return $this->json(new JsonResponseDAO("Product Deleted"));
    }

    /**
     * @Route("/edit/{id}", name="editProduct", methods={"PUT"})
     */
    public function edit(Request $request, int $id, ProductRepository $productRepository, CategoryRepository $categoryRepository): JsonResponse
    {

        $product = $productRepository->find($id);

        if ($product == null) {
            return new JsonResponse(new JsonResponseDAO("Product Not Found !"));
        }

        $data = json_decode($request->getContent(), true);

        $product->setNameproduct($data["nameProduct"]);
        $product->setPriceproduct($data["priceProduct"]);
        $product->setQtyproduct($data["qtyProd"]);
        $product->setIdcategory($categoryRepository->find($data["idCategory"]));

        $em = $this->getDoctrine()->getManager();
        $em->flush();


        return $this->json(new JsonResponseDAO("Product Updated"));
    }


}