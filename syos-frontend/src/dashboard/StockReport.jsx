import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';


const StockReport = () => {
    const location = useLocation();
  const currentPath = location.pathname;

  const [reportData, setReportData] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    fetch('http://localhost:8080/SYOS%20Backend/stockreport')
      .then((res) => {
        if (!res.ok) throw new Error('Failed to fetch stock report');
        return res.json();
      })
      .then((data) => setReportData(data))
      .catch((err) => setError(err.message));
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
        <h2>Stock Report</h2>
        {error ? (
          <p>{error}</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Item Code</th>
                <th>Date of Purchase</th>
                <th>Quantity Received</th>
                <th>Expiry Date</th>
              </tr>
            </thead>
            <tbody>
              {reportData.map((entry, index) => (
                <tr key={index}>
                  <td>{entry.itemCode}</td>
                  <td>{entry.dateOfPurchase}</td>
                  <td>{entry.quantityReceived}</td>
                  <td>{entry.expiryDate}</td>
                </tr>
              ))}
              {reportData.length === 0 && (
                <tr>
                  <td colSpan="4">No stock data available.</td>
                </tr>
              )}
            </tbody>
          </table>
        )}
      </main>
    </div>
  );
};

export default StockReport;
