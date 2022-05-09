<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblUser
 *
 * @ORM\Table(name="tbl_user", indexes={@ORM\Index(name="fk_badge_user", columns={"idBadge"}), @ORM\Index(name="fk_user_stateUser", columns={"stateUser"})})
 * @ORM\Entity
 */
class TblUser
{



    /**
     * @var int
     *
     * @ORM\Column(name="idUser", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $iduser;

    /**
     * @var string
     *
     * @ORM\Column(name="nameUser", type="string", length=100, nullable=false)
     */
    private $nameuser;

    /**
     * @var string
     *
     * @ORM\Column(name="emailUser", type="string", length=50, nullable=false)
     */
    private $emailuser;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="createdDTM", type="date", nullable=false, options={"default"="current_timestamp()"})
     */
    private $createddtm = 'current_timestamp()';

    /**
     * @var \DateTime|null
     *
     * @ORM\Column(name="desactivationDTM", type="date", nullable=true, options={"default"="NULL"})
     */
    private $desactivationdtm = 'NULL';

    /**
     * @var string
     *
     * @ORM\Column(name="pwdUser", type="string", length=255, nullable=false)
     */
    private $pwduser;

    /**
     * @var string
     *
     * @ORM\Column(name="phone", type="string", length=8, nullable=false)
     */
    private $phone;

    /**
     * @var string|null
     *
     * @ORM\Column(name="photoUser", type="string", length=255, nullable=true, options={"default"="NULL"})
     */
    private $photouser = 'NULL';

    /**
     * @var string
     *
     * @ORM\Column(name="roleUser", type="string", length=11, nullable=false)
     */
    private $roleuser;

    /**
     * @var int|null
     *
     * @ORM\Column(name="levelUser", type="integer", nullable=true, options={"default"="1"})
     */
    private $leveluser = 1;

    /**
     * @var string|null
     *
     * @ORM\Column(name="cv", type="string", length=255, nullable=true, options={"default"="NULL"})
     */
    private $cv = 'NULL';

    /**
     * @var string|null
     *
     * @ORM\Column(name="descUser", type="string", length=255, nullable=true, options={"default"="NULL"})
     */
    private $descuser = 'NULL';

    /**
     * @var \TblStateuser
     *
     * @ORM\ManyToOne(targetEntity="TblStateuser")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="stateUser", referencedColumnName="idStateUser")
     * })
     */
    private $stateuser;

    /**
     * @var \TblBadge
     *
     * @ORM\ManyToOne(targetEntity="TblBadge")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idBadge", referencedColumnName="idBadge")
     * })
     */
    private $idbadge;

    public function getIduser(): ?int
    {
        return $this->iduser;
    }

    public function getNameuser(): ?string
    {
        return $this->nameuser;
    }

    public function setNameuser(string $nameuser): self
    {
        $this->nameuser = $nameuser;

        return $this;
    }

    public function getEmailuser(): ?string
    {
        return $this->emailuser;
    }

    public function setEmailuser(string $emailuser): self
    {
        $this->emailuser = $emailuser;

        return $this;
    }

    public function getCreateddtm(): ?\DateTimeInterface
    {
        return $this->createddtm;
    }

    public function setCreateddtm(\DateTimeInterface $createddtm): self
    {
        $this->createddtm = $createddtm;

        return $this;
    }

    public function getDesactivationdtm(): ?\DateTimeInterface
    {
        return $this->desactivationdtm;
    }

    public function setDesactivationdtm(?\DateTimeInterface $desactivationdtm): self
    {
        $this->desactivationdtm = $desactivationdtm;

        return $this;
    }

    public function getPwduser(): ?string
    {
        return $this->pwduser;
    }

    public function setPwduser(string $pwduser): self
    {
        $this->pwduser = $pwduser;

        return $this;
    }

    public function getPhone(): ?string
    {
        return $this->phone;
    }

    public function setPhone(string $phone): self
    {
        $this->phone = $phone;

        return $this;
    }

    public function getPhotouser(): ?string
    {
        return $this->photouser;
    }

    public function setPhotouser(?string $photouser): self
    {
        $this->photouser = $photouser;

        return $this;
    }

    public function getRoleuser(): ?string
    {
        return $this->roleuser;
    }

    public function setRoleuser(string $roleuser): self
    {
        $this->roleuser = $roleuser;

        return $this;
    }

    public function getLeveluser(): ?int
    {
        return $this->leveluser;
    }

    public function setLeveluser(?int $leveluser): self
    {
        $this->leveluser = $leveluser;

        return $this;
    }

    public function getCv(): ?string
    {
        return $this->cv;
    }

    public function setCv(?string $cv): self
    {
        $this->cv = $cv;

        return $this;
    }

    public function getDescuser(): ?string
    {
        return $this->descuser;
    }

    public function setDescuser(?string $descuser): self
    {
        $this->descuser = $descuser;

        return $this;
    }

    public function getStateuser(): ?TblStateuser
    {
        return $this->stateuser;
    }

    public function setStateuser(?TblStateuser $stateuser): self
    {
        $this->stateuser = $stateuser;

        return $this;
    }

    public function getIdbadge(): ?TblBadge
    {
        return $this->idbadge;
    }

    public function setIdbadge(?TblBadge $idbadge): self
    {
        $this->idbadge = $idbadge;

        return $this;
    }

    public function __toString()
    {
        return $this->nameuser ;
    }


}
