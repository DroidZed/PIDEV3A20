<?php

namespace App\Repository;

use App\Entity\TblProduct;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\ORM\OptimisticLockException;
use Doctrine\ORM\ORMException;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method TblProduct|null find($id, $lockMode = null, $lockVersion = null)
 * @method TblProduct|null findOneBy(array $criteria, array $orderBy = null)
 * @method TblProduct[]    findAll()
 * @method TblProduct[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ProductRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, TblProduct::class);
    }

    /**
     * @throws ORMException
     * @throws OptimisticLockException
     */
    public function add(TblProduct $entity, bool $flush = true): void
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
    public function remove(TblProduct $entity, bool $flush = true): void
    {
        $this->_em->remove($entity);
        if ($flush) {
            $this->_em->flush();
        }
    }

    /**
    * @return TblProduct[] Returns an array of TblProduct objects
    */
    public function findNonZero()
    {
        return $this->createQueryBuilder('p')
            ->andWhere('p.qtyproduct > 0')
            ->getQuery()
            ->getResult()
        ;
    }


    /*
    public function findOneBySomeField($value): ?TblProduct
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
