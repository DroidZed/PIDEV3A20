<?php

namespace App\Entity;

class JsonResponseDAO
{
    private string $message;

    /**
     * @param string $message
     */
    public function __construct(string $message)
    {
        $this->message = $message;
    }

    /**
     * @return string
     */
    public function getMessage(): string
    {
        return $this->message;
    }

    /**
     * @param string $message
     * @return JsonResponseDAO
     */
    public function setMessage(string $message): JsonResponseDAO
    {
        $this->message = $message;
        return $this;
    }


}