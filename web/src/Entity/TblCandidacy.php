<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblCandidacy
 *
 * @ORM\Table(name="tbl_candidacy", indexes={@ORM\Index(name="fk_candidacy_user", columns={"idUser"}), @ORM\Index(name="fk_candidacy_offer", columns={"idOffer"})})
 * @ORM\Entity(repositoryClass="App\Repository\CandidacyRepository")
 */
class TblCandidacy
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
     * @var \DateTime
     *
     * @ORM\Column(name="candidacyDTM", type="datetime", nullable=false, options={"default"="CURRENT_TIMESTAMP"})
     */
    private $candidacydtm = 'CURRENT_TIMESTAMP';

    /**
     * @var string|null
     *
     * @ORM\Column(name="etat", type="string", length=20, nullable=true)
     */
    private $etat;

    /**
     * @var string|null
     *
     * @ORM\Column(name="imageCV", type="string", length=255, nullable=true)
     */
    private $imagecv;

    /**
     * @var \TblOffer
     *
     * @ORM\ManyToOne(targetEntity="TblOffer")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idOffer", referencedColumnName="idOffer")
     * })
     */
    private $idoffer;

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idUser", referencedColumnName="idUser")
     * })
     */
    private $iduser;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getCandidacydtm(): ?\DateTimeInterface
    {
        return $this->candidacydtm;
    }

    public function setCandidacydtm(\DateTimeInterface $candidacydtm): self
    {
        $this->candidacydtm = $candidacydtm;

        return $this;
    }

    public function getEtat(): ?string
    {
        return $this->etat;
    }

    public function setEtat(?string $etat): self
    {
        $this->etat = $etat;

        return $this;
    }

    public function getImagecv(): ?string
    {
        return $this->imagecv;
    }

    public function setImagecv(?string $imagecv): self
    {
        $this->imagecv = $imagecv;

        return $this;
    }

    public function getIdoffer(): ?TblOffer
    {
        return $this->idoffer;
    }

    public function setIdoffer(?TblOffer $idoffer): self
    {
        $this->idoffer = $idoffer;

        return $this;
    }

    public function getIduser(): ?User
    {
        return $this->iduser;
    }

    public function setIduser(?User $iduser): self
    {
        $this->iduser = $iduser;

        return $this;
    }


}
