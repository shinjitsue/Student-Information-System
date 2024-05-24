<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h1>Courses for {{ $student->name }}</h1> 

    <ul>
        @foreach ($student->courses as $course)
            <li>{{ $course->title }}</li>
        @endforeach
    </ul>
</body>
</html>