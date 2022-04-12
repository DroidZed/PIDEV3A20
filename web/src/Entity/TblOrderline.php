<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblOrderline
 *
 * @ORM\Table(name="tbl_orderline", indexes={@ORM\Index(name="fk_ordline_product", columns={"idProduct"}), @ORM\Index(name="fk_ordline_userorder", columns={"numberOrder"})})
 * @ORM\Entity
 */
class TblOrderline
{
    /**
     * @var int
     *
     * @ORM\Column(name="idOrderLine", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idorderline;

    /**
     * @var int
     *
     * @ORM\Column(name="quantOrdLine", type="integer", nullable=false)
     */
    private $quantordline;

    /**
     * @var \TblProduct
     *
     * @ORM\ManyToOne(targetEntity="TblProduct")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idProduct", referencedColumnName="idProduct")
     * })
     */
    private $idproduct;

    /**
     * @var \TblUserorder
     *
     * @ORM\ManyToOne(targetEntity="TblUserorder")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="numberOrder", referencedColumnName="numberOrder")
     * })
     */
    private $numberorder;

    public function getIdorderline(): ?int
    {
        return $this->idorderline;
    }

    public function getQuantordline(): ?int
    {
        return $this->quantordline;
    }

    public function setQuantordline(int $quantordline): self
    {
        $this->quantordline = $quantordline;

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

    public function getNumberorder(): ?TblUserorder
    {
        return $this->numberorder;
    }

    public function setNumberorder(?TblUserorder $numberorder): self
    {
        $this->numberorder = $numberorder;

        return $this;
    }


}
