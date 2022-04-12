<?php

namespace App\Form;

use App\Entity\TblProduct;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ProductType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nameproduct')
            ->add('priceproduct')
            ->add('qtyproduct')
            ->add('imageproduct')
            ->add('idcategory')
            ->add('ajouter',SubmitType::class);

        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => TblProduct::class,
        ]);
    }
}
