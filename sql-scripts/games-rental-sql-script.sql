DROP DATABASE  IF EXISTS `game_rental_final_project`;

CREATE DATABASE  IF NOT EXISTS `game_rental_final_project`;
USE `game_rental_final_project`;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` char(80) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--
-- NOTE: The passwords are encrypted using BCrypt
--
-- A generation tool is avail at: http://www.luv2code.com/generate-bcrypt-password
--
-- Default passwords here are: fun123
--

INSERT INTO `user` (username,password,first_name,last_name,email)
VALUES 
('test','$2a$04$w9J38qoGQ1ofLTnF7rI5..WNNFGMc7Mqr2rBGYMjWqy0b4JxXaYf.','Test','Test','test@gmail.com'),
('admin','$2a$04$q2YRXxTjxMfTVwK9QosW2.GvtwGO8Ivhb3b9bRkuFbfTy.IsqDvwu','Admin','Admin','admin@gmail.com'),
('ripan','$2a$04$zfEIsh0g6fCuOpD1N.UON.AxWc2.QCcBfFAlQQDkMOiHgPCAk17pe','Ripan','Halder','ripan@gmail.com');

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roles`
--

INSERT INTO `role` (name)
VALUES 
('ROLE_USER'),('ROLE_ADMIN');

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  
  PRIMARY KEY (`user_id`,`role_id`),
  
  KEY `FK_ROLE_idx` (`role_id`),
  
  CONSTRAINT `FK_USER_05` FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_ROLE` FOREIGN KEY (`role_id`) 
  REFERENCES `role` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;

--
-- Dumping data for table `users_roles`
--

INSERT INTO `users_roles` (user_id,role_id)
VALUES 
(1, 1),
(2, 1),
(2, 2),
(3, 1),
(3, 2)

--INSERT INTO Category (title) VALUES ('Action'),('Adventure'),('Simulation'),('Real Time Strategy (RTS)'),('Puzzle'),('Massively Mutiplayer Online (MMO)'),('Stealth Shooter'),('Sports'),('Role-Playing (RPG)'),('Educational');
--INSERT INTO Platform (title) VALUES ('X-BOX One'),('PS4'),('Nintendo'),('PC');

--Assassins - http://tiny.cc/jxce5y
--Fifa19 - http://tiny.cc/7zde5y
--PubG - http://tiny.cc/qc8e5y