<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblTypeoffer
 *
 * @ORM\Table(name="tbl_typeoffer")
 * @ORM\Entity(repositoryClass="App\Repository\TypeOfferRepository")
 */
class TblTypeoffer
{
    /**
     * @var int
     *
     * @ORM\Column(name="idTypeOffer", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idtypeoffer;

    /**
     * @var string
     *
     * @ORM\Column(name="typeOffer", type="string", length=50, nullable=false)
     */
    private $typeoffer;

    public function getIdtypeoffer(): ?int
    {
        return $this->idtypeoffer;
    }

    public function getTypeoffer(): ?string
    {
        return $this->typeoffer;
    }

    public function setTypeoffer(string $typeoffer): self
    {
        $this->typeoffer = $typeoffer;

        return $this;
    }


}
