<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblStatusorder
 *
 * @ORM\Table(name="tbl_statusorder")
 * @ORM\Entity(repositoryClass="App\Repository\StatusOrderRepository")
 */
class TblStatusorder
{
    /**
     * @var int
     *
     * @ORM\Column(name="idStatusOrder", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idstatusorder;

    /**
     * @var string
     *
     * @ORM\Column(name="statusOrder", type="string", length=11, nullable=false)
     */
    private $statusorder;

    public function getIdstatusorder(): ?int
    {
        return $this->idstatusorder;
    }

    public function getStatusorder(): ?string
    {
        return $this->statusorder;
    }

    public function setStatusorder(string $statusorder): self
    {
        $this->statusorder = $statusorder;

        return $this;
    }

    public function __toString()
    {
        return $this->statusorder;
    }


}
