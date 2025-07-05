import React from 'react'
import Navbar from './navbar/Navbar'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import Home from './home/Home'
import About from './about/About'
import Contact from './contact/Contact'
import Items from './items/Items'
import Checkout from './checkout/Checkout'
import { CartProvider } from './CartContext'

import NewOrderDashboard from './dashboard/NewOrderDashboard'
import BatchDashboard from './dashboard/BatchDashboard'
import SalesReport from './dashboard/SalesReport'
import OnsiteSalesReport from './dashboard/OnsiteSalesReport'
import OnlineReshelveReport from './dashboard/OnlineReshelveReport'
import ReorderLevelReport from './dashboard/ReorderLevelReport'
import StockReport from './dashboard/StockReport'
import BillReport from './dashboard/BillReport'
import OnsiteReshelveReport from './dashboard/OnsiteReshelveReport'

const App = () => {
  return (
    <CartProvider>
      <Router>
        <Navbar />
        <Routes>
          <Route path='/' element={<Home />} />
          <Route path='/about' element={<About />} />
          <Route path='/contact' element={<Contact />} />
          <Route path='/products' element={<Items />} />
          <Route path='/checkout' element={<Checkout />} />
          <Route path='/neworderdashboard' element={<NewOrderDashboard />} />
          <Route path='/batchdashboard' element={<BatchDashboard />} />
          <Route path='/salesreport' element={<SalesReport />} />
          <Route path='/onsitesalesreport' element={<OnsiteSalesReport />} />
          <Route path='/onlinereshelvereport' element={<OnlineReshelveReport />} />
          <Route path='/onsitereshelvereport' element={<OnsiteReshelveReport />} />
          <Route path='/reorderlevelreport' element={<ReorderLevelReport />} />
          <Route path='/stockreport' element={<StockReport />} />
          <Route path='/billreport' element={<BillReport />} />


        </Routes>

      </Router>
    </CartProvider>
  )
}

export default App