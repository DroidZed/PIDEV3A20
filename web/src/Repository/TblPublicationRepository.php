<?php

namespace App\Repository;

use Doctrine\ORM\EntityRepository;

class TblPublicationRepository extends EntityRepository
{

    public function triLikesASC($id)
    {
        return$this->getEntityManager()
            ->createQuery(
                'SELECT r FROM App\Entity\TblVideogame r   where r.namevg like :suj OR r.rating like :suj  ORDER BY nbrjaime ASC;'
            )
            ->setParameter('suj', $id)
            ->getResult();
    }
}