<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * TblFidcard
 *
 * @ORM\Table(name="tbl_fidcard", indexes={@ORM\Index(name="fk_fidcard_cardtype", columns={"idCardType"}), @ORM\Index(name="fk_fidcard_user", columns={"idUser"})})
 * @ORM\Entity
 */
class TblFidcard
{
    /**
     * @var int
     *
     * @ORM\Column(name="idFidCard", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idfidcard;

    /**
     * @var int
     *
     * @ORM\Column(name="nbPointsFid", type="integer", nullable=false)
     * @Groups("fidcard")
     */
    private $nbpointsfid = '0';

    /**
     * @var \DateTime|null
     *
     * @ORM\Column(name="createdDTM", type="date", nullable=true, options={"default"="NULL"})
     */
    private $createddtm = 'NULL';

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idUser", referencedColumnName="idUser")
     * })
     */
    private $iduser;

    /**
     * @var \TblCardtype
     *
     * @ORM\ManyToOne(targetEntity="TblCardtype")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idCardType", referencedColumnName="idCardType")
     * })
     * @Groups("fidcard")
     */
    private $idcardtype;

    public function getIdfidcard(): ?int
    {
        return $this->idfidcard;
    }

    public function getNbpointsfid(): ?int
    {
        return $this->nbpointsfid;
    }

    public function setNbpointsfid(int $nbpointsfid): self
    {
        $this->nbpointsfid = $nbpointsfid;

        return $this;
    }

    public function getCreateddtm(): ?\DateTimeInterface
    {
        return $this->createddtm;
    }

    public function setCreateddtm(?\DateTimeInterface $createddtm): self
    {
        $this->createddtm = $createddtm;

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

    public function getIdcardtype(): ?TblCardtype
    {
        return $this->idcardtype;
    }

    public function setIdcardtype(?TblCardtype $idcardtype): self
    {
        $this->idcardtype = $idcardtype;

        return $this;
    }


}
