CREATE TABLE customer (
	customerId int(11) NOT NULL AUTO_INCREMENT,
	customerName varchar(30) NOT NULL,
    customerAddress varchar(100) NOT NULL,
    customerEmail varchar(50) NOT NULL,
    customerPhone varchar(16) NOT NULL,
    customerDOB varchar(10) NOT NULL, 
	PRIMARY KEY(customerId)
);

CREATE table  account (
	accountId int(11) NOT NULL AUTO_INCREMENT,
	accountNumber int(11) AUTO_INCREMENT,
	accountBalance int(30) default 0,
    accountLimit int(11) NOT NULL default 500,
    accountActive int(1) NOT NULL default 1,
	PRIMARY KEY (accountID)
);


CREATE TABLE customerAccount (
	customerAccountId int(11) NOT NULL AUTO_INCREMENT  PRIMARY KEY,
	customerID		int(11) NOT NULL,
	accountID		int(11) NOT NULL,
	FOREIGN KEY (customerId) REFERENCES customer (customerId),
	FOREIGN KEY (accountId) REFERENCES account (accountId)
);

