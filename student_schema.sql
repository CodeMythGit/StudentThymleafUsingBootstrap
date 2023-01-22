CREATE DATABASE IF NOT EXISTS CodeMyth;
USE CodeMyth ;

USE CodeMyth ;
 create table STUDENT(
	ID bigint NOT NULL auto_increment,
    NAME varchar(100) default NULL,
    CITY VARCHAR(50) default NULL,
    MARKS integer default 0,
    RESULT boolean default false,
    CREATED_DATE timestamp default current_timestamp,
    primary key(ID));


 USE CodeMyth ;
 select * from STUDENT;