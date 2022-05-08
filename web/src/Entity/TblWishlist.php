<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;

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
     * @Groups("wishlist:items")
     */
    private $idwishlist;

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idUser", referencedColumnName="idUser")
     * })
     * @Groups("wishlist:items")
     */
    private $iduser;

    /**
     * @var \TblProduct
     *
     * @ORM\ManyToOne(targetEntity="TblProduct")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idProduct", referencedColumnName="idProduct")
     * })
     *
     * @Groups("wishlist:items")
     */
    private $idproduct;

    public function getIdwishlist(): ?int
    {
        return $this->idwishlist;
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
