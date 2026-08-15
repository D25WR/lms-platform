package com.divya.lms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

public class CourseDtos {
    @Data
    public static class CourseRequest {
        @NotBlank private String title;
        private String description;
    }

    @Data
    @AllArgsConstructor
    public static class CourseResponse {
        private Long id;
        private String title;
        private String description;
        private String instructorName;
        private long enrolledCount;
    }

    @Data
    public static class LessonRequest {
        @NotBlank private String title;
        private String content;
        private Integer orderIndex;
    }

    @Data
    @AllArgsConstructor
    public static class ProgressResponse {
        private double percentComplete;
        private int totalLessons;
        private int completedLessons;
    }
}
