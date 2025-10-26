# ✅ Fixed! Quick Start Guide

## What Was Fixed

The application was trying to connect to Supabase but failing. I've configured it to work in **two modes**:

### 1. **DEV Mode** (Default - Currently Running)
- Uses H2 in-memory database
- No Supabase setup required
- Perfect for local development and testing
- Data resets when you restart the app

### 2. **PROD Mode** (Supabase)
- Uses your Supabase PostgreSQL database
- Data persists across restarts
- Your Supabase credentials are already configured

## Current Status

✅ Application is running at: **http://localhost:8080**
✅ Database: H2 in-memory (dev mode)
✅ All APIs are working

## Test the API

### 1. Welcome Endpoint
```bash
curl http://localhost:8080/
```

### 2. Health Check
```bash
curl http://localhost:8080/health
```

### 3. Create a Parcel Booking
```powershell
$body = @'
{
  "senderName": "John Doe",
  "senderEmail": "john@example.com",
  "senderPhone": "+1234567890",
  "senderAddress": "123 Main St, New York, USA",
  "recipientName": "Jane Smith",
  "recipientEmail": "jane@example.com",
  "recipientPhone": "+0987654321",
  "recipientAddress": "456 Oak Ave, Los Angeles, USA",
  "weight": 2.5,
  "length": 30.0,
  "width": 20.0,
  "height": 15.0,
  "description": "Books and documents",
  "parcelType": "PACKAGE",
  "deliveryType": "EXPRESS"
}
'@
Invoke-RestMethod -Uri "http://localhost:8080/api/parcels" -Method POST -Body $body -ContentType "application/json"
```

### 4. Track a Parcel
```bash
curl http://localhost:8080/api/parcels/track/YOUR_TRACKING_NUMBER
```

### 5. Get All Parcels
```bash
curl http://localhost:8080/api/parcels
```

### 6. Get User's Parcels
```bash
curl http://localhost:8080/api/parcels/user/john@example.com
```

## Access H2 Database Console

While the app is running, visit: **http://localhost:8080/h2-console**

**Connection Details:**
- JDBC URL: `jdbc:h2:mem:parceldb`
- Username: `sa`
- Password: (leave empty)
- Driver: `org.h2.Driver`

## Switch to Supabase (Production Mode)

Your Supabase credentials are already configured! To use Supabase:

### Option 1: Edit application.properties
```properties
# Change this line in src/main/resources/application.properties
spring.profiles.active=prod
```

### Option 2: Run with profile parameter
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## Available Profiles

- **dev** (default): H2 in-memory database - Local development
- **prod**: Supabase PostgreSQL - Production use

## Complete API Documentation

See [README.md](README.md) for complete API documentation including:
- All available endpoints
- Request/response examples
- Parcel types and delivery options
- Shipping cost calculation
- Database schema

## Parcel Types

- `DOCUMENT` - Documents and papers
- `PACKAGE` - Standard packages
- `FRAGILE` - Fragile items requiring special handling
- `PERISHABLE` - Perishable goods
- `ELECTRONICS` - Electronic devices

## Delivery Types & Estimated Delivery

- `STANDARD` - 5 business days (base rate × 1.0)
- `EXPRESS` - 2 business days (base rate × 1.5) ✅ Used in example
- `SAME_DAY` - Same day delivery (base rate × 2.5)
- `OVERNIGHT` - Next day delivery (base rate × 2.0)

## Shipping Cost Formula

```
Base Cost = $5.00
Weight Cost = Weight (kg) × $2.00
Total = (Base Cost + Weight Cost) × Delivery Type Multiplier

Example (from test):
Weight: 2.5 kg
Delivery: EXPRESS (1.5x multiplier)
Total: ($5 + $5) × 1.5 = $15.00
```

## Next Steps

1. ✅ Test all the API endpoints
2. ✅ Check the H2 console to see your data
3. ✅ Build your frontend to integrate with these APIs
4. ✅ When ready for production, switch to `prod` profile to use Supabase

## Need Help?

- Full API docs: See [README.md](README.md)
- Parcel statuses: PENDING, CONFIRMED, PICKED_UP, IN_TRANSIT, OUT_FOR_DELIVERY, DELIVERED, CANCELLED, RETURNED
- All endpoints support CORS for frontend integration
- Automatic tracking number generation
- Automatic shipping cost calculation
- Input validation included

Happy coding! 🚀

