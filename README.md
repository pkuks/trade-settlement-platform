# Capital Markets Trade Settlement Platform

A personal learning project exploring **trade processing and settlement workflows in capital markets**, with a focus on enterprise application architecture, reliable transaction processing, security, auditability and event-driven processing.

The platform models key stages of the trade lifecycle, from trade creation and validation through settlement instruction creation, settlement processing, status management, failure handling and retry.

The project is **actively under development**, with additional capabilities being explored to deepen understanding of capital markets and post-trade processing.

---

## Key Capabilities

### Trade Management

* Trade creation and validation
* Trade retrieval and search
* Trade reference uniqueness validation
* Currency and date validations
* Trade lifecycle and status management

### Settlement Processing

* Settlement instruction creation
* Settlement processing workflow
* Settlement and failure handling
* Settlement retry processing
* Settlement status management

### Security

* JWT-based authentication
* Role-based authorization
* Protected REST APIs
* Custom Spring Security authentication flow

### Event-Driven Processing

* Kafka-based event publishing and consumption
* Asynchronous processing of business events
* Idempotent processing considerations
* Event-driven settlement workflow

### Audit & Reliability

* Business event audit trail
* Centralized exception handling
* Business rule validation
* Database migrations using Flyway
* Transactional processing using Spring transactions

---

## Technology Stack

| Area               | Technology                 |
| ------------------ | -------------------------- |
| Language           | Java 21                    |
| Framework          | Spring Boot                |
| Security           | Spring Security, JWT       |
| Persistence        | Spring Data JPA, Hibernate |
| Database           | PostgreSQL                 |
| Messaging          | Apache Kafka               |
| Database Migration | Flyway                     |
| Containerization   | Docker                     |
| Build              | Maven                      |
| API                | REST                       |

---

## Architecture

The current implementation uses a modular Spring Boot architecture with REST APIs, PostgreSQL persistence and Kafka-based event-driven processing.

```text

                    ┌─────────────────────┐
                    │       Client        │
                    │      REST API       │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │   Spring Boot App   │
                    │                     │
                    │  Trade APIs         │
                    │  Settlement APIs    │
                    │  Security           │
                    │  Business Services  │
                    └───────┬───────┬─────┘
                            │       │
                            │       ▼
                            │  ┌─────────────┐
                            │  │    Kafka    │
                            │  │    Events   │
                            │  └──────┬──────┘
                            │         │
                            ▼         ▼
                    ┌─────────────────────┐
                    │     PostgreSQL      │
                    │                     │
                    │ Trade               │
                    │ Settlement          │
                    │ Audit               │
                    └─────────────────────┘

```

The architecture is being evolved incrementally to explore patterns applicable to distributed and event-driven financial systems.

---
## Trade & Settlement Workflow

The current workflow broadly follows:

```text
Trade Creation
      │
      ▼
Trade Validation
      │
      ▼
Trade Ready for Settlement
      │
      ▼
Settlement Instruction Creation
      │
      ▼
Settlement Processing
      │
      ├──────────────► Failed
      │                  │
      │                  ▼
      │                Retry
      │                  │
      │                  └──────► Settlement Processing
      │
      ▼
   Settled
```

Each significant business transition can be captured through the audit trail.

---

## API

The application exposes REST APIs for authentication, trade management and settlement processing.

Examples include:

```text
POST   /api/auth/register
POST   /api/auth/login
GET    /api/me

POST   /api/trades
GET    /api/trades
GET    /api/trades/{id}
PATCH   /api/trades/{id}/validate
POST   /api/trades/{id}/ready-for-settlement

POST   /api/trades/{id}/settlements
POST   /api/settlements/{id}/process
POST   /api/settlements/{id}/settle
POST   /api/settlements/{id}/fail
POST   /api/settlements/{id}/retry
```

Authentication-protected APIs require a valid JWT token.

---

## Project Status

**Actively under development**

### Implemented

* Trade lifecycle management
* Trade validation
* Settlement instruction creation
* Settlement processing
* Settlement, failure and retry workflows
* JWT authentication and role-based authorization
* Kafka event-driven processing
* Audit trail
* PostgreSQL persistence
* Flyway database migrations
* REST APIs
* Centralized exception handling

### Currently Exploring

* Advanced Kafka processing patterns
* Reliable event delivery
* Idempotent message processing
* Transactional Outbox Pattern
* Distributed transaction considerations
* Reconciliation workflows
* Additional post-trade processing capabilities

---

## Running Locally

### Prerequisites

* Java 21
* Maven
* PostgreSQL
* Docker Desktop

Kafka can be started using the project's Docker configuration.

### Build

```bash
mvn clean install
```

### Run

```bash
mvn spring-boot:run
```

Additional setup and configuration details are available in the project documentation.

---

## Learning Objectives

This project is intended to provide hands-on exploration of:

* Capital markets trade and settlement processes
* Post-trade workflows
* Enterprise Java and Spring Boot development
* Transaction management and data consistency
* Event-driven architecture
* Kafka messaging
* Authentication and authorization
* Database design and persistence
* Reliability and failure handling
* Distributed-system design considerations

---

## Future Enhancements

Potential areas for further development include:

* Trade matching
* Settlement netting
* Reconciliation
* Custody position management
* Corporate actions
* SWIFT message simulation
* Dead-letter queue and failure recovery
* Observability and monitoring
* Performance and load testing
* Kubernetes deployment
* CI/CD pipeline

---

## About

This is a personal learning and portfolio project focused on **capital markets, trade settlement, event-driven architecture and enterprise application development**.

The platform is continuously enhanced as additional domain concepts and technical patterns are explored.
