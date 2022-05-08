<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
/**
 * TblBadge
 *
 * @ORM\Table(name="tbl_badge")
 * @ORM\Entity
 * @UniqueEntity("namebadge")
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
     * @Assert\NotBlank
     * @Assert\Type(
     *     type="string",
     *     message="The value {{ value }} is not a valid {{ type }}."
     * )
     * @ORM\Column(name="nameBadge", type="string", length=50, nullable=false)
     */
    private $namebadge;

    /**
     * @var string
     * @ORM\Column(name="descBadge", type="string", length=100, nullable=false)
     * @Assert\NotBlank
     * @Assert\Type(
     *     type="string",
     *     message="The value {{ value }} is not a valid {{ type }}."
     * )
     */
    private $descbadge;

    /**
     * @var string
     * @ORM\Column(name="photoBadge", type="string", length=100, nullable=false)
     * @Assert\Type(
     *     type="string",
     *     message="The value {{ value }} is not a valid {{ type }}."
     * )
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
