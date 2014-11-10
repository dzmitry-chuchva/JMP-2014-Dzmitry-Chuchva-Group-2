DROP DATABASE IF EXISTS `banksystem_test`;

CREATE DATABASE `banksystem_test`;

CREATE TABLE `banksystem_test`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `role_id` INT NULL,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `login` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  PRIMARY KEY (`user_id`));
  
  CREATE TABLE `banksystem_test`.`role` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(45) NULL,
  PRIMARY KEY (`role_id`));

  CREATE TABLE `banksystem_test`.`currency` (
  `currency_id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(45) NULL,
  PRIMARY KEY (`currency_id`));

  CREATE TABLE `banksystem_test`.`conversion` (
  `from_id` INT NOT NULL,
  `to_id` INT NOT NULL,
  `rate` DOUBLE NULL,
  PRIMARY KEY (`from_id`, `to_id`));

  CREATE TABLE `banksystem_test`.`account` (
  `account_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL,
  `currency_id` INT NULL,
  `value` DOUBLE NULL,
  PRIMARY KEY (`account_id`));