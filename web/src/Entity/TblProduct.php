<?php

namespace App\Entity;
use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\ORM\Mapping as ORM;

/**
 * TblProduct
 *
 * @ORM\Table(name="tbl_product", indexes={@ORM\Index(name="fk_product_category", columns={"idCategory"}), @ORM\Index(name="fk_product_user", columns={"idUser"})})
 * @ORM\Entity(repositoryClass="App\Repository\ProductRepository")
 */
class TblProduct
{
    /**
     * @var int
     *
     * @ORM\Column(name="idProduct", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idproduct;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Empty Case !!")
     * @Assert\Length(
     *     min=3,
     *     max= 20,
     *     minMessage ="Name should be longer than 3",
     *     maxMessage ="Name should be shoter than 20")
     *
     */
    private $nameproduct;

    /**
     * @var float
     * @Assert\NotBlank(message="Empty Case !!")
     * @Assert\Range(min=1,max=9999)
     * @ORM\Column(name="priceProduct", type="float", precision=10, scale=0, nullable=false)
     */
    private $priceproduct;

    /**
     * @var int
     * @Assert\Positive
     * @ORM\Column(name="QtyProduct", type="integer", nullable=false)
     */
    private $qtyproduct;

    /**
     * @var string
     * @Assert\NotBlank(message="Empty Case !!")
     * @ORM\Column(name="imageProduct", type="string", length=150, nullable=false)
     */
    private $imageproduct;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="createdDTM", type="date", nullable=false, options={"default"="CURRENT_TIMESTAMP"})
     */
    private $createddtm = 'CURRENT_TIMESTAMP';

    /**
     * @var \TblCategory
     *
     * @ORM\ManyToOne(targetEntity="TblCategory")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idCategory", referencedColumnName="idCategory")
     * })
     */
    private $idcategory;

    /**
     * @var \TblUser
     *
     * @ORM\ManyToOne(targetEntity="TblUser")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idUser", referencedColumnName="idUser")
     * })
     */
    private $iduser;

    public function getIdproduct(): ?int
    {
        return $this->idproduct;
    }

    public function getNameproduct(): ?string
    {
        return $this->nameproduct;
    }

    public function setNameproduct(string $nameproduct): self
    {
        $this->nameproduct = $nameproduct;

        return $this;
    }

    public function getPriceproduct(): ?float
    {
        return $this->priceproduct;
    }

    public function setPriceproduct(float $priceproduct): self
    {
        $this->priceproduct = $priceproduct;

        return $this;
    }

    public function getQtyproduct(): ?int
    {
        return $this->qtyproduct;
    }

    public function setQtyproduct(int $qtyproduct): self
    {
        $this->qtyproduct = $qtyproduct;

        return $this;
    }

    public function getImageproduct(): ?string
    {
        return $this->imageproduct;
    }

    public function setImageproduct(string $imageproduct): self
    {
        $this->imageproduct = $imageproduct;

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

    public function getIdcategory(): ?TblCategory
    {
        return $this->idcategory;
    }

    public function setIdcategory(?TblCategory $idcategory): self
    {
        $this->idcategory = $idcategory;

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
