# Development

Guide for running the project locally.

## Requirements

- **JDK 25** (Gradle uses toolchains; it will tell you at build time if it is missing)
- **Docker** and **Docker Compose**
- No need to install Gradle: use the `./gradlew` wrapper

## 1. Start the database

The local infrastructure is defined in [`docs/infrastructure/docker-compose.yml`](infrastructure/docker-compose.yml) and starts a PostgreSQL 16 instance.

```bash
docker compose -f docs/infrastructure/docker-compose.yml up -d
```

| Parameter | Value       |
|-----------|-------------|
| Host      | `localhost` |
| Port      | `5434`      |
| Database  | `bankingdb` |
| User      | `postgres`  |
| Password  | `password`  |

Data is stored in the `postgres_data` volume, so it persists across restarts.

To stop it:

```bash
docker compose -f docs/infrastructure/docker-compose.yml down       # keeps the data
docker compose -f docs/infrastructure/docker-compose.yml down -v    # also removes the volume
```

## 2. Run the application

The database connection is configured in the `dev` profile (`../src/main/resources/application-dev.yml`), so it must be activated:

```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

The application is available at `http://localhost:8083`:

- Health check: http://localhost:8083/actuator/health
- Swagger UI: http://localhost:8083/swagger-ui.html

With the `dev` profile, Hibernate creates/updates the schema automatically (`ddl-auto: update`) and logs the SQL to the console.

## 3. Useful commands

```bash
./gradlew build            # compile and run the tests
./gradlew test             # tests only (also generates the JaCoCo report)
./gradlew check            # tests plus the 80% line coverage check
./gradlew spotlessCheck    # check code formatting
./gradlew spotlessApply    # apply formatting (google-java-format AOSP + license header)
```

> Spotless uses `ratchetFrom 'origin/main'`, so it only checks files changed relative to `origin/main`.

> The coverage report is at `build/reports/jacoco/test/html/index.html`. The `infrastructure/config`, `infrastructure/dtos` and `application/dtos` packages, `*Problem` classes and `BankingTransactionsApiApplication` are excluded from coverage. The README badge shows instruction coverage, while the check requires 80% line coverage, so the two numbers differ.
