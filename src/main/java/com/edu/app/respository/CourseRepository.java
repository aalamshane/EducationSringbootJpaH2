package com.edu.app.respository;

import com.edu.app.entity.Course;
import com.edu.app.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByStudentsContains(Student student);
    // Custom queries can be added here if needed
}

