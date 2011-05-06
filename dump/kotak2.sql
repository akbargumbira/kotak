-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 06, 2011 at 12:16 PM
-- Server version: 5.1.41
-- PHP Version: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `kotak`
--

-- --------------------------------------------------------

--
-- Table structure for table `revision_repo`
--

CREATE TABLE IF NOT EXISTS `revision_repo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `rev_num` int(11) NOT NULL,
  `structure` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_2` (`user_id`,`rev_num`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=39 ;

--
-- Dumping data for table `revision_repo`
--

INSERT INTO `revision_repo` (`id`, `user_id`, `rev_num`, `structure`) VALUES
(16, 2, 1, '{"name":"rezanachmad@gmail.com","lastModified":"May 6, 2011 6:39:52 AM","isFile":false,"files":[]}'),
(23, 2, 2, '{"name":"rezanachmad@gmail.com","lastModified":"May 6, 2011 9:09:00 AM","isFile":false,"files":[{"name":"hai","lastModified":"May 6, 2011 9:09:00 AM","isFile":false,"files":[{"name":"hehe.txt","lastModified":"May 6, 2011 9:09:00 AM","isFile":true,"files":[]}]}]}'),
(24, 2, 3, '{"name":"rezanachmad@gmail.com","lastModified":"May 6, 2011 9:09:33 AM","isFile":false,"files":[{"name":"hai","lastModified":"May 6, 2011 9:09:00 AM","isFile":false,"files":[{"name":"hehe.txt","lastModified":"May 6, 2011 9:09:00 AM","isFile":true,"files":[]}]},{"name":"rezan","lastModified":"May 6, 2011 9:09:33 AM","isFile":false,"files":[{"name":"achmad","lastModified":"May 6, 2011 9:09:33 AM","isFile":false,"files":[{"name":"video.txt","lastModified":"May 6, 2011 9:09:33 AM","isFile":true,"files":[]}]}]}]}'),
(25, 2, 4, '{"name":"rezanachmad@gmail.com","lastModified":"May 6, 2011 9:09:56 AM","isFile":false,"files":[{"name":"hai","lastModified":"May 6, 2011 9:09:00 AM","isFile":false,"files":[{"name":"hehe.txt","lastModified":"May 6, 2011 9:09:00 AM","isFile":true,"files":[]}]},{"name":"rezan","lastModified":"May 6, 2011 9:09:56 AM","isFile":false,"files":[{"name":"achmad","lastModified":"May 6, 2011 9:09:56 AM","isFile":false,"files":[{"name":"video.txt","lastModified":"May 6, 2011 9:09:56 AM","isFile":true,"files":[]}]}]}]}'),
(26, 2, 5, '{"name":"rezanachmad@gmail.com","lastModified":"May 6, 2011 9:10:30 AM","isFile":false,"files":[{"name":"hai","lastModified":"May 6, 2011 9:09:00 AM","isFile":false,"files":[{"name":"hehe.txt","lastModified":"May 6, 2011 9:09:00 AM","isFile":true,"files":[]}]},{"name":"rezan","lastModified":"May 6, 2011 9:10:30 AM","isFile":false,"files":[{"name":"achmad","lastModified":"May 6, 2011 9:10:30 AM","isFile":false,"files":[{"name":"video.txt","lastModified":"May 6, 2011 9:09:56 AM","isFile":true,"files":[]},{"name":"video","lastModified":"May 6, 2011 9:10:30 AM","isFile":false,"files":[{"name":"video.txt","lastModified":"May 6, 2011 9:10:30 AM","isFile":true,"files":[]}]}]}]}]}'),
(34, 2, 6, '{"name":"rezanachmad@gmail.com","lastModified":"May 6, 2011 10:32:01 AM","isFile":false,"files":[{"name":"rezan","lastModified":"May 6, 2011 9:10:30 AM","isFile":false,"files":[{"name":"achmad","lastModified":"May 6, 2011 9:10:30 AM","isFile":false,"files":[{"name":"video.txt","lastModified":"May 6, 2011 9:09:56 AM","isFile":true,"files":[]},{"name":"video","lastModified":"May 6, 2011 9:10:30 AM","isFile":false,"files":[{"name":"video.txt","lastModified":"May 6, 2011 9:10:30 AM","isFile":true,"files":[]}]}]}]}]}'),
(35, 2, 7, '{"name":"rezanachmad@gmail.com","lastModified":"May 6, 2011 10:32:01 AM","isFile":false,"files":[{"name":"rezan","lastModified":"May 6, 2011 9:10:30 AM","isFile":false,"files":[{"name":"achmad","lastModified":"May 6, 2011 9:10:30 AM","isFile":false,"files":[{"name":"video.txt","lastModified":"May 6, 2011 9:09:56 AM","isFile":true,"files":[]},{"name":"video","lastModified":"May 6, 2011 9:10:30 AM","isFile":false,"files":[{"name":"video.txt","lastModified":"May 6, 2011 9:10:30 AM","isFile":true,"files":[]}]}]}]},{"name":"haha","lastModified":"May 6, 2011 10:32:01 AM","isFile":false,"files":[{"name":"abc.txt","lastModified":"May 6, 2011 10:32:01 AM","isFile":true,"files":[]}]}]}'),
(36, 2, 8, '{"name":"rezanachmad@gmail.com","lastModified":"May 6, 2011 11:02:04 AM","isFile":false,"files":[{"name":"haha","lastModified":"May 6, 2011 10:32:01 AM","isFile":false,"files":[{"name":"abc.txt","lastModified":"May 6, 2011 10:32:01 AM","isFile":true,"files":[]}]}]}'),
(37, 2, 9, '{"name":"rezanachmad@gmail.com","lastModified":"May 6, 2011 11:07:58 AM","isFile":false,"files":[{"name":"haha","lastModified":"May 6, 2011 11:07:58 AM","isFile":false,"files":[]}]}'),
(38, 2, 10, '{"name":"rezanachmad@gmail.com","lastModified":"May 6, 2011 11:08:20 AM","isFile":false,"files":[]}');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `email`, `password`) VALUES
(2, 'rezanachmad@gmail.com', 'rezan');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `revision_repo`
--
ALTER TABLE `revision_repo`
  ADD CONSTRAINT `revision_repo_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
