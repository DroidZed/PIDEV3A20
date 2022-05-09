<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use App\Repository\AnswerPostRepository;

/**
 * TblAnswerpost
 *
 * @ORM\Table(name="tbl_answerpost", indexes={@ORM\Index(name="fk_user_answerPost", columns={"idUser"}), @ORM\Index(name="fk_post_answerPost", columns={"idPost"})})
 * @ORM\Entity(repositoryClass=AnswerPostRepository::class)
 */
class TblAnswerpost
{
    /**
     * @var int
     *
     * @ORM\Column(name="idAnswer", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idanswer;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="answeredDTM", type="datetime", nullable=false, options={"default"="current_timestamp()"})
     */
    private $answereddtm = 'current_timestamp()';

    /**
     * @var string
     *
     * @ORM\Column(name="answer", type="string", length=255, nullable=false)
     */
    private $answer;

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
     * @var \TblPost
     *
     * @ORM\ManyToOne(targetEntity="TblPost")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idPost", referencedColumnName="idPost")
     * })
     */
    private $idpost;

    public function getIdanswer(): ?int
    {
        return $this->idanswer;
    }

    public function getAnswereddtm(): ?\DateTimeInterface
    {
        return $this->answereddtm;
    }

    public function setAnswereddtm(\DateTimeInterface $answereddtm): self
    {
        $this->answereddtm = $answereddtm;

        return $this;
    }

    public function getAnswer(): ?string
    {
        return $this->answer;
    }

    public function setAnswer(string $answer): self
    {
        $this->answer = $answer;

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

    public function getIdpost(): ?TblPost
    {
        return $this->idpost;
    }

    public function setIdpost(?TblPost $idpost): self
    {
        $this->idpost = $idpost;

        return $this;
    }
}
