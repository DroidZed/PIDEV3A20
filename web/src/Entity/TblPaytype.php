<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblPaytype
 *
 * @ORM\Table(name="tbl_paytype")
 * @ORM\Entity(repositoryClass="App\Repository\PayTypeRepository")
 */
class TblPaytype
{
    /**
     * @var int
     *
     * @ORM\Column(name="idPayType", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idpaytype;

    /**
     * @var string
     *
     * @ORM\Column(name="payType", type="string", length=15, nullable=false)
     */
    private $paytype;

    public function getIdpaytype(): ?int
    {
        return $this->idpaytype;
    }

    public function getPaytype(): ?string
    {
        return $this->paytype;
    }

    public function setPaytype(string $paytype): self
    {
        $this->paytype = $paytype;

        return $this;
    }


}
