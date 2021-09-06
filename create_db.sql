
CREATE TABLE User (
                      id INT AUTO_INCREMENT NOT NULL,
                      email VARCHAR(100) NOT NULL,
                      first_name VARCHAR(100),
                      last_name VARCHAR(100),
                      password VARCHAR(100) NOT NULL,
                      bank_account VARCHAR(100),
                      balance DECIMAL(10,2) DEFAULT 0,
                      PRIMARY KEY (id)
);


CREATE UNIQUE INDEX user_unique_email
    ON User
        ( email );

CREATE TABLE Bank_Operation (
                                id INT AUTO_INCREMENT NOT NULL,
                                bank_account VARCHAR(100) NOT NULL,
                                amount DECIMAL(10,2) NOT NULL,
                                description VARCHAR(1000),
                                date DATETIME NOT NULL,
                                type VARCHAR(100) NOT NULL,
                                user_id INT NOT NULL,
                                PRIMARY KEY (id)
);


CREATE TABLE Transfer (
                          id INT AUTO_INCREMENT NOT NULL,
                          amount DECIMAL(10,2) NOT NULL,
                          rate DECIMAL(4,2) NOT NULL,
                          description VARCHAR(1000),
                          date DATETIME NOT NULL,
                          user_id INT NOT NULL,
                          buddy_id INT,
                          PRIMARY KEY (id)
);


CREATE TABLE Relationship (
                              user_id INT NOT NULL,
                              buddy_id INT NOT NULL,
                              PRIMARY KEY (user_id, buddy_id)
);


ALTER TABLE Relationship ADD CONSTRAINT user_relationship_fk
    FOREIGN KEY (user_id)
        REFERENCES User (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION;

ALTER TABLE Relationship ADD CONSTRAINT user_relationship_fk1
    FOREIGN KEY (buddy_id)
        REFERENCES User (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION;

ALTER TABLE Transfer ADD CONSTRAINT user_transfer_fk
    FOREIGN KEY (user_id)
        REFERENCES User (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION;

ALTER TABLE Transfer ADD CONSTRAINT user_transfer_fk1
    FOREIGN KEY (buddy_id)
        REFERENCES User (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION;

ALTER TABLE Bank_Operation ADD CONSTRAINT user_bank_operation_fk
    FOREIGN KEY (user_id)
        REFERENCES User (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION;
