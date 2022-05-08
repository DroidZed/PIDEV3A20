<?php

namespace App\Services;
use phpDocumentor\Reflection\Types\Void_;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;

class SendEmailService
{

    private $mailer;

    public function __construct(MailerInterface $mailer)
    {
        $this->mailer = $mailer;
    }

    public function sendEmail($to = 'rayen.bakali@gmail.com',
                              $subject ='Product Online',
                              $content ='<p>See Twig integration for better HTML integration!</p>'): void
    {
        $email = (new Email())
            ->from('nebulaagaming67@gmail.com')
            ->to($to)
            //->cc('cc@example.com')
            //->bcc('bcc@example.com')
            //->replyTo('fabien@example.com')
            //->priority(Email::PRIORITY_HIGH)
            ->subject($subject)
            //->text('Your Product is online')
            ->html($content);

        $this->mailer->send($email);

        // ...
    }
}