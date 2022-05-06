<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblTypecomplaint
 *
 * @ORM\Table(name="tbl_typecomplaint")
 * @ORM\Entity(repositoryClass="App\Repository\TypeComplaintRepository")
 */
class TblTypecomplaint
{
    /**
     * @var int
     *
     * @ORM\Column(name="idType", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idtype;

    /**
     * @var string
     *
     * @ORM\Column(name="nameType", type="string", length=50, nullable=false)
     */
    private $nametype;

    public function getIdtype(): ?int
    {
        return $this->idtype;
    }

    public function getNametype(): ?string
    {
        return $this->nametype;
    }

    public function setNametype(string $nametype): self
    {
        $this->nametype = $nametype;

        return $this;
    }

    public function __toString()
    {
       return $this->nametype;
    }


}
