// Main JavaScript file for Event Management System

document.addEventListener('DOMContentLoaded', function() {
    // Mobile menu toggle
    const mobileMenuBtn = document.getElementById('mobileMenuBtn');
    if (mobileMenuBtn) {
        mobileMenuBtn.addEventListener('click', function() {
            const navbar = document.querySelector('.navbar');
            if (navbar.classList.contains('mobile-menu-closed')) {
                navbar.classList.remove('mobile-menu-closed');
                navbar.classList.add('mobile-menu-open');
            } else {
                navbar.classList.remove('mobile-menu-open');
                navbar.classList.add('mobile-menu-closed');
            }
        });
    }
    
    // Password confirmation validation
    const registerForm = document.querySelector('form[action*="/register"]');
    if (registerForm) {
        registerForm.addEventListener('submit', function(event) {
            const password = document.getElementById('password');
            const confirmPassword = document.getElementById('confirmPassword');
            
            if (password && confirmPassword && password.value !== confirmPassword.value) {
                event.preventDefault();
                alert('Passwords do not match!');
                
                // Add error highlighting
                password.classList.add('error');
                confirmPassword.classList.add('error');
            }
        });
    }
    
    // Change password form validation
    const changePasswordForm = document.querySelector('form[action*="/changePassword"]');
    if (changePasswordForm) {
        changePasswordForm.addEventListener('submit', function(event) {
            const newPassword = document.getElementById('newPassword');
            const confirmPassword = document.getElementById('confirmPassword');
            
            if (newPassword && confirmPassword && newPassword.value !== confirmPassword.value) {
                event.preventDefault();
                alert('New passwords do not match!');
                
                // Add error highlighting
                newPassword.classList.add('error');
                confirmPassword.classList.add('error');
            }
        });
    }
    
    // Admin dashboard charts (if Chart.js is available)
    if (typeof Chart !== 'undefined' && document.getElementById('eventsChart')) {
        // This is a placeholder for the Chart.js implementation
        // In a real application, this would fetch data from the server
        
        const eventsCtx = document.getElementById('eventsChart').getContext('2d');
        const eventsChart = new Chart(eventsCtx, {
            type: 'bar',
            data: {
                labels: ['January', 'February', 'March', 'April', 'May', 'June'],
                datasets: [{
                    label: 'Number of Events',
                    data: [12, 19, 3, 5, 2, 3],
                    backgroundColor: 'rgba(52, 152, 219, 0.5)',
                    borderColor: 'rgba(52, 152, 219, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }
    
    // Confirm delete actions
    const deleteButtons = document.querySelectorAll('.btn-danger');
    deleteButtons.forEach(button => {
        if (!button.hasAttribute('onclick')) {
            button.addEventListener('click', function(event) {
                if (!confirm('Are you sure you want to delete this item?')) {
                    event.preventDefault();
                }
            });
        }
    });
    
    // Form validation for booking forms
    const bookingForm = document.querySelector('.booking-form');
    if (bookingForm) {
        bookingForm.addEventListener('submit', function(event) {
            const phone = document.getElementById('phone');
            if (phone && phone.value.trim() === '') {
                event.preventDefault();
                alert('Please enter a valid phone number');
                phone.focus();
            }
            
            const meetingTime = document.getElementById('meetingTime');
            if (meetingTime && meetingTime.value.trim() === '') {
                event.preventDefault();
                alert('Please select a meeting time');
                meetingTime.focus();
            }
        });
    }
    
    // Auto-hide alerts after 5 seconds
    const alerts = document.querySelectorAll('.alert');
    if (alerts.length > 0) {
        setTimeout(function() {
            alerts.forEach(alert => {
                alert.style.opacity = '0';
                setTimeout(() => {
                    alert.style.display = 'none';
                }, 500);
            });
        }, 5000);
    }
});
