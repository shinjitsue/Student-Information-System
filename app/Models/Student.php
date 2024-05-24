<?php

namespace App\Models;

class Student extends BaseModel 
{
    // Specify the fillable fields for the Student model
    protected $fillable = ['name', 'age', 'address', 'contact_number'];

    // Define the relationship between Student and Course models
    public function courses()
    {
        //many-to-many relationship  with the Course model, using the 'student_courses'
        return $this->belongsToMany(Course::class, 'student_courses');
    }
}