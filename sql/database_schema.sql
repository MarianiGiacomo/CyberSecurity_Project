CREATE TABLE person (
email VARCHAR(20) NOT NULL PRIMARY KEY,
password VARCHAR(50)
);

CREATE TABLE forum (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    text varchar(200)
);
