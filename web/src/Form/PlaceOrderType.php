<?php

namespace App\Form;

use App\Entity\TblUserorder;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class PlaceOrderType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('createddtm')
            ->add('paydtm')
            ->add('orderaddress')
            ->add('idpaytype')
            ->add('iduser')
            ->add('idstatusorder')
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => TblUserorder::class,
        ]);
    }
}
