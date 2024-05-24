<?php

namespace App\Models;

class Course extends BaseModel 
{
    // Specify the fillable fields for the Course model
    protected $fillable = ['title', 'description'];

    // Define the relationship between Course and Student models
    public function students()
    {
        //many-to-many relationship  with the Student model, using the 'student_courses'
        return $this->belongsToMany(Student::class, 'student_courses'); 
    }
}