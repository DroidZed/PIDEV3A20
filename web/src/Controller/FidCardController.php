<?php

namespace App\Controller;

use App\Repository\CardTypeRepository;
use App\Repository\FidCardRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/tbl/fidcard")
 */
class FidCardController extends AbstractController
{

    /**
     * @Route("/{idfidcard}", name="card", methods={"GET"})
     */
    public function index($idfidcard, FidCardRepository $fidCardRepository): Response
    {

        return $this->render("frontTemplate/tbl_fidcard/index.html.twig", [
            "card" => $fidCardRepository->find($idfidcard)
        ]);
    }

    /**
     * @Route("/test", name="card", methods={"GET"})
     */
    public function test(): Response
    {
        return $this->render("frontTemplate/test/front.html.twig");
    }

    /**
     * @Route("/{idfidcard}/removePoints", name="rmPts", methods={"GET", "PUT"})
     */
    public function removePoints(int $idfidcard, Request $req, FidCardRepository $fidCardRepository, CardTypeRepository $cardTypeRepository): Response
    {
        $entityManager = $this->getDoctrine()->getManager();

        $amount = $req->query->getInt("amount");

        $fidelityCard = $fidCardRepository->find($idfidcard);

        $totalPoints = $fidelityCard->getNbpointsfid() - $amount;

        if ($totalPoints <= 0) {
            $fidelityCard->setNbpointsfid(0);
            $fidelityCard->setIdcardtype($cardTypeRepository->find(1)); // -> Bronze
            $entityManager->flush();

            return $this->render("frontTemplate/tbl_fidcard/index.html.twig", [
                "card" => $fidCardRepository->find($idfidcard),
                "err" => $totalPoints < 0 ? "You don't have enough points !" : null
            ]);
        }

        elseif (($totalPoints <= 70)) {
            $fidelityCard->setNbpointsfid($totalPoints);
            $fidelityCard->setIdcardtype($cardTypeRepository->find(1)); // -> Bronze
            $entityManager->flush();

            return $this->redirectToRoute("card", [
                "idfidcard" => $idfidcard
            ]);
        }

        elseif ($totalPoints < 150) {
            $fidelityCard->setNbpointsfid($totalPoints);
            $fidelityCard->setIdcardtype($cardTypeRepository->find(2)); // -> Silver
            $entityManager->flush();

            return $this->redirectToRoute("card", [
                "idfidcard" => $idfidcard
            ]);
        }

        else {
            $fidelityCard->setNbpointsfid($totalPoints);
            $fidelityCard->setIdcardtype($cardTypeRepository->find(3)); // -> Gold
            $entityManager->flush();

            return $this->redirectToRoute("card", [
                "idfidcard" => $idfidcard
            ]);
        }
    }

    /**
     * @Route("/{idfidcard}/addPoints", name="addPts", methods={"GET", "PUT"})
     */
    public function addPoints(int $idfidcard, Request $req, FidCardRepository $fidCardRepository, CardTypeRepository $cardTypeRepository)
    {

        $entityManager = $this->getDoctrine()->getManager();

        $amount = $req->query->getInt("amount");

        if ($amount < 0) {
            return $this->render("frontTemplate/tbl_fidcard/index.html.twig", [
                "card" => $fidCardRepository->find($idfidcard),
                "err" => "An error occured while adding the points !"
            ]);
        }

        $fidelityCard = $fidCardRepository->find($idfidcard);

        $fidelityCard->setNbpointsfid($fidelityCard->getNbpointsfid() + $amount);

        $totalPoints = $fidelityCard->getNbpointsfid();

        if ($totalPoints < 70) {
            $fidelityCard->setIdcardtype($cardTypeRepository->find(1)); // -> Bronze
        }

        elseif ($totalPoints > 70 && $totalPoints < 150) {
            $fidelityCard->setIdcardtype($cardTypeRepository->find(2)); // -> Silver
        }

        else {
            $fidelityCard->setIdcardtype($cardTypeRepository->find(3)); // -> Gold
        }

        $entityManager->flush();
        return $this->redirectToRoute("card", [
            "idfidcard" => $idfidcard
        ]);

    }
}
