<?php

namespace App\Controller;

use App\Entity\User;
use App\Form\EntrepriseModifyType;
use App\Form\EntrepriseType;
use App\Repository\StateUserRepository;
use App\Repository\UserRepository;

use App\Services\MailerService;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Serializer\Exception\ExceptionInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Filesystem\Filesystem;


class MobileController extends AbstractController
{
    private $user;

    /**
     * @Route("/getCodeM", name="getCode")
     * @param \Swift_Mailer $mailer
     * @param MailerService $mailerService
     * @param Request $request
     * @param NormalizerInterface $normalizable
     * @param UserRepository $userRepository
     * @return Response
     * @throws ExceptionInterface
     */

    public function getCode(\Swift_Mailer $mailer,MailerService $mailerService,Request $request,NormalizerInterface $normalizable,UserRepository $userRepository): Response
    {
        $user=new User();
        $userEmail = $this->getDoctrine()->getRepository(User::class)->findBy(
            ['email' =>$request->get('email')]
        );

        if($userEmail)
        {
            $user=$userEmail;
        }

        $code = md5(uniqid());
        $mailerService->send(
            "Recupération compte",
            "nebulagaming120@gmail.com",
            $user[0]->getEmail(),
            "mailTemplates/code.html.twig", ['code' => $code]);

        $jsonContent=$normalizable->normalize($code,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));
    }

    /**
     * @Route("/checkUserUnique", name="checkUserUnique")
     * @param Request $request
     * @param NormalizerInterface $normalizable
     * @param UserPasswordEncoderInterface $encoder
     * @return Response
     * @throws ExceptionInterface
     */
    public function checkUserUnique(Request $request,NormalizerInterface $normalizable,UserPasswordEncoderInterface $encoder): Response
    {

        $userBD_email = $this->getDoctrine()->getRepository(User::class)->findBy(
            ['email' =>$request->get('email')]
        );

        if($userBD_email)
        {
            $result=-2;
        }
        else{
            $result=1;
        }
        $jsonContent=$normalizable->normalize($result,'json',[]);
        return new Response(json_encode($jsonContent));
    }


    /**
     * @Route("/loginCheck", name="loginCheck")
     * @param Request $request
     * @param NormalizerInterface $normalizable
     * @param UserRepository $userRepository
     * @param UserPasswordEncoderInterface $encoder
     * @return Response
     * @throws ExceptionInterface
     */

    public function loginCheck(Request $request,NormalizerInterface $normalizable,UserRepository $userRepository,UserPasswordEncoderInterface $encoder): Response //mail
    {  $result=-1;
        $user=new User();
        $User = $this->getDoctrine()->getRepository(User::class)->findBy(
            ['email' =>$request->get('email')]);

        $password=$encoder->encodePassword($user,$request->get('password'));
        if($User)
        {

            if($User[0]->getPassword()==$password)
            {

                if (in_array('ROLE_MEMBRE',$User[0]->getRoles(), true))
                $result=2;
                else if (in_array('ROLE_ENTREPRISE', $User[0]->getRoles(), true))
                    $result=1;
            }}

        $jsonContent=$normalizable->normalize($result,'json',[]);
        return new Response(json_encode($jsonContent));
    }


    /**
     * @Route("/getUser", name="getUser")
     * @param Request $request
     * @param NormalizerInterface $normalizable
     * @param UserRepository $userRepository
     * @return Response
     * @throws ExceptionInterface
     */

    public function getUserByEmail(Request $request,NormalizerInterface $normalizable,UserRepository $userRepository): Response
    {
        $user=new User();
        $userEmail = $this->getDoctrine()->getRepository(User::class)->findBy(
            ['email' =>$request->get('email')]
        );

        if($userEmail)
        {
            $user=$userEmail;
        }

        $jsonContent=$normalizable->normalize($user,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));
    }


    /**
     * @Route("/modifyEnt", name="modifyEnt")
     * @param Request $request
     * @param NormalizerInterface $normalizable
     * @param UserPasswordEncoderInterface $encoder
     * @return Response
     * @throws ExceptionInterface
     */
    public function modifyEnt(Request $request,NormalizerInterface $normalizable,UserPasswordEncoderInterface $encoder): Response
    {
        $em=$this->getDoctrine()->getManager();
        $User = $this->getDoctrine()->getRepository(User::class)->findBy(
            ['email' =>$request->get('email')]);
        $user = $User[0];
        $user->setNom($request->get('nom'));
        $user->setTel($request->get('tel'));
        $user->setOffre($request->get('descuser'));

        if($request->get('photo')!=null && $user->getPhoto()!=$request->get('photo')  )
        {
            $fileNamePhoto = $request->get('photo');
            $filePathMobilePhoto="C://Users//ASUS//AppData//Local//Temp";
            $uploads_directoryPic = $this->getParameter('images_directory');
            $filesystempic = new Filesystem();
            $filesystempic->copy($filePathMobilePhoto."//".$fileNamePhoto , $uploads_directoryPic."$fileNamePhoto");
            $user->setPhoto($request->get('photo'));
        }

        $em->flush();
        $jsonContent=$normalizable->normalize($user,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));
    }



 /**
     * @Route("/modifyMembre", name="modifyMembre")
     * @param Request $request
     * @param NormalizerInterface $normalizable
     * @param UserPasswordEncoderInterface $encoder
     * @return Response
     * @throws ExceptionInterface
     */
    public function modifyMembre(Request $request,NormalizerInterface $normalizable,UserPasswordEncoderInterface $encoder): Response
    {
        $em=$this->getDoctrine()->getManager();
        $User = $this->getDoctrine()->getRepository(User::class)->findBy(
            ['email' =>$request->get('email')]);
        $user = $User[0];
        $user->setNom($request->get('nom'));
        $user->setTel($request->get('tel'));
$user->setDescuser($request->get('descuser'));


       if($request->get('photo')!=null && $user->getPhoto()!=$request->get('photo')  )
        {
            $fileNamePhoto = $request->get('photo');
            $filePathMobilePhoto="C://Users//ASUS//AppData//Local//Temp";
            $uploads_directoryPic = $this->getParameter('images_directory');
            $filesystempic = new Filesystem();
            $filesystempic->copy($filePathMobilePhoto."//".$fileNamePhoto , $uploads_directoryPic."$fileNamePhoto");
            $user->setPhoto($request->get('photo'));
        }

        $em->flush();
        $jsonContent=$normalizable->normalize($user,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));
    }


    /**
     * @Route("/newMembreM", name="membreNewM", methods={"GET","POST"})
     * @param Request $request
     * @param NormalizerInterface $normalizable
     * @param UserPasswordEncoderInterface $encoder
     * @param StateUserRepository $stateUserRepository
     * @return Response
     * @throws ExceptionInterface
     */
    public function newMembre(Request $request, NormalizerInterface $normalizable, UserPasswordEncoderInterface $encoder,StateUserRepository $stateUserRepository): Response
    {
        $em=$this->getDoctrine()->getManager();
        $user=new User();
        $password=$encoder->encodePassword($user,$request->get('password'));

        $fileNamePhoto = $request->get('photo');

        $uploads_directoryPic = $this->getParameter('images_directory');
        $filesystempic = new Filesystem();



$user->setNom($request->get('nom'));
$user->setEmail($request->get('email'));
        $user->setCreateddtm(new \DateTime());
        $user->setTel($request->get('tel'));
        $user->setRoles(array('ROLE_MEMBRE'));
        $stateuser =$stateUserRepository->find(0);
        $user->setStateuser($stateuser);
$user->setPassword($password);
$user->setPhoto($fileNamePhoto);
$user->setDescuser($request->get('descuser'));
        $em->persist($user);
        $em->flush();

        $jsonContent=$normalizable->normalize($user,'json',[]);
        return new Response(json_encode($jsonContent));
    }

    /**
     * @Route("/newEntrepriseM", name="entrepriseNewM", methods={"GET","POST"})
     * @param Request $request
     * @param NormalizerInterface $normalizable
     * @param UserPasswordEncoderInterface $encoder
     * @param StateUserRepository $stateUserRepository
     * @return Response
     * @throws ExceptionInterface
     */
    public function newEnt(Request $request, NormalizerInterface $normalizable, UserPasswordEncoderInterface $encoder,StateUserRepository $stateUserRepository): Response
    {
        $em=$this->getDoctrine()->getManager();
        $user=new User();
        $password=$encoder->encodePassword($user,$request->get('password'));

        $fileNamePhoto = $request->get('photo');

        $uploads_directoryPic = $this->getParameter('images_directory');
        $filesystempic = new Filesystem();


  
        $user->setNom($request->get('nom'));
        $user->setEmail($request->get('email'));
        $user->setCreateddtm(new \DateTime());
        $user->setTel($request->get('tel'));
        $user->setRoles(array('ROLE_ENTREPRISE'));
        $val=$user->getStateuser();
        $stateuser =$stateUserRepository->find(0);
        $user->setPhoto($fileNamePhoto);
        $user->setStateuser($stateuser);
        $user->setPassword($password);
        $em->persist($user);
        $em->flush();

        $jsonContent=$normalizable->normalize($user,'json',[]);
        return new Response(json_encode($jsonContent));
    }


    /**
     * @param Request $request
     * @param NormalizerInterface $normalizable
     * @return Response
     * @throws ExceptionInterface
     * @Route("/Entreprise/Profil/Change_informationsM", name="ProfilEtudiantM")
     */

    function modifierEnt(Request $request, NormalizerInterface $normalizable): Response
    {
        $em=$this->getDoctrine()->getManager();
        $User = $this->getDoctrine()->getRepository(User::class)->findBy(
            ['email' =>$request->get('email')]);
        $user = $User[0];
        $user->setNom($request->get('nom'));
        $user->setTel($request->get('tel'));



        if($request->get('photo')!=null && $user->getPhoto()!=$request->get('photo')  )
        {
            $fileNamePhoto = $request->get('photo');
            $filePathMobilePhoto="C://Users//ASUS//AppData//Local//Temp";
            $uploads_directoryPic = $this->getParameter('images_directory');
            $filesystempic = new Filesystem();
            $filesystempic->copy($filePathMobilePhoto."//".$fileNamePhoto , $uploads_directoryPic."$fileNamePhoto");
            $user->setPhoto($request->get('photo'));
        }

        $em->flush();
        $jsonContent=$normalizable->normalize($user,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));

    }




    /**
     * @Route("/modifyPasswordEnt", name="entrepriseModifyPassword")
     * @param Request $request
     * @param NormalizerInterface $normalizable
     * @param UserPasswordEncoderInterface $encoder
     * @return Response
     * @throws ExceptionInterface
     */
    public function modifyEtuPassword(Request $request,NormalizerInterface $normalizable,UserPasswordEncoderInterface $encoder): Response
    {   $User=new User();
        $em=$this->getDoctrine()->getManager();
        $user = $this->getDoctrine()->getRepository(User::class)->find($request->get('email'));
        $password=$encoder->encodePassword($User,$request->get('password'));
        $user->setPassword($password);
        $em->flush();
        $jsonContent=$normalizable->normalize($user,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));
    }


    /**
     * @Route("/modifyPasswordUser", name="UserModifyPassword")
     * @param Request $request
     * @param NormalizerInterface $normalizable
     * @param UserPasswordEncoderInterface $encoder
     * @return Response
     * @throws ExceptionInterface
     */
    public function modifyEntPassword(Request $request,NormalizerInterface $normalizable,UserPasswordEncoderInterface $encoder): Response
    {   $User=new User();
        $em=$this->getDoctrine()->getManager();
        $user = $this->getDoctrine()->getRepository(User::class)->findBy(
            ['email' =>$request->get('email')]);

        $password=$encoder->encodePassword($User,$request->get('password'));

        $user[0]->setPassword($password);
        $em->flush();
        $jsonContent=$normalizable->normalize($user,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));
    }


    /**
     * @Route("/api/client/forgetPasswordCheck", name="api_forgetPasswordCheck")
     * @param Request $request
     * @param NormalizerInterface $normalizable
     * @param UserRepository $userRepository
     * @return Response
     * @throws ExceptionInterface
     */

    public function forgetPasswordCheck(Request $request,NormalizerInterface $normalizable,UserRepository $userRepository): Response
    {   $result=false;
        $user=new User();
        $user = $this->getDoctrine()->getRepository(User::class)->findBy(
            ['email' =>$request->get('email')]);

        if($user)
        {
        if($user[0]->getEmail()==$request->get("email"))
        {
            $result=true;

        }}

        $jsonContent=$normalizable->normalize($result,'json',[]);
        return new Response(json_encode($jsonContent));
    }

}