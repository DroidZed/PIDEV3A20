<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblPublication
 *
 * @ORM\Table(name="tbl_publication", indexes={@ORM\Index(name="fk_pub_Vg", columns={"idGV"})})
 * @ORM\Entity(repositoryClass="App\Repository\PublicationRepository")
 */
class TblPublication
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
     * @var int|null
     *
     * @ORM\Column(name="idUser", type="integer", nullable=true)
     */
    private $iduser;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="DatePub", type="datetime", nullable=false, options={"default"="CURRENT_TIMESTAMP"})
     */
    private $datepub = 'CURRENT_TIMESTAMP';

    /**
     * @var int|null
     *
     * @ORM\Column(name="TypePub", type="integer", nullable=true)
     */
    private $typepub;

    /**
     * @var string
     *
     * @ORM\Column(name="descriptionPub", type="string", length=255, nullable=false)
     */
    private $descriptionpub;

    /**
     * @var float
     *
     * @ORM\Column(name="nbrjaime", type="float", precision=10, scale=0, nullable=false)
     */
    private $nbrjaime = '0';

    /**
     * @var \TblVideogame
     *
     * @ORM\ManyToOne(targetEntity="TblVideogame")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idGV", referencedColumnName="id")
     * })
     */
    private $idgv;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getIduser(): ?int
    {
        return $this->iduser;
    }

    public function setIduser(?int $iduser): self
    {
        $this->iduser = $iduser;

        return $this;
    }

    public function getDatepub(): ?\DateTimeInterface
    {
        return $this->datepub;
    }

    public function setDatepub(\DateTimeInterface $datepub): self
    {
        $this->datepub = $datepub;

        return $this;
    }

    public function getTypepub(): ?int
    {
        return $this->typepub;
    }

    public function setTypepub(?int $typepub): self
    {
        $this->typepub = $typepub;

        return $this;
    }

    public function getDescriptionpub(): ?string
    {
        return $this->descriptionpub;
    }

    public function setDescriptionpub(string $descriptionpub): self
    {
        $this->descriptionpub = $descriptionpub;

        return $this;
    }

    public function getNbrjaime(): ?float
    {
        return $this->nbrjaime;
    }

    public function setNbrjaime(float $nbrjaime): self
    {
        $this->nbrjaime = $nbrjaime;

        return $this;
    }

    public function getIdgv(): ?TblVideogame
    {
        return $this->idgv;
    }

    public function setIdgv(?TblVideogame $idgv): self
    {
        $this->idgv = $idgv;

        return $this;
    }


}
