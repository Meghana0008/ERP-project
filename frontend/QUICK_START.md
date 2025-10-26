# Quick Start Guide

## Running the Application

### 1. Start Backend
```bash
cd backend
mvn spring-boot:run
```
Wait for: "Started OnlineOrderingApplication" in the console

### 2. Start Frontend

**Option 1: Simple Browser**
- Open `frontend/index.html` in your browser

**Option 2: Local Server (Recommended)**
```bash
cd frontend
python -m http.server 8000
```
Then open: `http://localhost:8000`

### 3. Use the Application

1. **Book a Parcel**: Fill the form and submit
2. **Track**: Enter tracking number from the booking
3. **My Parcels**: Enter your email to see all parcels

## API Endpoints Used

- POST `/api/parcels` - Create parcel
- GET `/api/parcels/track/{trackingNumber}` - Track parcel

**Note:** Backend runs on port 8081 by default
- GET `/api/parcels/user/{email}` - Get user parcels

## Features

- ✅ Responsive navbar with smooth scrolling
- ✅ Beautiful hero section
- ✅ Complete booking form
- ✅ Real-time tracking
- ✅ Parcel management
- ✅ Toast notifications
- ✅ Mobile-friendly design
