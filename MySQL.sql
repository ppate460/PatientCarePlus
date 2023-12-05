use PatientCarePlus;

-- drop table messages;

-- create table messages(
-- 	message_id int not null auto_increment,
--     from_id int not null,
--     to_id int not null,
--     sender_type int not null,
--     subject varchar(200) default null,
--     message varchar(500) default null,
--     sent_date datetime default null,
--     root_message_id int not null default 1,
--     primary key(message_id)
-- );


-- drop table doctors;

-- create table doctors(
-- 	doctor_id int not null auto_increment,
--     username varchar(45) not null,
--     password varchar(45) not null,
--     email varchar(80) not null,
--     patient_id int default null,
--     last_name varchar(45) not null,
--     first_name varchar(45) not null,
--     work_address varchar(200) default null,
--     certification varchar(300) default null,
--     phone varchar(50) not null,
--     primary key(doctor_id)
-- );

select *  from doctors;
select *  from patients;
select *  from connections;
select *  from logger;
select *  from messages;
select *  from vitals;
select *  from consent;

-- set sql_safe_updates = 0;

-- delete from doctors;
-- delete from patients;
-- delete from connections;
-- delete from logger;
-- delete from messages;
-- delete from vitals;
-- delete from consent;

-- set sql_safe_updates = 1;
