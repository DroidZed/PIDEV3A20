<?php

namespace App\Controller;

use App\Entity\TblDomain;
use App\Entity\TblOffer;
use App\Form\DomainType;
use CMEN\GoogleChartsBundle\GoogleCharts\Charts\PieChart;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;

class DomainController extends AbstractController
{
    /**
     * @Route("/showDomain", name="display_Domain")
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
     * @Route("/addDomain", name="addDomain")
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
     * @Route("/removeDomain/{id}", name="supp_Domain")
     */
    public function suppressionDomain(TblDomain $Domain): Response
    {
        $em = $this->getDoctrine()->getManager();
        $em->remove($Domain);
        $em->flush();

        return $this->redirectToRoute('display_Domain');

    }

    /**
     * @Route("/modifDomain/{id}", name="modifDomain")
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


}
