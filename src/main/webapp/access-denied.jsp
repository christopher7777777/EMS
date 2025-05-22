<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Access Denied</title>
    <style>
        /* Reset default margins and padding */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        /* Body styling */
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f9;
            color: #333;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            padding: 20px;
        }

        /* Container for content */
        .container {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 30px;
            max-width: 500px;
            width: 100%;
            text-align: center;
        }

        /* Heading styling */
        h2 {
            color: #d32f2f; /* Red for error emphasis */
            margin-bottom: 20px;
            font-size: 24px;
        }

        /* Paragraph styling */
        p {
            font-size: 16px;
            line-height: 1.5;
            margin-bottom: 20px;
            color: #555;
        }

        /* Link styling */
        a {
            display: inline-block;
            padding: 10px 20px;
            background-color: #1976d2; /* Blue button */
            color: #fff;
            text-decoration: none;
            border-radius: 4px;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }

        a:hover {
            background-color: #1565c0; /* Darker blue on hover */
        }

        /* Responsive design */
        @media (max-width: 600px) {
            .container {
                padding: 20px;
                margin: 10px;
            }

            h2 {
                font-size: 20px;
            }

            p {
                font-size: 14px;
            }

            a {
                font-size: 14px;
                padding: 8px 16px;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Access Denied</h2>
    <p><%= session.getAttribute("errorMessage") != null ? session.getAttribute("errorMessage") : "You do not have permission to access this resource." %></p>
    <a href="${pageContext.request.contextPath}/login.jsp">Return</a>

    <% session.removeAttribute("errorMessage"); // Clear message after displaying %>
</div>
</body>
</html>