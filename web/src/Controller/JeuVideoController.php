<?php

namespace App\Controller;

use App\Entity\TblPublication;
use App\Entity\TblVideogame;
use App\Form\JeuVideoType;
use App\Services\QrCodeService;
use Knp\Component\Pager\PaginatorInterface;
use Knp\Snappy\Pdf;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\Request;
use Endroid\QrCode\Builder\BuilderInterface;
use Endroid\QrCodeBundle\Response\QrCodeResponse;
use Endroid\QrCode\QrCode;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Dompdf\Dompdf;
use Dompdf\Options;

class JeuVideoController extends AbstractController
{
    /**
     * @Route("/membre/Jeu", name="display_Vg" , methods={"GET"})
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
     * @Route("/membre/Stat", name="Stat")
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
     * @Route("/membre/addJeu", name="addJeu")
     * @param Request $request
     * @param QrcodeService $qrcodeService
     * @return Response
     */
    public function addJeu(Request $request, QrCodeService $qrCodeService): Response
    {

        $JV = new  TblVideogame();

        $form = $this->createForm(JeuVideoType::class, $JV);

        $form->handleRequest($request);

        if($form->isSubmitted() &&  $form->isValid() )
        {
            $data = $form->get('namevg')->getData();
            $file = $form->get('imagevg')->getData();
            $fileName = md5(uniqid()).'.'.$file->guessExtension();
            $qrCodeService->gen($data, $this->getParameter('images_directory').$fileName);
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
     * @Route("/membre/removeJeu/{id}", name="supp_Vg")
     */
    public function suppressionJeu(TblVideogame $JV): Response
    {
        $em = $this->getDoctrine()->getManager();
        $em->remove($JV);
        $em->flush();

        return $this->redirectToRoute('display_Vg');

    }

    /**
     * @Route("/membre/modifJeu/{id}", name="modifJeu")
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
     * @Route("/membre/r/search_recc", name="search_recc", methods={"GET"})
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
     * @Route("/membre/pdfC", name="pdfC")
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

}
