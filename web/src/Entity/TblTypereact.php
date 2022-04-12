<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblTypereact
 *
 * @ORM\Table(name="tbl_typereact")
 * @ORM\Entity
 */
class TblTypereact
{
    /**
     * @var int
     *
     * @ORM\Column(name="idTypeReact", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idtypereact;

    /**
     * @var string
     *
     * @ORM\Column(name="typeReact", type="string", length=10, nullable=false)
     */
    private $typereact;

    /**
     * @var string
     *
     * @ORM\Column(name="iconReact", type="string", length=100, nullable=false)
     */
    private $iconreact;

    public function getIdtypereact(): ?int
    {
        return $this->idtypereact;
    }

    public function getTypereact(): ?string
    {
        return $this->typereact;
    }

    public function setTypereact(string $typereact): self
    {
        $this->typereact = $typereact;

        return $this;
    }

    public function getIconreact(): ?string
    {
        return $this->iconreact;
    }

    public function setIconreact(string $iconreact): self
    {
        $this->iconreact = $iconreact;

        return $this;
    }


}
