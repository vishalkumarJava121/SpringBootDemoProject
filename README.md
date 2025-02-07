# SpringBootDemoProject
For SpringBootDemoProject

# run this query if you are using H2 DB

CREATE TABLE Transactions (

transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
account_id INTEGER ,
amount Double,
timestamp DATE,
type VARCHAR(20),
FOREIGN KEY (account_id)
REFERENCES Accounts(account_id)
);



Create table Accounts (
account_id BIGINT AUTO_INCREMENT PRIMARY KEY,
aadhar_no varchar(50),
balance Integer,
account_holder_name varchar(250),
account_number varchar(500),
account_creation_Date Date
);

