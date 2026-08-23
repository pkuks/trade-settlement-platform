# Capital Markets Trade Settlement Platform

A domain-focused enterprise application for trade capture, settlement processing, and operational workflows in the capital markets domain.

The project is being developed using Java and Spring Boot, with a focus on enterprise application development, secure API design, transactional data processing, and financial-domain workflows.

> **Status:** Actively under development

---

## Overview

The Trade Settlement Platform is a reference implementation of a simplified capital markets trade settlement system.

The application is being developed incrementally, starting with the backend and progressively introducing trade processing, settlement workflows, event-driven processing, and an Angular frontend.

The project demonstrates practical implementation of:

- Enterprise REST API development
- Domain-oriented application structure
- Relational database design
- Database migration management
- Authentication and authorization
- JWT-based security
- Transaction management
- Concurrency and data consistency
- Event-driven architecture
- Settlement processing

---

## Architecture

The application is initially being developed as a modular monolith with clear domain boundaries.

```text
                         ┌──────────────────────┐
                         │      Angular UI      │
                         │       Planned        │
                         └──────────┬───────────┘
                                    │
                                  REST
                                    │
                                    ▼
                    ┌─────────────────────────────┐
                    │       Spring Boot API       │
                    │                             │
                    │  Authentication             │
                    │  User Management             │
                    │  Trade Management            │
                    │  Settlement Processing       │
                    └──────────────┬──────────────┘
                                   │
                    ┌──────────────┼──────────────┐
                    │              │              │
                    ▼              ▼              ▼
              ┌──────────┐   ┌──────────┐   ┌──────────┐
              │PostgreSQL│   │  Kafka   │   │  Redis   │
              │          │   │ Planned  │   │ Planned  │
              └──────────┘   └──────────┘   └──────────┘


```

## Technology Stack

### Implemented

- Java 21
- Spring Boot 3.5.16
- Spring Web
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- PostgreSQL
- Flyway
- Bean Validation
- Maven

### Planned

- Apache Kafka
- Testcontainers
- Docker
- Angular
- OpenAPI / Swagger
- Micrometer

## Current Features

- JWT Authentication & Authorization
- User & Role Management
- Trade Lifecycle Management
- Settlement Lifecycle Management
- Settlement Retry Processing
- Audit Trail & Event History
- Pagination & Search APIs
- Optimistic Locking Foundation
- PostgreSQL + Flyway Migrations
- Spring Boot 3.5 + Java 21