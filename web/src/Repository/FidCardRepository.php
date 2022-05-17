<?php

namespace App\Repository;

use App\Entity\TblFidcard;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\ORM\OptimisticLockException;
use Doctrine\ORM\ORMException;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method TblFidcard|null find($id, $lockMode = null, $lockVersion = null)
 * @method TblFidcard|null findOneBy(array $criteria, array $orderBy = null)
 * @method TblFidcard[]    findAll()
 * @method TblFidcard[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class FidCardRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, TblFidcard::class);
    }

    /**
     * @throws ORMException
     * @throws OptimisticLockException
     */
    public function add(TblFidcard $entity, bool $flush = true): void
    {
        $this->_em->persist($entity);
        if ($flush) {
            $this->_em->flush();
        }
    }

    /**
     * @throws ORMException
     * @throws OptimisticLockException
     */
    public function remove(TblFidcard $entity, bool $flush = true): void
    {
        $this->_em->remove($entity);
        if ($flush) {
            $this->_em->flush();
        }
    }

    /**
     * @return mixed
     */
    public function joinUsersWithFidCard()
    {
        return $this->_em->createQuery("SELECT u.nom, u.descuser, f.nbpointsfid, u.photo, t.cardtype from App\Entity\TblFidcard f JOIN f.iduser u JOIN f.idcardtype t order by f.nbpointsfid DESC");
    }

    public function getUsersAndTheirPoints(int $limit = 3) {
        $query = $this->joinUsersWithFidCard();
        if ($limit > 0) {
            $query->setMaxResults( $limit );
        }
        return $query->getResult();
    }

    // /**
    //  * @return TblFidcard[] Returns an array of TblFidcard objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('t')
            ->andWhere('t.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('t.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?TblFidcard
    {
        return $this->createQueryBuilder('t')
            ->andWhere('t.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
}
