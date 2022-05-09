<?php

namespace App\Entity;
use phpDocumentor\Reflection\Types\This;
use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\ORM\Mapping as ORM;
use Vich\UploaderBundle\Mapping\Annotation as Vich;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * TblProduct
 *
 * @ORM\Table(name="tbl_product", indexes={@ORM\Index(name="fk_product_category", columns={"idCategory"}), @ORM\Index(name="fk_product_user", columns={"idUser"})})
 * @ORM\Entity(repositoryClass="App\Repository\ProductRepository")
 * @Vich\Uploadable
 */
class TblProduct
{
    /**
     * @var int
     *
     * @ORM\Column(name="idProduct", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     * @Groups({"wishlist:items", "products"})
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
     * @var string
     * @Groups({"wishlist:items", "products"})
     * @ORM\Column(name="nameProduct", type="string", length=100, nullable=false)
     */
    private $nameproduct;

    /**
     * @var float
     * @Assert\NotBlank(message="Empty Case !!")
     * @Assert\Range(min=1,max=9999)
     * @Groups({"wishlist:items", "products"})
     * @ORM\Column(name="priceProduct", type="float", precision=10, scale=0, nullable=false)
     */
    private $priceproduct;

    /**
     * @var int
     * @Assert\Positive
     * @Groups({"wishlist:items", "products"})
     * @ORM\Column(name="QtyProduct", type="integer", nullable=false)
     */
    private $qtyproduct;

    /**
     * @var string
     * @ORM\Column(name="imageProduct", type="string", length=150, nullable=true)
     * @Groups({"wishlist:items", "products"})
     */
    private $imageproduct;

    /**
     * @var \DateTime
     * @ORM\Column(name="createdDTM", type="date", nullable=false, options={"default"="CURRENT_TIMESTAMP"})
     */
    private $createddtm = 'current_timestamp()';

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idUser", referencedColumnName="idUser")
     * })
     * @Groups({"wishlist:items", "products"})
     */
    private $iduser;

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
     * @ORM\Column(name="image" , type="string", length=255)
     * @var string
     */
    private $image;

    /**
     * @Vich\UploadableField(mapping="product_images", fileNameProperty="image")
     * @var File
     */
    private $imageFile;

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

    public function getIduser(): ?User
    {
        return $this->iduser;
    }

    public function setIduser(?User $iduser): self
    {
        $this->iduser = $iduser;

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

    /**
     * @param string|null $image
     * @return $this
     */
    public function setImageFile( $image = null)
    {
        $this->imageFile = $image;

        // VERY IMPORTANT:
        // It is required that at least one field changes if you are using Doctrine,
        // otherwise the event listeners won't be called and the file is lost
       // if ($image) {
            // if 'updatedAt' is not defined in your entity, use another property
          //  $this->updatedAt = new \DateTime('now');
      //  }
    }

    public function getImageFile()
    {
        return $this->imageFile;
    }

    public function setImage($image)
    {
        $this->image = $image;
    }

    public function getImage()
    {
        return $this->image;
    }


}
