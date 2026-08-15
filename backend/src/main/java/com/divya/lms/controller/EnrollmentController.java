package com.divya.lms.controller;

import com.divya.lms.dto.CourseDtos.ProgressResponse;
import com.divya.lms.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping("/{courseId}")
    public ResponseEntity<Void> enroll(Authentication auth, @PathVariable Long courseId) {
        enrollmentService.enroll(auth.getName(), courseId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/lessons/{lessonId}/complete")
    public ResponseEntity<Void> completeLesson(Authentication auth, @PathVariable Long lessonId) {
        enrollmentService.markLessonComplete(auth.getName(), lessonId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{courseId}/progress")
    public ResponseEntity<ProgressResponse> progress(Authentication auth, @PathVariable Long courseId) {
        return ResponseEntity.ok(enrollmentService.getCourseProgress(auth.getName(), courseId));
    }
}
