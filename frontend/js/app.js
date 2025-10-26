// Backend API URL
const API_BASE_URL = 'http://localhost:8081/api/parcels';

// Initialize app
document.addEventListener('DOMContentLoaded', () => {
    initNavbar();
    initForm();
});

// Navbar Functionality
function initNavbar() {
    const hamburger = document.getElementById('hamburger');
    const navMenu = document.getElementById('navMenu');
    const navLinks = document.querySelectorAll('.nav-link');

    hamburger.addEventListener('click', () => {
        navMenu.classList.toggle('active');
    });

    navLinks.forEach(link => {
        link.addEventListener('click', () => {
            navMenu.classList.remove('active');
            setActiveNavLink(link);
        });
    });
}

function setActiveNavLink(activeLink) {
    document.querySelectorAll('.nav-link').forEach(link => {
        link.classList.remove('active');
    });
    activeLink.classList.add('active');
}

function scrollToSection(sectionId) {
    const section = document.getElementById(sectionId);
    section.scrollIntoView({ behavior: 'smooth' });
    
    // Update active nav link
    const navLinks = document.querySelectorAll('.nav-link');
    navLinks.forEach(link => {
        if (link.getAttribute('href') === `#${sectionId}`) {
            setActiveNavLink(link);
        }
    });
}

// Form Submission
function initForm() {
    const form = document.getElementById('parcelForm');
    form.addEventListener('submit', handleFormSubmit);
}

async function handleFormSubmit(e) {
    e.preventDefault();
    
    const formData = new FormData(e.target);
    const data = {
        senderName: formData.get('senderName'),
        senderEmail: formData.get('senderEmail'),
        senderPhone: formData.get('senderPhone'),
        senderAddress: formData.get('senderAddress'),
        recipientName: formData.get('recipientName'),
        recipientEmail: formData.get('recipientEmail'),
        recipientPhone: formData.get('recipientPhone'),
        recipientAddress: formData.get('recipientAddress'),
        weight: parseFloat(formData.get('weight')),
        length: parseFloat(formData.get('length')),
        width: parseFloat(formData.get('width')),
        height: parseFloat(formData.get('height')),
        description: formData.get('description') || '',
        parcelType: formData.get('parcelType'),
        deliveryType: formData.get('deliveryType')
    };

    console.log('Submitting parcel data:', data);
    console.log('API URL:', API_BASE_URL);

    try {
        showToast('Submitting booking...', 'info');
        const response = await fetch(API_BASE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        console.log('Response status:', response.status);
        
        const result = await response.json();
        console.log('Response data:', result);

        if (response.ok) {
            showToast('Parcel booked successfully! Tracking Number: ' + result.trackingNumber, 'success');
            e.target.reset();
            scrollToSection('my-parcels');
        } else {
            showToast(result.error || 'Failed to book parcel', 'error');
        }
    } catch (error) {
        console.error('Error submitting parcel:', error);
        showToast('Error: ' + error.message + '. Make sure backend is running on port 8081', 'error');
    }
}

// Track Parcel Functionality
async function trackParcel() {
    const trackingNumber = document.getElementById('trackingInput').value.trim();
    
    if (!trackingNumber) {
        showToast('Please enter a tracking number', 'error');
        return;
    }

    console.log('Tracking number:', trackingNumber);

    try {
        const url = `${API_BASE_URL}/track/${trackingNumber}`;
        console.log('Track URL:', url);
        
        const response = await fetch(url);
        console.log('Track response status:', response.status);
        
        const result = await response.json();
        console.log('Track response data:', result);

        if (response.ok) {
            displayParcelDetails(result);
            showToast('Parcel found!', 'success');
        } else {
            document.getElementById('trackResult').innerHTML = `
                <div class="parcel-card">
                    <p style="color: ${getVar('--danger-color')};">${result.error}</p>
                </div>
            `;
            showToast(result.error, 'error');
        }
    } catch (error) {
        console.error('Error tracking parcel:', error);
        showToast('Error tracking parcel: ' + error.message, 'error');
    }
}

function displayParcelDetails(parcel) {
    const trackResult = document.getElementById('trackResult');
    const status = parcel.status;
    
    trackResult.innerHTML = `
        <div class="parcel-card">
            <div class="parcel-header">
                <div class="tracking-number">Tracking #${parcel.trackingNumber}</div>
                <span class="status-badge status-${status}">${formatStatus(status)}</span>
            </div>
            <div class="parcel-details">
                <div class="parcel-detail-item">
                    <strong>Sender:</strong> ${parcel.senderName}
                </div>
                <div class="parcel-detail-item">
                    <strong>Recipient:</strong> ${parcel.recipientName}
                </div>
                <div class="parcel-detail-item">
                    <strong>Parcel Type:</strong> ${parcel.parcelType}
                </div>
                <div class="parcel-detail-item">
                    <strong>Delivery Type:</strong> ${parcel.deliveryType}
                </div>
                <div class="parcel-detail-item">
                    <strong>Weight:</strong> ${parcel.weight} kg
                </div>
                <div class="parcel-detail-item">
                    <strong>Dimensions:</strong> ${parcel.length} x ${parcel.width} x ${parcel.height} cm
                </div>
                <div class="parcel-detail-item">
                    <strong>Shipping Cost:</strong> $${parcel.shippingCost?.toFixed(2) || 'N/A'}
                </div>
                <div class="parcel-detail-item">
                    <strong>Status:</strong> ${formatStatus(status)}
                </div>
            </div>
            ${parcel.description ? `<div style="margin-top: 15px; padding: 15px; background-color: var(--light-color); border-radius: 8px;">
                <strong>Description:</strong> ${parcel.description}
            </div>` : ''}
            ${parcel.estimatedDeliveryDate ? `<div class="parcel-detail-item" style="grid-column: 1 / -1;">
                <strong>Estimated Delivery:</strong> ${formatDateTime(parcel.estimatedDeliveryDate)}
            </div>` : ''}
        </div>
    `;
}

// Load User Parcels
async function loadUserParcels() {
    const email = document.getElementById('userEmail').value.trim();
    
    if (!email) {
        showToast('Please enter your email', 'error');
        return;
    }

    console.log('Loading parcels for email:', email);

    try {
        const url = `${API_BASE_URL}/user/${email}`;
        console.log('Load parcels URL:', url);
        
        const response = await fetch(url);
        console.log('Load parcels response status:', response.status);
        
        const parcels = await response.json();
        console.log('Parcels data:', parcels);

        displayParcelsList(parcels);
        
        if (parcels.length > 0) {
            showToast(`Found ${parcels.length} parcel(s)`, 'success');
        } else {
            showToast('No parcels found for this email', 'info');
        }
    } catch (error) {
        console.error('Error loading parcels:', error);
        showToast('Error loading parcels: ' + error.message, 'error');
    }
}

function displayParcelsList(parcels) {
    const parcelsList = document.getElementById('parcelsList');
    
    if (parcels.length === 0) {
        parcelsList.innerHTML = '<div class="parcel-card"><p style="text-align: center; color: #6b7280;">No parcels found</p></div>';
        return;
    }

    parcelsList.innerHTML = parcels.map(parcel => `
        <div class="parcel-card">
            <div class="parcel-header">
                <div class="tracking-number">Tracking #${parcel.trackingNumber}</div>
                <span class="status-badge status-${parcel.status}">${formatStatus(parcel.status)}</span>
            </div>
            <div class="parcel-details">
                <div class="parcel-detail-item">
                    <strong>From:</strong> ${parcel.senderName}
                </div>
                <div class="parcel-detail-item">
                    <strong>To:</strong> ${parcel.recipientName}
                </div>
                <div class="parcel-detail-item">
                    <strong>Type:</strong> ${parcel.parcelType}
                </div>
                <div class="parcel-detail-item">
                    <strong>Delivery:</strong> ${parcel.deliveryType}
                </div>
                <div class="parcel-detail-item">
                    <strong>Weight:</strong> ${parcel.weight} kg
                </div>
                <div class="parcel-detail-item">
                    <strong>Cost:</strong> $${parcel.shippingCost?.toFixed(2) || 'N/A'}
                </div>
            </div>
            <div style="margin-top: 15px; padding: 15px; background-color: var(--light-color); border-radius: 8px;">
                <strong>Created:</strong> ${formatDateTime(parcel.createdAt)}
            </div>
        </div>
    `).join('');
}

// Utility Functions
function formatStatus(status) {
    return status.replace(/_/g, ' ').toLowerCase().replace(/\b\w/g, l => l.toUpperCase());
}

function formatDateTime(dateTimeString) {
    if (!dateTimeString) return 'N/A';
    const date = new Date(dateTimeString);
    return date.toLocaleString();
}

function getVar(variable) {
    return getComputedStyle(document.documentElement).getPropertyValue(variable);
}

// Toast Notification
function showToast(message, type = 'info') {
    const toast = document.getElementById('toast');
    toast.textContent = message;
    toast.className = `toast ${type}`;
    toast.classList.add('show');

    setTimeout(() => {
        toast.classList.remove('show');
    }, 5000);
}

// Handle Enter key in tracking input
document.addEventListener('DOMContentLoaded', () => {
    const trackingInput = document.getElementById('trackingInput');
    if (trackingInput) {
        trackingInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                trackParcel();
            }
        });
    }
});
