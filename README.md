# UPM Final Year Project (RuoYi-Based Visa Management System)

This repository contains my final year project built on the **RuoYi** framework. The system delivers a web-based management platform with a Vue 3 front end and a Spring Boot back end, including visa-related modules such as appointment, activity, expiry, and submission state management.

## Tech Stack

**Frontend (Vue 3 + Vite)**
- Vue 3, Vue Router, Vuex, Pinia
- Element Plus UI
- Axios
- Vite build tooling

**Backend (Spring Boot)**
- Spring Boot 2.5.15
- Spring Security + JWT
- MyBatis
- Quartz (scheduling)

**Database & Cache**
- MySQL
- Redis

## Project Structure

- `ruoyi-admin`: Spring Boot application entry point and REST API host.
- `ruoyi-framework`: Common framework configuration.
- `ruoyi-system`: Core system modules (users, roles, menus, etc.).
- `ruoyi-quartz`: Scheduled task support.
- `ruoyi-generator`: Code generator module.
- `ruoyi-common`: Shared utilities and common domain classes.
- `ruoyi-appointment`: Visa management domain (appointments, activities, expiry, submission state).
- `rouyi-ui-Vue3`: Vue 3 front-end application.
- `sql`: Database initialization and sample data scripts (e.g., `visa-management.sql`, `ry_20240629.sql`, `quartz.sql`).
- `img`: UI screenshots used in this README.

## Key Features

- Visa appointment management.
- Visa activity tracking.
- Visa expiry monitoring.
- Submission state management.
- RuoYi platform capabilities such as code generation and scheduled tasks.

## Configuration Highlights

- Backend server runs on port **8080** by default.
- Redis is enabled for caching and session/token support.
- MySQL is configured via the Druid datasource profile.

Configuration files live under `ruoyi-admin/src/main/resources`:
- `application.yml`
- `application-druid.yml`

## Local Development

### Prerequisites

- JDK 8
- Maven 3.x
- Node.js 16+ (recommended for Vue 3 + Vite)
- MySQL + Redis

### Backend

```bash
mvn -pl ruoyi-admin -am spring-boot:run
```

### Frontend

```bash
cd rouyi-ui-Vue3
npm install
npm run dev
```

### Database

Import the SQL scripts in `sql/` to initialize schema and seed data as needed.

## Deployment

The system is deployed on a Google Cloud virtual machine.

## Project Interface

![Image text](https://github.com/COLrge/My_upm_fyp/blob/master/img/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202025-01-26%20211705.png)
![Image text](https://github.com/COLrge/My_upm_fyp/blob/master/img/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202025-01-26%20212139.png)
![Image text](https://github.com/COLrge/My_upm_fyp/blob/master/img/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202025-01-26%20212252.png)
![Image text](https://github.com/COLrge/My_upm_fyp/blob/master/img/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202025-01-26%20224433.png)
![Image text](https://github.com/COLrge/My_upm_fyp/blob/master/img/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202025-01-26%20224456.png)
![Image text](https://github.com/COLrge/My_upm_fyp/blob/master/img/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202025-01-26%20224527.png)
![Image text](https://github.com/COLrge/My_upm_fyp/blob/master/img/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202025-01-26%20224610.png)
![Image text](https://github.com/COLrge/My_upm_fyp/blob/master/img/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202025-01-26%20224634.png)
![Image text](https://github.com/COLrge/My_upm_fyp/blob/master/img/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202025-01-26%20224732.png)
![Image text](https://github.com/COLrge/My_upm_fyp/blob/master/img/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202025-01-26%20224850.png)
![Image text](https://github.com/COLrge/My_upm_fyp/blob/master/img/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202025-01-26%20224910.png)
![Image text](https://github.com/COLrge/My_upm_fyp/blob/master/img/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202025-01-26%20212342.png)
