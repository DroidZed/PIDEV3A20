<?php

namespace App\Form;

use App\Entity\TblProduct;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Vich\UploaderBundle\Form\Type\VichImageType;

class ProductType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nameproduct')
            ->add('priceproduct')   
            ->add('qtyproduct')
            ->add('imageFile',VichImageType::class , ['label'=> 'Image'])
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
