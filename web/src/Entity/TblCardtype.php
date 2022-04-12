<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblCardtype
 *
 * @ORM\Table(name="tbl_cardtype")
 * @ORM\Entity
 */
class TblCardtype
{
    /**
     * @var int
     *
     * @ORM\Column(name="idCardType", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idcardtype;

    /**
     * @var string
     *
     * @ORM\Column(name="cardType", type="string", length=8, nullable=false)
     */
    private $cardtype;

    public function getIdcardtype(): ?int
    {
        return $this->idcardtype;
    }

    public function getCardtype(): ?string
    {
        return $this->cardtype;
    }

    public function setCardtype(string $cardtype): self
    {
        $this->cardtype = $cardtype;

        return $this;
    }


}
