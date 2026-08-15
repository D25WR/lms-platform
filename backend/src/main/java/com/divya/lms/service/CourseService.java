package com.divya.lms.service;

import com.divya.lms.dto.CourseDtos.*;
import com.divya.lms.entity.Course;
import com.divya.lms.entity.Lesson;
import com.divya.lms.entity.User;
import com.divya.lms.exception.ResourceNotFoundException;
import com.divya.lms.repository.CourseRepository;
import com.divya.lms.repository.EnrollmentRepository;
import com.divya.lms.repository.LessonRepository;
import com.divya.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;

    public CourseResponse createCourse(String instructorEmail, CourseRequest request) {
        User instructor = userRepository.findByEmail(instructorEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));
        Course course = Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .instructor(instructor)
                .build();
        Course saved = courseRepository.save(course);
        return toResponse(saved);
    }

    public List<CourseResponse> listCourses() {
        return courseRepository.findAll().stream().map(this::toResponse).toList();
    }

    public Lesson addLesson(Long courseId, LessonRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        Lesson lesson = Lesson.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .orderIndex(request.getOrderIndex() != null ? request.getOrderIndex() : 0)
                .course(course)
                .build();
        return lessonRepository.save(lesson);
    }

    public List<Lesson> getLessons(Long courseId) {
        return lessonRepository.findByCourseIdOrderByOrderIndexAsc(courseId);
    }

    private CourseResponse toResponse(Course c) {
        long enrolled = enrollmentRepository.countByCourseId(c.getId());
        return new CourseResponse(c.getId(), c.getTitle(), c.getDescription(), c.getInstructor().getFullName(), enrolled);
    }
}
