<?php

namespace App\Controller\WebServices_Order;

use Dompdf\Dompdf;
use Dompdf\Options;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Mailer\Exception\TransportExceptionInterface;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use App\Entity\JsonResponseDAO;
use App\Repository\CardTypeRepository;
use App\Repository\FidCardRepository;
use App\Repository\PayTypeRepository;
use App\Repository\OrderLineRepository;
use App\Repository\ProductRepository;
use App\Repository\StatusOrderRepository;
use App\Repository\UserOrderRepository;
use App\Repository\UserRepository;
use App\Services\GetUser;
use App\Services\QrCodeService;
use App\Services\ReceiptMailingService;
use App\Entity\TblOrderline;
use App\Entity\TblProduct;
use App\Entity\TblUserorder;
use App\Entity\User;
use App\Form\PlaceOrderType;


class Order_WS extends AbstractController
{
    /**
     * @Route("/checkout", name="checkOutJson", methods={"POST"})
     */
    public function new(Request               $request,
                        FidCardRepository     $fidelityCardRepository,
                        ReceiptMailingService $mailerService,
                        QrCodeService         $qrCodeService,
                        UserRepository        $userRepository,
                        UserOrderRepository   $userOrderRepository,
                        OrderLineRepository   $orderLineRepository,
                        ProductRepository     $productRepository,
                        StatusOrderRepository $statusOrderRepository,
                        PayTypeRepository       $payTypeRepository,
                        NormalizerInterface   $normalizer): JsonResponse
    {


        $data = json_decode($request->getContent(), true);




        $user = $userRepository->find($data["idUser"]);
        $userOrder = new TblUserorder();


        $userOrder->setOrderaddress($data["address"]);
        $userOrder->setIdpaytype($payTypeRepository->findOneBy(["paytype" => $data["payType"]]));

        // Adding points to the user

        $card = $fidelityCardRepository->findOneBy(["iduser" => $user]);

        $card->setNbpointsfid($card->getNbpointsfid() + rand(1, 10));

        $fidelityCardRepository->add($card, false);

        $userOrder->setCreateddtm(new \DateTime());


        if ($userOrder->getIdpaytype()->getPaytype() == "CREDIT_CARD") {
            $userOrder->setIdstatusorder($statusOrderRepository->findOneBy(["statusorder" => "PENDING"]));
            $userOrder->setPaydtm(new \DateTime());
        }

        if ($userOrder->getIdpaytype()->getPaytype() == "DELIVERY") {
            $userOrder->setIdstatusorder($statusOrderRepository->findOneBy(["statusorder" => "DELIVERY"]));
            $userOrder->setPaydtm(null);
        }

        $userOrder->setIduser($user);

        $savedOrder = $userOrderRepository->save($userOrder);
        $orderLines = [];

        $cartItems = $data["cartItems"];

        for ($i = 0; $i < count($cartItems); $i++) {
            $temp = new TblOrderline();
            $prod = $productRepository->find($cartItems[$i]["idProd"]);

            $prod->setQtyproduct($prod->getQtyproduct() - 1);

            if ($prod->getQtyproduct() <= 0) {
                $prod->setQtyproduct(0);
            }

            $productRepository->add($prod, false);

            $temp->setIdproduct($prod);
            $temp->setNumberorder($userOrderRepository->find($savedOrder));
            $temp->setQuantordline($cartItems[$i]["qt"]);
            $orderLineRepository->add($temp);
            $orderLines += [$prod->getNameproduct() => $temp];
        }

        $this->sendEmail($user, $userOrder, $mailerService, $qrCodeService, $orderLines);

        return $this->json(new JsonResponseDAO("Your order has been placed, please verify your email the receipt."));

    }


    /**
     * @param  $user
     * @param TblUserorder $order
     * @param ReceiptMailingService $mailerService
     * @param QrCodeService $qrCodeService
     * @param TblOrderline[] $orderLines
     * @return void
     */
    public function sendEmail(
        $user,
        TblUserorder $order,
        ReceiptMailingService $mailerService,
        QrCodeService $qrCodeService,
        array $orderLines): void
    {
        try {

            $qrCodeImageName = 'qrC' . $order->getNumberorder() . '_.png';
            $pdfFileName = 'pdf' . $order->getNumberorder() . '_.pdf';

            $qrCodesDir = $this->getParameter('kernel.project_dir') . '/public/frontTemplate/images/qr/';
            $pdfsDir = $this->getParameter('kernel.project_dir') . '/public/frontTemplate/pdfs/';

            $qrSavePath = $qrCodesDir . $qrCodeImageName;

            $pdfSavePath = $pdfsDir . $pdfFileName;

            // generate the qr code
            $qrCodeService->gen('Your order number: ' . $order->getNumberorder(), $qrSavePath);

            $dompdf = new Dompdf(new Options([
                'defaultFont' => 'Arial',
                'isHtmlParser' => true,
                'isRemoteEnabled' => true
            ]));

            // Retrieve the HTML generated in our twig file
            $html = $this->renderView('frontTemplate/orders/receiptPDF.html.twig',
                [
                    'website' => 'Nebula Gaming',
                    'name' => $order->getIduser()->getNom(),
                    'address' => $order->getOrderaddress(),
                    'orderNo' => $order->getNumberorder(),
                    'orderLines' => $orderLines
                ]);

            // Load HTML to Dompdf
            $dompdf->loadHtml($html);

            // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
            $dompdf->setPaper('A4');

            // Render the HTML as PDF
            $dompdf->render();

            // Store PDF Binary Data
            $output = $dompdf->output();

            // Write file to the desired path
            file_put_contents($pdfSavePath, $output);

            // send the email
            $mailerService->send(
                $user->getEmail(),
                $this->getParameter("app.mailfrom"),
                "[Receipt " . $order->getNumberorder() . " - " . $order->getIduser()->getNom() . "]",
                $qrCodeImageName,
                $pdfSavePath,
                $orderLines,
                $order);

        } catch (TransportExceptionInterface $e) {
            echo $e->getMessage();
        }
    }
}