<?php

namespace App\Controller;

use App\Entity\TblPost;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Routing\Annotation\Route ;


class MapController extends AbstractController
{

    /**
     * @Route ("/map/{idpost}" , name="app_event_map")
     */
    public function showMap (TblPost $event) {

        return $this->render('frontTemplate/Map/mapEvent.html.twig', [
            'latitude' => json_encode($event->getLatitude()) ,
            'longitude' =>  json_encode($event->getLongitude()) ,
        ]) ;
    }
}