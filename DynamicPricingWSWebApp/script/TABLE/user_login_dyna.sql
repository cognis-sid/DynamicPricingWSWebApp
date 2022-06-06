
CREATE TABLE rcltbls.user_login_dyna (	
	user_id varchar(10) NOT NULL,	 
	user_name varchar(200) NULL,
	password varchar(50) NULL,	
	email_id varchar(256) NULL,
	record_change_date timestamp,
	record_status varchar(1) NULL,	
	city varchar(25) NULL,
	state varchar(2) NULL,
	country varchar(2) NULL,
	phone varchar(17) NULL,
	user_role varchar NULL,
	token varchar NULL,	
	CONSTRAINT pk_user_login_dyna PRIMARY KEY (user_id)
);
INSERT INTO rcltbls.user_login_dyna
(user_id, user_name, "password",   record_status, user_role)
VALUES('super', 'Super Admin', 'password',  'A', '3');



ghar wapasi