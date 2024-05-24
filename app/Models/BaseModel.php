<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

/**
 * Class BaseModel
 *
 * This class serves as the base model for all other models in the application.
 * It extends the Illuminate\Database\Eloquent\Model class.
 */
class BaseModel extends Model
{
    /**
     * Get a new factory instance for the model.
     *
     * This method is used to create a new instance of the model's factory class.
     * The factory class is determined based on the model's class name.
     *
     * @return mixed
     */
    protected static function newFactory()
    {
        $factoryClass = 'Database\\Factories\\' . class_basename(static::class) . 'Factory';
        return new $factoryClass; 
    }
}