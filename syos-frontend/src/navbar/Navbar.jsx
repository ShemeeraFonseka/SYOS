import React, { useState,useContext } from 'react'
import './Navbar.css'
import { GiHamburgerMenu } from "react-icons/gi";
import { LuShoppingCart } from "react-icons/lu";
import { useNavigate } from 'react-router-dom';
import LoginModal from '../login/LoginModal';
import RegisterModal from '../register/RegisterModal';
import { CartContext } from "../CartContext";
import { IoIosClose } from "react-icons/io";


const Navbar = () => {

    const [isOpen, setIsOpen] = useState(false)
    const [isLoginModalOpen, setIsLoginModalOpen] = useState(false);
    const [isRegisterModalOpen, setIsRegisterModalOpen] = useState(false);
    const { cartItems, cartItemCount, increaseCart, decreaseCart } = useContext(CartContext);
    const [showCartTab, setShowCartTab] = useState(false);

    const toggleCartTab = () => setShowCartTab(prev => !prev);
    const closeCartTab = () => setShowCartTab(false);

    const handleCheckout = () => {
    const userID = sessionStorage.getItem("userID");
    if (!userID) {
      alert("You must be logged in to access the checkout page.");
      navigate('/'); 
      setShowCartTab(false);
    } else {
      navigate('/checkout');
    }
  };



    const navigate=useNavigate();

    const navigateHome=()=>{
        navigate('/')
    }

    const navigateAbout=()=>{
        navigate('/about')
    }

    const navigateContact=()=>{
        navigate('/contact')
    }

    const navigateProducts=()=>{
        navigate('/products')
    }

    const navigateSignup=()=>{
        navigate('/signup')
    }

    const openLoginModal = () => setIsLoginModalOpen(true);
    const closeLoginModal = () => setIsLoginModalOpen(false);

    const openRegisterModal = () => setIsRegisterModalOpen(true); // Open RegisterModal
    const closeRegisterModal = () => setIsRegisterModalOpen(false); // Close RegisterModal

   
    return (
        <div className='navbar'>

            <div className='logo'>SYOS</div>

            <div className='menu' onClick={() => setIsOpen(!isOpen)}>
                <GiHamburgerMenu />
            </div>

            <ul className={`nav-links ${isOpen ? "open" : ""}`}>
                <li><a onClick={navigateHome}>Home</a></li>
                <li><a onClick={navigateAbout}>About</a></li>
                <li><a onClick={navigateProducts}>Products</a></li>
                <li><a onClick={navigateContact}>Contact</a></li>
                <li><a><LuShoppingCart size={20} onClick={toggleCartTab}  /></a></li>
                <span className="cart-badge">{cartItemCount}</span>
                <li><a onClick={openLoginModal}>Login </a>|<a onClick={openRegisterModal}> Register</a></li>
            </ul>
            <LoginModal isOpen={isLoginModalOpen} onClose={closeLoginModal} />
            <RegisterModal onSuccessfulRegister={openLoginModal} isRegisterOpen={isRegisterModalOpen} onRegisterClose={closeRegisterModal} /> {/* Add RegisterModal here */}

            {showCartTab && (
                <div className="cartTab">
                    <div className='close-section'>
                        <IoIosClose className='close-icon' onClick={closeCartTab} />
                    </div>
                    <h1 className="tab-name">My Cart</h1>
                    <div className="listCart">
                        {cartItems.length > 0 ? (
                            cartItems.map((cartItem, index) => (
                                <div key={index} className="item">
                                    <div className="image">
                                        <img className="cart-image" src={process.env.PUBLIC_URL + cartItem.image} alt="" />
                                    </div>
                                    <div className="name" style={{ fontSize: "15px" }}>
                                        {cartItem.name}
                                    </div>
                                    <div className="totalPrice">
                                        <span style={{ fontSize: "15px" }}>Rs. {cartItem.price * cartItem.quantity}</span>
                                    </div>
                                    <div className="quantity">
                                        <span className="minus" style={{ fontSize: "20px" }} onClick={() => decreaseCart(cartItem)}>-</span>
                                        <span style={{ fontSize: "15px",color:'black'}}>{cartItem.quantity}</span>
                                        <span className="plus" style={{ fontSize: "20px" }} onClick={() => increaseCart(cartItem)}>+</span>
                                    </div>
                                </div>
                            ))
                        ) : (
                            <div className="empty-cart">Your cart is empty.</div>
                        )}
                    </div>
                    <div className="btn">
                        <button className="checkout" onClick={handleCheckout}>Check Out</button>
                    </div>
                </div>
            )}

        </div>
    )
}

export default Navbar