import React from 'react';
import './About.css';
import Footer from '../footer/Footer';

const About = () => {
  return (
    <div>
    <div style={{ display: 'flex', padding: '100px' }}>
      
      {/* Left: 2x2 Images */}
      <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '10px', marginRight: '40px' }}>
        <div className="image-container"><img className='images' src="/img1.jpeg" alt="1" /></div>
        <div className="image-container"><img className='images' src="/img2.jpg" alt="2" /></div>
        <div className="image-container"><img className='images' src="/img3.jpg" alt="3" /></div>
        <div className="image-container"><img className='images' src="/img4.jpg" alt="4" /></div>
      </div>

      {/* Right: Paragraph */}
      <div>
        <h1>About Us</h1>
        <p className='aboutcontent'>
          Welcome to our website! We are dedicated to delivering high-quality services and fresh, wholesome products that enhance your everyday life. Our mission is to create meaningful digital experiences while connecting you with the finest selection of fruits, vegetables, and dairy products.

At the heart of our platform is a commitment to freshness and quality. We carefully source a wide variety of seasonal fruits, nutrient-rich vegetables, and farm-fresh dairy to ensure that your family gets the best. Whether you're planning a healthy meal, stocking your pantry, or making a quick grocery run, we make the process simple, convenient, and reliable.

We believe in innovation, sustainability, and putting our customers first. With a passionate team and years of expertise, we aim to exceed expectations and provide exceptional value. Your health and satisfaction are our top priorities, and we’re proud to support your journey to a better lifestyle—one fresh product at a time.
        </p>
      </div>

    </div>
    <Footer/>
    </div>
  );
};

export default About;
