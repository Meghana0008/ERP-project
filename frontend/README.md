# Parcel Express Frontend

A modern, responsive frontend application for the Parcel Express booking and tracking system.

## Features

- **Responsive Design**: Works perfectly on desktop, tablet, and mobile devices
- **Book Parcels**: Complete booking form for creating new parcel shipments
- **Track Parcels**: Track your parcels with tracking numbers
- **My Parcels**: View all your parcels by email
- **Modern UI**: Beautiful, professional design with smooth animations

## Structure

```
frontend/
├── index.html          # Main HTML file
├── css/
│   └── style.css       # All styles
├── js/
│   └── app.js          # Application logic and API calls
└── README.md           # This file
```

## Setup & Usage

### 1. Start the Backend

First, make sure the backend is running:

```bash
cd backend
mvn spring-boot:run
```

The backend will be available at `http://localhost:8081`

### 2. Open the Frontend

Simply open `index.html` in your web browser, or use a local server:

**Option A: Direct browser**
- Just double-click `index.html` or open it in your browser

**Option B: Using Python (Recommended for development)**
```bash
# Python 3
python -m http.server 3000

# Python 2
python -m SimpleHTTPServer 3000
```
Then navigate to `http://localhost:3000`

**Option C: Using Node.js**
```bash
npx serve
```

### 3. Using the Application

#### Book a Parcel
1. Click "Book Parcel" in the navigation or scroll to the form
2. Fill in the sender and recipient information
3. Enter parcel details (weight, dimensions, type)
4. Select delivery type
5. Submit the form

#### Track a Parcel
1. Click "Track" in the navigation
2. Enter your tracking number
3. Click "Track" button

#### View My Parcels
1. Click "My Parcels" in the navigation
2. Enter your email address
3. Click "Load Parcels" to see all your parcels

## API Configuration

The frontend is configured to connect to the backend API at:
- Default: `http://localhost:8081/api/parcels`

To change this, edit `js/app.js`:
```javascript
const API_BASE_URL = 'http://your-backend-url:8081/api/parcels';
```

## Features Overview

### Booking Form
- Sender information (name, email, phone, address)
- Recipient information (name, email, phone, address)
- Parcel details (weight, dimensions, type)
- Delivery type selection
- Optional description

### Tracking
- Real-time parcel tracking
- Status updates
- Detailed parcel information

### My Parcels
- View all parcels by email
- Filter by sender or recipient email
- Detailed parcel cards with status badges

## Technologies Used

- **HTML5**: Semantic markup
- **CSS3**: Modern styling with CSS Grid and Flexbox
- **JavaScript (ES6+)**: Vanilla JS for API calls and interactions
- **Font Awesome**: Icons
- **Responsive Design**: Mobile-first approach

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## Notes

- Make sure CORS is enabled on the backend (already configured with `@CrossOrigin(origins = "*")`)
- The backend must be running before using the frontend
- All API calls use the REST API endpoints defined in the backend

## Troubleshooting

### Frontend can't connect to backend
1. Ensure backend is running on `http://localhost:8081`
2. Check browser console for errors
3. Verify CORS is enabled on backend

### Parcels not showing
1. Check if email format is correct
2. Ensure you're entering the same email used during booking
3. Verify backend is running and accessible
