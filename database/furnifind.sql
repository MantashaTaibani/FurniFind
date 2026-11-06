-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 06, 2026 at 09:23 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `furnifind`
--

-- --------------------------------------------------------

--
-- Table structure for table `borrow_records`
--

CREATE TABLE `borrow_records` (
  `borrow_id` int(11) NOT NULL,
  `catalogue_id` int(11) DEFAULT NULL,
  `customer_name` varchar(100) DEFAULT NULL,
  `borrowed_date` date DEFAULT NULL,
  `returned_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `borrow_records`
--

INSERT INTO `borrow_records` (`borrow_id`, `catalogue_id`, `customer_name`, `borrowed_date`, `returned_date`) VALUES
(1, 1, 'Mantasha', '2026-10-17', '2026-10-17');

-- --------------------------------------------------------

--
-- Table structure for table `catalogues`
--

CREATE TABLE `catalogues` (
  `catalogue_id` int(11) NOT NULL,
  `company` varchar(100) DEFAULT NULL,
  `catalogue_name` varchar(150) DEFAULT NULL,
  `design` varchar(150) DEFAULT NULL,
  `category` varchar(100) DEFAULT NULL,
  `sr_no` int(11) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `total_stock` int(11) DEFAULT NULL,
  `rack_no` varchar(30) DEFAULT NULL,
  `stack_no` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `catalogues`
--

INSERT INTO `catalogues` (`catalogue_id`, `company`, `catalogue_name`, `design`, `category`, `sr_no`, `price`, `total_stock`, `rack_no`, `stack_no`) VALUES
(1, 'D-Decor', 'Royals', 'Flower', 'Curtain', 101, 500.00, 1, 'R1', 'S1'),
(2, 'Asian Paints', 'Truple', 'Lining', 'Sofa Cloth', 201, 600.00, 1, 'R5', 'S2'),
(3, 'FabriCare', 'Casa Botanica', 'Mix', 'Curtain', 301, 800.00, 2, 'R3', 'S4');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `borrow_records`
--
ALTER TABLE `borrow_records`
  ADD PRIMARY KEY (`borrow_id`),
  ADD KEY `catalogue_id` (`catalogue_id`);

--
-- Indexes for table `catalogues`
--
ALTER TABLE `catalogues`
  ADD PRIMARY KEY (`catalogue_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `borrow_records`
--
ALTER TABLE `borrow_records`
  MODIFY `borrow_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `catalogues`
--
ALTER TABLE `catalogues`
  MODIFY `catalogue_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `borrow_records`
--
ALTER TABLE `borrow_records`
  ADD CONSTRAINT `borrow_records_ibfk_1` FOREIGN KEY (`catalogue_id`) REFERENCES `catalogues` (`catalogue_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
