
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema superhero_sightings_test
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `superhero_sightings_test` ;

-- -----------------------------------------------------
-- Schema superhero_sightings_test
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `superhero_sightings_test` DEFAULT CHARACTER SET utf8 ;
USE `superhero_sightings_test` ;

DROP TABLE IF EXISTS `superhero_sightings_test`.`users` ;

CREATE TABLE IF NOT EXISTS `superhero_sightings_test`.`users` (
 `user_id` int(11) NOT NULL AUTO_INCREMENT,
 `username` varchar(20) NOT NULL,
 `password` varchar(100) NOT NULL,
 `enabled` tinyint(1) NOT NULL,
 PRIMARY KEY (`user_id`),
 KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

DROP TABLE IF EXISTS `superhero_sightings_test`.`authorities` ;

CREATE TABLE IF NOT EXISTS `authorities` (
 `user_id` int(11) NOT NULL,
 `authority` varchar(28) NOT NULL,
 KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `authorities`
 ADD CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

-- -----------------------------------------------------
-- Table `superhero_sightings_test`.`locations`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superhero_sightings_test`.`locations` ;

CREATE TABLE IF NOT EXISTS `superhero_sightings_test`.`locations` (
  `location_id` INT(11) NOT NULL AUTO_INCREMENT,
  `location_name` VARCHAR(45) NOT NULL,
  `location_description` TEXT NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `city` VARCHAR(30) NOT NULL,
  `state` VARCHAR(2) NOT NULL,
  `zip` VARCHAR(5) NOT NULL,
  `latitude` DOUBLE(9,6) NOT NULL,
  `longitude` DOUBLE(9,6) NOT NULL,
  PRIMARY KEY (`location_id`),
  UNIQUE INDEX `address_UNIQUE` (`address` ASC),
  UNIQUE INDEX `zip_UNIQUE` (`zip` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `superhero_sightings_test`.`organizations`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superhero_sightings_test`.`organizations` ;

CREATE TABLE IF NOT EXISTS `superhero_sightings_test`.`organizations` (
  `organization_id` INT(11) NOT NULL AUTO_INCREMENT,
  `organization_name` VARCHAR(45) NOT NULL,
  `organization_description` TEXT NOT NULL,
  `organization_contact` VARCHAR(45) NOT NULL,
  `location_id` INT(11) NOT NULL,
  PRIMARY KEY (`organization_id`),
  INDEX `fk_organizations_local` (`location_id` ASC),
  CONSTRAINT `fk_organizations_local`
    FOREIGN KEY (`location_id`)
    REFERENCES `superhero_sightings_test`.`locations` (`location_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `superhero_sightings_test`.`superheros`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superhero_sightings_test`.`superheros` ;

CREATE TABLE IF NOT EXISTS `superhero_sightings_test`.`superheros` (
  `super_id` INT(11) NOT NULL AUTO_INCREMENT,
  `superhero_name` VARCHAR(45) NOT NULL,
  `description` TEXT NOT NULL,
  `superpower` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`super_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `superhero_sightings_test`.`super_organization_affiliation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superhero_sightings_test`.`super_organization_affiliation` ;

CREATE TABLE IF NOT EXISTS `superhero_sightings_test`.`super_organization_affiliation` (
  `super_id` INT(11) NOT NULL,
  `organization_id` INT(11) NOT NULL,
  PRIMARY KEY (`super_id`, `organization_id`),
  INDEX `fk_soa_organization` (`organization_id` ASC),
  CONSTRAINT `fk_soa_organization`
    FOREIGN KEY (`organization_id`)
    REFERENCES `superhero_sightings_test`.`organizations` (`organization_id`),
  CONSTRAINT `fk_soa_superhero`
    FOREIGN KEY (`super_id`)
    REFERENCES `superhero_sightings_test`.`superheros` (`super_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `superhero_sightings_test`.`super_sightings`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superhero_sightings_test`.`super_sightings` ;

CREATE TABLE IF NOT EXISTS `superhero_sightings_test`.`super_sightings` (
  `sight_id` INT(11) NOT NULL AUTO_INCREMENT,
  `super_id` INT(11) NOT NULL,
  `location_id` INT(11) NOT NULL,
  `Sighting_date` DATETIME NOT NULL,
  PRIMARY KEY (`sight_id`),
  INDEX `fk_supersighting_superhero` (`super_id` ASC),
  INDEX `fk_supersighting_location` (`location_id` ASC),
  CONSTRAINT `fk_supersighting_location`
    FOREIGN KEY (`location_id`)
    REFERENCES `superhero_sightings_test`.`locations` (`location_id`),
  CONSTRAINT `fk_supersighting_superhero`
    FOREIGN KEY (`super_id`)
    REFERENCES `superhero_sightings_test`.`superheros` (`super_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;



SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
