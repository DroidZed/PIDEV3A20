<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblReaction
 *
 * @ORM\Table(name="tbl_reaction", indexes={@ORM\Index(name="fk_reaction_user", columns={"idUser"}), @ORM\Index(name="fk_reaction_tyReact", columns={"idTypeReact"}), @ORM\Index(name="fk_reaction_post", columns={"idPost"}), @ORM\Index(name="fk_reaction_comment", columns={"idComment"})})
 * @ORM\Entity
 */
class TblReaction
{
    /**
     * @var int
     *
     * @ORM\Column(name="idReact", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idreact;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="reactedDTM", type="datetime", nullable=false, options={"default"="CURRENT_TIMESTAMP"})
     */
    private $reacteddtm = 'CURRENT_TIMESTAMP';

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
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idUser", referencedColumnName="idUser")
     * })
     */
    private $iduser;

    /**
     * @var \TblComment
     *
     * @ORM\ManyToOne(targetEntity="TblComment")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idComment", referencedColumnName="idComment")
     * })
     */
    private $idcomment;

    /**
     * @var \TblTypereact
     *
     * @ORM\ManyToOne(targetEntity="TblTypereact")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idTypeReact", referencedColumnName="idTypeReact")
     * })
     */
    private $idtypereact;

    public function getIdreact(): ?int
    {
        return $this->idreact;
    }

    public function getReacteddtm(): ?\DateTimeInterface
    {
        return $this->reacteddtm;
    }

    public function setReacteddtm(\DateTimeInterface $reacteddtm): self
    {
        $this->reacteddtm = $reacteddtm;

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
  

    public function getIduser(): ?User

    {
        return $this->iduser;
    }

    public function setIduser(?User $iduser): self
    {
        $this->iduser = $iduser;

        return $this;
    }

    public function getIdcomment(): ?TblComment
    {
        return $this->idcomment;
    }

    public function setIdcomment(?TblComment $idcomment): self
    {
        $this->idcomment = $idcomment;

        return $this;
    }

    public function getIdtypereact(): ?TblTypereact
    {
        return $this->idtypereact;
    }

    public function setIdtypereact(?TblTypereact $idtypereact): self
    {
        $this->idtypereact = $idtypereact;

        return $this;
    }


}
