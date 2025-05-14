## 🚀 Setup and Installation
### Prerequisites
- Java 21
- Maven
- Docker & Docker Compose

### 1. Cloning the Repository
```sh
git clone https://github.com/mateusz-plaska/dm-delivery-and-parcel-management.git
cd dm-delivery-and-parcel-management
```

## 🏃 Running the application on Docker

### 1. Run
With Docker installed run the following command from the project's root directory:
```sh
docker compose up -d
```
This will:
- Build the Spring Boot application.
- Run the application in a container.

### 2. Exit

To stop application run:
```sh
docker compose stop
```

You can start it again with:
```sh
docker compose start
```

Or remove application containers:
```sh
docker compose down
```

## 🏗️ Testing
Run unit and integration tests with:
```sh
mvn test
```

## 🔍 API Endpoints

### 🌍 Get deliver
Retrieve deliver status for a specific user based on their tracking number. 
```http
GET /api/track/{userId}/{trackingNumber}
```
Example response:
```json
{
    "userId": "405",
    "trackingNumber": "556677",
    "status": "W przygotowaniu",
    "location": "Wrocław, Polska",
    "estimatedDelivery": "2025-05-17T14:00:00Z"
}
```


### ➕ Create a new parcel
Send a new parcel to a specific locker.
```http
POST /api/packages/send
Content-Type: application/json

{ 
    "userId": "405", 
    "trackingNumber": "556677", 
    "lockerId": "locker81ab7",  
    "packageSize": "small"
 }
```
Example response:
```json
{
    "message": "Paczka wysłana pomyślnie",
    "trackingNumber": "556677"
}
```


## 👨‍💻 Stack
- Java
- Spring Boot
- Docker
- Maven
