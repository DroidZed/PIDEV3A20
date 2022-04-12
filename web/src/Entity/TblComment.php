<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblComment
 *
 * @ORM\Table(name="tbl_comment", indexes={@ORM\Index(name="fk_post_comment", columns={"idPost"}), @ORM\Index(name="fk_user_comment", columns={"idUser"})})
 * @ORM\Entity
 */
class TblComment
{
    /**
     * @var int
     *
     * @ORM\Column(name="idComment", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idcomment;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="postedDTM", type="date", nullable=false)
     */
    private $posteddtm;

    /**
     * @var string
     *
     * @ORM\Column(name="comment", type="string", length=300, nullable=false)
     */
    private $comment;

    /**
     * @var \TblPost
     *
     * @ORM\ManyToOne(targetEntity="TblPost")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idPost", referencedColumnName="idPost")
     * })
     */
    private $idpost;

    /**
     * @var \TblUser
     *
     * @ORM\ManyToOne(targetEntity="TblUser")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idUser", referencedColumnName="idUser")
     * })
     */
    private $iduser;

    public function getIdcomment(): ?int
    {
        return $this->idcomment;
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

    public function getComment(): ?string
    {
        return $this->comment;
    }

    public function setComment(string $comment): self
    {
        $this->comment = $comment;

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
