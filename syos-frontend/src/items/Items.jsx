import React, { useEffect, useState,useContext } from 'react';
import axios from 'axios';
import './Items.css';
import { CartContext } from '../CartContext';
import Footer from '../footer/Footer';

const Items = () => {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const { addToCart } = useContext(CartContext);

  useEffect(() => {
    axios
      .get('http://localhost:8080/SYOS%20Backend/items?action=getAll')
      .then((response) => {
        setItems(response.data);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching items:", error);
        setLoading(false);
      });
  }, []);

  if (loading) return <p>Loading items...</p>;

  const fruitsData = items.filter(item => item.category.toLowerCase() === 'fruits');

  const vegeData = items.filter(item => item.category.toLowerCase() === 'vegetables');

  const dairyData = items.filter(item => item.category.toLowerCase() === 'dairy');

  return (
    <div className="item-container">
      <h2>Fruits</h2>
      <div className="listProduct">
        {fruitsData.length > 0 ? (
          fruitsData.map((fruit) => (
            <div key={fruit.itemCode} className="item">
              <img className="product-images" src={process.env.PUBLIC_URL + fruit.path} alt={fruit.itemName} />
              <div className="product-name">{fruit.itemName}</div>
              <div className="price">Rs. {fruit.pricePerUnit}</div>
              <button className="addCart" onClick={() => addToCart(fruit)}>Add To Cart</button>
            </div>
          ))
        ) : (
          <p>No fruits found.</p>
        )}
      </div>

      <h2>Vegetables</h2>
      <div className="listProduct">
        {vegeData.length > 0 ? (
          vegeData.map((vege) => (
            <div key={vege.itemCode} className="item">
              <img className="product-images" src={process.env.PUBLIC_URL + vege.path} alt={vege.itemName} />
              <div className="product-name">{vege.itemName}</div>
              <div className="price">Rs. {vege.pricePerUnit}</div>
              <button className="addCart" onClick={() => addToCart(vege)}>Add To Cart</button>
            </div>
          ))
        ) : (
          <p>No vegetables found.</p>
        )}
      </div>

      <h2>Dairy</h2>
      <div className="listProduct">
        {dairyData.length > 0 ? (
          dairyData.map((dairy) => (
            <div key={dairy.itemCode} className="item">
              <img className="product-images" src={process.env.PUBLIC_URL + dairy.path} alt={dairy.itemName} />
              <div className="product-name">{dairy.itemName}</div>
              <div className="price">Rs. {dairy.pricePerUnit}</div>
              <button className="addCart" onClick={() => addToCart(dairy)}>Add To Cart</button>
            </div>
          ))
        ) : (
          <p>No dairy found.</p>
        )}
      </div>
      <br></br><br></br><br></br>
<Footer/>
    </div>
  );
};

export default Items;
