<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblTest
 *
 * @ORM\Table(name="tbl_test", indexes={@ORM\Index(name="fk_test_user", columns={"idUser"})})
 * @ORM\Entity(repositoryClass="App\Repository\TestRepository")
 */
class TblTest
{
    /**
     * @var int
     *
     * @ORM\Column(name="idTest", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idtest;

    /**
     * @var string
     *
     * @ORM\Column(name="durationTest", type="string", length=50, nullable=false)
     */
    private $durationtest;

    /**
     * @var string
     *
     * @ORM\Column(name="questionTest", type="string", length=255, nullable=false)
     */
    private $questiontest;

    /**
     * @var int
     *
     * @ORM\Column(name="nbQuestion", type="integer", nullable=false)
     */
    private $nbquestion;

    /**
     * @var string
     *
     * @ORM\Column(name="choix", type="string", length=255, nullable=false)
     */
    private $choix;

    /**
     * @var string
     *
     * @ORM\Column(name="question", type="string", length=255, nullable=false)
     */
    private $question;

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idUser", referencedColumnName="idUser")
     * })
     */
    private $iduser;

    public function getIdtest(): ?int
    {
        return $this->idtest;
    }

    public function getDurationtest(): ?string
    {
        return $this->durationtest;
    }

    public function setDurationtest(string $durationtest): self
    {
        $this->durationtest = $durationtest;

        return $this;
    }

    public function getQuestiontest(): ?string
    {
        return $this->questiontest;
    }

    public function setQuestiontest(string $questiontest): self
    {
        $this->questiontest = $questiontest;

        return $this;
    }

    public function getNbquestion(): ?int
    {
        return $this->nbquestion;
    }

    public function setNbquestion(int $nbquestion): self
    {
        $this->nbquestion = $nbquestion;

        return $this;
    }

    public function getChoix(): ?string
    {
        return $this->choix;
    }

    public function setChoix(string $choix): self
    {
        $this->choix = $choix;

        return $this;
    }

    public function getQuestion(): ?string
    {
        return $this->question;
    }

    public function setQuestion(string $question): self
    {
        $this->question = $question;

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


}
