<?php

namespace App\Http\Controllers;

use App\Models\Student;

class StudentController extends Controller
{
    //  method to the StudentController class
    public function index()
    {
        $students = Student::with('courses')->get();
        return view('students.index', ['students' => $students]);
    }

    //  method to the StudentController class
    public function showCourses($id)
    {

        $student = Student::with('courses')->findOrFail($id);
        return view('students.courses', ['student' => $student]);
    }
}