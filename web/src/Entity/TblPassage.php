<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblPassage
 *
 * @ORM\Table(name="tbl_passage", indexes={@ORM\Index(name="fk_passage_candidacy", columns={"idCandidacy"}), @ORM\Index(name="fk_passage_test", columns={"idTest"})})
 * @ORM\Entity
 */
class TblPassage
{
    /**
     * @var int
     *
     * @ORM\Column(name="idPassage", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idpassage;

    /**
     * @var string
     *
     * @ORM\Column(name="resultatPassage", type="string", length=255, nullable=false)
     */
    private $resultatpassage;

    /**
     * @var \TblTest
     *
     * @ORM\ManyToOne(targetEntity="TblTest")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idTest", referencedColumnName="idTest")
     * })
     */
    private $idtest;

    /**
     * @var \TblCandidacy
     *
     * @ORM\ManyToOne(targetEntity="TblCandidacy")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idCandidacy", referencedColumnName="id")
     * })
     */
    private $idcandidacy;

    public function getIdpassage(): ?int
    {
        return $this->idpassage;
    }

    public function getResultatpassage(): ?string
    {
        return $this->resultatpassage;
    }

    public function setResultatpassage(string $resultatpassage): self
    {
        $this->resultatpassage = $resultatpassage;

        return $this;
    }

    public function getIdtest(): ?TblTest
    {
        return $this->idtest;
    }

    public function setIdtest(?TblTest $idtest): self
    {
        $this->idtest = $idtest;

        return $this;
    }

    public function getIdcandidacy(): ?TblCandidacy
    {
        return $this->idcandidacy;
    }

    public function setIdcandidacy(?TblCandidacy $idcandidacy): self
    {
        $this->idcandidacy = $idcandidacy;

        return $this;
    }


}
