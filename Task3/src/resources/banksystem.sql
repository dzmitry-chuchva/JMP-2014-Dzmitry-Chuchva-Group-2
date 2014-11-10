DROP DATABASE IF EXISTS `banksystem`;

CREATE DATABASE `banksystem`;

CREATE TABLE `banksystem`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `role_id` INT NULL,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `login` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  PRIMARY KEY (`user_id`));
  
  CREATE TABLE `banksystem`.`role` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(45) NULL,
  PRIMARY KEY (`role_id`));

  CREATE TABLE `banksystem`.`currency` (
  `currency_id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(45) NULL,
  PRIMARY KEY (`currency_id`));

  CREATE TABLE `banksystem`.`conversion` (
  `from_id` INT NOT NULL,
  `to_id` INT NOT NULL,
  `rate` DOUBLE NULL,
  PRIMARY KEY (`from_id`, `to_id`));

  CREATE TABLE `banksystem`.`account` (
  `account_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL,
  `currency_id` INT NULL,
  `value` DOUBLE NULL,
  PRIMARY KEY (`account_id`));

INSERT INTO `banksystem`.`role` (`role`) VALUES ('admin');
INSERT INTO `banksystem`.`role` (`role`) VALUES ('customer');

INSERT INTO `banksystem`.`user` (`role_id`, `first_name`, `last_name`, `login`, `password`) VALUES ('1', 'kiryl', 'klachkou', 'admin', 'admin');

INSERT INTO `banksystem`.`currency` (`type`) VALUES ('USD');
INSERT INTO `banksystem`.`currency` (`type`) VALUES ('EUR');
INSERT INTO `banksystem`.`currency` (`type`) VALUES ('BYR');

INSERT INTO `banksystem`.`conversion` (`from_id`, `to_id`, `rate`) VALUES ('1', '1', '1');
INSERT INTO `banksystem`.`conversion` (`from_id`, `to_id`, `rate`) VALUES ('1', '2', '0.8');
INSERT INTO `banksystem`.`conversion` (`from_id`, `to_id`, `rate`) VALUES ('1', '3', '10740');
INSERT INTO `banksystem`.`conversion` (`from_id`, `to_id`, `rate`) VALUES ('2', '1', '1.25');
INSERT INTO `banksystem`.`conversion` (`from_id`, `to_id`, `rate`) VALUES ('2', '2', '1');
INSERT INTO `banksystem`.`conversion` (`from_id`, `to_id`, `rate`) VALUES ('2', '3', '13440');
INSERT INTO `banksystem`.`conversion` (`from_id`, `to_id`, `rate`) VALUES ('3', '1', '0.00009');
INSERT INTO `banksystem`.`conversion` (`from_id`, `to_id`, `rate`) VALUES ('3', '2', '0.00007');
INSERT INTO `banksystem`.`conversion` (`from_id`, `to_id`, `rate`) VALUES ('3', '3', '1');
