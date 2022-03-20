<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblCommentaire
 *
 * @ORM\Table(name="tbl_commentaire", indexes={@ORM\Index(name="fk_comm_pub", columns={"IdPub"})})
 * @ORM\Entity(repositoryClass="App\Repository\CommentaireRepository")
 */
class TblCommentaire
{
    /**
     * @var int
     *
     * @ORM\Column(name="idCom", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idcom;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="dateCom", type="datetime", nullable=false, options={"default"="CURRENT_TIMESTAMP"})
     */
    private $datecom = 'CURRENT_TIMESTAMP';

    /**
     * @var string
     *
     * @ORM\Column(name="descriptionCom", type="string", length=255, nullable=false)
     */
    private $descriptioncom;

    /**
     * @var \TblPublication
     *
     * @ORM\ManyToOne(targetEntity="TblPublication")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="IdPub", referencedColumnName="id")
     * })
     */
    private $idpub;

    public function getIdcom(): ?int
    {
        return $this->idcom;
    }

    public function getDatecom(): ?\DateTimeInterface
    {
        return $this->datecom;
    }

    public function setDatecom(\DateTimeInterface $datecom): self
    {
        $this->datecom = $datecom;

        return $this;
    }

    public function getDescriptioncom(): ?string
    {
        return $this->descriptioncom;
    }

    public function setDescriptioncom(string $descriptioncom): self
    {
        $this->descriptioncom = $descriptioncom;

        return $this;
    }

    public function getIdpub(): ?TblPublication
    {
        return $this->idpub;
    }

    public function setIdpub(?TblPublication $idpub): self
    {
        $this->idpub = $idpub;

        return $this;
    }


}
