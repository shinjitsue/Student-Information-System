<?php

namespace Database\Factories;

use App\Models\Student; 
use Illuminate\Database\Eloquent\Factories\Factory;

class StudentFactory extends Factory
{
    // The name of the factory's corresponding model
    protected $model = Student::class;

    // Define the model's default state
    public function definition()
    {
        return [
            'name' => $this->faker->name,
            'age' => $this->faker->numberBetween(15, 25), 
            'address' => $this->faker->address,
            'contact_number' => $this->faker->phoneNumber,
        ];
    }
}