<?php

namespace App\services;
use Endroid\QrCode\Builder\BuilderInterface;
use Endroid\QrCode\ErrorCorrectionLevel\ErrorCorrectionLevelLow;

class QrcodeService
{
    /**
     * @var BuilderInterface
     */

    protected $builder;
    public function __construct(BuilderInterface $builder)
    {
        $this->builder = $builder;
    }

    public function qrcode($query)
    {
        $url = 'https://www.google.com/search?q=';

        $date = new \DateTime('NOW');
        $dateString = $date->format('d-m-Y H:i:s');

        $result = $this->builder
            ->errorCorrectionLevel(new ErrorCorrectionLevelLow())
            ->data($url.$query)
            ->size(400)
            ->margin(10)
            ->logoPath(\dirname(__DIR__, 2).'/public/uploads/'.'images/logo.png')
            ->logoResizeToWidth('100')
            ->logoResizeToHeight('25')
            ->labelText($dateString)
            ->build();

        $namePng = uniqid('', '').'.png';
        $result->saveToFile((\dirname(__DIR__, 2).'/public/uploads/qr_code'.$namePng));

        return $result->getDataUri();
    }
}