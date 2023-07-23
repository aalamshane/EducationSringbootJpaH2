package com.edu.app.service;


import com.edu.app.entity.Course;
import com.edu.app.entity.CourseReq;

import java.util.List;

public interface CourseService {
    public Course addCourse(CourseReq course);

    public List<Course> getAllCourses();

    public Course updateCourse(Course course);

    public void deleteCourse(Long courseId);
}
