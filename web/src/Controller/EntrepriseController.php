<?php

namespace App\Controller;

use App\Entity\TblStateuser;
use App\Repository\StateUserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\User;
use App\Form\EntrepriseType;
use App\Repository\UserRepository;
use App\Services\MailerService;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Dompdf\Dompdf;
use Dompdf\Options;
use Symfony\Flex\Options as op;


class EntrepriseController extends AbstractController
{

    private $encoder;


    public function __construct(UserPasswordEncoderInterface $encoder)
    {
        $this->encoder = $encoder;


    }

    /**
     * @Route("/newEntreprise", name="entrepriseNew", methods={"GET","POST"})
     */
    public function new(Request $request,MailerService $mailerService,StateUserRepository $stateUserRepository): Response
    {
        $user = new User();
        $form = $this->createForm(EntrepriseType::class, $user);
        $user->setRoles(array('ROLE_ENTREPRISE'));
        $stateuser= new TblStateuser();
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $password = $form->getData()->getPassword();
            $token=md5(uniqid());
            $user->setActivationToken($token);
            $user->setCreateddtm(new \DateTime());
            $stateuser =$stateUserRepository->find(0);
            $user->setStateuser($stateuser);
            $form->getData()->setPassword($this->encoder->encodePassword($user, $password));

            $filePhoto = $form->get('photo')->getData();
            $fileCv = $form->get('cv')->getData();


            if($filePhoto)
            {
                $fileNamePhoto = md5(uniqid()).'.'.$filePhoto->guessExtension();
                $filePhoto->move($this->getParameter('images_directory'), $fileNamePhoto);
                $user->setPhoto($fileNamePhoto);
            }

            if($fileCv)
            {
                $fileNameCv = md5(uniqid()).'.'.$fileCv->guessExtension();
                $fileCv->move($this->getParameter('images_directory'), $fileNameCv);
                $user->setCv($fileNameCv);
            }


            $entityManager->persist($user);
            $entityManager->flush();
            $mailerService->send(
                "Bienvenue sur notre site",
                "nebulagaming120@gmail.com",
                $form->get('email')->getData(),
                "mailTemplates/emailEntreprise.html.twig",['entreprise'=>$user]);




            return $this->redirectToRoute('loginBack', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('entreprise/new.html.twig', [
            'entreprise' => $user,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/activation/{token}",name="activation")
     */
    public function activation($token,UserRepository $userRepository)
    {



       $user = $userRepository->find($token);
      /*  if (!$user) {


            return $this->redirectToRoute("loginBack");
        }*/

        $user->setActivationToken('activé');
        $user->setRoles(array('ROLE_ENTREPRISE'));

        $em = $this->getDoctrine()->getManager();

        $em->flush();
        $this->addFlash("info", "Votre Compte est active");


        return $this->redirectToRoute("loginBack");

    }


    /**
     * @Route("/pdfEntreprise",name="pdfEntreprise")
     */

    public function pdf()
    {
        $User=$this->getDoctrine()->getRepository(User::class)->findBy(['roles' => array('["ROLE_ENTREPRISE"]')]);


        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);

        // Retrieve the HTML generated in our twig file
        $date=date("Y/m/d");
        $html = $this->renderView("back/pdfEntreprise.html.twig",[
            'users' => $User,
            'date'=>$date
        ]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html);


        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();


        // Output the generated PDF to Browser (force download)
        $dompdf->stream("ListeEntreprise.pdf", [
            "Attachment" => true
        ]);
    }



}
