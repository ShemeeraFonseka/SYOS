-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: May 25, 2025 at 10:44 AM
-- Server version: 8.0.31
-- PHP Version: 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `syosstore`
--

-- --------------------------------------------------------

--
-- Table structure for table `batch`
--

DROP TABLE IF EXISTS `batch`;
CREATE TABLE IF NOT EXISTS `batch` (
  `batchID` int NOT NULL AUTO_INCREMENT,
  `itemCode` varchar(10) NOT NULL,
  `dateOfPurchase` date NOT NULL,
  `quantityReceived` int NOT NULL,
  `expiryDate` date NOT NULL,
  `stockQuantity` int NOT NULL,
  PRIMARY KEY (`batchID`),
  KEY `fk_batch_item` (`itemCode`)
) ENGINE=MyISAM AUTO_INCREMENT=376 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `batch`
--

INSERT INTO `batch` (`batchID`, `itemCode`, `dateOfPurchase`, `quantityReceived`, `expiryDate`, `stockQuantity`) VALUES
(1, 'fru001', '2024-10-12', 44, '2025-01-01', 44),
(8, 'fru004', '2023-10-02', 20, '2025-10-07', 19),
(346, 'fru003', '2025-05-04', 30, '2025-07-12', 29),
(345, 'fru002', '2025-04-07', 50, '2025-06-26', 50),
(352, 'veg004', '2025-04-30', 66, '2025-06-03', 66),
(351, 'veg003', '2025-05-01', 50, '2025-06-01', 50),
(350, 'veg002', '2025-05-04', 60, '2025-05-30', 60),
(341, 'fru005', '2025-05-01', 58, '2025-06-20', 57),
(349, 'veg001', '2025-03-03', 36, '2025-04-10', 26),
(348, 'fru006', '2025-05-04', 2000, '2025-07-12', 39),
(353, 'veg005', '2025-05-05', 70, '2025-05-31', 70),
(354, 'veg006', '2025-04-07', 60, '2025-05-01', 60),
(355, 'dar001', '2025-05-04', 89, '2025-06-13', 87),
(356, 'dar002', '2025-05-04', 80, '2025-10-21', 80),
(357, 'dar003', '2025-02-11', 200, '2025-09-10', 200),
(358, 'dar004', '2025-03-11', 70, '2025-07-18', 70),
(359, 'dar005', '2025-04-15', 45, '2025-05-05', 45),
(360, 'dar006', '2025-04-29', 67, '2025-05-31', 67),
(361, 'dar001', '2025-05-04', 89, '2025-06-13', 89),
(362, 'dar002', '2025-05-04', 80, '2025-10-21', 80),
(363, 'dar003', '2025-02-11', 200, '2025-09-10', 200),
(364, 'dar004', '2025-03-11', 70, '2025-07-18', 70),
(365, 'dar005', '2025-04-15', 45, '2025-05-05', 45),
(366, 'dar006', '2025-04-29', 67, '2025-05-31', 67),
(367, 'fru001', '2024-10-10', 100, '2025-10-10', 100),
(368, 'fru001', '2024-10-10', 100, '2025-10-10', 100),
(369, 'fru001', '2024-10-10', 100, '2025-10-10', 100),
(370, 'fru001', '2024-10-10', 100, '2025-10-10', 100),
(371, 'fru001', '2024-10-10', 100, '2025-10-10', 100),
(372, 'veg004', '2025-05-02', 70, '2025-05-23', 70),
(373, 'fru001', '2025-04-30', 65, '2025-06-07', 65),
(374, 'fru006', '2025-04-27', 48, '2025-06-07', 48),
(375, 'dar002', '2024-07-30', 60, '2026-02-20', 60);

-- --------------------------------------------------------

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
CREATE TABLE IF NOT EXISTS `bill` (
  `billID` int NOT NULL AUTO_INCREMENT,
  `billSerialNumber` varchar(36) NOT NULL,
  `billDate` date NOT NULL,
  `transactionType` varchar(10) NOT NULL,
  `totalAmount` float NOT NULL,
  `discount` float NOT NULL,
  `cashTendered` float NOT NULL,
  `cashChange` float NOT NULL,
  `customerID` int NOT NULL,
  `amountAfterDiscount` float NOT NULL,
  PRIMARY KEY (`billID`)
) ENGINE=MyISAM AUTO_INCREMENT=840 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `bill`
--

INSERT INTO `bill` (`billID`, `billSerialNumber`, `billDate`, `transactionType`, `totalAmount`, `discount`, `cashTendered`, `cashChange`, `customerID`, `amountAfterDiscount`) VALUES
(839, 'e37ec89e-5f72-4fe4-ace4-3ed3a087a351', '2025-05-24', 'ONSITE', 420, 0, 800, 380, 8, 420),
(838, '877eadfd-116a-4b68-826e-3639bfabd810', '2025-05-24', 'ONLINE', 7000, 350, 8000, 650, 3, 6650),
(757, 'b9ea6a75-6d58-45d0-8ce0-444152124182', '2025-05-24', 'ONLINE', 3470, 0, 3470, 0, 3, 3470),
(756, '4bfdb73d-a1aa-4b55-bd33-7a1a78ada045', '2025-05-24', 'ONSITE', 1368, 0, 2000, 632, 8, 1368);

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
CREATE TABLE IF NOT EXISTS `item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `itemCode` text NOT NULL,
  `itemName` text NOT NULL,
  `pricePerUnit` float NOT NULL,
  `path` text NOT NULL,
  `category` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`id`, `itemCode`, `itemName`, `pricePerUnit`, `path`, `category`) VALUES
(1, 'fru001', 'Papaya 1.25kg', 235, 'http://localhost:8080/SYOS%20Backend/images/papaya.png', 'Fruits'),
(2, 'fru002', 'Mango 1kg', 570, 'http://localhost:8080/SYOS%20Backend/images/mango.png', 'Fruits'),
(3, 'fru003', 'Watermelon 2kg', 180, 'http://localhost:8080/SYOS%20Backend/images/watermelon.png', 'Fruits'),
(4, 'fru004', 'Apple 500g', 710, 'http://localhost:8080/SYOS%20Backend/images/apple.png', 'Fruits'),
(5, 'fru005', 'Grapes 500g', 1590, 'http://localhost:8080/SYOS%20Backend/images/grapes.png', 'Fruits'),
(6, 'fru006', 'Orange 600g', 990, 'http://localhost:8080/SYOS%20Backend/images/orange.png', 'Fruits'),
(7, 'veg001', 'Pumpkin 500g', 70, 'http://localhost:8080/SYOS%20Backend/images/pumpkin.png', 'Vegetables'),
(8, 'veg002', 'Carrot 500g', 225, 'http://localhost:8080/SYOS%20Backend/images/carrot.png', 'Vegetables'),
(9, 'veg003', 'Leeks 250g', 70, 'http://localhost:8080/SYOS%20Backend/images/leeks.png', 'Vegetables'),
(10, 'veg004', 'Tomato 500g', 70, 'http://localhost:8080/SYOS%20Backend/images/tomato.png', 'Vegetables'),
(11, 'veg005', 'Capcicum 250g', 145, 'http://localhost:8080/SYOS%20Backend/images/capcicum.png', 'Vegetables'),
(12, 'veg006', 'Cabbage 500g', 165, 'http://localhost:8080/SYOS%20Backend/images/cabbage.png', 'Vegetables'),
(13, 'dar001', 'Kotmale Curd 900g', 544, 'http://localhost:8080/SYOS%20Backend/images/curd.png', 'Dairy'),
(14, 'dar002', 'Puredale Kirithe 400g', 830, 'http://localhost:8080/SYOS%20Backend/images/puredel.png', 'Dairy'),
(15, 'dar003', 'Kotmale Ball Cheese 325g', 1485, 'http://localhost:8080/SYOS%20Backend/images/cheese.png', 'Dairy'),
(16, 'dar004', 'Ambewela Fresh Milk 1L', 550, 'http://localhost:8080/SYOS%20Backend/images/ambe.png', 'Dairy'),
(17, 'dar005', 'Cheese Spread 175g', 180, 'http://localhost:8080/SYOS%20Backend/images/spread.png', 'Dairy'),
(18, 'dar006', 'Happy Cow Cheese 120g', 448, 'http://localhost:8080/SYOS%20Backend/images/happy.png', 'Dairy');

-- --------------------------------------------------------

--
-- Table structure for table `onlineinventory`
--

DROP TABLE IF EXISTS `onlineinventory`;
CREATE TABLE IF NOT EXISTS `onlineinventory` (
  `onlineInventoryID` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `date` timestamp NOT NULL,
  `quantitySold` int NOT NULL,
  `itemCode` varchar(10) NOT NULL,
  `batchID` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `orderID` varchar(100) NOT NULL,
  PRIMARY KEY (`onlineInventoryID`),
  KEY `fk_batch_item` (`itemCode`),
  KEY `batchID` (`batchID`),
  KEY `orderID` (`orderID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `onlineinventory`
--

INSERT INTO `onlineinventory` (`onlineInventoryID`, `date`, `quantitySold`, `itemCode`, `batchID`, `orderID`) VALUES
('b7d8bd69-f7e6-4072-bc5d-fec9d3c16858', '2025-05-24 10:40:38', 1, 'fru006', '348', '24b49ba5-5df9-45db-9638-0ddc3f0f3521'),
('1d74f4d4-e88e-40a0-b016-a4626c0e4786', '2025-05-24 10:40:38', 1, 'fru005', '341', '7e632fe9-b371-4d19-aac4-44462b14b6ac'),
('293f3e50-33c4-4a5f-ba5a-3a87c90cb5a8', '2025-05-24 10:40:38', 1, 'fru004', '8', '62ae6b88-3d30-48c3-ae25-1c3eac6269b3'),
('bc2ad633-5ab5-4568-872e-6ecb602dfce7', '2025-05-24 10:40:38', 1, 'fru003', '346', '7936dd7a-d6f2-4592-85e4-453a2df0e6fc');

-- --------------------------------------------------------

--
-- Table structure for table `orderitem`
--

DROP TABLE IF EXISTS `orderitem`;
CREATE TABLE IF NOT EXISTS `orderitem` (
  `orderID` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `itemCode` varchar(20) NOT NULL,
  `quantity` int NOT NULL,
  `totalPrice` float NOT NULL,
  `billSerialNumber` varchar(36) NOT NULL,
  PRIMARY KEY (`orderID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `orderitem`
--

INSERT INTO `orderitem` (`orderID`, `itemCode`, `quantity`, `totalPrice`, `billSerialNumber`) VALUES
('e24378f8-2a5a-426e-8847-5bcf798aeee0', 'veg001', 6, 420, 'e37ec89e-5f72-4fe4-ace4-3ed3a087a351'),
('62ae6b88-3d30-48c3-ae25-1c3eac6269b3', 'fru004', 1, 710, 'b9ea6a75-6d58-45d0-8ce0-444152124182'),
('24b49ba5-5df9-45db-9638-0ddc3f0f3521', 'fru006', 1, 990, 'b9ea6a75-6d58-45d0-8ce0-444152124182'),
('7936dd7a-d6f2-4592-85e4-453a2df0e6fc', 'fru003', 1, 180, 'b9ea6a75-6d58-45d0-8ce0-444152124182'),
('7e632fe9-b371-4d19-aac4-44462b14b6ac', 'fru005', 1, 1590, 'b9ea6a75-6d58-45d0-8ce0-444152124182'),
('8676534c-463d-4a93-9d29-a8819b6ada16', 'dar001', 2, 1088, '4bfdb73d-a1aa-4b55-bd33-7a1a78ada045'),
('a710cc2e-c659-4b9e-9dbe-21accf667f02', 'veg001', 4, 280, '4bfdb73d-a1aa-4b55-bd33-7a1a78ada045');

-- --------------------------------------------------------

--
-- Table structure for table `shelfinventory`
--

DROP TABLE IF EXISTS `shelfinventory`;
CREATE TABLE IF NOT EXISTS `shelfinventory` (
  `shelfInventoryID` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `date` timestamp NOT NULL,
  `quantitySold` int NOT NULL,
  `itemCode` varchar(10) NOT NULL,
  `batchID` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `orderID` varchar(100) NOT NULL,
  PRIMARY KEY (`shelfInventoryID`),
  KEY `fk_batch_item` (`itemCode`),
  KEY `batchID` (`batchID`),
  KEY `orderID` (`orderID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `shelfinventory`
--

INSERT INTO `shelfinventory` (`shelfInventoryID`, `date`, `quantitySold`, `itemCode`, `batchID`, `orderID`) VALUES
('b6faf0bd-eef2-4ea5-a117-f5a7271e4efe', '2025-05-24 16:06:43', 6, 'veg001', '349', 'e24378f8-2a5a-426e-8847-5bcf798aeee0'),
('50cf1394-bb6e-4d98-8610-4a2f9920152d', '2025-05-24 10:12:45', 4, 'veg001', '349', 'a710cc2e-c659-4b9e-9dbe-21accf667f02'),
('c634603b-0484-4d73-859e-852f7eca1e8f', '2025-05-24 10:12:45', 2, 'dar001', '355', '8676534c-463d-4a93-9d29-a8819b6ada16');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `userID` int NOT NULL AUTO_INCREMENT,
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` text NOT NULL,
  `phone` text NOT NULL,
  `address` text NOT NULL,
  `username` text NOT NULL,
  `password` text NOT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userID`, `name`, `email`, `phone`, `address`, `username`, `password`) VALUES
(3, 'shemeera', 'ss@gmail.com', '0774563553', 'Colombo', 'shsh', 'ssss'),
(11, 'amali', 'amali@gmail.com', '0774645433', 'Colombo', 'amali', 'amali123'),
(10, 'Pumpkin 500g', 'shemeerafonseka@gmail.com', '0774979282', 'Negombo', 'CODCSD212F-015', '789'),
(8, 'admin', 'syos@gmail.com', '0774545635', 'Colombo', 'admin', 'admin');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
