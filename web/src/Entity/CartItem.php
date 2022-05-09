<?php

namespace App\Entity;

class CartItem
{
    private int $userId;
    private TblProduct $product;
    private int $quantity;

    /**
     * @param int $userId
     */
    public function __construct(int $userId)
    {
        $this->userId = $userId;
    }

    /**
     * @return int
     */
    public function getUserId(): int
    {
        return $this->userId;
    }

    /**
     * @param int $userId
     * @return CartItem
     */
    public function setUserId(int $userId): CartItem
    {
        $this->userId = $userId;
        return $this;
    }

    /**
     * @return TblProduct
     */
    public function getProduct(): TblProduct
    {
        return $this->product;
    }

    /**
     * @param TblProduct $product
     * @return CartItem
     */
    public function setProduct(TblProduct $product): CartItem
    {
        $this->product = $product;
        return $this;
    }

    /**
     * @return int
     */
    public function getQuantity(): int
    {
        return $this->quantity;
    }

    /**
     * @param int $quantity
     * @return CartItem
     */
    public function setQuantity(int $quantity): CartItem
    {
        $this->quantity = $quantity;
        return $this;
    }


}