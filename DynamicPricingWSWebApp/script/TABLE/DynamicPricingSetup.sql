-- rcltbls.pricing_setup definition

-- Drop table

-- DROP TABLE rcltbls.pricing_setup;

CREATE TABLE rcltbls.pricing_setup (
	pk_pricing_setup varchar(5) NOT NULL,
	port varchar(10) NULL,
	terminal varchar(20) NULL,
	situation varchar(30) NULL,
	start_event timestamp NULL,
	end_event timestamp NULL,
	start_effect timestamp NULL,
	end_effect timestamp NULL,
	max_crane varchar(30) NULL,
	max_truck_vessel varchar(30) NULL,
	operating_truck_vessel varchar(30) NULL,
	no_of_birth_max int4 NULL,
	no_of_birth_available int4 NULL,
	stevedore_shortage_percentage int4 NULL,
	normal_formality timestamp NULL,
	abnormal_formality timestamp NULL,
	CONSTRAINT pricing_setup_pkey PRIMARY KEY (pk_pricing_setup)
);


