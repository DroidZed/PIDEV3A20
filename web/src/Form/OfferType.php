<?php

namespace App\Form;

use App\Entity\TblDomain;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\MoneyType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class OfferType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('titleOffer')
            ->add('TypeOffer')
            ->add('descoffer',TextareaType::class )
            ->add('startDTM', DateType::class)
            ->add('endDTM',DateType::class)
            ->add('iDdomain', EntityType::class,[
                'class'=>TblDomain::class, 'choice_label'=>'name'
            ])
            ->add('salaryOffer')
            ->add('regionOffer')
            ->add('addressOffer')
            ->add('Submit', SubmitType::class);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            // Configure your form options here
        ]);
    }
}
