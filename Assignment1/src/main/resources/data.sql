INSERT INTO customer(customerName, customerAddress, customerEmail,customerPhone, customerDOB) 
            VALUES 	('John Doe', '68 Bovoyage Bulvar Dublin','John.doe@email.com'    ,'0012003456765','01-01-2000'),
                    ('Peter White', '24 Chedar Avenue Dublin','Peter.white@email.com','0012098345098','24-03-1960'),
                    ('Jane Silver', '123 Green Street Dublin','Jane.silver@email.com','0012083459384','06-08-1980');

INSERT INTO account(accountNumber, accountLimit,accountBalance) VALUES 
                                                (100001, 500,  110000);														
INSERT INTO account(accountLimit,accountBalance) VALUES (500, 1100000),
									                    (600,  379800);

INSERT INTO customerAccount(customerId, accountId) VALUES (1,1),
												      (2,2),
												      (3,3);
