<?php

namespace App\Controller;

use App\Entity\TblStateuser;
use App\Entity\User;
use App\Form\MembreType;
use App\Form\UserType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\UserRepository;
use App\Services\MailerService;
use Dompdf\Dompdf;
use Dompdf\Options;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use App\Repository\StateUserRepository;

class MembreController extends AbstractController
{


    /**
     * @Route("/newMembre", name="newMembre", methods={"GET","POST"})
     */
    public function new(\Swift_Mailer $mailer,Request $request,StateUserRepository $stateUserRepository,MailerService $mailerService,UserPasswordEncoderInterface $encoder): Response
    {
        $user = new User();
        $stateuser= new TblStateuser();
        $form = $this->createForm(MembreType::class, $user);
        $user->setRoles(array('ROLE_MEMBRE'));


        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();

            $token=md5(uniqid());
            $user->setActivationToken($token);
            $user->setCreateddtm(new \DateTime());
            $stateuser =$stateUserRepository->find(0);
            $user->setStateuser($stateuser);
            $password = $form->getData()->getPassword();
            $form->getData()->setPassword($encoder->encodePassword($user, $password));


            $filePhoto = $form->get('photo')->getData();


            if($filePhoto)
            {
                $fileNamePhoto = md5(uniqid()).'.'.$filePhoto->guessExtension();
                $filePhoto->move($this->getParameter('images_directory'), $fileNamePhoto);
                $user->setPhoto($fileNamePhoto);
            }


            $entityManager->persist($user);
            $entityManager->flush();
            $mailerService->send(
                "Bienvenue sur notre site",
                "nebulagaming120@gmail.com",
                $form->get('email')->getData(),
                "mail/Template/emailTemplate.html.twig",['token'=>$token]);



            return $this->redirectToRoute('loginBack', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('membre/new.html.twig', [
            'Membre' => $user,
            'form' => $form->createView(),
        ]);
    }




    /**
     * @Route("/activation/{token}",name="activationM")
     */
    public function activation($token,UserRepository $userRepository)
    {
        $user = new User();
        $user = $userRepository->findOneBy(['activation_token' => $token]);
        if (!$user) {


            return $this->redirectToRoute("loginBack");
        }

        $user->setActivationToken(null);
        $user->setRoles(array('ROLE_MEMBRE'));

        $em = $this->getDoctrine()->getManager();
        $em->flush();
        $this->addFlash("info", "Votre Compte est active");


        return $this->redirectToRoute("loginBack");

    }

    /**
     * @Route("/pdfMembre",name="pdfMembre")
     */

    public function pdf()
    {
        $User=$this->getDoctrine()->getRepository(User::class)->findBy(['roles' => array('["ROLE_MEMBRE"]')]);


        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);

        // Retrieve the HTML generated in our twig file
        $date=date("Y/m/d");
        $html = $this->renderView("backTemplate/pdfMembre.html.twig",[
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
        $dompdf->stream("ListeMembre.pdf", [
            "Attachment" => true
        ]);
    }


}
