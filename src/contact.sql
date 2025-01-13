CREATE DATABASE IF NOT EXISTS contact_management;

USE contact_management;

CREATE TABLE IF NOT EXISTS contacts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) ,
    phoneNumber VARCHAR(15) NOT NULL UNIQUE
);
SELECT * from contacts;