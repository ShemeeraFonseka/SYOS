import React, { useState } from 'react'
import axios from 'axios'
import { useNavigate } from 'react-router-dom';

const Register = () => {

  const[formData, setFormData]=useState({
    name:'',
    email:'',
    phone:'',
    address:'',
    username:'',
    password:'',
  })

  const handleChange=(e)=>{
    setFormData({...formData,[e.target.name]: e.target.value})
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const params = new URLSearchParams({ action: 'register', ...formData });
      const res = await axios.post('http://localhost:8080/SYOS%20Backend/register?action=register', params, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
      });
      alert(res.data.message);
    } catch (error) {
      console.error(error);
      alert('Registration failed');
    }
  };

  const navigate=useNavigate();

    const navigateLogin=()=>{
        navigate('/login')
    }

  return (
    <div>
      <div>
        <form onSubmit={handleSubmit}>
          <input name='name' onChange={handleChange} placeholder='Name' required/>
          <input name='email' onChange={handleChange} placeholder='Email' required/>
          <input name='phone' onChange={handleChange} placeholder='Phone' required/>
          <input name='address' onChange={handleChange} placeholder='Address' required/>
          <input name='username' onChange={handleChange} placeholder='Username' required/>
          <input name='password' onChange={handleChange} placeholder='Password' required/>
          <button type="submit">Register</button>
        </form>
        <button onClick={navigateLogin}>Login</button>
      </div>
    </div>
  )
}

export default Register