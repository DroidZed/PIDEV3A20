<?php

namespace App\Form;

use App\Entity\TblAnswerpost;
use App\Entity\TblOption;
use App\Entity\TblPost;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\RadioType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class AnswerQuizType extends AbstractType
{

    private $em;


    public function __construct(EntityManagerInterface $em)
    {
        $this->em = $em;
    }

    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $quiz = $options['quiz'] ;

        $builder
            ->add('answer',ChoiceType::class,[
                'label' => false,
                'choices' =>$this->getChoices($quiz),
                'expanded' => true ,
                'multiple' => false ,

            ])

        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => TblAnswerpost::class ,
            'quiz' => null
        ]);
    }

    public function getChoices (TblPost $quiz) {
        $optionRepository = $this->em->getRepository(TblOption::class);
        $options = $optionRepository->findBy(['idpost' => $quiz->getIdpost()]) ;

        $optionsRes = [] ;
        foreach($options as $option) {
            $optionsRes[] = $option->getContentoption() ;
        }
        $optionsKeys = array_values($optionsRes) ;
        $arrayOptions = array_combine($optionsKeys,$optionsKeys) ;

        return $arrayOptions ;


    }




}
