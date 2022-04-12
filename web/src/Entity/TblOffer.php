<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblOffer
 *
 * @ORM\Table(name="tbl_offer", indexes={@ORM\Index(name="fk_domain_offer", columns={"idDomain"}), @ORM\Index(name="idUser", columns={"idUser"})})
 * @ORM\Entity
 */
class TblOffer
{
    /**
     * @var int
     *
     * @ORM\Column(name="idOffer", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idoffer;

    /**
     * @var string
     *
     * @ORM\Column(name="titleOffer", type="string", length=30, nullable=false)
     */
    private $titleoffer;

    /**
     * @var string
     *
     * @ORM\Column(name="TypeOffer", type="string", length=20, nullable=false)
     */
    private $typeoffer;

    /**
     * @var string
     *
     * @ORM\Column(name="descOffer", type="string", length=255, nullable=false)
     */
    private $descoffer;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="startDTM", type="date", nullable=false)
     */
    private $startdtm;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="endDTM", type="date", nullable=false)
     */
    private $enddtm;

    /**
     * @var float
     *
     * @ORM\Column(name="salaryOffer", type="float", precision=10, scale=0, nullable=false)
     */
    private $salaryoffer;

    /**
     * @var string
     *
     * @ORM\Column(name="regionOffer", type="string", length=50, nullable=false)
     */
    private $regionoffer;

    /**
     * @var string
     *
     * @ORM\Column(name="addressOffer", type="string", length=50, nullable=false)
     */
    private $addressoffer;

    /**
     * @var \TblUser
     *
     * @ORM\ManyToOne(targetEntity="TblUser")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idUser", referencedColumnName="idUser")
     * })
     */
    private $iduser;

    /**
     * @var \TblDomain
     *
     * @ORM\ManyToOne(targetEntity="TblDomain")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idDomain", referencedColumnName="id")
     * })
     */
    private $iddomain;

    public function getIdoffer(): ?int
    {
        return $this->idoffer;
    }

    public function getTitleoffer(): ?string
    {
        return $this->titleoffer;
    }

    public function setTitleoffer(string $titleoffer): self
    {
        $this->titleoffer = $titleoffer;

        return $this;
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

    public function getDescoffer(): ?string
    {
        return $this->descoffer;
    }

    public function setDescoffer(string $descoffer): self
    {
        $this->descoffer = $descoffer;

        return $this;
    }

    public function getStartdtm(): ?\DateTimeInterface
    {
        return $this->startdtm;
    }

    public function setStartdtm(\DateTimeInterface $startdtm): self
    {
        $this->startdtm = $startdtm;

        return $this;
    }

    public function getEnddtm(): ?\DateTimeInterface
    {
        return $this->enddtm;
    }

    public function setEnddtm(\DateTimeInterface $enddtm): self
    {
        $this->enddtm = $enddtm;

        return $this;
    }

    public function getSalaryoffer(): ?float
    {
        return $this->salaryoffer;
    }

    public function setSalaryoffer(float $salaryoffer): self
    {
        $this->salaryoffer = $salaryoffer;

        return $this;
    }

    public function getRegionoffer(): ?string
    {
        return $this->regionoffer;
    }

    public function setRegionoffer(string $regionoffer): self
    {
        $this->regionoffer = $regionoffer;

        return $this;
    }

    public function getAddressoffer(): ?string
    {
        return $this->addressoffer;
    }

    public function setAddressoffer(string $addressoffer): self
    {
        $this->addressoffer = $addressoffer;

        return $this;
    }

    public function getIduser(): ?TblUser
    {
        return $this->iduser;
    }

    public function setIduser(?TblUser $iduser): self
    {
        $this->iduser = $iduser;

        return $this;
    }

    public function getIddomain(): ?TblDomain
    {
        return $this->iddomain;
    }

    public function setIddomain(?TblDomain $iddomain): self
    {
        $this->iddomain = $iddomain;

        return $this;
    }


}
