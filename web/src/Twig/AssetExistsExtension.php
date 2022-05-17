<?php

namespace App\Twig;

use Twig\Extension\AbstractExtension;
use Twig\TwigFunction;

class AssetExistsExtension extends AbstractExtension
{

    public function getFunctions(): array
    {
        return [
            new TwigFunction( 'asset_exists', [$this, 'asset_exists']),
        ];
    }

    public function asset_exists($path): bool
    {
        if (!is_file(realpath($path))) {
            return false;
        }

        return true;
    }

}