<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblStateuser
 *
 * @ORM\Table(name="tbl_stateuser")
 * @ORM\Entity(repositoryClass="App\Repository\StateUserRepository")
 */
class TblStateuser
{
    /**
     * @var int
     *
     * @ORM\Column(name="idStateUser", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idstateuser;

    /**
     * @var string
     *
     * @ORM\Column(name="name", type="string", length=11, nullable=false)
     */
    private $name;

    public function getIdstateuser(): ?int
    {
        return $this->idstateuser;
    }

    public function getName(): ?string
    {
        return $this->name;
    }

    public function setName(string $name): self
    {
        $this->name = $name;

        return $this;
    }


}
