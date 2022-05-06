<?php

namespace App\Services;

use Endroid\QrCode\Builder\Builder;
use Endroid\QrCode\Builder\BuilderInterface;
use Endroid\QrCode\Encoding\Encoding;
use Endroid\QrCode\ErrorCorrectionLevel\ErrorCorrectionLevelHigh;
use Endroid\QrCode\Label\Alignment\LabelAlignmentCenter;
use Endroid\QrCode\Label\Font\NotoSans;
use Endroid\QrCode\RoundBlockSizeMode\RoundBlockSizeModeMargin;
use Endroid\QrCode\Writer\PngWriter;
use Endroid\QrCode\Writer\Result\ResultInterface;

class QrCodeService
{
    private BuilderInterface $customQrCodeBuilder;

    /**
     * @param BuilderInterface $customQrCodeBuilder
     */
    public function __construct(BuilderInterface $customQrCodeBuilder)
    {
        $this->customQrCodeBuilder = $customQrCodeBuilder;
    }

    /**
     * @param string $data
     * @param string $savePath
     * @return void
     */
    public function gen(string $data, string $savePath): void
    {
        $this->customQrCodeBuilder
            ->data($data)
            ->build()->saveToFile($savePath);
    }
}