<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblReclamation
 *
 * @ORM\Table(name="tbl_reclamation", indexes={@ORM\Index(name="fk_type_rate", columns={"rate"}), @ORM\Index(name="fk_idUser_rec", columns={"idUser"}), @ORM\Index(name="fk_type_rec", columns={"typeComplaint"})})
 * @ORM\Entity
 */
class TblReclamation
{
    /**
     * @var int
     *
     * @ORM\Column(name="idComplaint", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idcomplaint;

    /**
     * @var string
     *
     * @ORM\Column(name="message", type="string", length=500, nullable=false)
     */
    private $message;

    /**
     * @var string
     *
     * @ORM\Column(name="statusComplaint", type="string", length=30, nullable=false, options={"default"="NON TRAITEE"})
     */
    private $statuscomplaint = 'NON TRAITEE';

    /**
     * @var string|null
     *
     * @ORM\Column(name="answerComplaint", type="string", length=500, nullable=true)
     */
    private $answercomplaint;

    /**
     * @var \TblRate
     *
     * @ORM\ManyToOne(targetEntity="TblRate")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="rate", referencedColumnName="idRate")
     * })
     */
    private $rate;

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
     * @var \TblTypecomplaint
     *
     * @ORM\ManyToOne(targetEntity="TblTypecomplaint")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="typeComplaint", referencedColumnName="idType")
     * })
     */
    private $typecomplaint;

    public function getIdcomplaint(): ?int
    {
        return $this->idcomplaint;
    }

    public function getMessage(): ?string
    {
        return $this->message;
    }

    public function setMessage(string $message): self
    {
        $this->message = $message;

        return $this;
    }

    public function getStatuscomplaint(): ?string
    {
        return $this->statuscomplaint;
    }

    public function setStatuscomplaint(string $statuscomplaint): self
    {
        $this->statuscomplaint = $statuscomplaint;

        return $this;
    }

    public function getAnswercomplaint(): ?string
    {
        return $this->answercomplaint;
    }

    public function setAnswercomplaint(?string $answercomplaint): self
    {
        $this->answercomplaint = $answercomplaint;

        return $this;
    }

    public function getRate(): ?TblRate
    {
        return $this->rate;
    }

    public function setRate(?TblRate $rate): self
    {
        $this->rate = $rate;

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

    public function getTypecomplaint(): ?TblTypecomplaint
    {
        return $this->typecomplaint;
    }

    public function setTypecomplaint(?TblTypecomplaint $typecomplaint): self
    {
        $this->typecomplaint = $typecomplaint;

        return $this;
    }


}
