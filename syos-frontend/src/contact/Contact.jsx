import React, { useState } from 'react';
import emailjs from 'emailjs-com';
import Footer from '../footer/Footer';
import './Contact.css';

const Contact = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    message: ''
  });

  const handleChange = (e) => {
    setFormData(prev => ({
      ...prev,
      [e.target.name]: e.target.value
    }));
  };

  const sendEmail = async (e) => {
    e.preventDefault();

    const templateParams = {
      name: formData.name,
      email: formData.email,
      message: formData.message
    };

    try {
      await emailjs.send(
        'service_vunoib8',        
        'template_v9unwf3',       
        templateParams,
        'soFQR3l3kCMKLYla3'       
      );

      alert('Message sent successfully!');
      setFormData({ name: '', email: '', message: '' });
    } catch (error) {
      alert('Failed to send message. Please try again.');
      console.error('EmailJS Error:', error);
    }
  };

  return (
    <div className='container'>
      <div style={{ display: 'flex', padding: '50px', gap: '40px', flexWrap: 'wrap' }}>

        {/* Left: Contact Info */}
        <div style={{ flex: 1, minWidth: '250px' }}>
          <h2>Contact Information</h2>
          <p><strong>Phone:</strong> +1 234 567 890</p>
          <p><strong>Email:</strong> contact@freshcart.com</p>
          <p><strong>Address:</strong> 123 Fresh Street, Green City, Country</p>
          <img className='map' src='/location.jpg'></img>
        </div>

        {/* Right: Contact Form */}
        <div style={{ flex: 1, minWidth: '300px' }}>
          <h2>Send Us a Message</h2>
          <form onSubmit={sendEmail}>
            <div style={{ marginBottom: '10px' }}>
              <label htmlFor="name">Name:</label><br />
              <input
                type="text"
                id="name"
                name="name"
                value={formData.name}
                onChange={handleChange}
                required
                className='item-input'
              />
            </div>

            <div style={{ marginBottom: '10px' }}>
              <label htmlFor="email">Email:</label><br />
              <input
                type="email"
                id="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                required
                className='item-input'
              />
            </div>

            <div style={{ marginBottom: '10px' }}>
              <label htmlFor="message">Message:</label><br />
              <textarea
                id="message"
                name="message"
                rows="5"
                value={formData.message}
                onChange={handleChange}
                required
                className='msg-input'
              ></textarea>
            </div>
<div className='button-container'><button className='sendbutton' type="submit">Send Message</button></div>
            
          </form>
        </div>
      </div>
      <Footer />
    </div>
  );
};

export default Contact;
