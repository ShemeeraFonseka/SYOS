import React, { createContext, useState, useEffect } from 'react';

export const CartContext = createContext();

export const CartProvider = ({ children }) => {
    const [cartItems, setCartItems] = useState([]);
    const [cartItemCount, setCartItemCount] = useState(0);

    useEffect(() => {
        const totalCount = cartItems.reduce((sum, item) => sum + item.quantity, 0);
        setCartItemCount(totalCount);
    }, [cartItems]);

    const addToCart = (item) => {
        const normalizedItem = {
            id: item.itemCode,
            name: item.itemName,
            price: item.pricePerUnit,
            image: item.path,
        };
    
        const existingItemIndex = cartItems.findIndex(cartItem => cartItem.id === normalizedItem.id);
    
        if (existingItemIndex !== -1) {
            const updatedCartItems = [...cartItems];
            updatedCartItems[existingItemIndex].quantity += 1;
            setCartItems(updatedCartItems);
        } else {
            setCartItems([...cartItems, { ...normalizedItem, quantity: 1 }]);
        }
    };
    

    const increaseCart = (item) => {
        const existingItemIndex = cartItems.findIndex(cartItem => cartItem.id === item.id);

        if (existingItemIndex !== -1) {
            const updatedCartItems = [...cartItems];
            updatedCartItems[existingItemIndex].quantity += 1;
            setCartItems(updatedCartItems);
        }
    };

    const decreaseCart = (item) => {
        const existingItemIndex = cartItems.findIndex(cartItem => cartItem.id === item.id);

        if (existingItemIndex !== -1) {
            const updatedCartItems = [...cartItems];
            updatedCartItems[existingItemIndex].quantity -= 1;

            if (updatedCartItems[existingItemIndex].quantity <= 0) {
                updatedCartItems.splice(existingItemIndex, 1);
            }

            setCartItems(updatedCartItems);
        }
    };

    const clearCart = () => {
        setCartItems([]);
    };

    return (
        <CartContext.Provider value={{ cartItems, cartItemCount, addToCart, increaseCart, decreaseCart, clearCart }}>
            {children}
        </CartContext.Provider>
    );
};
