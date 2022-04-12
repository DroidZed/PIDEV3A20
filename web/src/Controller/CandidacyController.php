<?php

namespace App\Controller;

use App\Entity\TblCandidacy;
use App\Entity\TblOffer;
use App\Entity\TblUser;
use App\Form\CandidacyType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/candidacy")
 */

class CandidacyController extends AbstractController
{
    /**
     * @Route("/", name="display_Candidacy")
     */
    public function index(): Response
    {
        $candidacys = $this->getDoctrine()->getManager()->getRepository(TblCandidacy::class)->findAll();
        return $this->render('candidacy/index.html.twig', [
            'candidacys'=>$candidacys
        ]);
    }
    /**
     * @Route("/Admin/{id}", name="display_Candidacy_Admin")
     */
    public function indexAdmin($id): Response
    {
        $candidacys = $this->getDoctrine()->getManager()->getRepository(TblCandidacy::class)->findBy([
                'idoffer' => $id,
            ]
        );
        return $this->render('candidacy/indexAdmin.html.twig', [
            'candidacys'=>$candidacys
        ]);
    }

    /**
     * @Route("/add/{id}", name="addCandidacy")
     */
    public function addCandidacy(Request $request,$id): Response
    {
        $candidacy = new TblCandidacy();
        $offer=$this->getDoctrine()->getRepository(TblOffer::class)->find($id);

        $form = $this->createForm(CandidacyType::class, $candidacy);

        $form->handleRequest($request);

        if($form->isSubmitted() &&  $form->isValid() )
        {
            $file = $form->get('imagecv')->getData();
            $fileName = md5(uniqid()).'.'.$file->guessExtension();
            try {
                $file->move(
                    $this->getParameter('images_directory'),
                    $fileName
                );
            } catch (FileException $e){

            }
            $candidacy->setImagecv($fileName);
            $candidacy->setEtat("En Cours");
            $candidacy->setCandidacydtm(new \DateTime());

            $candidacy->setIduser($this->getDoctrine()->getManager()->getRepository(TblUser::class)->find(1));
            $candidacy->setIdoffer($offer);
            $em = $this->getDoctrine()->getManager();
            $em->persist($candidacy); //add
            $em->flush();

            return $this->redirectToRoute('display_Candidacy');
        }

        return $this->render('candidacy/add.html.twig', ['form'=>$form->createView()]);

    }

    /**
     * @Route("/editAutoriser/", name="editAutoriser")
     */
    function AutoriserAction(Request $request){
        $candidacy=$this->getDoctrine()->getRepository(TblCandidacy::class)->find($request->get('id'));
        $candidacy->setEtat("Faire le test");
        $em = $this->getDoctrine()->getManager();
        $em->flush();

        return $this->redirectToRoute('display_Candidacy_Admin', ['id'=>$request->get('idoffre')]);
    }

    /**
     * @Route("/editRefuser/", name="editRefuser")
     */
    function RefuserAction(Request $request){
        $candidacy=$this->getDoctrine()->getRepository(TblCandidacy::class)->find($request->get('id'));
        $candidacy->setEtat("Refuser");
        $em = $this->getDoctrine()->getManager();
        $em->flush();

        return $this->redirectToRoute('display_Candidacy_Admin', ['id'=>$request->get('idoffre')]);
    }


}
