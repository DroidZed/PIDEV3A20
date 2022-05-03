<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Exception;
use JetBrains\PhpStorm\Internal\LanguageLevelTypeAware;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * User
 *
 * @ORM\Table(name="tbl_user", indexes={@ORM\Index(name="fk_badge_user", columns={"idBadge"}), @ORM\Index(name="fk_user_stateUser", columns={"stateUser"})})
 * @ORM\Entity
 * * @UniqueEntity(
 *     fields= {"email"},
 *     message= "mail utilisé"
 * )
 */
class User implements UserInterface,\Serializable
{
    /**
     * @var int
     * @Groups ("post:read")
     * @ORM\Column(name="idUser", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $id;

    /**
     * @var string
     * @Groups ("post:read")
     * @ORM\Column(name="nameUser", type="string", length=100, nullable=false)
     * @Assert\NotBlank
     * @Assert\Length (
     *
     *     max=20,
     *     min=6,
     *     minMessage="nom obligatoirement supperieur à {{ limit }} caracteres " ,
     *     maxMessage="nom obligatoirement ne pase pas {{ limit }} caracteres")
     */
    private $nom;

    /**
     * @var string
     * @Groups ("post:read")
     * @ORM\Column(name="emailUser", type="string", length=50, nullable=false)
     * @Assert\Email()
     *  @Assert\Regex(
     *     pattern="/^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/",
     *     message="not_valid_email"
     * )
     * @Assert\NotBlank
     * @Assert\Length (
     *
     *     max=15,
     *     min=5,
     *     minMessage="nom obligatoirement supperieur à {{ limit }} caracteres " ,
     *     maxMessage="nom obligatoirement ne pase pas {{ limit }} caracteres")
     */
    private $email;

    /**
     * @var \DateTime
     * @Groups ("post:read")
     * @ORM\Column(name="createdDTM", type="date", nullable=false, options={"default"="CURRENT_TIMESTAMP"})
     *
     */
    private $createddtm = 'CURRENT_TIMESTAMP';

    /**
     * @var \DateTime|null
     *@Groups ("post:read")
     * @ORM\Column(name="desactivationDTM", type="date", nullable=true)
     */
    private $desactivationdtm;

    /**
     * @var string
     * @Groups ("post:read")
     * @ORM\Column(name="pwdUser", type="string", length=60, nullable=false)
     *  @Assert\NotBlank
     * @Assert\Length (
     *
     *     max=15,
     *     min=5,
     *     minMessage="mdp obligatoirement supperieur à {{ limit }} caracteres " ,
     *     maxMessage="mdp obligatoirement ne pase pas {{ limit }} caracteres")
     */
    private $password;

    /**
     * @Groups ("post:read")
     * @var string
     * @Assert\NotBlank
     * @Assert\Length (
     *
     *     max=8,
     *     min=8,
     *     minMessage="mdp obligatoirement supperieur à {{ limit }} caracteres " ,
     *     maxMessage="mdp obligatoirement ne pase pas {{ limit }} caracteres")
     * @ORM\Column(name="phone", type="string", length=8, nullable=false)
     */
    private $tel;

    /**
     * @var string|null
     * @Groups ("post:read")
     * @ORM\Column(name="photoUser", type="string", length=255, nullable=true)
     * @Assert\Length (
     *
     *     max=8,
     *     min=8,
     *     minMessage="nom obligatoirement supperieur à {{ limit }} caracteres " ,
     *     maxMessage="nom obligatoirement ne pase pas {{ limit }} caracteres")
     */
    private $photo;

    /**
     * @var array
     * @Groups ("post:read")
     * @ORM\Column(name="roleUser", type="json")
     */
    private $roles;

    /**
     * @var int|null
     * @Groups ("post:read")
     * @ORM\Column(name="levelUser", type="integer", nullable=true, options={"default"="1"})
     */
    private $leveluser = 1;

    /**
     * @var string|null
     * @Groups ("post:read")
     * @ORM\Column(name="cv", type="string", length=255, nullable=true)
     */
    private $cv;

    /**
     * @var string|null
     * @Groups ("post:read")
     * @ORM\Column(name="descUser", type="string", length=255, nullable=true)
     */
    private $descuser;

    /**
     * @var string|null
     * @Groups ("post:read")
     * @ORM\Column(name="activation_token", type="string", length=20, nullable=true)
     */
    private $activation_token;
    /**
     * @var string|null
     * @Groups ("post:read")
     * @ORM\Column(name="reset_password_request", type="string", length=255, nullable=true)
     */
    private $reset_password_request;

    /**
     * @var TblBadge
     * @Groups ("post:read")
     * @ORM\ManyToOne(targetEntity="TblBadge")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idBadge", referencedColumnName="idBadge")
     * })
     */
    private $idbadge;

    /**
     * @var \TblStateuser
     * @Groups ("post:read")
     * @ORM\ManyToOne(targetEntity="TblStateuser")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="stateUser", referencedColumnName="idStateUser")
     * })
     */
    private $stateuser;

    /**
     * @return int
     */
    public function getId(): int
    {
        return $this->id;
    }

    /**
     * @param int $id
     */
    public function setId(int $id): void
    {
        $this->id = $id;
    }

    /**
     * @return string
     */
    public function getNom(): ?string
    {
        return $this->nom;
    }

    /**
     * @param string $nom
     */
    public function setNom(?string $nom): void
    {
        $this->nom = $nom;
    }
    /**
     * @param string $reset_password_request
     */
    public function setResetPasswordRequest(string $code): void
    {
        $this->reset_password_request = $code;
    }
    /**
     * @return string
     */
    public function getResetPasswordRequest(): ?string
    {
        return $this->reset_password_request;
    }
    /**
     * @return string
     */
    public function getEmail(): ?string
    {
        return $this->email;
    }

    /**
     * @param string $email
     */
    public function setEmail(string $email): void
    {
        $this->email = $email;
    }

    /**
     * @return \DateTime
     */
    public function getCreateddtm()
    {
        return $this->createddtm;
    }

    /**
     * @param \DateTime $createddtm
     */
    public function setCreateddtm($createddtm): void
    {
        $this->createddtm = $createddtm;
    }

    /**
     * @return \DateTime|null
     */
    public function getDesactivationdtm(): ?\DateTime
    {
        return $this->desactivationdtm;
    }

    /**
     * @param \DateTime|null $desactivationdtm
     */
    public function setDesactivationdtm(?\DateTime $desactivationdtm): void
    {
        $this->desactivationdtm = $desactivationdtm;
    }

    /**
     * @return string
     */
    public function getPassword(): ?string
    {
        return $this->password;
    }

    /**
     * @param string $password
     */
    public function setPassword(string $password): void
    {
        $this->password = $password;
    }

    /**
     * @return string
     */
    public function getTel(): ?string
    {
        return $this->tel;
    }

    /**
     * @param string $tel
     */
    public function setTel(string $tel): void
    {
        $this->tel = $tel;
    }

    /**
     * @return string|null
     */
    public function getPhoto(): ?string
    {
        return $this->photo;
    }

    /**
     * @param string|null $photo
     */
    public function setPhoto(?string $photo): void
    {
        $this->photo = $photo;
    }

    /**
     * @return array
     */
    public function getRoles(): array
    {
        $roles = $this->roles;

        return array_unique($roles);
    }

    public function setRoles(array $roles): self
    {
        $this->roles = $roles;

        return $this;
    }
    /**
     * @return int|null
     */
    public function getLeveluser(): ?int
    {
        return $this->leveluser;
    }

    /**
     * @param int|null $leveluser
     */
    public function setLeveluser(?int $leveluser): void
    {
        $this->leveluser = $leveluser;
    }

    /**
     * @return string|null
     */
    public function getCv(): ?string
    {
        return $this->cv;
    }

    /**
     * @param string|null $cv
     */
    public function setCv(?string $cv): void
    {
        $this->cv = $cv;
    }

    /**
     * @return string|null
     */
    public function getDescuser(): ?string
    {
        return $this->descuser;
    }

    /**
     * @param string|null $descuser
     */
    public function setDescuser(?string $descuser): void
    {
        $this->descuser = $descuser;
    }

    /**
     * @return string|null
     */
    public function getActivationToken(): ?string
    {
        return $this->activation_token;
    }

    /**
     * @param string|null $activation_token
     */
    public function setActivationToken(?string $activationToken): void
    {
        $this->activation_token = $activationToken;
    }

    /**
     * @return ?TblBadge
     */
    public function getIdbadge(): ?TblBadge
    {
        return $this->idbadge;
    }

    /**
     * @param TblBadge $idbadge
     */
    public function setIdbadge(?TblBadge $idbadge): void
    {
        $this->idbadge = $idbadge;
    }

    /**
     * @return \TblStateuser
     */
    public function getStateuser(): ?TblStateuser
    {
        return $this->stateuser;
    }

    /**
     * @param \TblStateuser $stateuser
     */
    public function setStateuser(?TblStateuser $stateuser): void
    {
        $this->stateuser = $stateuser;
    }


    public function serialize()
    {
        return serialize(
            [
                $this->id,
                $this->nom,
                $this->email,
                $this->password,
                $this->tel,
                $this->stateuser
            ]

        );
    }

    public function unserialize($data)
    {
        list(
            $this->id,
            $this->nom,
            $this->email,
            $this->password,
            $this->tel,
            $this->stateuser

            )=unserialize($data,['allowed_classes'=>false]);
    }

    public function getSalt()
    {

    }

    public function getUsername()
    {
        return $this->id;
    }

    public function eraseCredentials()
    {
        // TODO: Implement eraseCredentials() method.
    }


}
