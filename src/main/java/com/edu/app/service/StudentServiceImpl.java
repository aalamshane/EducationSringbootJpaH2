package com.edu.app.service;

import com.edu.app.entity.Course;
import com.edu.app.entity.Student;
import com.edu.app.entity.StudentReq;
import com.edu.app.exception.CourseNotFoundException;
import com.edu.app.exception.StudentNotFoundException;
import com.edu.app.respository.CourseRepository;
import com.edu.app.respository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    RestTemplate restTemplate;
    public Student addStudent(StudentReq studentReq) {
        Student student = new Student();
        student.setName(studentReq.getName());
        student.setArabicName(studentReq.getArabicName());
        student.setPhone(studentReq.getPhone());
        student.setEmail(studentReq.getEmail());
        student.setAddress(studentReq.getAddress());
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long studentId) {
        return studentRepository.findById(studentId);
    }

    public void deleteStudentById(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    public Student updateStudent(Student student) {
        // Check if the student with studentId exists in the database
        Long studentId = student.getId();
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            // If the student exists, update its details
            Student existingStudent = studentOptional.get();
            existingStudent.setName(student.getName());
            existingStudent.setEmail(student.getEmail());
            existingStudent.setPhone(student.getPhone());
            existingStudent.setAddress(student.getAddress());

            // Save the updated student to the database
            return studentRepository.save(existingStudent);
        } else {
            // If the student does not exist, throw an exception or handle the case accordingly
            throw new StudentNotFoundException("Student with studentId " + studentId + " not found.");
        }
    }
    @Override
    public Student allocateCourse(Long studentId, Long courseId) {
        // Find the student by studentId
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student with ID " + studentId + " not found."));

        // Find the course by courseId
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course with ID " + courseId + " not found."));

        // Associate the course with the student
        student.getCourses().add(course);

        // Save the updated student to the database
        return studentRepository.save(student);
    }
    @Override
    public Student updateStudentCourses(Long studentId, List<Long> courseIds) {
        // Get the student from the database using the studentId
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student with id " + studentId + " not found"));

        // Fetch the courses from the database using the courseIds
        List<Course> courses = courseRepository.findAllById(courseIds);

        // Set the updated list of courses for the student
        student.setCourses(new HashSet<Course>(courses));

        // Save the updated student object back to the database
        Student updatedStudent = studentRepository.save(student);

        return updatedStudent;
    }
    public List<Student> getAllStudentsWithCourses() {
        List<Student> students = studentRepository.findAll(); // Fetch all students from the database

        for (Student student : students) {
            List<Course> selectedCourses = courseRepository.findAllByStudentsContains(student);
            student.setCourses((Set<Course>) selectedCourses);
        }

        return students;
    }
    @Override
    public void deleteStudent(Long studentId) {
        // Check if the student with the given ID exists in the database
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            // If the student exists, delete it
            studentRepository.delete(studentOptional.get());
        } else {
            // If the student does not exist, throw an exception or handle the case accordingly
            throw new StudentNotFoundException("Student with ID " + studentId + " not found.");
        }
    }

    @Override
    public List<Course> getAllCourses() {
        ResponseEntity<List<Course>> response = restTemplate.exchange(
                "http://localhost:8080/courses",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Course>>() {}
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return Collections.emptyList();
        }
    }
}
