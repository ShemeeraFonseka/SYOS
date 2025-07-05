import React, { useState, useEffect } from 'react';
import './Dashboard.css';
import { v4 as uuidv4 } from 'uuid';
import { useLocation } from 'react-router-dom';


const NewOrderDashboard = () => {
  const [billDataForDisplay, setBillDataForDisplay] = useState(null);

  const location = useLocation();
  const currentPath = location.pathname;

  const [formData, setFormData] = useState({
    code: '',
    name: '',
    price: '',
    quantity: '',
    amount: ''
  });

  const [items, setItems] = useState([]);
  const [availableItems, setAvailableItems] = useState([]);


  // New state for discount, cash, etc.
  const [checkoutData, setCheckoutData] = useState({
    discount: '',
    cashTendered: '',
    cashChange: '',
    customerID: sessionStorage.getItem("userID"),
    amountAfterDiscount: ''
  });

  const handlePlaceOrder = async () => {
    if (items.length === 0) {
      alert("Please add items to the order");
      return;
    }

    if (!checkoutData.cashTendered) {
      alert("Cash Tendered is required");
      return;
    }

    const transactionType = checkoutData.customerID === "8" ? "ONSITE" : "ONLINE";

    // Prepare bill data
    const billData = {
      billSerialNumber: "",
      transactionType: transactionType,
      totalAmount: items.reduce((sum, item) => sum + parseFloat(item.amount), 0),
      discount: parseFloat(checkoutData.discount) || 0,
      cashTendered: parseFloat(checkoutData.cashTendered) || 0,
      cashChange: parseFloat(checkoutData.cashChange) || 0,
      customerID: checkoutData.customerID || null,
      amountAfterDiscount: parseFloat(checkoutData.amountAfterDiscount) || 0,
      orders: items.map(item => ({
        orderID: uuidv4(), // Generate unique order IDs
        itemCode: item.code,
        name: item.name,
        quantity: parseInt(item.quantity),
        amount: parseFloat(item.amount),
        price: parseFloat(item.price)
      }))
    };

    try {
      // First, save the bill
      const billResponse = await fetch('http://localhost:8080/SYOS%20Backend/bill', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(billData)
      });

      if (!billResponse.ok) {
        throw new Error('Failed to save bill');
      }

      const billResult = await billResponse.json();
      const billSerialNumber = billResult.billSerialNumber; 
      const billId = billResult.billId;

      // Then save each order item
      const orderPromises = billData.orders.map(order => {
        const orderData = new URLSearchParams();
        orderData.append('orderID', order.orderID);
        orderData.append('itemCode', order.itemCode);
        orderData.append('quantity', order.quantity);
        orderData.append('amount', order.amount);
        orderData.append('billSerialNumber', billSerialNumber);

        return fetch('http://localhost:8080/SYOS%20Backend/orders', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
          },
          body: orderData
        });
      });

      await Promise.all(orderPromises);

      setBillDataForDisplay({
        billId,
        billSerialNumber,
        transactionType,
        orders: billData.orders,
        totalAmount: billData.totalAmount.toFixed(2),
        discount: billData.discount.toFixed(2),
        amountAfterDiscount: billData.amountAfterDiscount.toFixed(2),
        cashTendered: billData.cashTendered.toFixed(2),
        cashChange: billData.cashChange.toFixed(2),
      });

      alert('Order placed successfully!');
      // Reset the form
      setItems([]);
      setCheckoutData(prev => ({
        ...prev,
        discount: '',
        cashTendered: '',
        cashChange: '',
        amountAfterDiscount: ''
      }));
    } catch (error) {
      console.error('Error placing order:', error);
      alert('Failed to place order');
    }
  };

  useEffect(() => {
    fetch('http://localhost:8080/SYOS%20Backend/items?action=getAll')
      .then((res) => res.json())
      .then((data) => setAvailableItems(data))
      .catch((error) => console.error("Error fetching items:", error));
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    let updatedForm = {
      ...formData,
      [name]: value
    };

    if (name === 'code') {
      const foundItem = availableItems.find((item) => item.itemCode === value);
      if (foundItem) {
        updatedForm.name = foundItem.itemName;
        updatedForm.price = foundItem.pricePerUnit;
      } else {
        updatedForm.name = '';
        updatedForm.price = '';
      }
    }

    const price = parseFloat(updatedForm.price);
    const quantity = parseFloat(updatedForm.quantity);
    updatedForm.amount = !isNaN(price) && !isNaN(quantity)
      ? (price * quantity).toFixed(2)
      : '';

    setFormData(updatedForm);
  };

  const handleCheckoutChange = (e) => {
    const { name, value } = e.target;
    const updatedCheckout = {
      ...checkoutData,
      [name]: value
    };



    const total = items.reduce((sum, item) => sum + parseFloat(item.amount), 0);
    const autoDiscount = total > 6000 ? total * 0.05 : 0;
    const discount = autoDiscount;
    const amountAfterDiscount = (total - discount).toFixed(2);
    const cashTendered = parseFloat(updatedCheckout.cashTendered);
    const cashChange = !isNaN(cashTendered)
      ? (cashTendered - amountAfterDiscount).toFixed(2)
      : '';
    updatedCheckout.discount = discount.toFixed(2);
    updatedCheckout.amountAfterDiscount = amountAfterDiscount;
    updatedCheckout.cashChange = cashChange;



    setCheckoutData(updatedCheckout);


  };

  const handleSubmit = (e) => {
    e.preventDefault();


    if (!formData.code || !formData.name || !formData.price || !formData.quantity) return;

    setItems([...items, formData]);
    setFormData({ code: '', name: '', price: '', quantity: '', amount: '' });
  };

  const handleQuantityChange = (index, delta) => {
    const updatedItems = [...items];
    let item = updatedItems[index];
    let newQuantity = parseInt(item.quantity) + delta;

    if (newQuantity <= 0) {
      updatedItems.splice(index, 1);
    } else {
      item.quantity = newQuantity;
      item.amount = (parseFloat(item.price) * newQuantity).toFixed(2);
    }

    setItems(updatedItems);
  };

  useEffect(() => {
    const total = items.reduce((sum, item) => sum + parseFloat(item.amount), 0);
    const autoDiscount = total > 6000 ? total * 0.05 : 0;
    const amountAfterDiscount = (total - autoDiscount).toFixed(2);
    const cashTendered = parseFloat(checkoutData.cashTendered);
    const cashChange = !isNaN(cashTendered)
      ? (cashTendered - amountAfterDiscount).toFixed(2)
      : '';

    setCheckoutData(prev => ({
      ...prev,
      discount: autoDiscount.toFixed(2),
      amountAfterDiscount,
      cashChange
    }));
  }, [items]);




  return (
    <div className="dashboard-container">
      <aside className="sidebar">
        <ul>
          <li><a href="/neworderdashboard" className={currentPath === "/neworderdashboard" ? "active" : ""}>New Order</a></li>
          <li><a href="/batchdashboard" className={currentPath === "/batchdashboard" ? "active" : ""}>Add Batch</a></li>
          <li><a href="/salesreport" className={currentPath === "/salesreport" ? "active" : ""}>Online Sales Report</a></li>
          <li><a href="/onsitesalesreport" className={currentPath === "/onsitesalesreport" ? "active" : ""}>Onsite Sales Report</a></li>
          <li><a href="/onlinereshelvereport" className={currentPath === "/onlinereshelvereport" ? "active" : ""}>Online Reshelve Report</a></li>
          <li><a href="/onsitereshelvereport" className={currentPath === "/onsitereshelvereport" ? "active" : ""}>Onsite Reshelve Report</a></li>
          <li><a href="/reorderlevelreport" className={currentPath === "/reorderlevelreport" ? "active" : ""}>Reorder Level Report</a></li>
          <li><a href="/stockreport" className={currentPath === "/stockreport" ? "active" : ""}>Stock Report</a></li>
          <li><a href="/billreport" className={currentPath === "/billreport" ? "active" : ""}>Bill Report</a></li>
        </ul>
      </aside>

      <main className="mainorder-content">
        <div className="form-section">
          <form onSubmit={handleSubmit}>
            <label>Item Code</label>
            <input className='item-input' name="code" value={formData.code} onChange={handleChange} />
            <label>Item Name</label>
            <input className='item-input' name="name" value={formData.name} readOnly />
            <label>Unit Price</label>
            <input className='item-input' name="price" value={formData.price} readOnly />
            <label>Quantity</label>
            <input className='item-input' name="quantity" value={formData.quantity} onChange={handleChange} />
            <label>Amount</label>
            <input className='item-input' name="amount" value={formData.amount} readOnly />
            <button className='addbutton' type="submit">Add</button>
          </form>
        </div>

        <div className="table-section">
          <table>
            <thead>
              <tr>
                <th>Item Code</th>
                <th>Item Name</th>
                <th>Unit Price</th>
                <th>Quantity</th>
                <th>Action</th>
                <th>Amount</th>
              </tr>
            </thead>
            <tbody>
              {items.map((item, index) => (
                <tr key={index}>
                  <td>{item.code}</td>
                  <td>{item.name}</td>
                  <td>{item.price}</td>
                  <td>{item.quantity}</td>
                  <td>
                    <button onClick={() => handleQuantityChange(index, -1)}>-</button>
                    <button onClick={() => handleQuantityChange(index, 1)}>+</button>
                  </td>
                  <td>{item.amount}</td>
                </tr>
              ))}
            </tbody>
          </table>

          {items.length > 0 && (
            <div className="order-total">
              <h3>
                Total: Rs.{' '}
                {items.reduce((sum, item) => sum + parseFloat(item.amount), 0).toFixed(2)}
              </h3>
            </div>
          )}

          {/* New checkout fields */}
          <div className="checkout-section">
            <input name="customerID" value={checkoutData.customerID} onChange={handleCheckoutChange} hidden />

            <label>Discount</label>
            <input className='item-input' name="discount" value={checkoutData.discount} onChange={handleCheckoutChange} readOnly />

            <label>Amount After Discount</label>
            <input className='item-input' name="amountAfterDiscount" value={checkoutData.amountAfterDiscount} readOnly />

            <label>Cash Tendered</label>
            <input className='item-input' name="cashTendered" value={checkoutData.cashTendered} onChange={handleCheckoutChange} required />

            <label>Cash Change</label>
            <input className='item-input' name="cashChange" value={checkoutData.cashChange} readOnly />

            
          </div>
          <div className='buttoncontainer'>
            <button className='orderbutton' onClick={handlePlaceOrder}>Place Order</button>
          </div>
          

          {billDataForDisplay && (
            <div className="billmodal-overlay">
              <div className="billmodal-content">
                <div className='billtopic'><h3>Bill Summary</h3></div>
                
                <p hidden><strong>Bill No:</strong> {billDataForDisplay.billSerialNumber}</p>
                <p><strong>Bill Serial Number:</strong> {billDataForDisplay.billId}</p>
                <p><strong>Transaction Type:</strong> {billDataForDisplay.transactionType}</p>
                <h4>Ordered Items</h4>
                <table>
                  <thead>
                    <tr>
                      <th>Item Code</th>
                      <th>Item Name</th>
                      <th>Unit Price</th>
                      <th>Quantity</th>
                      <th>Amount</th>
                    </tr>
                  </thead>
                  <tbody>
                    {billDataForDisplay.orders.map((order, index) => (
                      <tr key={index}>
                        <td>{order.itemCode}</td>
                        <td>{order.name}</td>
                        <td>{order.price}</td>
                        <td>{order.quantity}</td>
                        <td>{order.amount.toFixed(2)}</td>

                      </tr>
                    ))}
                  </tbody>
                </table>
                <p><strong>Total Amount:</strong> Rs. {billDataForDisplay.totalAmount}</p>
                <p><strong>Discount:</strong> Rs. {billDataForDisplay.discount}</p>
                <p><strong>Amount After Discount:</strong> Rs. {billDataForDisplay.amountAfterDiscount}</p>
                <p><strong>Cash Tendered:</strong> Rs. {billDataForDisplay.cashTendered}</p>
                <p><strong>Cash Change:</strong> Rs. {billDataForDisplay.cashChange}</p>


<div className='closebutton'><button className='buttonclose' onClick={() => setBillDataForDisplay(null)}>Close</button></div>
                
              </div>
            </div>
          )}




        </div>
      </main>
    </div>
  );
};

export default NewOrderDashboard;
