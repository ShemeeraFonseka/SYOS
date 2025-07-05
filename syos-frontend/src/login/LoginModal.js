import React, { useState } from 'react';
import './LoginModal.css';
import { IoIosClose } from "react-icons/io";
import { useNavigate } from 'react-router-dom';
import RegisterModal from '../register/RegisterModal';

const LoginModal = ({ isOpen, onClose }) => {
  const [error, setError] = useState('');
  const [message, setMessage] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [isRegisterModalOpen, setIsRegisterModalOpen] = useState(false);
  const [isLoginModalOpen, setIsLoginModalOpen] = useState(false); 

  

  const openLoginModal = () => setIsLoginModalOpen(true);
    const closeLoginModal = () => setIsLoginModalOpen(false);

  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
  
    if (!username || !password) {
      setError('All fields are required');
      return;
    }
  
    try {
      const formData = new URLSearchParams();
      formData.append('action', 'login');
      formData.append('username', username);
      formData.append('password', password);
      
  
      const response = await fetch('http://localhost:8080/SYOS%20Backend/login?action=login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: formData.toString(),
      });
  
      // Parse the response as JSON
      const data = await response.json();
  
      if (response.ok) {
        // Store user ID in session storage
        sessionStorage.setItem('userID', data.userID);
        sessionStorage.setItem('username', username);

        
        
        setMessage(data.message || 'Login successful');
        console.log('Login successful - User ID:', data.userID);
        navigate('/products');

        if (username === 'admin' && password === 'admin') {
          navigate('/neworderdashboard');
        }
  
        setUsername('');
        setPassword('');
        setError('');
        setMessage('');
  
        onClose();
      } else {
        setError('Invalid credentials');
      }
    } catch (error) {
      setMessage('Something went wrong!');
      console.error('Login error:', error);
    }
  };
  

  if (!isOpen) return null;

  return (
    <div className="modal">
      <div className="modal-content">
        <span className="close" onClick={onClose}><IoIosClose /></span>
        <form className="loginform" onSubmit={handleLogin}>
          <p style={{ fontSize: '20px', fontWeight: 'bold', color: 'black' }}>Login</p>
          <br />
          <input
            type="text"
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
            className="login-input"
          />
          <br />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            className="login-input"
          />
          <br /><br />
          <button className="login-button" type="submit">
            Login
          </button>
          {error && <p style={{ color: 'red', fontSize: '14px' }}>{error}</p>}
          {message && <p style={{ color: 'green', fontSize: '14px' }}>{message}</p>}        </form>
      </div>
      
    </div>
  );
};

export default LoginModal;
