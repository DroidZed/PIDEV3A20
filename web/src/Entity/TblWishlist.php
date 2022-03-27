<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblWishlist
 *
 * @ORM\Table(name="tbl_wishlist", indexes={@ORM\Index(name="fk_wishlist_user", columns={"idUser"}), @ORM\Index(name="fk_wishlist_product", columns={"idProduct"})})
 * @ORM\Entity
 */
class TblWishlist
{
    /**
     * @var int
     *
     * @ORM\Column(name="idWishlist", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idwishlist;

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
     * @var \TblProduct
     *
     * @ORM\ManyToOne(targetEntity="TblProduct")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idProduct", referencedColumnName="idProduct")
     * })
     */
    private $idproduct;

    public function getIdwishlist(): ?int
    {
        return $this->idwishlist;
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
