# Online Parcel Booking API

A comprehensive REST API for online parcel booking and tracking, built with Spring Boot and Supabase (PostgreSQL).

## Features

- 📦 Create and manage parcel bookings
- 🔍 Track parcels with unique tracking numbers
- 📧 Query parcels by sender/recipient email
- 📊 Multiple parcel types (Document, Package, Fragile, Perishable, Electronics)
- 🚚 Multiple delivery options (Standard, Express, Same Day, Overnight)
- 💰 Automatic shipping cost calculation
- 📅 Estimated delivery date calculation
- ✅ Real-time status tracking

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Supabase account

## Setup Instructions

### Quick Start (Local Development - No Supabase Required)

The application is pre-configured to run locally with an **H2 in-memory database**. Just build and run:

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

**Access H2 Console** at `http://localhost:8080/h2-console`:
- JDBC URL: `jdbc:h2:mem:parceldb`
- Username: `sa`
- Password: (leave empty)

### Running with Supabase (Production)

#### 1. Create a Supabase Project

1. Go to [Supabase](https://supabase.com/) and create a free account
2. Create a new project
3. Wait for the project to be fully initialized

#### 2. Get Your Supabase Credentials

1. In your Supabase project dashboard, go to **Settings** → **Database**
2. Find your connection details:
   - **Host**: `db.YOUR_PROJECT_REF.supabase.co`
   - **Database name**: `postgres`
   - **Port**: `5432`
   - **User**: `postgres`
   - **Password**: Your database password (set during project creation)

#### 3. Configure Supabase Credentials

Update `src/main/resources/application-prod.properties` with your credentials (already configured in your project).

#### 4. Run with Production Profile

```bash
# Run with Supabase
mvn spring-boot:run -Dspring-boot.run.profiles=prod

# Or set the profile in application.properties:
# spring.profiles.active=prod
```

### Switching Between Profiles

Edit `src/main/resources/application.properties`:

```properties
# For local development with H2 (default):
spring.profiles.active=dev

# For production with Supabase:
spring.profiles.active=prod
```

## API Endpoints

### Welcome & Health Check

- **GET** `/` - Welcome message with API information
- **GET** `/health` - Health check endpoint

### Parcel Management

#### Create a New Parcel Booking
- **POST** `/api/parcels`
- **Request Body:**
```json
{
  "senderName": "John Doe",
  "senderEmail": "john@example.com",
  "senderPhone": "+1234567890",
  "senderAddress": "123 Main St, City, Country",
  "recipientName": "Jane Smith",
  "recipientEmail": "jane@example.com",
  "recipientPhone": "+0987654321",
  "recipientAddress": "456 Oak Ave, City, Country",
  "weight": 2.5,
  "length": 30.0,
  "width": 20.0,
  "height": 15.0,
  "description": "Books and documents",
  "parcelType": "PACKAGE",
  "deliveryType": "EXPRESS"
}
```

#### Get All Parcels
- **GET** `/api/parcels`

#### Get Parcel by ID
- **GET** `/api/parcels/{id}`

#### Track Parcel by Tracking Number
- **GET** `/api/parcels/track/{trackingNumber}`

#### Get Parcels by Sender Email
- **GET** `/api/parcels/sender/{email}`

#### Get Parcels by Recipient Email
- **GET** `/api/parcels/recipient/{email}`

#### Get Parcels by User Email (as sender or recipient)
- **GET** `/api/parcels/user/{email}`

#### Get Parcels by Status
- **GET** `/api/parcels/status/{status}`
- Valid statuses: `PENDING`, `CONFIRMED`, `PICKED_UP`, `IN_TRANSIT`, `OUT_FOR_DELIVERY`, `DELIVERED`, `CANCELLED`, `RETURNED`

#### Update Parcel
- **PUT** `/api/parcels/{id}`
- **Request Body:** Same as create parcel

#### Update Parcel Status
- **PATCH** `/api/parcels/{id}/status`
- **Request Body:**
```json
{
  "status": "IN_TRANSIT"
}
```

#### Delete Parcel
- **DELETE** `/api/parcels/{id}`

## Parcel Types

- `DOCUMENT` - Documents and papers
- `PACKAGE` - Standard packages
- `FRAGILE` - Fragile items requiring special handling
- `PERISHABLE` - Perishable goods
- `ELECTRONICS` - Electronic devices

## Delivery Types

- `STANDARD` - 5 business days (base rate)
- `EXPRESS` - 2 business days (1.5x base rate)
- `SAME_DAY` - Same day delivery (2.5x base rate)
- `OVERNIGHT` - Next day delivery (2x base rate)

## Shipping Cost Calculation

Shipping cost is calculated as:
```
Base Cost = $5.00
Weight Cost = Weight (kg) × $2.00
Total = (Base Cost + Weight Cost) × Delivery Type Multiplier
```

## Database Schema

The application uses Hibernate to automatically create the `parcels` table with the following structure:

- **id** - Auto-generated primary key
- **tracking_number** - Unique tracking identifier
- **sender_*** - Sender information (name, email, phone, address)
- **recipient_*** - Recipient information (name, email, phone, address)
- **weight, length, width, height** - Parcel dimensions
- **description** - Parcel description
- **parcel_type** - Type of parcel
- **delivery_type** - Delivery option
- **status** - Current parcel status
- **shipping_cost** - Calculated shipping cost
- **estimated_delivery_date** - Estimated delivery date
- **actual_delivery_date** - Actual delivery date (set when delivered)
- **created_at** - Timestamp of creation
- **updated_at** - Timestamp of last update

## Testing with cURL

### Create a Parcel
```bash
curl -X POST http://localhost:8080/api/parcels \
  -H "Content-Type: application/json" \
  -d '{
    "senderName": "John Doe",
    "senderEmail": "john@example.com",
    "senderPhone": "+1234567890",
    "senderAddress": "123 Main St, City, Country",
    "recipientName": "Jane Smith",
    "recipientEmail": "jane@example.com",
    "recipientPhone": "+0987654321",
    "recipientAddress": "456 Oak Ave, City, Country",
    "weight": 2.5,
    "length": 30.0,
    "width": 20.0,
    "height": 15.0,
    "description": "Books",
    "parcelType": "PACKAGE",
    "deliveryType": "EXPRESS"
  }'
```

### Track a Parcel
```bash
curl http://localhost:8080/api/parcels/track/TRK12345678
```

### Get User's Parcels
```bash
curl http://localhost:8080/api/parcels/user/john@example.com
```

## Error Handling

The API returns appropriate HTTP status codes:
- `200 OK` - Successful request
- `201 Created` - Resource created successfully
- `400 Bad Request` - Invalid request data
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

Error responses include a descriptive message:
```json
{
  "error": "Parcel not found with id: 123"
}
```

## CORS Configuration

The API is configured to allow cross-origin requests from all origins (`*`). For production, update the `@CrossOrigin` annotation in `ParcelController.java` to specify allowed origins.

## Technologies Used

- **Spring Boot 3.5.6** - Java framework
- **Spring Data JPA** - Database access
- **PostgreSQL** - Database (via Supabase)
- **Hibernate Validator** - Input validation
- **Maven** - Dependency management

## Project Structure

```
backend/
├── src/main/java/com/online_ordering/online_ordering/
│   ├── controller/
│   │   └── ParcelController.java
│   ├── dto/
│   │   └── ParcelRequest.java
│   ├── entity/
│   │   └── Parcel.java
│   ├── repository/
│   │   └── ParcelRepository.java
│   ├── service/
│   │   └── ParcelService.java
│   ├── OnlineOrderingApplication.java
│   └── restApi.java
└── src/main/resources/
    └── application.properties
```

## Future Enhancements

- User authentication and authorization
- Payment integration
- Email notifications
- SMS notifications
- Admin dashboard
- Delivery driver tracking
- Package insurance
- Bulk booking
- Address validation
- Rate comparison

## Support

For issues or questions, please create an issue in the project repository.

## License

This project is licensed under the MIT License.

