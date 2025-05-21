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

    // Password validation for registration form
    const registerForm = document.querySelector('form[action*="/register"]');
    if (registerForm) {
        const passwordInput = document.getElementById('password');
        const confirmPasswordInput = document.getElementById('confirmPassword');
        const passwordRegex = /^(?=.*[A-Z])(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{6,}$/;

        // Real-time password validation feedback
        passwordInput.addEventListener('input', function() {
            if (!passwordRegex.test(passwordInput.value)) {
                passwordInput.classList.add('error');
                passwordInput.setCustomValidity(
                    'Password must be at least 6 characters long, contain at least one capital letter, and one symbol (!@#$%^&*).'
                );
            } else {
                passwordInput.classList.remove('error');
                passwordInput.setCustomValidity('');
            }
        });

        // Confirm password validation
        confirmPasswordInput.addEventListener('input', function() {
            if (passwordInput.value !== confirmPasswordInput.value) {
                confirmPasswordInput.classList.add('error');
                confirmPasswordInput.setCustomValidity('Passwords do not match.');
            } else {
                confirmPasswordInput.classList.remove('error');
                confirmPasswordInput.setCustomValidity('');
            }
        });

        registerForm.addEventListener('submit', function(event) {
            if (!passwordRegex.test(passwordInput.value)) {
                event.preventDefault();
                alert('Password must be at least 6 characters long, contain at least one capital letter, and one symbol (!@#$%^&*).');
                passwordInput.classList.add('error');
                passwordInput.focus();
                return;
            }

            if (passwordInput.value !== confirmPasswordInput.value) {
                event.preventDefault();
                alert('Passwords do not match!');
                confirmPasswordInput.classList.add('error');
                confirmPasswordInput.focus();
                return;
            }
        });
    }

    // Change password form validation (if applicable)
    const changePasswordForm = document.querySelector('form[action*="/changePassword"]');
    if (changePasswordForm) {
        const newPassword = document.getElementById('newPassword');
        const confirmPassword = document.getElementById('confirmPassword');
        const passwordRegex = /^(?=.*[A-Z])(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{6,}$/;

        newPassword.addEventListener('input', function() {
            if (!passwordRegex.test(newPassword.value)) {
                newPassword.classList.add('error');
                newPassword.setCustomValidity(
                    'Password must be at least 6 characters long, contain at least one capital letter, and one symbol (!@#$%^&*).'
                );
            } else {
                newPassword.classList.remove('error');
                newPassword.setCustomValidity('');
            }
        });

        confirmPassword.addEventListener('input', function() {
            if (newPassword.value !== confirmPassword.value) {
                confirmPassword.classList.add('error');
                confirmPassword.setCustomValidity('Passwords do not match.');
            } else {
                confirmPassword.classList.remove('error');
                confirmPassword.setCustomValidity('');
            }
        });

        changePasswordForm.addEventListener('submit', function(event) {
            if (!passwordRegex.test(newPassword.value)) {
                event.preventDefault();
                alert('Password must be at least 6 characters long, contain at least one capital letter, and one symbol (!@#$%^&*).');
                newPassword.classList.add('error');
                newPassword.focus();
                return;
            }

            if (newPassword.value !== confirmPassword.value) {
                event.preventDefault();
                alert('New passwords do not match!');
                confirmPassword.classList.add('error');
                confirmPassword.focus();
                return;
            }
        });
    }

    // Admin dashboard charts (if Chart.js is available)
    if (typeof Chart !== 'undefined' && document.getElementById('eventsChart')) {
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