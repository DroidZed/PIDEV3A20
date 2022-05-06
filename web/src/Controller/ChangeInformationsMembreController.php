<?php

namespace App\Controller;

use App\Form\MembreModifyType;
use App\Repository\UserRepository;
use App\Services\GetUser;
use App\Entity\User;
use App\Repository\StateUserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;


class ChangeInformationsMembreController extends AbstractController
{
    private $user;


    public function __construct(GetUser $Get_User)
    {
        $this->user = $Get_User->Get_User();

        if($Get_User == null)
        {
            return $this->redirectToRoute('login');
        }
    }


    /**
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\Response
     * @Route("/Membre/Profil/Change_informations", name="profilMembre")
     */
    function modifier(Request $request)
    {   $entityManager = $this->getDoctrine()->getManager();
        $form = $this->createForm(MembreModifyType::class,$this->user);
        $form->handleRequest($request);
        $file = $form->get('photo')->getData();
        if($form->isSubmitted()&&$form->isValid())
        {
            if($file)
            {
                $fileName = md5(uniqid()).'.'.$file->guessExtension();
                $file->move($this->getParameter('images_directory'), $fileName);
                $this->user->setPhoto($fileName);
                $form->getData()->setPhoto($fileName);
            }
            $entityManager->flush();
            $this->addFlash('info','Vos informations sont a jour');
        }

        return $this->render('membre/profile.html.twig',
            ['form_modify' => $form->createView(),
                'User'=>$this->user,
            ]);


    }
    /**
     * @Route("/", name="app_membre_delete", methods={"POST"})
     */
    public function delete(Request $request,  UserRepository $userRepository,StateUserRepository $stateUserRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$this->user->getId(), $request->request->get('_token'))) {
            $em = $this->getDoctrine()->getManager();
            $stateuser =$stateUserRepository->find(3);
            $this->user->setStateuser($stateuser);

            $em->flush();
        }

        return $this->redirectToRoute('loginBack', [], Response::HTTP_SEE_OTHER);
    }





}

