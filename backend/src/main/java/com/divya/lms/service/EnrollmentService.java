package com.divya.lms.service;

import com.divya.lms.dto.CourseDtos.ProgressResponse;
import com.divya.lms.entity.*;
import com.divya.lms.exception.ResourceNotFoundException;
import com.divya.lms.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Handles enrollment and per-lesson progress tracking — the core "does this
 * student actually complete the course" real-world problem an LMS must solve.
 */
@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final LessonProgressRepository progressRepository;
    private final UserRepository userRepository;

    public void enroll(String studentEmail, Long courseId) {
        User student = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        if (enrollmentRepository.findByStudentIdAndCourseId(student.getId(), course.getId()).isPresent()) {
            throw new IllegalArgumentException("Already enrolled in this course");
        }

        enrollmentRepository.save(Enrollment.builder().student(student).course(course).build());
    }

    public void markLessonComplete(String studentEmail, Long lessonId) {
        User student = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

        LessonProgress progress = progressRepository.findByStudentIdAndLessonId(student.getId(), lesson.getId())
                .orElse(LessonProgress.builder().student(student).lesson(lesson).build());
        progress.setCompleted(true);
        progressRepository.save(progress);
    }

    public ProgressResponse getCourseProgress(String studentEmail, Long courseId) {
        User student = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        List<Lesson> lessons = lessonRepository.findByCourseIdOrderByOrderIndexAsc(courseId);
        List<LessonProgress> progress = progressRepository.findByStudentIdAndLessonCourseId(student.getId(), courseId);

        int total = lessons.size();
        long completed = progress.stream().filter(LessonProgress::isCompleted).count();
        double percent = total == 0 ? 0 : (completed * 100.0) / total;

        return new ProgressResponse(percent, total, (int) completed);
    }
}
