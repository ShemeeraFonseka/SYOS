import React, { useState } from 'react';
import './RegisterModal.css';
import { IoIosClose } from "react-icons/io";
import axios from 'axios';

const RegisterModal = ({ isRegisterOpen, onRegisterClose, onSuccessfulRegister }) => {
  const [error, setError] = useState('');

  const [formData, setFormData] = useState({
    name: '',
    email: '',
    phone: '',
    address: '',
    username: '',
    password: '',
  });

  // Make these variables available in the component
  const { name, email, phone, address, username, password } = formData;

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validation
    if (!name || !email || !phone || !address || !username || !password) {
      setError('All fields are required');
      return;
    }

    if (!/\S+@\S+\.\S+/.test(email)) {
      setError("Invalid email format.");
      return;
    }

    if (!/^\d+$/.test(phone)) {
      setError("Phone number should contain only numbers.");
      return;
    }

    setError(''); // Clear previous errors

    try {
      const params = new URLSearchParams({ action: 'register', ...formData });
      const res = await axios.post(
        'http://localhost:8080/SYOS%20Backend/register?action=register',
        params,
        {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
          },
        }
      );

      alert(res.data.message);
      onSuccessfulRegister(); // call callback if needed
      onRegisterClose();      // close modal on success
    } catch (error) {
      console.error(error);
      alert('Registration failed');
    }
  };

  if (!isRegisterOpen) return null;

  return (
    <div className="modal">
      <div className="modal-content">
        <span className="close" onClick={onRegisterClose}><IoIosClose /></span>
        <form className="loginform" onSubmit={handleSubmit}>
          <p style={{ fontSize: '20px', fontWeight: 'bold', color: 'black' }}>Register</p><br />

          <input
            placeholder="Name"
            name="name"
            value={name}
            onChange={handleChange}
            type="text"
            className="login-input"
          /><br />

          <input
            placeholder="Email"
            name="email"
            type="email"
            value={email}
            onChange={handleChange}
            className="login-input"
          /><br />

          <input
            placeholder="Phone Number"
            name="phone"
            type="text"
            value={phone}
            onChange={handleChange}
            className="login-input"
          /><br />

          <input
            placeholder="Address"
            name="address"
            type="text"
            value={address}
            onChange={handleChange}
            className="login-input"
          /><br />

          <input
            placeholder="Username"
            name="username"
            value={username}
            onChange={handleChange}
            type="text"
            className="login-input"
          /><br />

          <input
            placeholder="Password"
            name="password"
            type="password"
            value={password}
            onChange={handleChange}
            className="login-input"
          /><br /><br />

          <button className="login-button" type="submit">Register</button>
          {error && <p style={{ color: 'red', fontSize: '14px' }}>{error}</p>}
        </form>
      </div>
    </div>
  );
};

export default RegisterModal;
