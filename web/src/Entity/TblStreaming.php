<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblStreaming
 *
 * @ORM\Table(name="tbl_streaming", indexes={@ORM\Index(name="fk_streaming", columns={"idUser"})})
 * @ORM\Entity(repositoryClass="App\Repository\StreamingRepository")
 */
class TblStreaming
{
    /**
     * @var int
     *
     * @ORM\Column(name="idStream", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idstream;

    /**
     * @var int
     *
     * @ORM\Column(name="idUser", type="integer", nullable=false)
     */
    private $iduser;

    /**
     * @var string
     *
     * @ORM\Column(name="link", type="string", length=250, nullable=false)
     */
    private $link;

    /**
     * @var string|null
     *
     * @ORM\Column(name="description", type="string", length=250, nullable=true)
     */
    private $description;

    /**
     * @var int|null
     *
     * @ORM\Column(name="nbVu", type="integer", nullable=true)
     */
    private $nbvu;

    /**
     * @var int
     *
     * @ORM\Column(name="etat", type="integer", nullable=false)
     */
    private $etat = '0';

    public function getIdstream(): ?int
    {
        return $this->idstream;
    }

    public function getIduser(): ?int
    {
        return $this->iduser;
    }

    public function setIduser(int $iduser): self
    {
        $this->iduser = $iduser;

        return $this;
    }

    public function getLink(): ?string
    {
        return $this->link;
    }

    public function setLink(string $link): self
    {
        $this->link = $link;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(?string $description): self
    {
        $this->description = $description;

        return $this;
    }

    public function getNbvu(): ?int
    {
        return $this->nbvu;
    }

    public function setNbvu(?int $nbvu): self
    {
        $this->nbvu = $nbvu;

        return $this;
    }

    public function getEtat(): ?int
    {
        return $this->etat;
    }

    public function setEtat(int $etat): self
    {
        $this->etat = $etat;

        return $this;
    }


}
