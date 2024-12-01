<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login page</title>
</head>
<body>
<div class="container">
  <div class="card register">
    <h2>Register</h2>
    <form action="Register">
      <input type="text" placeholder="Username" name="name" required>
      <input type="email" placeholder="Email" name="email"required>
      <input type="password" placeholder="Password" name="password"required>
      <input type="tel" placeholder="Mobile" name="mobile"required>
      <button type="submit">Register</button>
    </form>
  </div>
  <div class="separator"></div>
  <div class="card login">
    <h2>Login</h2>
    <form action="Login">
      <input type="email" placeholder="Email" name="email" required>
      <input type="password" placeholder="Password" name="password" required>
      <button type="submit">Login</button>
    </form>
  </div>
</div>

<style>
  body {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #ffffff;
  font-family: 'Arial', sans-serif;
  margin: 0;
}

.container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  max-width: 800px;
}

.card {
  width: 45%;
  margin: 0 10px;
  padding: 20px;
  background-color: #ffffff;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
  border-radius: 10px;
  box-sizing: border-box;
  height: 350px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.separator {
  width: 5px;
  background: linear-gradient(to bottom, #ffcc00, #ffcc00);
}

h2 {
  margin-bottom: 20px;
  color: #333;
}

form {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  justify-content: space-between;
}

input {
  margin-bottom: 15px;
  padding: 10px;
  border: none;
  border-bottom: 2px solid #ffcc00;
  font-size: 16px;
  background: none;
}

input:focus {
  outline: none;
  border-bottom-color: #ff9900;
}

button {
  padding: 10px;
  border: none;
  border-radius: 5px;
  background-color: #ffcc00;
  color: #fff;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #ff9900;
}
  
</style>
</body>
</html>