<?php

namespace App\Entity;

use DateTimeInterface;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * TblUserorder
 *
 * @ORM\Table(name="tbl_userorder", indexes={@ORM\Index(name="fk_userOrder_user", columns={"idUser"}), @ORM\Index(name="fk_userOrder_statusOrder", columns={"idStatusOrder"}), @ORM\Index(name="fk_userOrder_payType", columns={"idPayType"})})
 * @ORM\Entity
 */
class TblUserorder
{
    /**
     * @var int
     *
     * @ORM\Column(name="numberOrder", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $numberorder;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="createdDTM", type="date", nullable=false)
     */
    private $createddtm;

    /**
     * @var \DateTime|null
     *
     * @ORM\Column(name="payDTM", type="date", nullable=true, options={"default"="NULL"})
     */
    private $paydtm = 'NULL';

    /**
     * @var string
     *
     * @ORM\Column(name="orderAddress", type="string", length=255, nullable=false)
     * @Assert\Length(
     *     min = 10,
     *     minMessage = "Address too short !"
     * )
     */
    private $orderaddress;

    /**
     * @var \TblStatusorder
     *
     * @ORM\ManyToOne(targetEntity="TblStatusorder")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idStatusOrder", referencedColumnName="idStatusOrder")
     * })
     */
    private $idstatusorder;

    /**
     * @var \TblPaytype
     *
     * @ORM\ManyToOne(targetEntity="TblPaytype")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idPayType", referencedColumnName="idPayType")
     * })
     */
    private $idpaytype;

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idUser", referencedColumnName="idUser")
     * })
     */
    private $iduser;

    public function getNumberorder(): ?int
    {
        return $this->numberorder;
    }

    public function getCreateddtm(): DateTimeInterface
    {
        return $this->createddtm;
    }

    public function setCreateddtm(DateTimeInterface $createddtm): self
    {
        $this->createddtm = $createddtm;

        return $this;
    }

    public function getPaydtm(): ?DateTimeInterface
    {
        return $this->paydtm;
    }

    public function setPaydtm(?DateTimeInterface $paydtm): self
    {
        $this->paydtm = $paydtm;

        return $this;
    }

    public function getOrderaddress(): ?string
    {
        return $this->orderaddress;
    }

    public function setOrderaddress(string $orderaddress): self
    {
        $this->orderaddress = $orderaddress;

        return $this;
    }

    public function getIdstatusorder(): ?TblStatusorder
    {
        return $this->idstatusorder;
    }

    public function setIdstatusorder(?TblStatusorder $idstatusorder): self
    {
        $this->idstatusorder = $idstatusorder;

        return $this;
    }
  
    public function getIdpaytype(): ?TblPaytype
    {
        return $this->idpaytype;
    }

    public function setIdpaytype(?TblPaytype $idpaytype): self
    {
        $this->idpaytype = $idpaytype;

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


}
