<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * TblDomain
 *
 * @ORM\Table(name="tbl_domain")
 * @ORM\Entity
 */
class TblDomain
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $id;

    /**
     * @var string
     * @ORM\Column(name="name", type="string", length=50, nullable=false)
     *@Assert\NotBlank(message="Nom est vide!")
     */
    private $name;

    /**
     * @Assert\NotBlank(message="Description ne doit pas etre vide!")
     * @Assert\Length (
     *     min=7 ,
     *     max=100 ,
     *     minMessage= "doit etre >=7 ",
     *     maxMessage= "doit etre <=100" )
     * @ORM\Column(name="description", type="string", length=100, nullable=false)
     *
     */
    private $description;



    public function getId(): ?int
    {
        return $this->id;
    }

    public function getName(): ?string
    {
        return $this->name;
    }

    public function setName(string $name): self
    {
        $this->name = $name;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }


}
