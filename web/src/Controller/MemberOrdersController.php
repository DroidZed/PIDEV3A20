<?php

namespace App\Controller;

use App\Entity\TblOrderline;
use App\Entity\TblProduct;
use App\Entity\TblUser;
use App\Entity\TblUserorder;
use App\Form\PlaceOrderType;
use App\Repository\OrderLineRepository;
use App\Repository\StatusOrderRepository;
use App\Repository\UserOrderRepository;
use App\Repository\ProductRepository;
use App\Repository\UserRepository;
use App\Services\MailerService;
use App\Services\QrCodeService;
use DateTime;
use Dompdf\Dompdf;
use Dompdf\Options;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Mailer\Exception\TransportExceptionInterface;
use Symfony\Component\Routing\Annotation\Route;


class MemberOrdersController extends AbstractController
{
    private SessionInterface $session;

    public function __construct(SessionInterface $session)
    {
        $this->session = $session;
    }

    /**
     * @Route("/myOrders", name="myOrders", methods={"GET"})
     */
    public function index(UserOrderRepository $userOrderRepository): Response
    {
        return $this->render('frontTemplate/orders/index.html.twig', [
            'ordersList' => $userOrderRepository->findAll(),
        ]);
    }

    /**
     * @Route("/checkout", name="checkOut", methods={"GET", "POST"})
     */
    public function new(Request             $request,
                        UserRepository $userRepo,
                        MailerService $mailerService,
                        QrCodeService  $qrCodeService,
                        UserOrderRepository $userOrderRepository,
                        OrderLineRepository $orderLineRepository,
                        ProductRepository $productRepository,
                        StatusOrderRepository $statusOrderRepository,
                        CartController      $cartController): Response
    {
        $user = $userRepo->find(1);
        $userOrder = new TblUserorder();
        $cartItems = $cartController->getFromCart();

        $form = $this->createForm(PlaceOrderType::class, $userOrder);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $userOrder->setCreateddtm(new DateTime());
            if ($userOrder->getIdpaytype()->getPaytype() == "CREDIT_CARD") {
                $userOrder->setIdstatusorder($statusOrderRepository->findOneBy(["statusorder" => "PENDING"]));
            }

            if ($userOrder->getIdpaytype()->getPaytype() == "DELIVERY") {
                $userOrder->setIdstatusorder($statusOrderRepository->findOneBy(["statusorder" => "DELIVERY"]));
            }

            $userOrder->setIduser($user);

            $savedOrder = $userOrderRepository->save($userOrder);
            $orderLines = [];

            foreach ($cartItems as $v) {
                    $temp = new TblOrderline();
                    $temp->setIdproduct($productRepository->find($v->getProduct()->getIdproduct()));
                    $temp->setNumberorder($userOrderRepository->find($savedOrder));
                    $temp->setQuantordline($v->getQuantity());
                    $orderLineRepository->add($temp);
                    $orderLines += [$v->getProduct()->getNameproduct() => $temp];
            }

            $this->sendEmail($user, $userOrder,$mailerService,$qrCodeService, $orderLines);

            return $this->redirectToRoute('test', [], Response::HTTP_SEE_OTHER);
        }


        return $this->render('frontTemplate/orders/new.html.twig', [
            'cart' => $cartItems,
            'user' => $user,
            'form' => $form->createView(),
        ]);
    }


    /**
     * @param TblUser $user
     * @param TblUserorder $order
     * @param MailerService $mailerService
     * @param QrCodeService $qrCodeService
     * @param TblOrderline[] $orderLines
     * @return void
     */
    public function sendEmail(
        TblUser       $user,
        TblUserorder  $order,
        MailerService $mailerService,
        QrCodeService $qrCodeService,
        array         $orderLines): void
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
                    'name' => $order->getIduser()->getNameuser(),
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
                $user->getEmailuser(),
                $this->getParameter("app.mailfrom"),
                "[Receipt " . $order->getNumberorder() . " - " . $order->getIduser()->getNameuser() . "]",
                $qrSavePath,
                $pdfSavePath,
                $orderLines,
                $order);

        } catch (TransportExceptionInterface $e) {
            echo $e->getMessage();
        }
    }
}
