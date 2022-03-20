<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblRate
 *
 * @ORM\Table(name="tbl_rate")
 * @ORM\Entity(repositoryClass="App\Repository\RateRepository")
 */
class TblRate
{
    /**
     * @var int
     *
     * @ORM\Column(name="idRate", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idrate;

    /**
     * @var string
     *
     * @ORM\Column(name="name", type="string", length=50, nullable=false)
     */
    private $name;

    public function getIdrate(): ?int
    {
        return $this->idrate;
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


}
