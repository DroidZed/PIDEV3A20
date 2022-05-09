<?php

namespace App\Form;

use App\Entity\Property;
use App\Entity\TblOption;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class TblOptionType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('statusoptions',ChoiceType::class ,array(
                'choices' => $this->getChoices(),
                'label' => false ,
                'attr' => array (
                    'class' => "form-control" )
            ))
            ->add('contentoption',null,array(
                'label' => false ,
                'attr' => array (
                    'class' => "form-control" )
            ))
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => TblOption::class,
            'attr'=>array('novalidate'=>'novalidate')
        ]);
    }

    public function getChoices () {
        $choices = TblOption::STATUS_OPTION ;
        $output = [] ;
        foreach ($choices as $k => $v ) {
            $output[$v] = $k ;
        }
        return $output ;
    }
}
