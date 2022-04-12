<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblVideogame
 *
 * @ORM\Table(name="tbl_videogame", indexes={@ORM\Index(name="idUser", columns={"idUser"})})
 * @ORM\Entity
 */
class TblVideogame
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
     * @var bool|null
     *
     * @ORM\Column(name="statusVg", type="boolean", nullable=true, options={"default"="NULL"})
     */
    private $statusvg = 'NULL';

    /**
     * @var string|null
     *
     * @ORM\Column(name="nameVg", type="string", length=255, nullable=true, options={"default"="NULL"})
     */
    private $namevg = 'NULL';

    /**
     * @var string
     *
     * @ORM\Column(name="imageVg", type="string", length=255, nullable=false)
     */
    private $imagevg;

    /**
     * @var int
     *
     * @ORM\Column(name="rating", type="integer", nullable=false)
     */
    private $rating = '0';

    /**
     * @var int|null
     *
     * @ORM\Column(name="likes", type="integer", nullable=true)
     */
    private $likes = '0';

    /**
     * @var \TblUser
     *
     * @ORM\ManyToOne(targetEntity="TblUser")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idUser", referencedColumnName="idUser")
     * })
     */
    private $iduser;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getStatusvg(): ?bool
    {
        return $this->statusvg;
    }

    public function setStatusvg(?bool $statusvg): self
    {
        $this->statusvg = $statusvg;

        return $this;
    }

    public function getNamevg(): ?string
    {
        return $this->namevg;
    }

    public function setNamevg(?string $namevg): self
    {
        $this->namevg = $namevg;

        return $this;
    }

    public function getImagevg(): ?string
    {
        return $this->imagevg;
    }

    public function setImagevg(string $imagevg): self
    {
        $this->imagevg = $imagevg;

        return $this;
    }

    public function getRating(): ?int
    {
        return $this->rating;
    }

    public function setRating(int $rating): self
    {
        $this->rating = $rating;

        return $this;
    }

    public function getLikes(): ?int
    {
        return $this->likes;
    }

    public function setLikes(?int $likes): self
    {
        $this->likes = $likes;

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
