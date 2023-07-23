package com.edu.app.controller;

import com.edu.app.entity.Course;
import com.edu.app.entity.Student;
import com.edu.app.entity.StudentReq;
import com.edu.app.exception.CourseNotFoundException;
import com.edu.app.exception.StudentNotFoundException;
import com.edu.app.respository.StudentRepository;
import com.edu.app.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping
    public Student addStudent(@RequestBody StudentReq studentReq) {
        return studentService.addStudent(studentReq);
    }

    @PutMapping("/{studentId}/allocate-course/{courseId}")
    public ResponseEntity<Student> allocateCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        try {
            Student allocatedStudent = studentService.allocateCourse(studentId, courseId);
            return ResponseEntity.ok(allocatedStudent);
        } catch (StudentNotFoundException | CourseNotFoundException e) {
            // Handle not found exceptions and return appropriate error response
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Handle other exceptions and return error response with 500 status code
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping
    public List<Course> getAllCourses() {
        return studentService.getAllCourses();
    }
    @PutMapping("/{studentId}/courses")
    public ResponseEntity<Student> updateStudentCourses(
            @PathVariable Long studentId,
            @RequestBody List<Long> courseIds
    ) {
        try {
            // Update the student's courses using the studentId and courseIds
            Student updatedStudent = studentService.updateStudentCourses(studentId, courseIds);

            // Return the updated student object with HTTP status 200 (OK)
            return ResponseEntity.ok(updatedStudent);
        } catch (StudentNotFoundException | CourseNotFoundException e) {
            // Handle the case when the student or course is not found
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Handle other exceptions (e.g., database-related issues)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok("Student with ID " + studentId + " deleted successfully.");
    }
}

