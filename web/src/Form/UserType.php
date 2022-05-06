<?php

namespace App\Form;

use App\Entity\User;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class UserType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nom')
            ->add('email')
            ->add('createddtm')
            ->add('desactivationdtm')
            ->add('password')
            ->add('tel')
            ->add('photo')
            ->add('roles')
            ->add('leveluser')
            ->add('cv')
            ->add('descuser')
            ->add('activationToken')
            ->add('idbadge')
            ->add('stateuser')
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => User::class,
        ]);
    }
}
