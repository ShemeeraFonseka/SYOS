import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';


const OnsiteSalesReport = () => {
  const [reportData, setReportData] = useState([]);
  const [error, setError] = useState(null);

  const location = useLocation();
  const currentPath = location.pathname;

  useEffect(() => {
    fetch('http://localhost:8080/SYOS%20Backend/onsitesalesreport')
      .then((response) => {
        if (!response.ok) {
          throw new Error('Failed to fetch sales report');
        }
        return response.json();
      })
      .then((data) => setReportData(data))
      .catch((error) => setError(error.message));
  }, []);

  // Calculate totals
  const totalQuantity = reportData.reduce((sum, item) => sum + item.totalQuantity, 0);
  const totalRevenue = reportData.reduce((sum, item) => sum + item.totalRevenue, 0);

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
        <h2>Today's Onsite Sales Report</h2>
        {error ? (
          <p>{error}</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Item Name</th>
                <th>Item Code</th>
                <th>Quantity Sold</th>
                <th>Total Revenue</th>
              </tr>
            </thead>
            <tbody>
              {reportData.map((entry, index) => (
                <tr key={index}>
                  <td>{entry.itemName}</td>
                  <td>{entry.itemCode}</td>
                  <td>{entry.totalQuantity}</td>
                  <td>${entry.totalRevenue.toFixed(2)}</td>
                </tr>
              ))}
              {reportData.length === 0 ? (
                <tr>
                  <td colSpan="4">No records found for today.</td>
                </tr>
              ) : (
                <tr style={{ fontWeight: 'bold', backgroundColor: '#f0f0f0' }}>
                  <td colSpan="2">Total</td>
                  <td>{totalQuantity}</td>
                  <td>${totalRevenue.toFixed(2)}</td>
                </tr>
              )}
            </tbody>
          </table>
        )}
      </main>
    </div>
  );
};

export default OnsiteSalesReport;
