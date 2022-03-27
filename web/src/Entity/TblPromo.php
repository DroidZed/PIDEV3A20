<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblPromo
 *
 * @ORM\Table(name="tbl_promo", indexes={@ORM\Index(name="fk_promo_product", columns={"idProduct"})})
 * @ORM\Entity
 */
class TblPromo
{
    /**
     * @var int
     *
     * @ORM\Column(name="idPromo", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idpromo;

    /**
     * @var string
     *
     * @ORM\Column(name="codePromo", type="string", length=150, nullable=false)
     */
    private $codepromo;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="createdDTM", type="date", nullable=false, options={"default"="CURRENT_TIMESTAMP"})
     */
    private $createddtm = 'CURRENT_TIMESTAMP';

    /**
     * @var int
     *
     * @ORM\Column(name="discountPromo", type="integer", nullable=false)
     */
    private $discountpromo;

    /**
     * @var int|null
     *
     * @ORM\Column(name="statusPromo", type="integer", nullable=true, options={"default"="1"})
     */
    private $statuspromo = 1;

    /**
     * @var \TblProduct
     *
     * @ORM\ManyToOne(targetEntity="TblProduct")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idProduct", referencedColumnName="idProduct")
     * })
     */
    private $idproduct;

    public function getIdpromo(): ?int
    {
        return $this->idpromo;
    }

    public function getCodepromo(): ?string
    {
        return $this->codepromo;
    }

    public function setCodepromo(string $codepromo): self
    {
        $this->codepromo = $codepromo;

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

    public function getDiscountpromo(): ?int
    {
        return $this->discountpromo;
    }

    public function setDiscountpromo(int $discountpromo): self
    {
        $this->discountpromo = $discountpromo;

        return $this;
    }

    public function getStatuspromo(): ?int
    {
        return $this->statuspromo;
    }

    public function setStatuspromo(?int $statuspromo): self
    {
        $this->statuspromo = $statuspromo;

        return $this;
    }

    public function getIdproduct(): ?TblProduct
    {
        return $this->idproduct;
    }

    public function setIdproduct(?TblProduct $idproduct): self
    {
        $this->idproduct = $idproduct;

        return $this;
    }


}
