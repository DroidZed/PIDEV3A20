<?php

namespace App\Form;

use App\Entity\Property;
use App\Entity\TblOption;
use App\Entity\TblPost;
use App\Entity\User;
use App\Repository\OptionRepository;
use App\Repository\PostRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class QuizType extends AbstractType
{

    private $em;


    public function __construct(EntityManagerInterface $em)
    {
        $this->em = $em;
    }

    public function buildForm(FormBuilderInterface $builder, array $options): void
    {

        $quiz = $builder->getData() ;



        $builder
            ->add('titlepost',TextType::class,array(
                'label' => false,
                'attr' => array (
                    'class' => "form-control"
                )
            ))
            ->add('descpost',TextareaType::class,array(
                'label' => false,
                'attr' => array (
                    'class' => "form-control"
                )
            ))
            ->add('statuspost',ChoiceType::class,[
                'label' => false,
                'choices' =>$this->getChoicesStatus(),
                'attr' => array (
                    'class' => "form-control"
                )
            ])
            ->add('photopost',FileType::class,[
                'label' => false,
                'mapped' => false,
                'required' => false,
                'attr' => array (
                    'class' => "form-control"
                )
            ])

            ->add('iduser',EntityType::class,[
                'class' => User::class ,
                'choice_label' => 'nameuser',
                'label' => false ,
                'attr' => [
                    'class' => 'form-control'
                ]
            ])
            ->add('correctanswer', ChoiceType::class, array(
                'choices' => $this->getChoices($quiz),
                'label' => false,
                'attr' => [
                    'class' => 'form-control'
                ]

            ));
    }


    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => TblPost::class,
            'attr'=>array('novalidate'=>'novalidate')
        ]);
    }

    public function getChoices (TblPost  $quiz) : array {
        $optionRepository = $this->em->getRepository(TblOption::class);
        $options = $optionRepository->findByidpost($quiz->getIdpost());

        $optionsRes = [] ;
        foreach($options as $opt) {
            $optionsRes[] = $opt->getContentOption() ;
        }
        $optionsKeys = array_values($optionsRes) ;
        $arrayOptions = array_combine($optionsKeys,$optionsKeys) ;

        return $arrayOptions ;
    }

    public function getChoicesStatus() {
        $choices = TblPost::STATUS_POST ;
        $output = [] ;
        foreach ($choices as $k => $v ) {
            $output[$v] = $k ;
        }
        return $output ;
    }
}
