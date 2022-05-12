<?php

namespace App\Controller;

use App\Entity\TblPublication;
use App\Entity\TblVideogame;
use App\Form\JeuVideoType;
use App\services\QrcodeService;
use Doctrine\ORM\EntityManagerInterface;
use Knp\Component\Pager\PaginatorInterface;
use Knp\Snappy\Pdf;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\Request;
use Endroid\QrCode\Builder\BuilderInterface;
use Endroid\QrCodeBundle\Response\QrCodeResponse;
use Endroid\QrCode\QrCode;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Dompdf\Dompdf;
use Dompdf\Options;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;

class JeuVideoController extends AbstractController
{
    /**
     * @Route("/Jeu", name="display_Vg")
     * @param Request $request
     * @param PaginatorInterface $paginator
     * @return Response
     */
    public function index(Request $request, PaginatorInterface $paginator): Response
    {

        $donnees = $this->getDoctrine()->getManager()->getRepository(TblVideogame::class)->findAll();
        $pub = $this->getDoctrine()->getManager()->getRepository(TblPublication::class)->findAll();

        $jeux = $paginator->paginate(
          $donnees,
          $request->query->getInt('page', 1),
            3
        );

        return $this->render('jeu_video/index.html.twig', [
            'b'=>$jeux,
            'videog'=>$donnees,
            'pub'=>$pub
        ]);
    }

    /**
     * @Route("/Stat", name="Stat")
     * @param Request $request
     * @param PaginatorInterface $paginator
     * @return Response
     */
    public function stat(Request $request, PaginatorInterface $paginator)
    {
        $donnees = $this->getDoctrine()->getManager()->getRepository(TblVideogame::class)->findAll();
        $pub = $this->getDoctrine()->getManager()->getRepository(TblPublication::class)->findAll();

        $jeux = $paginator->paginate(
            $donnees,
            $request->query->getInt('page', 1),
            3
        );


        return $this->render('jeu_video/Stat.html.twig',[
            'b'=>$jeux,

            'videog'=>$donnees,
            'pub'=>$pub

        ]);
    }

    /**
     * @Route("/", name="display_Accueil")
     */
    public function indexFront(): Response
    {
        return $this->render('indexFront.html');
    }



    /**
     * @Route("/addJeu", name="addJeu")
     * @param Request $request
     * @param QrcodeService $qrcodeService
     * @return Response
     */
    public function addJeu(Request $request, QrcodeService $qrcodeService): Response
    {

        $JV = new  TblVideogame();

        $form = $this->createForm(JeuVideoType::class, $JV);

        $form->handleRequest($request);

        if($form->isSubmitted() &&  $form->isValid() )
        {
            $data = $form->get('namevg')->getData();
            $qrcodeService->qrcode($data);
            $file = $form->get('imagevg')->getData();
            $fileName = md5(uniqid()).'.'.$file->guessExtension();
            try {
                $file->move(
                    $this->getParameter('images_directory'),
                    $fileName
                );
            } catch (FileException $e){

            }
            $em = $this->getDoctrine()->getManager();
            $JV->setImagevg($fileName);
            $em->persist($JV); //add
            $em->flush();

            $this->addFlash('info','Jeu ajouté avec succés!');

            return $this->redirectToRoute('display_Vg');
        }

        $qrCode =null;
        return $this->render('jeu_video/createJV.html.twig', [
            'f'=>$form->createView(),
            'qrCode'=>$qrCode
        ]);

    }

    /**
     * @Route("/removeJeu/{id}", name="supp_Vg")
     */
    public function suppressionJeu(TblVideogame $JV): Response
    {
        $em = $this->getDoctrine()->getManager();
        $em->remove($JV);
        $em->flush();

        return $this->redirectToRoute('display_Vg');

    }

    /**
     * @Route("/modifJeu/{id}", name="modifJeu")
     */
    public function modifJeu(Request $request, $id): Response
    {
        $JV = $this->getDoctrine()->getManager()->getRepository(TblVideogame::class)->find($id);

        $form = $this->createForm(JeuVideoType::class, $JV);

        $form->handleRequest($request);

        if($form->isSubmitted() &&  $form->isValid() )
        {
            $file = $form->get('imagevg')->getData();
            $fileName = md5(uniqid()).'.'.$file->guessExtension();
            try {
                $file->move(
                    $this->getParameter('images_directory'),
                    $fileName
                );
            } catch (FileException $e){

            }
            $em = $this->getDoctrine()->getManager();
            $JV->setImagevg($fileName);
            $em->flush();

            return $this->redirectToRoute('display_Vg');
        }

        return $this->render('jeu_video/updateJV.html.twig', ['f'=>$form->createView()]);

    }

    /**
     * @Route("/r/search_recc", name="search_recc", methods={"GET"})
     */
    public function search_rec(Request $request, NormalizerInterface $Normalizer): Response
    {

        $requestString = $request->get('searchValue');
        $requestString3 = $request->get('orderid');

        $em = $this->getDoctrine()->getManager();
        if ($requestString3 == 'DESC') {
            $query = $em->createQuery(
                'SELECT r FROM App\Entity\TblVideogame r where r.namevg like :suj OR r.rating like :suj order by r.rating DESC '
            );
            $query->setParameter('suj', $requestString . '%');
        } elseif ($requestString3 == 'ASC'){
            $query = $em->createQuery(
                'SELECT r FROM App\Entity\TblVideogame r where r.namevg like :suj OR r.rating like :suj order by r.rating ASC '
            );
            $query->setParameter('suj', $requestString . '%');
        }

            $VideoGame = $query->getResult();
        $jsoncontentc = $Normalizer->normalize($VideoGame, 'json', ['groups' => 'posts:read']);
        $jsonc = json_encode($jsoncontentc);
        if ($jsonc == "[]") {
            return new Response(null);
        } else {
            return new Response($jsonc);
        }
    }
    /**
     * @Route("/pdfC", name="pdfC")
     */
    public function listpdf(Request $request)
    {

        $entityManager = $this->getDoctrine();
        $list = $entityManager->getRepository(TblVideogame::class)
            ->findAll();


        // Configure Dompdf according to your needs
        $pdfOptions = new Options();

        $pdfOptions->set('isRemoteEnabled', true);

        $pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new DOMPDF($pdfOptions);

        $contxt = stream_context_create([
            'ssl' => [
                'verify_peer' => FALSE,
                'verify_peer_name' => FALSE,
                'allow_self_signed'=> TRUE
            ]
        ]);

        $html=$this->render('jeu_video/pdfC.html.twig', [
            'e' => $list
        ]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html);



        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (force download)
        $dompdf->stream("jeu_video/pdfC.html.twig", [
            "Attachment" => false
        ]);

    }
/*
    /**
     * @Route("/SShot", name="SShot")
     *
    public function Stat_screenshot()
    {
        // Get snappy.image service
        $imageGenerator = $this->get('knp_snappy.image');

        $filepath = 'imagehd.jpg';//or filename.png

        // Set dimensions of the output image
        $imageGenerator->setOption('width',1920);
        $imageGenerator->setOption('height',1080);

        // Take a screenshot of Our Code World website !
        $imageGenerator->generate("http://127.0.0.1:8001/Stat", $filepath);

        return new Response("Image succesfully created in ".$filepath);
    }
*/

    //*****MOBILE

    /**
     * @Route("/Jeu/mobile/aff", name="affmobjv")
     */
    public function affmobjv(NormalizerInterface $normalizer)
    {
        $med=$this->getDoctrine()->getRepository(TblVideogame::class)->findAll();

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
     * @Route("/jeu/mobile/new", name="addmobJeu")
     */
    public function addmobRec(Request $request,NormalizerInterface $normalizer,EntityManagerInterface $entityManager)
    {
        $jv= new TblVideogame();

        $jv->setImagevg($request->get('image'));

        $jv->setNamevg($request->get('name'));
        $jv->setStatusvg(1);
        $jv->setRating($request->get('rating'));

        $entityManager->persist($jv);
        $entityManager->flush();

        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(1);
        $normalizer->setCircularReferenceHandler(function ($jv) {
            return $jv->getId();
        });
        $encoders = [new JsonEncoder()];
        $normalizers = array($normalizer);
        $serializer = new Serializer($normalizers,$encoders);
        $formatted = $serializer->normalize($jv);
        return new JsonResponse($formatted);

    }

    /**
     * @Route("/reclamation/mobile/editrec", name="editmobrec")
     */
    public function editmobrec(Request $request,NormalizerInterface $normalizer)
    {   $em=$this->getDoctrine()->getManager();

        $rec = $em->getRepository(Reclamation::class)->find($request->get('id'));


        $rec->setClaim($request->get('claim'));
        $rec->setSubject($request->get('sujet'));


        $em->flush();
        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(1);
        $normalizer->setCircularReferenceHandler(function ($rec) {
            return $rec->getId();
        });
        $encoders = [new JsonEncoder()];
        $normalizers = array($normalizer);
        $serializer = new Serializer($normalizers,$encoders);
        $formatted = $serializer->normalize($rec);
        return new JsonResponse($formatted);
    }
    /**
     * @Route("/jeu/mobile/del", name="delmobjv")
     */
    public function delmobcoach(Request $request,NormalizerInterface $normalizer)
    {           $em=$this->getDoctrine()->getManager();
        $rec=$this->getDoctrine()->getRepository(TblVideogame::class)
            ->find($request->get('id'));
        $em->remove($rec);
        $em->flush();
        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(1);
        $normalizer->setCircularReferenceHandler(function ($rec) {
            return $rec->getId();
        });
        $encoders = [new JsonEncoder()];
        $normalizers = array($normalizer);
        $serializer = new Serializer($normalizers,$encoders);
        $formatted = $serializer->normalize($rec);
        return new JsonResponse($formatted);

    }



}
