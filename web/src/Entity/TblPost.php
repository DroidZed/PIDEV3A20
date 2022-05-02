<?php

namespace App\Entity;
use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups ;

/**
 * TblPost
 *
 * @ORM\Table(name="tbl_post", indexes={@ORM\Index(name="fk_post_user", columns={"idUser"})})
 * @ORM\Entity
 */
class TblPost
{

    const STATUS_POST = [
        1 => 'Visible',
        0 => 'Not Visible'
    ] ;

    const LOCATIONS = [
        "Governorate Nabeul , Tunisia" => [
                'latitude' => 36.451830,
                'longitude' => 10.735150
            ],
        "Governorate Manouba , Tunisia" => [
            'latitude' => 36.812370 ,
            'longitude' => 10.093060
        ],
        "Governorate Kairouan , Tunisia" => [
            'latitude' => 35.672800 ,
            'longitude' => 10.094910
        ] ,
        "Governorate Monastir , Tunisia" => [
            'latitude' => 35.769260,
            'longitude' => 10.819970
        ] ,
        "Governorate Gafsa, Tunisia" => [
            'latitude' => 34.415990 ,
            'longitude' => 8.792010
        ] ,


    ] ;


    /**
     * @var int
     *
     * @ORM\Column(name="idPost", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     * @Groups ("post:read")
     */
    private $idpost;



    /**
     * @var \DateTime
     * @ORM\Column(name="postedDTM", type="datetime", nullable=false, options={"default"="CURRENT_TIMESTAMP"})
     * @Groups ("post:read")
     */
    private $posteddtm ;

    /**
     * @var string
     * @Assert\NotBlank
     * @Assert\Length(min=5,max=255,
     * minMessage="Le nom d'un post doit comporter au moins {{ limit }} caractères",
     * maxMessage="Le nom d'un post doit comporter au plus  {{ limit }} caractères" )
     * @ORM\Column(name="titlePost", type="string", length=100, nullable=false)
     * @Groups ("post:read")
     */
    private $titlepost;

    /**
     * @var string
     * @Assert\NotBlank
     * @Assert\Length(min=5,max=255,
     * minMessage="Post title requires at least  {{ limit }} characters ",
     * maxMessage="Post title maxiumum length is {{ limit }} characters " )
     * @Groups ("post:read")
     * @ORM\Column(name="descPost", type="string", length=500, nullable=false)
     */
    private $descpost;

    /**
     * @var int
     * @Groups ("post:read")
     * @ORM\Column(name="statusPost", type="integer", nullable=false)
     */
    private $statuspost;

    /**
     * @var string|null
     * @Groups ("post:read")
     * @ORM\Column(name="photoPost", type="string", length=200, nullable=true)
     */
    private $photopost;

    /**
     * @var string
     * @Groups ("post:read")
     * @ORM\Column(name="typePost", type="string", length=50, nullable=false)
     */
    private $typepost;

    /**
     * @var \DateTime|null
     * @Assert\GreaterThan("today")
     * @ORM\Column(name="startDTM", type="date", nullable=true)
     * @Groups ("post:read")
     */
    private $startdtm;

    /**
     * @var \DateTime|null
     * @Assert\GreaterThan("today")
     * @ORM\Column(name="endDTM", type="date", nullable=true)
     * @Groups ("post:read")
     */
    private $enddtm;

    /**
     * @var string|null
     * @ORM\Column(name="addressEvent", type="string", length=150, nullable=true)
     * @Groups ("post:read")
     */
    private $addressevent;

    /**
     * @var int|null
     * @Assert\Type(type="integer", message="The value {{ value }} is not a type {{ type }} valid.")
     * @ORM\Column(name="nbTicketAvailable", type="integer", nullable=true)
     * @Groups ("post:read")
     */
    private $nbticketavailable;

    /**
     * @var string|null
     * @Groups ("post:read")
     * @ORM\Column(name="correctAnswer", type="string", length=255, nullable=true)
     */
    private $correctanswer;

    /**
     * @var float|null
     * @Assert\Type(type="float", message="The value {{ value }} is not a type {{ type }} valid.")
     * @Groups ("post:read")
     * @ORM\Column(name="latitude", type="float", precision=10, scale=0, nullable=true)
     */
    private $latitude;

    /**
     * @var float|null
     * @Assert\Type(type="float", message="The value {{ value }} is not a type {{ type }} valid.")
     * @ORM\Column(name="longitude", type="float", precision=10, scale=0, nullable=true)
     * @Groups ("post:read")
     */
    private $longitude;

    /**
     * @var \TblUser
     * @ORM\ManyToOne(targetEntity="TblUser")
     * @ORM\JoinColumns({
     * @ORM\JoinColumn(name="idUser", referencedColumnName="idUser")
     * })
     * @Groups ("post:read")
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

    public function __toString()
    {
        return $this->getTitlepost();
    }



}
