<?php

namespace App\Services;

use App\Entity\CartItem;
use App\Entity\TblUserorder;
use Symfony\Bridge\Twig\Mime\TemplatedEmail;
use Symfony\Component\Mailer\Exception\TransportExceptionInterface;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Address;

class ReceiptMailingService
{
    private MailerInterface $mailer;

    /**
     * @param MailerInterface $MAILER
     */
    public function __construct(MailerInterface $MAILER)
    {
        $this->mailer = $MAILER;
    }

    /**
     * @param string $to
     * @param string $from
     * @param string $subject
     * @param string $qrPath
     * @param string $pdfPath
     * @param array $orderLines
     * @param TblUserorder $order
     * @throws TransportExceptionInterface
     */
    public function send(string $to,
                         string $from,
                         string $subject,
                         string $qrPath,
                         string $pdfPath,
                         array $orderLines,
                         TblUserorder $order
    ): void
    {
        $email = (new TemplatedEmail())
            ->from(new Address($from))
            ->to(new Address($to))
            ->subject($subject)
            ->context([
                'website' => 'Nebula Gaming',
                'qrPath' => $qrPath,
                'name' => $order->getIduser()->getNom(),
                'address' => $order->getOrderaddress(),
                'orderNo' => $order->getNumberorder(),
                'orderLines' => $orderLines
            ])
            ->attach(fopen($pdfPath, 'r'))
            ->htmlTemplate('frontTemplate/orders/receipt.html.twig');

        $this->mailer->send($email);
    }
}