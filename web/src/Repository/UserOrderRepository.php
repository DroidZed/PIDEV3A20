<?php

namespace App\Repository;

use App\Entity\TblUserorder;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\ORM\OptimisticLockException;
use Doctrine\ORM\ORMException;
use Doctrine\ORM\Query;
use Doctrine\Persistence\ManagerRegistry;
use Doctrine\ORM\Tools\Pagination\Paginator;

/**
 * @method TblUserorder|null find($id, $lockMode = null, $lockVersion = null)
 * @method TblUserorder|null findOneBy(array $criteria, array $orderBy = null)
 * @method TblUserorder[]    findAll()
 * @method TblUserorder[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class UserOrderRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, TblUserorder::class);
    }

    /**
     * @throws ORMException
     * @throws OptimisticLockException
     */
    public function add(TblUserorder $entity, bool $flush = true): void
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
    public function remove(TblUserorder $entity, bool $flush = true): void
    {
        $this->_em->remove($entity);
        if ($flush) {
            $this->_em->flush();
        }
    }

    public function save(TblUserorder $entity, bool $flush = true): int
    {
        $this->_em->persist($entity);
        if ($flush) {
            $this->_em->flush();

        }
        return $entity->getNumberorder();
    }

    /**
     *
     * @param integer $currentPage The current page (passed from controller)
     *
     * @return Paginator
     */
    public function getAllOrdersAdmin(int $currentPage = 1): Paginator
    {
        // Create our query
        $query = $this->createQueryBuilder('o')->getQuery();

        return $this->paginate($query, $currentPage);
    }

    /**
     * Paginator Helper
     *
     * Pass through a query object, current page & limit
     * the offset is calculated from the page and limit
     * returns an `Paginator` instance, which you can call the following on:
     *
     *     $paginator->getIterator()->count() # Total fetched (ie: `5` posts)
     *     $paginator->count() # Count of ALL posts (ie: `20` posts)
     *     $paginator->getIterator() # ArrayIterator
     *
     * @param Query $dql   DQL Query Object
     * @param integer $page  Current page (defaults to 1)
     * @param integer $limit The total number per page (defaults to 5)
     *
     * @return Paginator
     */
    public function paginate(Query $dql, int $page = 1, int $limit = 5): Paginator
    {
        $paginator = new Paginator($dql);

        $paginator->getQuery()
            ->setFirstResult($limit * ($page - 1)) // Offset
            ->setMaxResults($limit); // Limit

        return $paginator;
    }


    public function getOrdersSortedPaginated(string $crit, string $order, int $currentPage = 1): Paginator
    {

        // Create our query
        $query = $this->createQueryBuilder('o')
            ->addOrderBy("o.".$crit, $order)
            ->getQuery();

        return $this->paginate($query, $currentPage);
    }

    // /**
    //  * @return TblUserorder[] Returns an array of TblUserorder objects
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
    public function findOneBySomeField($value): ?TblUserorder
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
