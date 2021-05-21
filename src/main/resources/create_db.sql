
CREATE TABLE User (
                      email VARCHAR(100) NOT NULL,
                      first_name VARCHAR(100),
                      last_name VARCHAR(100),
                      password VARCHAR(100) NOT NULL,
                      bank_account VARCHAR(100),
                      balance DECIMAL(12,4) DEFAULT 0,
                      PRIMARY KEY (email)
);


CREATE TABLE Transfer (
                          id INT AUTO_INCREMENT NOT NULL,
                          amount DECIMAL(12,4) NOT NULL,
                          rate DECIMAL(6,4) NOT NULL,
                          description VARCHAR(1000),
                          bank_account VARCHAR(100),
                          date DATETIME NOT NULL,
                          user_email VARCHAR(100) NOT NULL,
                          buddy_email VARCHAR(100),
                          PRIMARY KEY (id)
);


CREATE TABLE Relationship (
                              user_email VARCHAR(100) NOT NULL,
                              buddy_email VARCHAR(100) NOT NULL,
                              PRIMARY KEY (user_email, buddy_email)
);


ALTER TABLE Relationship ADD CONSTRAINT user_relationship_fk
    FOREIGN KEY (user_email)
        REFERENCES User (email)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION;

ALTER TABLE Relationship ADD CONSTRAINT user_relationship_fk1
    FOREIGN KEY (buddy_email)
        REFERENCES User (email)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION;

ALTER TABLE Transfer ADD CONSTRAINT user_transfer_fk
    FOREIGN KEY (user_email)
        REFERENCES User (email)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION;

ALTER TABLE Transfer ADD CONSTRAINT user_transfer_fk1
    FOREIGN KEY (buddy_email)
        REFERENCES User (email)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION;