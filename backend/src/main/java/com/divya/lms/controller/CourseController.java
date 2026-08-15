package com.divya.lms.controller;

import com.divya.lms.dto.CourseDtos.*;
import com.divya.lms.entity.Lesson;
import com.divya.lms.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseResponse> create(Authentication auth, @Valid @RequestBody CourseRequest request) {
        return ResponseEntity.ok(courseService.createCourse(auth.getName(), request));
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> list() {
        return ResponseEntity.ok(courseService.listCourses());
    }

    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<Lesson> addLesson(@PathVariable Long courseId, @Valid @RequestBody LessonRequest request) {
        return ResponseEntity.ok(courseService.addLesson(courseId, request));
    }

    @GetMapping("/{courseId}/lessons")
    public ResponseEntity<List<Lesson>> lessons(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getLessons(courseId));
    }
}
