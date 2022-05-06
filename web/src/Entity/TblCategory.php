<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * TblCategory
 *
 * @ORM\Table(name="tbl_category")
 * @ORM\Entity(repositoryClass="App\Repository\CategoryRepository")
 */
class TblCategory
{
    /**
     * @var int
     *
     * @ORM\Column(name="idCategory", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     * @Groups({"products"})
     */
    private $idcategory;

    /**
     * @var string
     *
     * @ORM\Column(name="nameCategory", type="string", length=150, nullable=false)
     * @Groups({"wishlist:items", "products"})
     */
    private $namecategory;

    public function getIdcategory(): ?int
    {
        return $this->idcategory;
    }

    public function getNamecategory(): ?string
    {
        return $this->namecategory;
    }

    public function setNamecategory(string $namecategory): self
    {
        $this->namecategory = $namecategory;

        return $this;
    }


}
