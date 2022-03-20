<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblBadge
 *
 * @ORM\Table(name="tbl_badge")
 * @ORM\Entity(repositoryClass="App\Repository\BadgeRepository")
 */
class TblBadge
{
    /**
     * @var int
     *
     * @ORM\Column(name="idBadge", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idbadge;

    /**
     * @var string
     *
     * @ORM\Column(name="nameBadge", type="string", length=50, nullable=false)
     */
    private $namebadge;

    /**
     * @var string
     *
     * @ORM\Column(name="descBadge", type="string", length=100, nullable=false)
     */
    private $descbadge;

    /**
     * @var string
     *
     * @ORM\Column(name="photoBadge", type="string", length=100, nullable=false)
     */
    private $photobadge;

    public function getIdbadge(): ?int
    {
        return $this->idbadge;
    }

    public function getNamebadge(): ?string
    {
        return $this->namebadge;
    }

    public function setNamebadge(string $namebadge): self
    {
        $this->namebadge = $namebadge;

        return $this;
    }

    public function getDescbadge(): ?string
    {
        return $this->descbadge;
    }

    public function setDescbadge(string $descbadge): self
    {
        $this->descbadge = $descbadge;

        return $this;
    }

    public function getPhotobadge(): ?string
    {
        return $this->photobadge;
    }

    public function setPhotobadge(string $photobadge): self
    {
        $this->photobadge = $photobadge;

        return $this;
    }


}
