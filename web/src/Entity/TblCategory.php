<?php

namespace App\Entity;
use Symfony\Component\Validator\Constraints as Assert;

use Doctrine\ORM\Mapping as ORM;

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
     * @ORM\Column(name="idCategory", type="integer", nullable=false )
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idcategory;

    /**
     * @Assert\NotBlank(message=" Nom Category doit etre non vide")
     * @Assert\Length(
     *      min = 5,
     *      minMessage=" Entrer un nom de Category au mini de 5 caracteres"
     *
     *     )
     * @ORM\Column(type="string", length=255 , name="nameCategory")
     */
    private $nameCategory;

    public function getIdCategory(): ?int
    {
        return $this->idcategory;
    }

    public function getNameCategory(): ?string
    {
        return $this->nameCategory;
    }

    public function setNameCategory(string $nameCategory): self
    {
        $this->nameCategory = $nameCategory;

        return $this;
    }

    public function __toString()
    {
        return $this->nameCategory;

    }


}
