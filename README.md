# 📚 LearnHub — Online Learning Management System (LMS)

A full-stack e-learning platform where instructors publish courses with ordered lessons, and students enroll and track their completion progress — solving the real-world problem of **structured, trackable online course delivery** for small ed-tech teams or trainers.

Built with **Java Spring Boot**, **Spring Security + JWT**, **MySQL**, and **React**.

## 🧩 The Problem

Independent instructors and small training businesses need a simple way to publish course content and know whether students are actually progressing — without the cost/complexity of an enterprise LMS like Moodle.

## ✨ Features

- 🔐 Role-based auth: **STUDENT** and **INSTRUCTOR**
- 🎓 Instructors create courses and ordered lessons
- 🧑‍🎓 Students browse a public catalog and enroll (duplicate enrollment prevented)
- ✅ Per-lesson completion tracking with an aggregated **course progress percentage**
- 📊 Enrollment counts shown per course

## 🏗️ Architecture

```
React SPA ──JWT──▶ Spring Boot REST API ──JPA──▶ MySQL
                       │
       CourseService (catalog)  EnrollmentService (progress tracking)
```

## 🛠️ Tech Stack

Java 17 · Spring Boot 3 · Spring Security · Spring Data JPA · MySQL 8 · JWT (jjwt) · React 18 · Axios · Maven · npm

## 🚀 Getting Started

### Backend
```bash
cd backend
cp src/main/resources/application-example.properties src/main/resources/application.properties
mvn spring-boot:run
```
Runs on `http://localhost:8082`.

### Frontend
```bash
cd frontend
npm install
npm start
```

## 📡 Key API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register as STUDENT or INSTRUCTOR |
| POST | `/api/auth/login` | Log in |
| GET | `/api/courses` | Public course catalog |
| POST | `/api/courses` | Instructor creates a course |
| POST | `/api/courses/{id}/lessons` | Instructor adds a lesson |
| POST | `/api/enrollments/{courseId}` | Student enrolls |
| POST | `/api/enrollments/lessons/{lessonId}/complete` | Mark a lesson complete |
| GET | `/api/enrollments/{courseId}/progress` | Get completion % for a course |

## 🗺️ Roadmap

- [ ] Quizzes and auto-graded assessments
- [ ] Video lesson hosting
- [ ] Certificates on 100% completion
- [ ] Instructor analytics dashboard

## 👩‍💻 Author

**Divya Waghmare** — [LinkedIn](https://linkedin.com/in/divya-waghmare) · [GitHub](https://github.com/D25WR)

## 📄 License

MIT — see [LICENSE](LICENSE)
