<?php

namespace App\Controller;

use App\Form\UpdateOrderStateType;
use App\Repository\PayTypeRepository;
use App\Repository\StatusOrderRepository;
use App\Repository\UserOrderRepository;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;


class MemberOrdersAdminController extends AbstractController
{

    /**
     * @Route("/admin/orders", name="adminOrders", methods={"GET"})
     * @param Request|null $req
     * @param UserOrderRepository $userOrderRepository
     * @return Response
     */
    public function index(?Request $req, UserOrderRepository $userOrderRepository): Response
    {

        $page = $req->get("page") ? $req->get("page") : 1;

        $orders = $userOrderRepository->getAllOrdersAdmin($page);

        $maxPages = ceil($orders->count() / 5);

        return $this->render('backTemplate/listOrders.html.twig', [
            'ordersList' => $orders,
            'maxPages' => $maxPages,
            'currPage' => $page
        ]);
    }

    /**
     * @Route("/admin/orders/viewOrder/{orderNumber}", name="viewOrder")
     * @param $orderNumber
     * @param Request $req
     * @param UserOrderRepository $userOrderRepository
     * @param StatusOrderRepository $statusOrderRepository
     * @return Response
     */
    public function viewOrder($orderNumber, Request $req, UserOrderRepository $userOrderRepository, StatusOrderRepository $statusOrderRepository): Response
    {

        $order = $userOrderRepository->find($orderNumber);

        $form = $this->createForm(UpdateOrderStateType::class, $order);

        $form->handleRequest($req);

        if ($form->isSubmitted()) {

            $orderStatus = $req->get("status");

            $status = $statusOrderRepository->findOneBy(["statusorder" => $orderStatus]);

            $order->setIdstatusorder($status);

            if ($orderStatus == "CANCELLED" || $orderStatus == "DELIVERY") {

                $order->setPaydtm(null);
            }

            $userOrderRepository->save($order, false);

            return $this->redirect($req->getUri());
        }

        return $this->render(
            "backTemplate/showOrder.html.twig",
            [
                "order" => $order,
                "form" => $form->createView()
            ]
        );
    }

    /**
     * @Route("/admin/orders/search", name="searchOrders", methods={"POST"})
     * @param Request $req
     * @param UserRepository $userRepository
     * @param StatusOrderRepository $statusOrderRepository
     * @param PayTypeRepository $payTypeRepository
     * @param UserOrderRepository $orderRepository
     * @return Response
     */
    public function search(Request               $req,
                           UserRepository        $userRepository,
                           StatusOrderRepository $statusOrderRepository,
                           PayTypeRepository     $payTypeRepository,
                           UserOrderRepository   $orderRepository): Response
    {

        $data = json_decode($req->getContent(), true);

        $criteria = "";
        $term = "";

        switch ($data["criteria"]) {
            case "Status":
                $criteria = "idstatusorder";
                $term = $statusOrderRepository->findBy(["statusorder" => $data["term"]]);
                break;
            case "Payment Type":
                $criteria = "idpaytype";
                $term = $payTypeRepository->findBy(["paytype" => $data["term"]]);
                break;
            case "Issuer":
                $criteria = "iduser";
                $term = $userRepository->findBy(["nameuser" => $data["term"]]);
                break;
            default:
                break;
        }

        return $this->json($orderRepository->findBy([
            $criteria => $term
        ]), Response::HTTP_OK);
    }

    /**
     * @Route("/admin/orders/sortThem", name="sortThem", methods={"GET"})
     * @param Request $req
     * @param UserOrderRepository $repo
     * @return Response
     */
    public function sortThem(Request $req, UserOrderRepository $repo): Response
    {
        $page = $req->get("page") ? $req->get("page") : 1;

        $orders = $repo->getOrdersSortedPaginated($req->get("criteria"), $req->get("order"), $page);

        $maxPages = ceil($orders->count() / 5);

        return $this->render(
            "backTemplate/listOrders.html.twig",
            [
                "ordersList" => $orders,
                'maxPages' => $maxPages,
                'currPage' => $page,
            ]);
    }
}