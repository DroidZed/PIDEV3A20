<?php

namespace App\Controller;

use App\Repository\FidCardRepository;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/dummyProfile")
 */
class UserProfileController extends AbstractController
{
    /**
     * @Route("/{idUser}", name="dummyProfile")
     */
    public function index(int $idUser, UserRepository $userRepository, FidCardRepository $fidCardRepository): Response
    {
        return $this->render('user_profile/index.html.twig', [
            'dummyU' => $userRepository->find($idUser),
            'fidCard' => $fidCardRepository->find($idUser)
        ]);
    }
}
