<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblPost
 *
 * @ORM\Table(name="tbl_post", indexes={@ORM\Index(name="fk_post_user", columns={"idUser"})})
 * @ORM\Entity(repositoryClass="App\Repository\PostRepository")
 */
class TblPost
{
    /**
     * @var int
     *
     * @ORM\Column(name="idPost", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idpost;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="postedDTM", type="datetime", nullable=false, options={"default"="CURRENT_TIMESTAMP"})
     */
    private $posteddtm = 'CURRENT_TIMESTAMP';

    /**
     * @var string
     *
     * @ORM\Column(name="titlePost", type="string", length=100, nullable=false)
     */
    private $titlepost;

    /**
     * @var string
     *
     * @ORM\Column(name="descPost", type="string", length=500, nullable=false)
     */
    private $descpost;

    /**
     * @var int
     *
     * @ORM\Column(name="statusPost", type="integer", nullable=false)
     */
    private $statuspost;

    /**
     * @var string|null
     *
     * @ORM\Column(name="photoPost", type="string", length=200, nullable=true)
     */
    private $photopost;

    /**
     * @var string
     *
     * @ORM\Column(name="typePost", type="string", length=50, nullable=false)
     */
    private $typepost;

    /**
     * @var \DateTime|null
     *
     * @ORM\Column(name="startDTM", type="date", nullable=true)
     */
    private $startdtm;

    /**
     * @var \DateTime|null
     *
     * @ORM\Column(name="endDTM", type="date", nullable=true)
     */
    private $enddtm;

    /**
     * @var string|null
     *
     * @ORM\Column(name="addressEvent", type="string", length=150, nullable=true)
     */
    private $addressevent;

    /**
     * @var int|null
     *
     * @ORM\Column(name="nbTicketAvailable", type="integer", nullable=true)
     */
    private $nbticketavailable;

    /**
     * @var string|null
     *
     * @ORM\Column(name="correctAnswer", type="string", length=255, nullable=true)
     */
    private $correctanswer;

    /**
     * @var float|null
     *
     * @ORM\Column(name="latitude", type="float", precision=10, scale=0, nullable=true)
     */
    private $latitude;

    /**
     * @var float|null
     *
     * @ORM\Column(name="longitude", type="float", precision=10, scale=0, nullable=true)
     */
    private $longitude;

    /**
     * @var \TblUser
     *
     * @ORM\ManyToOne(targetEntity="TblUser")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idUser", referencedColumnName="idUser")
     * })
     */
    private $iduser;

    public function getIdpost(): ?int
    {
        return $this->idpost;
    }

    public function getPosteddtm(): ?\DateTimeInterface
    {
        return $this->posteddtm;
    }

    public function setPosteddtm(\DateTimeInterface $posteddtm): self
    {
        $this->posteddtm = $posteddtm;

        return $this;
    }

    public function getTitlepost(): ?string
    {
        return $this->titlepost;
    }

    public function setTitlepost(string $titlepost): self
    {
        $this->titlepost = $titlepost;

        return $this;
    }

    public function getDescpost(): ?string
    {
        return $this->descpost;
    }

    public function setDescpost(string $descpost): self
    {
        $this->descpost = $descpost;

        return $this;
    }

    public function getStatuspost(): ?int
    {
        return $this->statuspost;
    }

    public function setStatuspost(int $statuspost): self
    {
        $this->statuspost = $statuspost;

        return $this;
    }

    public function getPhotopost(): ?string
    {
        return $this->photopost;
    }

    public function setPhotopost(?string $photopost): self
    {
        $this->photopost = $photopost;

        return $this;
    }

    public function getTypepost(): ?string
    {
        return $this->typepost;
    }

    public function setTypepost(string $typepost): self
    {
        $this->typepost = $typepost;

        return $this;
    }

    public function getStartdtm(): ?\DateTimeInterface
    {
        return $this->startdtm;
    }

    public function setStartdtm(?\DateTimeInterface $startdtm): self
    {
        $this->startdtm = $startdtm;

        return $this;
    }

    public function getEnddtm(): ?\DateTimeInterface
    {
        return $this->enddtm;
    }

    public function setEnddtm(?\DateTimeInterface $enddtm): self
    {
        $this->enddtm = $enddtm;

        return $this;
    }

    public function getAddressevent(): ?string
    {
        return $this->addressevent;
    }

    public function setAddressevent(?string $addressevent): self
    {
        $this->addressevent = $addressevent;

        return $this;
    }

    public function getNbticketavailable(): ?int
    {
        return $this->nbticketavailable;
    }

    public function setNbticketavailable(?int $nbticketavailable): self
    {
        $this->nbticketavailable = $nbticketavailable;

        return $this;
    }

    public function getCorrectanswer(): ?string
    {
        return $this->correctanswer;
    }

    public function setCorrectanswer(?string $correctanswer): self
    {
        $this->correctanswer = $correctanswer;

        return $this;
    }

    public function getLatitude(): ?float
    {
        return $this->latitude;
    }

    public function setLatitude(?float $latitude): self
    {
        $this->latitude = $latitude;

        return $this;
    }

    public function getLongitude(): ?float
    {
        return $this->longitude;
    }

    public function setLongitude(?float $longitude): self
    {
        $this->longitude = $longitude;

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


}
