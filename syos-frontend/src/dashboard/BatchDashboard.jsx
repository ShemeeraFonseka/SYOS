
import './Dashboard.css';
import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';


const BatchDashboard = () => {
    const [formData, setFormData] = useState({
        itemCode: '',
        dateOfPurchase: '',
        quantityReceived: '',
        expiryDate: '',
        stockQuantity: ''
    });

    const location = useLocation();
    const currentPath = location.pathname;

    const [message, setMessage] = useState('');

    const [availableItems, setAvailableItems] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/SYOS%20Backend/items?action=getAll')
            .then((res) => res.json())
            .then((data) => {
                console.log("Fetched Items:", data);
                setAvailableItems(data);
            })
            .catch((error) => console.error("Error fetching items:", error));
    }, []);




    const handleChange = (e) => {
        setFormData(prev => ({
            ...prev,
            [e.target.name]: e.target.value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const url = 'http://localhost:8080/SYOS%20Backend/addbatch';

        const formBody = new URLSearchParams();
        formBody.append('itemCode', formData.itemCode);
        formBody.append('dateOfPurchase', formData.dateOfPurchase);
        formBody.append('quantityReceived', formData.quantityReceived);
        formBody.append('expiryDate', formData.expiryDate);
        formBody.append('stockQuantity', formData.stockQuantity);

        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: formBody
            });

            const result = await response.json();

            if (response.ok) {
                setMessage(result.message || 'Batch submitted successfully.');
                setFormData({
                    itemCode: '',
                    dateOfPurchase: '',
                    quantityReceived: '',
                    expiryDate: '',
                    stockQuantity: ''
                });
                alert ('Batch Inserted Successfully')
            } else {
                setMessage('Error: ' + (result.error || 'Unknown error'));
            }
        } catch (error) {
            console.error('Request error:', error);
            setMessage('An error occurred while submitting the batch.');
        }
    };


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
            <main className="main-content">
                <div className="form-section">
                    <h2>Create New Batch</h2>
                    <form onSubmit={handleSubmit}>

                        <label>Item Code:</label>
                        <select className='select-input' name="itemCode" value={formData.itemCode} onChange={handleChange} required>
                            <option value="" disabled>Select an item</option>
                            {availableItems.map((item) => (
                                <option key={item.itemCode} value={item.itemCode}>
                                    {item.itemCode} - {item.itemName}
                                </option>
                            ))}
                        </select>


                        <label>Date of Purchase:</label>
                        <input className='item-input' type="date" name="dateOfPurchase" value={formData.dateOfPurchase} onChange={handleChange} required />

                        <label>Quantity Received:</label>
                        <input className='item-input' type="number" name="quantityReceived" value={formData.quantityReceived} onChange={handleChange} required />


                        <label>Expiry Date:</label>
                        <input className='item-input' type="date" name="expiryDate" value={formData.expiryDate} onChange={handleChange} required />


                        <label>Stock Quantity:</label>
                        <input className='item-input' type="number" name="stockQuantity" value={formData.stockQuantity} onChange={handleChange} required />



                        <div className='buttoncontainer'>
                            <button className='orderbutton' type="submit">Add Batch</button>

                        </div>
                    </form>

                </div>
            </main>
        </div>

    );
}

export default BatchDashboard