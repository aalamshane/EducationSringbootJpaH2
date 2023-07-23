package com.edu.app.service;


import com.edu.app.entity.Course;
import com.edu.app.entity.CourseReq;
import com.edu.app.exception.CourseNotFoundException;
import com.edu.app.respository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired(required=true)
    private CourseRepository courseRepository;

    public Course addCourse(CourseReq courseReq) {
        Course course = new Course();
        course.setCourseName(courseReq.getCourseName());
        course.setDescription(courseReq.getDescription());
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course updateCourse(Course course) {
        // Check if the course with courseId exists in the database
        Long courseId = course.getCourseId();
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            // If the course exists, update its details
            Course existingCourse = courseOptional.get();
            existingCourse.setCourseName(course.getCourseName());
            existingCourse.setDescription(course.getDescription());

            // Save the updated course to the database
            return courseRepository.save(existingCourse);
        } else {
            // If the course does not exist, throw an exception or handle the case accordingly
            throw new CourseNotFoundException("Course with courseId " + courseId + " not found.");
        }
    }

    public void deleteCourse(Long courseId) {
        // Check if the course with courseId exists in the database
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            // If the course exists, delete it
            courseRepository.delete(courseOptional.get());
        } else {
            // If the course does not exist, throw an exception or handle the case accordingly
            throw new CourseNotFoundException("Course with courseId " + courseId + " not found.");
        }
    }
}
