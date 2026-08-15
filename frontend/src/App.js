import React, { useEffect, useState } from "react";
import api from "./services/api";

export default function App() {
  const [courses, setCourses] = useState([]);
  const [token, setToken] = useState(localStorage.getItem("token"));

  useEffect(() => { api.get("/courses").then((res) => setCourses(res.data)); }, []);

  const enroll = async (id) => {
    if (!token) return alert("Please log in first");
    await api.post(`/enrollments/${id}`);
    alert("Enrolled!");
  };

  return (
    <div className="page-container">
      <h1>📚 LearnHub — Course Catalog</h1>
      {courses.map((c) => (
        <div key={c.id} className="course-card">
          <h3>{c.title}</h3>
          <p>{c.description}</p>
          <small>By {c.instructorName} · {c.enrolledCount} enrolled</small><br />
          <button onClick={() => enroll(c.id)}>Enroll</button>
        </div>
      ))}
    </div>
  );
}
