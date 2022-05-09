<?php

namespace App\Controller;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use Symfony\Component\Routing\Annotation\Route;

class MailerController extends AbstractController
{
    public function __construct (MailerInterface $mailer){
        $this->mailer=$mailer;
    }
    /**
     * @Route("/email")
     */
    public function sendEmail($to = 'rayen.bakali@gmail.com',
                              $subject='Time for Symfony Mailer!',
                              $content = '<p>See Twig integration for better HTML integration!</p>'): Response
    {
        $email = (new Email())
            ->from('nebulaagaming67@gmail.com')
            ->to($to)
            //->cc('cc@example.com')
            //->bcc('bcc@example.com')
            //->replyTo('fabien@example.com')
            //->priority(Email::PRIORITY_HIGH)
            ->subject($subject)
            //->text('Sending emails is fun again!')
            ->html($content);

        $mailer->send($email);

        // ...
    }
}