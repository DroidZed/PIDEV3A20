<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblOption
 *
 * @ORM\Table(name="tbl_option", indexes={@ORM\Index(name="fk_post_option", columns={"idPost"})})
 * @ORM\Entity(repositoryClass="App\Repository\OptionRepository")
 */
class TblOption
{
    /**
     * @var int
     *
     * @ORM\Column(name="idOption", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idoption;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="createdDTM", type="datetime", nullable=false, options={"default"="CURRENT_TIMESTAMP"})
     */
    private $createddtm = 'CURRENT_TIMESTAMP';

    /**
     * @var int
     *
     * @ORM\Column(name="statusOptions", type="integer", nullable=false)
     */
    private $statusoptions;

    /**
     * @var string
     *
     * @ORM\Column(name="contentOption", type="string", length=150, nullable=false)
     */
    private $contentoption;

    /**
     * @var \TblPost
     *
     * @ORM\ManyToOne(targetEntity="TblPost")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idPost", referencedColumnName="idPost")
     * })
     */
    private $idpost;

    public function getIdoption(): ?int
    {
        return $this->idoption;
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

    public function getStatusoptions(): ?int
    {
        return $this->statusoptions;
    }

    public function setStatusoptions(int $statusoptions): self
    {
        $this->statusoptions = $statusoptions;

        return $this;
    }

    public function getContentoption(): ?string
    {
        return $this->contentoption;
    }

    public function setContentoption(string $contentoption): self
    {
        $this->contentoption = $contentoption;

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
