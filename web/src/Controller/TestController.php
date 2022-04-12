<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class TestController extends AbstractController
{

    /**
     * @Route("/testBack", name="backMethod")
     */
    public function back(): Response
    {
        return $this->render('backTemplate/back.html.twig');
    }

}
