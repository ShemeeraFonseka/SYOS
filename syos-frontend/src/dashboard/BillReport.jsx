import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Dashboard.css'; // You can define shared styles for report pages
import { useLocation } from 'react-router-dom';


const BillReport = () => {
    const [bills, setBills] = useState([]);
    const [loading, setLoading] = useState(true);

    const location = useLocation();
  const currentPath = location.pathname;

    useEffect(() => {
        axios.get('http://localhost:8080/SYOS%20Backend/billreport')
            .then(response => {
                setBills(response.data);
                setLoading(false);
            })
            .catch(error => {
                console.error("Error fetching bill report:", error);
                setLoading(false);
            });
    }, []);

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
                <h2>Bill Report</h2>
                {loading ? (
                    <p>Loading...</p>
                ) : bills.length === 0 ? (
                    <p>No records found.</p>
                ) : (
                    <table>
                        <thead>
                            <tr>
                                <th>Bill Serial No</th>
                                <th>Bill Date</th>
                                <th>Transaction Type</th>
                                <th>Total Amount</th>
                                <th>Discount</th>
                                <th>Cash Tendered</th>
                                <th>Cash Change</th>
                                <th>Customer ID</th>
                            </tr>
                        </thead>
                        <tbody>
                            {bills.map((bill, index) => (
                                <tr key={index}>
                                    <td>{bill.billID}</td>
                                    <td>{bill.billDate}</td>
                                    <td>{bill.transactionType}</td>
                                    <td>{bill.totalAmount}</td>
                                    <td>{bill.discount}</td>
                                    <td>{bill.cashTendered}</td>
                                    <td>{bill.cashChange}</td>
                                    <td>{bill.customerID}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                )}
            </main>
        </div>
    );
};

export default BillReport;
