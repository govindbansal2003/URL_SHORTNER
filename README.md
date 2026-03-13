# 🔗 URL Shortener

A full-stack URL shortener built with **Spring Boot**, **MySQL**, and **Docker Compose**. Shorten long URLs, track click counts, and manage everything through a sleek dark-themed web UI or REST API.

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-brightgreen?logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker&logoColor=white)

---

## ✨ Features

- **Shorten URLs** — Generate unique 8-character short codes
- **302 Redirect** — Seamless redirection to original URLs
- **Click Tracking** — Automatic click count for every short URL
- **Stats Dashboard** — View all URLs with creation time and click stats
- **Copy to Clipboard** — One-click copy for shortened URLs
- **Dark-Themed UI** — Modern glassmorphism design with animations
- **Fully Dockerized** — One command to spin up the entire stack

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Spring Boot 3.5, Spring Data JPA, Hibernate |
| Database | MySQL 8.0 |
| Frontend | HTML5, CSS3, Vanilla JavaScript |
| Containerization | Docker, Docker Compose |
| Build Tool | Maven |
| Language | Java 21 |

---

## 🚀 Quick Start

### Prerequisites
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) installed and running

### Run the Application

```bash
# Clone the repository
git clone https://github.com/govindbansal2003/URL_SHORTNER.git
cd URL_SHORTNER

# Start everything with Docker Compose
docker-compose up --build
```

Open **http://localhost:8080** in your browser 🎉

### Stop the Application

```bash
docker-compose down
```

To also remove the database volume:
```bash
docker-compose down -v
```

---

## 📡 API Endpoints

| Method | Endpoint | Description | Body |
|--------|----------|-------------|------|
| `POST` | `/api/shorten` | Create a short URL | `{"url": "https://example.com"}` |
| `GET` | `/api/urls` | List all shortened URLs with stats | — |
| `GET` | `/api/urls/{code}/stats` | Get stats for a specific URL | — |
| `GET` | `/{code}` | Redirect to original URL (302) | — |

### Example: Shorten a URL

```bash
curl -X POST http://localhost:8080/api/shorten \
  -H "Content-Type: application/json" \
  -d '{"url": "https://www.google.com"}'
```

**Response:**
```json
{
  "shortCode": "AbCd1234",
  "shortUrl": "http://localhost:8080/AbCd1234",
  "originalUrl": "https://www.google.com"
}
```

### Example: List All URLs

```bash
curl http://localhost:8080/api/urls
```

---

## 📁 Project Structure

```
URL_SHORTNER/
├── docker-compose.yml
├── Dockerfile
├── pom.xml
└── src/main/
    ├── java/com/urlshortner/
    │   ├── UrlShortnerApplication.java
    │   ├── controller/
    │   │   └── UrlShortenerController.java
    │   ├── dto/
    │   │   ├── ShortenRequest.java
    │   │   ├── ShortenResponse.java
    │   │   └── UrlStatsResponse.java
    │   ├── entity/
    │   │   └── UrlMapping.java
    │   ├── exception/
    │   │   └── GlobalExceptionHandler.java
    │   ├── repository/
    │   │   └── UrlMappingRepository.java
    │   └── service/
    │       └── UrlShortenerService.java
    └── resources/
        ├── application.properties
        └── static/
            └── index.html
```

---

## ⚙️ Configuration

Environment variables (configurable in `docker-compose.yml`):

| Variable | Default | Description |
|----------|---------|-------------|
| `SPRING_DATASOURCE_URL` | `jdbc:mysql://db:3306/urlshortener` | MySQL connection URL |
| `SPRING_DATASOURCE_USERNAME` | `root` | Database username |
| `SPRING_DATASOURCE_PASSWORD` | `root` | Database password |

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📜 License

This project is open source and available under the [MIT License](LICENSE).

---

<p align="center">
  Made with ❤️ by <a href="https://github.com/govindbansal2003">Govind Bansal</a>
</p>
