import React from "react";
import "./Footer.css";
import { FaFacebook } from "react-icons/fa6";
import { FaInstagram } from "react-icons/fa";


const Footer = () => {
  return (
    <div className="footer">

      <div className="f-content">
        <div className="footer-topic">SYOS</div>
        <div className="footer-links">
          <p>Home</p>
          <p>Abouts Us</p>
          <p>Products</p>
          <p>Contact Us</p>
        </div>
        <div className="touch">Stay in Touch</div>
        <div className="f-icons">
        <FaFacebook size={"1.5rem"}/>
        <FaInstagram size={"1.5rem"}/>
        </div>
      </div>
    </div>
  );
};

export default Footer;
