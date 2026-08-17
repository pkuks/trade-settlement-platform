# Trade Settlement & Custody Platform

A domain-focused enterprise application for trade capture, settlement processing, and operational workflows in the capital markets domain.

The project is being developed using Java and Spring Boot, with a focus on enterprise application development, secure API design, transactional data processing, and financial-domain workflows.

> **Status:** Actively under development

---

## Overview

The Trade Settlement & Custody Platform is a reference implementation of a simplified capital markets trade settlement system.

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

### User Management

- User creation REST API
- User and Role domain model
- User-role relationship
- User status management
- Request validation
- Duplicate username handling
- Invalid role handling
- Global exception handling
- BCrypt password hashing

### Authentication

- Spring Security authentication
- AuthenticationManager
- UserDetailsService
- Password authentication
- JWT generation
- JWT user identity claims
- JWT role claims
- JWT user ID claim

## Planned Features

### Security

- JWT authentication filter
- Stateless authentication
- Role-based authorization
- User status validation

### Trade Management

- Trade capture
- Trade validation
- Trade search and filtering
- Trade lifecycle management

### Settlement

- Settlement instruction creation
- Settlement processing
- Settlement status management
- Optimistic locking
- Failure handling
- Retry processing
- Idempotent settlement processing

### Event Processing

- Kafka-based trade events
- Asynchronous settlement processing
- At-least-once message processing
- Idempotent consumers
- Retry and dead-letter handling

### Operations

- Settlement monitoring
- Audit trail
- Reconciliation
- Operational reporting

### Frontend

- Angular application
- Authentication
- Trade capture
- Trade search
- Settlement dashboard
- JWT interceptor
- Role-based UI

### Testing & Production Readiness

- Unit testing
- Integration testing
- Testcontainers
- Dockerization
- OpenAPI documentation
- Health checks
- Metrics and observability
- CI/CD
