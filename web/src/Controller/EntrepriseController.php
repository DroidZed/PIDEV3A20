<?php

namespace App\Controller;

use App\Entity\TblStateuser;
use App\Form\EntrepriseModifyType;
use App\Form\EntreprisePasswordType;
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
     * @Route("/Entreprise/new", name="entrepriseNew", methods={"GET","POST"})
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
     * @Route("/Entreprise/activation/{token}",name="activation")
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
     * @Route("/Entreprise/pdfEntreprise",name="pdfEntreprise")
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
        $html = $this->renderView("backTemplate/pdfEntreprise.html.twig",[
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


    /**
     * @Route("/Entreprise/email",name="email")
     */
    public function email()
    {
        $user = new User();
        $code = md5(uniqid());
        $form = $this->createForm(EntrepriseType::class, $user);

        return $this->render('security/forgotPassword1.html.twig', [

            'form' => $form->createView(),
        ]);

    }
    /**
     * @Route("/Entrerise/code",name="code")
     */
    public function code(Request $request,MailerService $mailerService,UserRepository $repository)
    {


        $email=$request->query->get('email');
        $user=$this->getDoctrine()->getRepository(User::class)->findBy(['email'=>$email]);

        $user1=$this->getDoctrine()->getRepository(User::class)->find(array_values($user)[0]);



        $code = md5(uniqid());

        if($user!=null) {
            $mailerService->send(
                "Recupération compte",
                "nebulagaming120@gmail.com",
                $email,
                "mailTemplates/code.html.twig", ['code' => $code]);



        }

$user1->setPassword(($this->encoder->encodePassword($user1, $code)));
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->flush();
        return $this->render('security/forgotPassword1.html.twig', [

            'entreprise' => $user,
        ]);

    }
    /**
     * @Route("/Entreprise/codebd",name="codebd")
     */
    public function codebd(Request $request,MailerService $mailerService,UserRepository $repository)
    {


        $email=$request->query->get('email');

        $user=$this->getDoctrine()->getRepository(User::class)->findBy(['email'=>$email]);

        $user1=$this->getDoctrine()->getRepository(User::class)->find(array_values($user)[0]);

        $form = $this->createForm(EntreprisePasswordType::class, $user1);

        $code = md5(uniqid());

        if($user!=null) {
            $mailerService->send(
                "Recupération compte",
                "nebulagaming120@gmail.com",
                $email,
                "mailTemplates/code.html.twig", ['code' => $code]);



        }

        $user1->setResetPasswordRequest($code);

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->flush();
        return $this->render('security/forgotPassword2.html.twig', [

            'User' => $user1,
            'form_modify'=>$form->createView(),
        ]);

    }
    /**
     * @Route("/Entreprise/changepwd/{id}",name="changepwd")
     */
    public function changepwd($id,Request $request)
    {
        $user = new User();
        $user=$this->getDoctrine()->getRepository(User::class)->find($id);
        $mdp1=$request->query->get('mdp1');
        $mdp2=$request->query->get('mdp2');
        $code=$request->query->get('codeverif');
        if(($mdp1==$mdp2) and $user->getResetPasswordRequest()==$code) {
            $user->setPassword(($this->encoder->encodePassword($user, $mdp1)));
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->flush();
            return $this->render('frontTemplate/index.html.twig', [

            ]);
        }
    }
}
