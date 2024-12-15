CREATE TABLE users (
    uid SERIAL PRIMARY KEY,
    username TEXT NOT NULL,
    login TEXT NOT NULL,
    password TEXT NOT NULL,
    balance NUMERIC(10, 2) DEFAULT 0
);

CREATE TABLE transfers(
	transfer_id SERIAL PRIMARY KEY,
	sender_id INTEGER NOT NULL,
	receiver_id INTEGER NOT NULL,
	amount NUMERIC(10, 2) NOT NULL,
	transfer_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

	CONSTRAINT fk_sender
		FOREIGN KEY (sender_id) REFERENCES users(uid)
		ON DELETE CASCADE,
	CONSTRAINT fk_receiver
		FOREIGN KEY (receiver_id) REFERENCES users(uid)
		ON DELETE CASCADE,
	 CONSTRAINT chk_amount_positive
        CHECK (amount > 0)
);


INSERT INTO users (username, login, password, balance)
VALUES
('dorajn', 'login123', '$2a$10$ZnWfGL4b4CuyghN2eeJF.uwvcK7xltEvc3QcTfwLcfrTnzYgzPiC6', 0);

select * from users;
select * from transfers

delete from users;
drop table users;
drop table transfers;
