# AgriFleet Core Service

The AgriFleet Core Service is the shared Spring Boot REST service for the platform's core operational data. It manages vehicles, depots, and farmer bookings used by the frontend and other AgriFleet backend services.

## Responsibilities

- Vehicle CRUD operations and availability updates
- Depot CRUD operations and active/inactive state
- Farmer booking creation and retrieval
- Booking status updates and deletion
- Persistence of shared operational data in SQLite
- Cross-origin access for the AgriFleet frontend

## Technology

- Java 17
- Spring Boot `4.1.1`
- Spring Web
- Spring Data JPA
- Hibernate Community SQLite dialect
- SQLite JDBC driver
- Maven

## Prerequisites

- Java 17 or newer
- Maven, or the included Maven Wrapper

## Running Locally

From this directory, start the service with the Maven Wrapper.

Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

Linux or macOS:

```bash
./mvnw spring-boot:run
```

The service starts on [http://localhost:8080](http://localhost:8080). The API base path is `/api/v1`.

To build and run the packaged application:

```bash
.\mvnw.cmd clean package
java -jar target/agrifleet-core-service-0.0.1-SNAPSHOT.jar
```

## Configuration

The default configuration is in `src/main/resources/application.properties`:

```properties
server.port=8080
spring.datasource.url=jdbc:sqlite:../AgriFleet.db
spring.jpa.hibernate.ddl-auto=update
```

The SQLite database is resolved relative to the service directory and is shared as `AgriFleet.db` with the other AgriFleet services that use this database. Hibernate updates the schema automatically when the service starts. SQL logging is enabled by default.

To use another database location or port, override the Spring properties through an external configuration file or command-line arguments, for example:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.arguments=--server.port=8090 --spring.datasource.url=jdbc:sqlite:C:/data/AgriFleet.db"
```

## API Endpoints

All endpoints return JSON. Successful create operations return `201 Created`; missing resources return `404 Not Found`; successful deletes return `204 No Content`.

### Vehicles

| Method   | Endpoint                | Description            |
| -------- | ----------------------- | ---------------------- |
| `GET`    | `/api/v1/vehicles`      | List all vehicles      |
| `GET`    | `/api/v1/vehicles/{id}` | Get one vehicle        |
| `POST`   | `/api/v1/vehicles`      | Create a vehicle       |
| `PUT`    | `/api/v1/vehicles/{id}` | Update vehicle details |
| `DELETE` | `/api/v1/vehicles/{id}` | Delete a vehicle       |

Example vehicle payload:

```json
{
  "vehicleType": "TRACTOR",
  "specs": "{\"capacity\": 10}",
  "pricing": "{\"hourlyRate\": 2500}",
  "rating": 4.8,
  "currentLat": 8.3114,
  "currentLng": 80.4037,
  "availabilityStatus": "AVAILABLE"
}
```

### Depots

| Method   | Endpoint              | Description          |
| -------- | --------------------- | -------------------- |
| `GET`    | `/api/v1/depots`      | List all depots      |
| `GET`    | `/api/v1/depots/{id}` | Get one depot        |
| `POST`   | `/api/v1/depots`      | Create a depot       |
| `PUT`    | `/api/v1/depots/{id}` | Update depot details |
| `DELETE` | `/api/v1/depots/{id}` | Delete a depot       |

Example depot payload:

```json
{
  "depotName": "Anuradhapura Central Depot",
  "address": "Anuradhapura, Sri Lanka",
  "latitude": 8.3114,
  "longitude": 80.4037,
  "isActive": true
}
```

### Bookings

| Method   | Endpoint                                       | Description           |
| -------- | ---------------------------------------------- | --------------------- |
| `GET`    | `/api/v1/bookings`                             | List all bookings     |
| `GET`    | `/api/v1/bookings/{id}`                        | Get one booking       |
| `POST`   | `/api/v1/bookings`                             | Create a booking      |
| `PUT`    | `/api/v1/bookings/{id}/status?status={status}` | Update booking status |
| `DELETE` | `/api/v1/bookings/{id}`                        | Delete a booking      |

Example booking payload:

```json
{
  "farmerId": 1,
  "farmLat": 8.335,
  "farmLng": 80.445,
  "acreage": 12.5,
  "cropType": "RICE",
  "requiredWindowStart": "2026-09-03T08:00:00",
  "requiredWindowEnd": "2026-09-03T16:00:00",
  "bookingStatus": "PENDING"
}
```

Example status update:

```text
PUT http://localhost:8080/api/v1/bookings/1/status?status=ALLOCATED
```

## Project Structure

```text
src/main/java/com/agrifleet_core_service/
  controller/   REST endpoints
  entity/       JPA entities for vehicles, depots, and bookings
  repository/   Spring Data repositories
src/main/resources/
  application.properties
```

## Testing

Run the test suite with:

```powershell
.\mvnw.cmd test
```

## Related Services

The frontend expects this service at `http://localhost:8080/api/v1` by default. Other AgriFleet services provide routing, allocation, network analysis, decision support, and tour optimization functionality on their own ports.
