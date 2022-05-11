<?php

namespace App\Form;

use App\Entity\TblPost;
use App\Entity\User;
use App\Helpers_NewsFeed\LocationHelper;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\DateTimeType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\NumberType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\GreaterThan;
use Symfony\Component\Validator\Context\ExecutionContextInterface;
use Symfony\Component\Validator\Constraints;

class EventType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('titlepost',TextType::class,[
                'label' => false ,
                'attr' => [
                    'class' => "form-control"
                ]
            ])
            ->add('descpost',TextareaType::class,[
                'label' => false,
                'attr' => [
                    'class' => "form-control"
                ]

            ])
            ->add('statuspost' , ChoiceType::class,[
                'label' => false,
                'choices' =>$this->getChoices(),
                'attr' => [
                    'class' => "form-control"
                ]
            ])
            ->add('photopost',FileType::class,[
                'label' => false,
                'mapped' => false,
                'required' => false,
                'attr' => [
                    'class' => "form-control"
                ]
            ])

            ->add('startdtm',DateTimeType::class,[
                'label' => false ,
                'required' => false ,
                'attr' => [
                    'class' => 'form-control'

                ],
                 'placeholder' => [
                    'year' => 'Year', 'month' => 'Month', 'day' => 'Day',
                    'hour' => 'Hour', 'minute' => 'Minute', 'second' => 'Second',
                ],
            ])

            ->add('enddtm',DateTimeType::class,[
                'label' => false ,
                'required' => false ,
                'attr' => [
                    'class' => 'form-control'
                ],
                'placeholder' => [
                    'year' => 'Year', 'month' => 'Month', 'day' => 'Day',
                    'hour' => 'Hour', 'minute' => 'Minute', 'second' => 'Second',
                ],
                'constraints' => [
                    new GreaterThan([
                        'propertyPath' => 'parent.all[startdtm].data'
                    ]),
                ]
            ])

            ->add('addressevent',ChoiceType::class,[
                'label' => false,
                'choices' =>$this->getLocations(),
                'attr' => [
                    'class' => "form-control"
                ]
            ])
            ->add('nbticketavailable',NumberType::class,[
                'label' => false ,
                'attr' => [
                    'class' => 'form-control'
                ]
            ])

            ->add('iduser',EntityType::class,[
                'class' => User::class ,
                'choice_label' => 'nom',
                'label' => false ,
                'attr' => [
                    'class' => 'form-control'
                ]
            ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => TblPost::class,
            'selected_option' => null ,
            'attr'=>array('novalidate'=>'novalidate')
        ]);
    }

    public function getChoices() {
        $choices = TblPost::STATUS_POST ;
        $output = [] ;
        foreach ($choices as $k => $v ) {
            $output[$v] = $k ;
        }
        return $output ;
    }

    public function getLocations () {
        $locations = array_keys(TblPost::LOCATIONS) ;
        $array = array_combine($locations , $locations) ;
        return $array ;
    }
}
