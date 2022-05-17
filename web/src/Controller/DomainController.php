<?php

namespace App\Controller;

use App\Entity\TblDomain;
use App\Entity\TblOffer;
use App\Form\DomainType;
use CMEN\GoogleChartsBundle\GoogleCharts\Charts\PieChart;
use CMEN\GoogleChartsBundle\GoogleCharts\Options\Domain;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\HttpFoundation\JsonResponse;
use Doctrine\ORM\EntityManagerInterface;

class DomainController extends AbstractController
{
    /**
     * @Route("/admin/dmoains", name="display_Domain")
     */
    public function index(): Response
    {
        $pieChart = new PieChart();

        $Domain = $this->getDoctrine()->getManager()->getRepository(TblDomain::class)->findAll();
        $data= array();
        $stat=['Les Domaines', '%'];
        array_push($data,$stat);

        foreach($Domain as $Domainn)
        {
            $stat=array();
            $tmp = new TblOffer();
            $tmp = $this->getDoctrine()->getManager()->getRepository(TblOffer::class)->findBy([
                'iddomain' => $Domainn
            ]);
            $total = count($tmp);
            $stat=[$Domainn->getName(),$total];
            array_push($data,$stat);
        }
        $pieChart->getData()->setArrayToDataTable(
            $data
        );

        $pieChart->getOptions()->setTitle('Les Domaines');
        $pieChart->getOptions()->setHeight(500);
        $pieChart->getOptions()->setWidth(900);
        $pieChart->getOptions()->getTitleTextStyle()->setBold(true);
        $pieChart->getOptions()->getTitleTextStyle()->setColor('#009900');
        $pieChart->getOptions()->getTitleTextStyle()->setItalic(true);
        $pieChart->getOptions()->getTitleTextStyle()->setFontName('Arial');
        $pieChart->getOptions()->getTitleTextStyle()->setFontSize(20);

        return $this->render('domain/index.html.twig', [

            'b'=>$Domain,
            'piechart' => $pieChart
        ]);
    }


    /**
     * @Route("/admin/addDomain", name="addDomain")
     */
    public function addDomain(Request $request): Response
    {
        $Domain = new  TblDomain();

        $form = $this->createForm(DomainType::class, $Domain);

        $form->handleRequest($request);

        if($form->isSubmitted() &&  $form->isValid() )
        {

            $em = $this->getDoctrine()->getManager();
            $em->persist($Domain); //add
            $em->flush();

            return $this->redirectToRoute('display_Domain');
        }

        return $this->render('domain/createDomain.html.twig', ['f'=>$form->createView()]);

    }

    /**
     * @Route("/admin/removeDomain/{id}", name="supp_Domain")
     */
    public function suppressionDomain(TblDomain $Domain): Response
    {
        $em = $this->getDoctrine()->getManager();
        $em->remove($Domain);
        $em->flush();

        return $this->redirectToRoute('display_Domain');

    }

    /**
     * @Route("/admin/modifDomain/{id}", name="modifDomain")
     */
    public function modifDomain(Request $request, $id): Response
    {
        $Domain = $this->getDoctrine()->getManager()->getRepository(TblDomain::class)->find($id);

        $form = $this->createForm(DomainType::class, $Domain);

        $form->handleRequest($request);

        if($form->isSubmitted() &&  $form->isValid() )
        {
            $em = $this->getDoctrine()->getManager();
            $em->flush();

            return $this->redirectToRoute('display_Domain');
        }

        return $this->render('domain/updateDomain.html.twig', ['f'=>$form->createView()]);

    }

    //*****MOBILE

    /**
     * @Route("/domain/mobile/aff", name="affmobdom")
     */
    public function affmobdom(Request $request,NormalizerInterface $normalizer)
    {

        $med=$this->getDoctrine()->getRepository(TblDomain::class)->findAll();
        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(1);
        $normalizer->setCircularReferenceHandler(function ($med) {
            return $med->getId();
        });
        $encoders = [new JsonEncoder()];
        $normalizers = array($normalizer);
        $serializer = new Serializer($normalizers,$encoders);
        $formatted = $serializer->normalize($med);
        return new JsonResponse($formatted);
    }



    /**
     * @Route("/domain/mobile/new", name="addmobdom")
     */
    public function addmobdom(Request $request,NormalizerInterface $normalizer,EntityManagerInterface $entityManager)
    {
        $msg= new TblDomain();

        $msg->setDescription($request->get('description'));
        $msg->setName($request->get('name'));


        $entityManager->persist($msg);
        $entityManager->flush();

        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(1);
        $normalizer->setCircularReferenceHandler(function ($msg) {
            return $msg->getId();
        });
        $encoders = [new JsonEncoder()];
        $normalizers = array($normalizer);
        $serializer = new Serializer($normalizers,$encoders);
        $formatted = $serializer->normalize($msg);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/messages/mobile/editmsg", name="editmobmsg")
     */
    public function editmobmsg(Request $request,NormalizerInterface $normalizer)
    {   $em=$this->getDoctrine()->getManager();

        $msg = $em->getRepository(Messages::class)->find($request->get('id'));

        $msg->setImage($request->get('image'));
        $msg->setMessage($request->get('msg'));
        $msg->setTitle($request->get('title'));


        $em->flush();
        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(1);
        $normalizer->setCircularReferenceHandler(function ($msg) {
            return $msg->getId();
        });
        $encoders = [new JsonEncoder()];
        $normalizers = array($normalizer);
        $serializer = new Serializer($normalizers,$encoders);
        $formatted = $serializer->normalize($msg);
        return new JsonResponse($formatted);
    }
    /**
     * @Route("/domaine/mobile/del", name="delmobmsg")
     */
    public function delmobmsg(Request $request,NormalizerInterface $normalizer)
    {           $em=$this->getDoctrine()->getManager();
        $msg=$this->getDoctrine()->getRepository(TblDomain::class)
            ->find($request->get('id'));
        $em->remove($msg);
        $em->flush();
        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(1);
        $normalizer->setCircularReferenceHandler(function ($msg) {
            return $msg->getId();
        });
        $encoders = [new JsonEncoder()];
        $normalizers = array($normalizer);
        $serializer = new Serializer($normalizers,$encoders);
        $formatted = $serializer->normalize($msg);
        return new JsonResponse($formatted);

    }


}
