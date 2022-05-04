<?php

namespace App\Form;

use App\Entity\TblTypecomplaint;
use App\Entity\User;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ModererType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nom')
            ->add('email')

            ->add('password')
            ->add('tel')
            ->add('photo')
            ->add('roles')
            ->add('leveluser')
            ->add('cv')
            ->add('descuser')
            ->add('activation_token')

            ->add('stateuser' , EntityType::class, [
                'class' => TblTypecomplaint::class,
                'disabled'=>true])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => User::class,
        ]);
    }
}
