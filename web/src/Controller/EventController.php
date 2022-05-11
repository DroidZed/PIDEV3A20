<?php

namespace App\Controller;

use App\Entity\TblParticipation;
use App\Entity\User;
use Symfony\Component\Routing\Annotation\Route ;
use App\Entity\TblPost;
use App\Form\EventType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\RouterInterface;
use App\Services\GetUser;
use App\Services\SendEmailService;


class EventController extends AbstractController
{

    /**
     * @var RouterInterface
     */
    private $router;
    private $getUser;

    /**
     * @param RouterInterface $router
     * @param GetUser $getUser
     */
    public function __construct(RouterInterface $router,GetUser $getUser)
    {
        $this->router = $router;
        $this->getUser=$getUser;

    }

    /**
     * @Route("/admin/event", name="app_event_index", methods={"GET"})
     */
    public function index(EntityManagerInterface $entityManager): Response
    {
        $events = $entityManager
            ->getRepository(TblPost::class)
            ->findBytypepost('Event');


        return $this->render('backTemplate/event/index.html.twig', [
            'events' => $events,
        ]);
    }

    /**
     * @Route("/admin/event/new", name="app_event_new", methods={"GET", "POST"})
     */
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $event = new TblPost();
        $form = $this->createForm(EventType::class, $event);
        $form->handleRequest($request);


        if ($form->isSubmitted() && $form->isValid()) {

            $file = $request->files->get('event')['photopost'];
            $addressEvent = $form->get('addressevent')->getData();

            $event->setAddressevent($addressEvent) ;
            $event->setLatitude(TblPost::LOCATIONS[$addressEvent]['latitude']);
            $event->setLongitude(TblPost::LOCATIONS[$addressEvent]['longitude']) ;

            $uploads_directory = $this->getParameter('uploads_directory') ;

            $filename = null ;
            if ($file != null) {
                $filename = md5(uniqid()). '.' . $file->guessExtension() ;
                $file->move(
                    $uploads_directory ,
                    $filename
                );
            }
            $event->setTypepost('Event') ;
            $event->setPhotopost($filename) ;

            $event->setPosteddtm(new \DateTime('now')) ;


            $entityManager->persist($event);
            $entityManager->flush();
            $this->addFlash('success' , 'Event added with success !');
            return $this->redirectToRoute('app_event_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('backTemplate/event/new.html.twig', [
            'event' => $event,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/admin/event/{idpost}", name="app_event_show", methods={"GET"})
     */
    public function show(TblPost $event): Response
    {
        return $this->render('backTemplate/event/show.html.twig', [
            'event' => $event,
        ]);
    }

    /**
     * @Route("/admin/event/{idpost}/edit", name="app_event_edit", methods={"GET", "POST"})
     */
    public function edit(Request $request, TblPost $event , EntityManagerInterface $entityManager , \Swift_Mailer $mailer , SendEmailService $mailerService): Response
    {
        $form = $this->createForm(EventType::class, $event);
        $form->handleRequest($request);
        $photoEvent = $event->getPhotopost() ;

        if ($form->isSubmitted() && $form->isValid()) {

            //Informations from Form
            $addressEvent = $form->get('addressevent')->getData();
            $startDate = $form->get('startdtm')->getData() ;
            $endDate = $form->get('enddtm')->getData() ;

            //Informations from dataBase
            $addressEventDB = $event->getAddressevent() ;
            $startDateDB = $event->getStartdtm() ;
            $endDateDB = $event->getEnddtm() ;

            $event->setAddressevent($addressEvent) ;
            $event->setLatitude(TblPost::LOCATIONS[$addressEvent]['latitude']);
            $event->setLongitude(TblPost::LOCATIONS[$addressEvent]['longitude']) ;

                $file = $request->files->get('event')['photopost'];

                if ($file == null) {
                    $event->setPhotopost($photoEvent) ;
                }
                else {
                    $uploads_directory = $this->getParameter('uploads_directory') ;

                        $filename = md5(uniqid()). '.' . $file->guessExtension() ;
                        $file->move(
                            $uploads_directory ,
                            $filename
                        );

                    $event->setPhotopost($filename) ;
                }

            $entityManager->flush();

            /*$uow = $entityManager->getUnitOfWork();
            $uow->computeChangeSets();

            $changeset = $uow->getEntityChangeSet($event);*/


             //Get event Participants
            $participantsRepository = $entityManager->getRepository(TblParticipation::class) ;
            $participants = $participantsRepository->findBy(['idpost' => $event->getIdpost()]) ;

            //Send Email of Notification to participants of event
            foreach ($participants as  $participant) {
                $participant = $entityManager->getRepository(User::class)->find($participant->getIdUser()) ;


                if ($addressEvent != null) { //Modifications had occured on address
                    $mailerService->sendEmail(
                        $participant->getEmail() ,
                        'Nebula Gaming wants to tell you something ! ' ,
                        "Modifications had occured on address of ".$event->getTitlePost() );
                        
                }if (  $startDate != null) {
                    $mailerService->sendEmail(
                        $participant->getEmail() ,
                        'Nebula Gaming wants to tell you something ! ' ,
                        "Modifications had occured on start Date of ".$event->getTitlePost() );

                }if ($endDate != null) {

                    $mailerService->sendEmail(
                        $participant->getEmail() ,
                        'Nebula Gaming wants to tell you something ! ' ,
                        "Modifications had occured on end Date of ".$event->getTitlePost() );

                }
            }



            $this->addFlash('success' , 'Event updated with success !');
            return $this->redirectToRoute('app_event_index', ["idpost" => $event->getIdpost()], Response::HTTP_SEE_OTHER);
        }

        return $this->render('backTemplate/event/edit.html.twig', [
            'event' => $event,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/admin/event/{idpost}", name="app_event_delete", methods={"POST"})
     */
    public function delete(Request $request, TblPost $event, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$event->getIdpost(), $request->request->get('_token'))) {
            $entityManager->remove($event);
            $entityManager->flush();
        }

        $this->addFlash('success' , 'Event deleted with success !');
        return $this->redirectToRoute('app_event_index', [], Response::HTTP_SEE_OTHER);
    }


    public function mailer ( User $user , \Swift_Mailer $mailer , String $mailContent) {

        $from = array("hindzaafouri19@gmail.com" => "Nebula Gaming");
        $message = (new \Swift_Message("Nebula Gaming Wants to tell you Something !"))
        //Expediteur
        ->setFrom($from)
        //Destinataire
        ->setTo($user->getEmail())
        ->setSubject("Nebula Gaming Wants to tell you Something !")
        ->setBody($mailContent , 'text/plain') ;

        //on Envoie le messsage
        $mailer->send($message) ;
        $this->addFlash('message' , 'Le email a bien été envoyé aux participants de cet évenement ') ;
    }
}
