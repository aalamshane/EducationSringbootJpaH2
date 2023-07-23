package com.edu.app.service;

import com.edu.app.entity.Course;
import com.edu.app.entity.Student;
import com.edu.app.entity.StudentReq;
import com.edu.app.exception.StudentNotFoundException;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    public Student addStudent(StudentReq studentReq);

    public List<Student> getAllStudents();
    public Optional<Student> getStudentById(Long studentId);

    public void deleteStudentById(Long studentId);

    public Student updateStudent(Student student);

    Student allocateCourse(Long studentId, Long courseId);

    Student updateStudentCourses(Long studentId, List<Long> courseIds);

    void deleteStudent(Long studentId);

    List<Course> getAllCourses();
}
