<?php

namespace App\Form;

use App\Entity\TblUser;
use App\Entity\TblPost;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\DateTimeType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\HiddenType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class TblPostType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder

            ->add('titlepost',TextType::class,array(
                'label' => false,
                'attr' => array (
                     'class' => "form-control"
                )
            ))
            ->add('descpost',TextType::class,array(
                'label' => false,
                'attr' => array (
                    'class' => "form-control"
                )
            ))
            ->add('statuspost',ChoiceType::class,[
                'label' => false,
                'choices' =>$this->getChoices(),
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
                'class' => TblUser::class ,
                'choice_label' => 'nameuser',
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
}
