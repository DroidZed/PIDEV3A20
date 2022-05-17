<?php

namespace App\Controller\WebServices_FidCard;

use App\Repository\FidCardRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Exception\ExceptionInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

class FidelityCardWS extends AbstractController
{

    /**
     * @Route("/fidcard/get", name="getCardOfUser")
     * @throws ExceptionInterface
     */
    public function getUserCardDetails(Request $req, FidCardRepository $fidCardRepository, NormalizerInterface $normalizer): JsonResponse
    {

        return $this->json(
            $normalizer->normalize(
                $fidCardRepository->findOneBy(
                    ["iduser" => $req->get('idUser')]
                ),
                'json',
                ['groups' => 'fidcard']
            )
        );
    }
}