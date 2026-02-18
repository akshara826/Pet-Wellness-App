// ✅ API CONNECTOR (Spring Boot Backend)

import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:8080", 
  // Spring Boot default port
});

export default API;
