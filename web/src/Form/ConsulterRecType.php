<?php

namespace App\Form;

use App\Entity\TblReclamation;
use App\Entity\TblTypecomplaint;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ConsulterRecType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('message', TextType::class, array ('attr' => array ('readonly' => true)))
            ->add('statuscomplaint' , TextType::class, array ('attr' => array ('readonly' => true)))
            ->add('answercomplaint')

            ->add('typecomplaint' , EntityType::class, [
                'class' => TblTypecomplaint::class,
                'disabled'=>true])

        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => TblReclamation::class,
        ]);
    }
}
