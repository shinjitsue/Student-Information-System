<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Student;

class StudentController extends Controller
{
    // This method retrieves all the students from the database and displays them in a view
    public function index()
    {
        $students = Student::all();
        return view('students.index', ['students' => $students]);
    }

    // This method retrieves a specific student and their associated courses from the database and displays them in a view
    public function showCourses($id)
    {
        $student = Student::with('courses')->findOrFail($id);
        return view('students.courses', ['student' => $student]);
    }
}