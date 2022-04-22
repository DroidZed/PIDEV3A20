<?php

namespace App\Controller;

use App\Entity\TblStateuser;
use App\Entity\TblTypecomplaint;
use App\Entity\User;
use App\Form\AdminType;
use App\Form\MembreModifyType;
use App\Form\ModererType;
use App\Repository\StateUserRepository;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;


class AdminController extends AbstractController
{

    private $encoder;


    public function __construct(UserPasswordEncoderInterface $encoder)
    {
        $this->encoder = $encoder;


    }
    /**
     * @Route("/admin", name="admin_index", methods={"GET"})
     */
    public function index(): Response
    {
        $Admins = $this->getDoctrine()->getRepository(User::class)->findByRole("ROLE_ADMIN");

        return $this->render('backTemplate/admin/index.html.twig', [
            'admins' => $Admins,
        ]);
    }




    /**
     * @Route("/admin/newAdmin", name="admin_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $user = new User();
        $form = $this->createForm(AdminType::class, $user);
        $user->setRoles(array('ROLE_ADMIN'));

        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $password = $form->getData()->getPassword();
            $form->getData()->setPassword($this->encoder->encodePassword($user, $password));
            $entityManager->persist($user);
            $entityManager->flush();
            return $this->redirectToRoute('admin_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('backTemplate/admin/new.html.twig', [
            'admin' => $user,
            'form' => $form->createView(),
        ]);
    }


    /**
     * @Route("/admin/listEntreprise", name="entreprise_index", methods={"GET"})
     */
    public function list_entreprise(): Response
    { $states=$this->getDoctrine()->getRepository(TblStateuser::class)->findAll();
        $Entreprises=$this->getDoctrine()->getRepository(User::class)->findBy([
            "roles" => ["0" => '["ROLE_ENTREPRISE"]']
        ]);


        return $this->render('backTemplate/listEntreprise.html.twig', [
            'entreprises' => $Entreprises,
            'state'=>$states,
        ]);
    }


    /**
     * @Route("/admin/listMembre", name="membre_index", methods={"GET"})
     */
    public function list_membre(): Response
    { $states=$this->getDoctrine()->getRepository(TblStateuser::class)->findAll();
        $Membre=$this->getDoctrine()->getRepository(User::class)->findBy([
            "roles" => ["0" => '["ROLE_MEMBRE"]']]);
        return $this->render('backTemplate/listMembre.html.twig', [
            'membres' => $Membre,
            'state'=>$states,
        ]);
    }


    /**
     * @Route("admin/search/{searchString}", name="search")
     */
    public function search($searchString): JsonResponse
    {
        $serializer = new Serializer([new ObjectNormalizer()]);
        $repository = $this->getDoctrine()->getRepository(User::class);
        $users = $repository->findBynom($searchString);
        $data=$serializer->normalize($users);
        return new JsonResponse($data);
    }

    /**
     * @Route("admin/listEntreprise/search/{searchString}", name="searchEnt")
     */
    public function searchEnt($searchString): JsonResponse
    {
        $serializer = new Serializer([new ObjectNormalizer()]);
        $repository = $this->getDoctrine()->getRepository(User::class);
        $users = $repository->findBynom($searchString);
        $data=$serializer->normalize($users);
        return new JsonResponse($data);
    }

    /**
     *
     * @Route("admin/listMembre/search/{searchString}", name="searchMem")
     */
    public function searchMem($searchString): JsonResponse
    {
        $serializer = new Serializer([new ObjectNormalizer()]);
        $repository = $this->getDoctrine()->getRepository(User::class);
        $users = $repository->findBynom($searchString);
        $data=$serializer->normalize($users);
        return new JsonResponse($data);

    }
    /**
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\Response
     * @Route("/admin/Profil/moderer/{iduser}", name="moderer")
     */
    function modifier(Request $request, $iduser,StateUserRepository $stateUserRepository)
    { $user=new User();
        $user=  $this->getDoctrine()->getRepository(User::class)->find($iduser);
        if($user->getStateuser()->getName()=='Active')
        $stateuser =$stateUserRepository->find(2);
        else
            $stateuser =$stateUserRepository->find(0);
        $user->setStateuser($stateuser);
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->flush();
        return $this->redirectToRoute('membre_index', [], Response::HTTP_SEE_OTHER);

    }
    /**
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\Response
     * @Route("/admin/listeEntreprise/{iduser}", name="modererent")
     */
    function modif(Request $request, $iduser,StateUserRepository $stateUserRepository)
    { $user=new User();
        $user=  $this->getDoctrine()->getRepository(User::class)->find($iduser);
        if($user->getStateuser()->getName()=='Active')
            $stateuser =$stateUserRepository->find(2);
        else
            $stateuser =$stateUserRepository->find(0);
        $user->setStateuser($stateuser);
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->flush();
        return $this->redirectToRoute('entreprise_index', [], Response::HTTP_SEE_OTHER);

    }
}
