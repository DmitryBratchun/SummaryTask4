-- ===============================================================
-- Create script DB for MySQL
-- ===============================================================
SET NAMES utf8;
SET CHARACTER SET UTF8;

DROP DATABASE IF EXISTS summary_Task4;
CREATE DATABASE summary_Task4 CHARACTER SET utf8 COLLATE utf8_bin;

USE summary_Task4;
-- ===============================================================
-- Table Users roles
-- ===============================================================
DROP TABLE IF EXISTS `summary_task4`.`roles`;
CREATE TABLE roles(
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(10) NOT NULL UNIQUE
);
-- insert data into roles table
INSERT INTO roles VALUES(0, 'admin');
INSERT INTO roles VALUES(1, 'client');
-- ===============================================================
-- Table Users
-- ===============================================================
DROP TABLE IF EXISTS `summary_task4`.`users`;
CREATE TABLE users(
	id INTEGER NOT NULL auto_increment PRIMARY KEY,
	login VARCHAR(20) NOT NULL UNIQUE,
	password VARCHAR(20) NOT NULL,
	first_name VARCHAR(20) NOT NULL,
	last_name VARCHAR(20) NOT NULL,
	email VARCHAR(129) NOT NULL,
	lang ENUM('ru','en') NOT NULL DEFAULT 'ru',
	role_id INTEGER NOT NULL, 
		FOREIGN KEY (role_id)
		REFERENCES roles(id) 
		ON DELETE CASCADE 
		ON UPDATE RESTRICT
);
-- ===============================================================
-- Table Entrents
-- ===============================================================
DROP TABLE IF EXISTS `summary_task4`.`entrants`;
CREATE TABLE entrants(
  	id INT NOT NULL PRIMARY KEY, 
  		FOREIGN KEY (id)
        	REFERENCES users (id)
        	ON DELETE CASCADE
		ON UPDATE RESTRICT,
  	city VARCHAR(45) NOT NULL,
  	region VARCHAR(45) NOT NULL,
  	school VARCHAR(45) NOT NULL,
  	isBlocked TINYINT(1) DEFAULT null
  	);
-- ===============================================================
-- Table Faculties
-- ===============================================================
DROP TABLE IF EXISTS `summary_task4`.`faculties`;
CREATE TABLE faculties(
	id INT NOT NULL auto_increment PRIMARY KEY,
	name_ru VARCHAR(100) NOT NULL UNIQUE,
  	name_en VARCHAR(100) NOT NULL UNIQUE,
  	total_places INT NOT NULL,
  	budget_places INT NOT NULL
);
-- ===============================================================
-- Table Subjects
-- ===============================================================
DROP TABLE IF EXISTS `summary_task4`.`subjects`;
CREATE TABLE subjects(
	id INT NOT NULL auto_increment PRIMARY KEY,
	name_ru VARCHAR(45) UNIQUE,
	name_en VARCHAR(45) UNIQUE
);
-- ===============================================================
-- Table Exam Type
-- ===============================================================
DROP TABLE IF EXISTS `summary_task4`.`exam_type`;
CREATE TABLE exam_type(
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(12) NOT NULL UNIQUE
);
-- insert data into exam type table
INSERT INTO exam_type VALUES(0, 'diploma');
INSERT INTO exam_type VALUES(1, 'preliminary');

-- ===============================================================
-- Table Entry Type
-- ===============================================================
DROP TABLE IF EXISTS `summary_task4`.`entry_type`;
CREATE TABLE entry_type(
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(20) NOT NULL UNIQUE
);
-- insert data into entry type table

INSERT INTO entry_type VALUES(0, 'not included');
INSERT INTO entry_type VALUES(1, 'under consideration');
INSERT INTO entry_type VALUES(2, 'budget');
INSERT INTO entry_type VALUES(3, 'contract');
INSERT INTO entry_type VALUES(4, 'failed');

-- ===============================================================
-- Table statement
-- ===============================================================
DROP TABLE IF EXISTS `summary_task4`.`statement`;
CREATE TABLE statement(
	entrant_id INT NOT NULL,
		FOREIGN KEY (entrant_id)
        	REFERENCES entrants (id)
        	ON DELETE CASCADE
		ON UPDATE RESTRICT,
	faculty_id INT NOT NULL,
		FOREIGN KEY (faculty_id)
        	REFERENCES faculties (id)
        	ON DELETE CASCADE
		ON UPDATE RESTRICT,
	diploma_score DOUBLE NOT NULL,
	preliminary_score DOUBLE NOT NULL,
	entry_type_id INT NOT NULL,
		FOREIGN KEY (entry_type_id)
        	REFERENCES entry_type (id)
        	ON DELETE CASCADE
		ON UPDATE RESTRICT,
	PRIMARY KEY(faculty_id, entrant_id)
	
);

-- ===============================================================
-- Table Grades
-- ===============================================================
DROP TABLE IF EXISTS `summary_task4`.`grades`;
CREATE TABLE grades(
	entrant_id INT NOT NULL,
		FOREIGN KEY (entrant_id)
        	REFERENCES entrants (id)
        	ON DELETE CASCADE
		ON UPDATE RESTRICT,
	faculty_id INT NOT NULL,
		FOREIGN KEY (faculty_id)
        	REFERENCES faculties (id)
       	 	ON DELETE CASCADE
		ON UPDATE RESTRICT,
	subject_id INT NOT NULL,
		FOREIGN KEY (subject_id)
        	REFERENCES subjects (id)
        	ON DELETE CASCADE
		ON UPDATE RESTRICT,
	value INT NOT NULL,
	exam_type_id INT NOT NULL,
		FOREIGN KEY (exam_type_id)
        	REFERENCES exam_type (id)
        	ON DELETE CASCADE
		ON UPDATE RESTRICT,
	PRIMARY KEY(faculty_id, subject_id, entrant_id, exam_type_id)
);
-- ===============================================================
-- Table Faculty subjects
-- ===============================================================
DROP TABLE IF EXISTS `summary_task4`.`faculty_subjects`;
CREATE TABLE faculty_subjects(
	faculty_id INT NOT NULL,
		FOREIGN KEY (faculty_id)
        REFERENCES faculties (id)
        ON DELETE CASCADE
		ON UPDATE RESTRICT,
	subject_id INT NOT NULL,
		FOREIGN KEY (subject_id)
        REFERENCES subjects (id)
        ON DELETE CASCADE
		ON UPDATE RESTRICT,
	PRIMARY KEY(faculty_id, subject_id)
);
-- ===============================================================
-- Insert data
-- ===============================================================
INSERT INTO `users` (`login`, `password`, `first_Name`, `last_Name`, `email`, `role_id`) VALUES ("admin","admin","Dmitry","Bratchun","brat@gmail.com",0);

INSERT INTO `users` (`login`, `password`, `first_Name`, `last_Name`, `email`, `role_id`) VALUES ("client1","123","Роман","Ковальчук","brathund@gmail.com",1);
INSERT INTO `users` (`login`, `password`, `first_Name`, `last_Name`, `email`, `role_id`) VALUES ("client2","123","Дмитрий","Свинаренко","client2@gmail.com",1);
INSERT INTO `users` (`login`, `password`, `first_Name`, `last_Name`, `email`, `role_id`) VALUES ("client3","123","Сергей","Краснокутский","client3@gmail.com",1);
INSERT INTO `users` (`login`, `password`, `first_Name`, `last_Name`, `email`, `role_id`) VALUES ("client4","123","Алексей","Смирнов","client4@gmail.com",1);
INSERT INTO `users` (`login`, `password`, `first_Name`, `last_Name`, `email`, `role_id`) VALUES ("client5","123","Максим","Лебедев","client5@gmail.com",1);
INSERT INTO `users` (`login`, `password`, `first_Name`, `last_Name`, `email`, `role_id`) VALUES ("client6","123","Виктория","Терихова","client6@gmail.com",1);
INSERT INTO `users` (`login`, `password`, `first_Name`, `last_Name`, `email`, `role_id`) VALUES ("client7","123","Дмитрий","Медведев","client7@gmail.com",1);
INSERT INTO `users` (`login`, `password`, `first_Name`, `last_Name`, `email`, `role_id`) VALUES ("client8","123","Богдан","Незнайко","client8@gmail.com",1);
INSERT INTO `users` (`login`, `password`, `first_Name`, `last_Name`, `email`, `role_id`) VALUES ("client9","123","Владимир","Таскан","client9@gmail.com",1);
INSERT INTO `users` (`login`, `password`, `first_Name`, `last_Name`, `email`, `role_id`) VALUES ("client10","123","Нестор","Кириченко","client10@gmail.com",1);
INSERT INTO `users` (`login`, `password`, `first_Name`, `last_Name`, `email`, `role_id`) VALUES ("client11","123","Ричард","Фейнман","client11@gmail.com",1);
INSERT INTO `users` (`login`, `password`, `first_Name`, `last_Name`, `email`, `role_id`) VALUES ("client12","123","Митио","Каку","client12@gmail.com",1);
INSERT INTO `users` (`login`, `password`, `first_Name`, `last_Name`, `email`, `role_id`) VALUES ("client13","123","Лоуренс","Краус","client13@gmail.com",1);
INSERT INTO `users` (`login`, `password`, `first_Name`, `last_Name`, `email`, `role_id`) VALUES ("client14","123","Сергей","Тимонов","client14@gmail.com",1);
INSERT INTO `users` (`login`, `password`, `first_Name`, `last_Name`, `email`, `role_id`) VALUES ("client15","123","Олег","Смольников","client15@gmail.com",1);
INSERT INTO `users` (`login`, `password`, `first_Name`, `last_Name`, `email`, `role_id`) VALUES ("client16","123","Олег","Мильгевский","client16@gmail.com",1);
INSERT INTO `users` (`login`, `password`, `first_Name`, `last_Name`, `email`, `role_id`) VALUES ("client17","123","Роман","Смирнов","client17@gmail.com",1);
INSERT INTO `users` (`login`, `password`, `first_Name`, `last_Name`, `email`, `role_id`) VALUES ("client18","123","Иван","Иванов","client18@gmail.com",1);
INSERT INTO `users` (`login`, `password`, `first_Name`, `last_Name`, `email`, `role_id`) VALUES ("client19","123","Богдан","Шамо","client19@gmail.com",1);
INSERT INTO `users` (`login`, `password`, `first_Name`, `last_Name`, `email`, `role_id`) VALUES ("client20","123","Георгий","Каневец","client20@gmail.com",1);

INSERT INTO `entrants` (`city`, `region`, `school`, `id`) VALUES ("Харьков", "Харьковская", "ШК№1", 2);
INSERT INTO `entrants` (`city`, `region`, `school`, `id`) VALUES ("Харьков", "Харьковская", "ШК№1", 3);
INSERT INTO `entrants` (`city`, `region`, `school`, `id`) VALUES ("Харьков", "Харьковская", "ШК№3", 4);
INSERT INTO `entrants` (`city`, `region`, `school`, `id`) VALUES ("Харьков", "Харьковская", "ШК№3", 5);
INSERT INTO `entrants` (`city`, `region`, `school`, `id`) VALUES ("Харьков", "Харьковская", "ШК№4", 6);
INSERT INTO `entrants` (`city`, `region`, `school`, `id`) VALUES ("Харьков", "Харьковская", "ШК№4", 7);
INSERT INTO `entrants` (`city`, `region`, `school`, `id`) VALUES ("Полтава", "Полтавская", "ШК№1", 8);
INSERT INTO `entrants` (`city`, `region`, `school`, `id`) VALUES ("Полтава", "Полтавская", "ШК№2", 9);
INSERT INTO `entrants` (`city`, `region`, `school`, `id`) VALUES ("Полтава", "Полтавская", "ШК№1", 10);
INSERT INTO `entrants` (`city`, `region`, `school`, `id`) VALUES ("Миргород", "Полтавска", "ШК№5", 11);
INSERT INTO `entrants` (`city`, `region`, `school`, `id`) VALUES ("Миргород", "Полтавска", "ШК№5", 12);
INSERT INTO `entrants` (`city`, `region`, `school`, `id`) VALUES ("Миргород", "Полтавска", "ШК№5", 13);
INSERT INTO `entrants` (`city`, `region`, `school`, `id`) VALUES ("Миргород", "Полтавска", "ШК№1", 14);
INSERT INTO `entrants` (`city`, `region`, `school`, `id`) VALUES ("Киев", "Киевская", "ШК№2", 15);
INSERT INTO `entrants` (`city`, `region`, `school`, `id`) VALUES ("Киев", "Киевская", "ШК№1", 16);
INSERT INTO `entrants` (`city`, `region`, `school`, `id`) VALUES ("Киев", "Киевская", "ШК№3", 17);
INSERT INTO `entrants` (`city`, `region`, `school`, `id`) VALUES ("Киев", "Киевская", "ШК№1", 18);
INSERT INTO `entrants` (`city`, `region`, `school`, `id`) VALUES ("Киев", "Киевская", "ШК№4", 19);
INSERT INTO `entrants` (`city`, `region`, `school`, `id`) VALUES ("Киев", "Киевская", "ШК№1", 20);
INSERT INTO `entrants` (`city`, `region`, `school`, `id`) VALUES ("Киев", "Киевская", "ШК№4", 21);

INSERT INTO subjects (name_ru, name_en) VALUE ('История','History');
INSERT INTO subjects (name_ru, name_en) VALUE ('Биология','Biology');
INSERT INTO subjects (name_ru, name_en) VALUE ('Физика','Physics');
INSERT INTO subjects (name_ru, name_en) VALUE ('Информатика','Computer science');
INSERT INTO subjects (name_ru, name_en) VALUE ('Математика','Mathematics');
INSERT INTO subjects (name_ru, name_en) VALUE ('Иностранный язык','Foreign language');
INSERT INTO subjects (name_ru, name_en) VALUE ('Музыка','Music');
INSERT INTO subjects (name_ru, name_en) VALUE ('Литература','Literature');
INSERT INTO subjects (name_ru, name_en) VALUE ('Русский язык','Russian language');
INSERT INTO subjects (name_ru, name_en) VALUE ('География','Geography');

INSERT INTO faculties (name_ru, name_en, total_places, budget_places) VALUES ("Автоматизация и кибербезопасность энергосистем", "Automation and cybersecurity of power systems", 12,3);
INSERT INTO faculties (name_ru, name_en, total_places, budget_places) VALUES ("Автоматизированные электромеханические системы", "Automated Electromechanical Systems", 10,3);
INSERT INTO faculties (name_ru, name_en, total_places, budget_places) VALUES ("Двигатели внутреннего сгорания", "Internal combustion engines", 11,2);
INSERT INTO faculties (name_ru, name_en, total_places, budget_places) VALUES ("Инженерная электрофизика", "Electrophysics Engineering", 14,4);
INSERT INTO faculties (name_ru, name_en, total_places, budget_places) VALUES ("Общая электротехника", "General electrical engineering", 22,5);
INSERT INTO faculties (name_ru, name_en, total_places, budget_places) VALUES ("Парогенераторостроение", "Steam generator", 12,3);
INSERT INTO faculties (name_ru, name_en, total_places, budget_places) VALUES ("Передача электрической энергии", "Electric power transmission", 13,4);
INSERT INTO faculties (name_ru, name_en, total_places, budget_places) VALUES ("Промышленная и биомедицинская электроника", "Industrial and biomedical electronics", 10,5);
INSERT INTO faculties (name_ru, name_en, total_places, budget_places) VALUES ("Теоретические основы электротехники", "Theoretical Foundations of Electrical Engineering", 13,4);
INSERT INTO faculties (name_ru, name_en, total_places, budget_places) VALUES ("Теплотехника и энергоэффективные технологии", "Heat engineering and energy efficient technologies", 19,5);
INSERT INTO faculties (name_ru, name_en, total_places, budget_places) VALUES ("Техническая криофизика", "Technical Cryophysics", 13,2);
INSERT INTO faculties (name_ru, name_en, total_places, budget_places) VALUES ("Турбиностроение", "Turbine building", 15,5);
INSERT INTO faculties (name_ru, name_en, total_places, budget_places) VALUES ("Электрические аппараты", "Electrical apparatus", 13,3);
INSERT INTO faculties (name_ru, name_en, total_places, budget_places) VALUES ("Электрические машины", "Electric cars", 12,3);
INSERT INTO faculties (name_ru, name_en, total_places, budget_places) VALUES ("Электрические станции", "Power stations", 15,5);
INSERT INTO faculties (name_ru, name_en, total_places, budget_places) VALUES ("Электрический транспорт и тепловозостроение", "Electric transport and diesel locomotive", 15,5);
INSERT INTO faculties (name_ru, name_en, total_places, budget_places) VALUES ("Электроизоляционная и кабельная техника", "Electrical insulation and cable technology", 12,5);

INSERT INTO faculty_subjects VALUE(1,1);
INSERT INTO faculty_subjects VALUE(1,2);
INSERT INTO faculty_subjects VALUE(2,2);
INSERT INTO faculty_subjects VALUE(2,5);
INSERT INTO faculty_subjects VALUE(3,1);
INSERT INTO faculty_subjects VALUE(3,4);
INSERT INTO faculty_subjects VALUE(4,3);
INSERT INTO faculty_subjects VALUE(4,6);
INSERT INTO faculty_subjects VALUE(5,3);
INSERT INTO faculty_subjects VALUE(5,6);
INSERT INTO faculty_subjects VALUE(6,3);
INSERT INTO faculty_subjects VALUE(6,6);
INSERT INTO faculty_subjects VALUE(7,3);
INSERT INTO faculty_subjects VALUE(7,6);
INSERT INTO faculty_subjects VALUE(8,3);
INSERT INTO faculty_subjects VALUE(9,6);
INSERT INTO faculty_subjects VALUE(10,3);
INSERT INTO faculty_subjects VALUE(10,6);
INSERT INTO faculty_subjects VALUE(11,3);
INSERT INTO faculty_subjects VALUE(12,6);
INSERT INTO faculty_subjects VALUE(13,3);
INSERT INTO faculty_subjects VALUE(13,6);

INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (21,1,1,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (21,1,2,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (21,1,3,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (21,1,4,9,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (21,1,5,12,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (21,1,6,4,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (21,1,7,7,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (21,1,8,8,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (21,1,9,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (21,1,10,9,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (21,1,1,10,1);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (21,1,2,5,1);

INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (2,1,1,6,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (2,1,2,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (2,1,3,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (2,1,4,7,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (2,1,5,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (2,1,6,9,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (2,1,7,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (2,1,8,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (2,1,9,4,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (2,1,10,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (2,1,1,9,1);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (2,1,2,10,1);

INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (3,1,1,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (3,1,2,9,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (3,1,3,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (3,1,4,7,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (3,1,5,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (3,1,6,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (3,1,7,11,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (3,1,8,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (3,1,9,12,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (3,1,10,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (3,1,1,10,1);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (3,1,2,10,1);

INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (4,1,1,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (4,1,2,7,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (4,1,3,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (4,1,4,9,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (4,1,5,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (4,1,6,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (4,1,7,8,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (4,1,8,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (4,1,9,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (4,1,10,4,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (4,1,1,10,1);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (4,1,2,10,1);

INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (5,1,1,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (5,1,2,9,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (5,1,3,11,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (5,1,4,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (5,1,5,4,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (5,1,6,12,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (5,1,7,5,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (5,1,8,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (5,1,9,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (5,1,10,4,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (5,1,1,10,1);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (5,1,2,10,1);

INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (6,1,1,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (6,1,2,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (6,1,3,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (6,1,4,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (6,1,5,4,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (6,1,6,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (6,1,7,3,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (6,1,8,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (6,1,9,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (6,1,10,5,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (6,1,1,7,1);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (6,1,2,10,1);

INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (7,1,1,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (7,1,2,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (7,1,3,7,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (7,1,4,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (7,1,5,6,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (7,1,6,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (7,1,7,6,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (7,1,8,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (7,1,9,5,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (7,1,10,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (7,1,1,6,1);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (7,1,2,10,1);

INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (8,1,1,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (8,1,2,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (8,1,3,6,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (8,1,4,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (8,1,5,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (8,1,6,5,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (8,1,7,6,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (8,1,8,7,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (8,1,9,6,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (8,1,10,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (8,1,1,3,1);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (8,1,2,10,1);

INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (9,1,1,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (9,1,2,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (9,1,3,4,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (9,1,4,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (9,1,5,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (9,1,6,5,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (9,1,7,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (9,1,8,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (9,1,9,6,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (9,1,10,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (9,1,1,6,1);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (9,1,2,10,1);

INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (10,1,1,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (10,1,2,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (10,1,3,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (10,1,4,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (10,1,5,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (10,1,6,11,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (10,1,7,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (10,1,8,11,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (10,1,9,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (10,1,10,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (10,1,1,10,1);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (10,1,2,11,1);

INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (11,1,1,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (11,1,2,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (11,1,3,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (11,1,4,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (11,1,5,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (11,1,6,11,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (11,1,7,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (11,1,8,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (11,1,9,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (11,1,10,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (11,1,1,10,1);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (11,1,2,10,1);

INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (12,1,1,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (12,1,2,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (12,1,3,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (12,1,4,9,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (12,1,5,9,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (12,1,6,8,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (12,1,7,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (12,1,8,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (12,1,9,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (12,1,10,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (12,1,1,10,1);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (12,1,2,10,1);

INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (13,1,1,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (13,1,2,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (13,1,3,5,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (13,1,4,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (13,1,5,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (13,1,6,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (13,1,7,7,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (13,1,8,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (13,1,9,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (13,1,10,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (13,1,1,4,1);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (13,1,2,10,1);

INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (14,1,1,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (14,1,2,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (14,1,3,8,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (14,1,4,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (14,1,5,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (14,1,6,6,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (14,1,7,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (14,1,8,7,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (14,1,9,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (14,1,10,8,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (14,1,1,10,1);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (14,1,2,10,1);

INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (15,1,1,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (15,1,2,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (15,1,3,9,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (15,1,4,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (15,1,5,5,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (15,1,6,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (15,1,7,7,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (15,1,8,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (15,1,9,6,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (15,1,10,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (15,1,1,10,1);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (15,1,2,10,1);

INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (16,1,1,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (16,1,2,5,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (16,1,3,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (16,1,4,7,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (16,1,5,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (16,1,6,7,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (16,1,7,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (16,1,8,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (16,1,9,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (16,1,10,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (16,1,1,10,1);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (16,1,2,10,1);

INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (17,1,1,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (17,1,2,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (17,1,3,6,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (17,1,4,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (17,1,5,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (17,1,6,6,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (17,1,7,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (17,1,8,5,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (17,1,9,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (17,1,10,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (17,1,1,10,1);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (17,1,2,7,1);

INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (18,1,1,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (18,1,2,5,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (18,1,3,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (18,1,4,8,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (18,1,5,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (18,1,6,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (18,1,7,6,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (18,1,8,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (18,1,9,5,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (18,1,10,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (18,1,1,8,1);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (18,1,2,10,1);

INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (19,1,1,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (19,1,2,12,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (19,1,3,9,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (19,1,4,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (19,1,5,12,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (19,1,6,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (19,1,7,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (19,1,8,12,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (19,1,9,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (19,1,10,10,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (19,1,1,3,1);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (19,1,2,5,1);

INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (20,1,1,7,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (20,1,2,5,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (20,1,3,9,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (20,1,4,7,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (20,1,5,4,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (20,1,6,7,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (20,1,7,6,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (20,1,8,6,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (20,1,9,5,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (20,1,10,3,0);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (20,1,1,11,1);
INSERT INTO `grades` (`entrant_id`, `faculty_id`, `subject_id`, `value`, `exam_type_id`) VALUES (20,1,2,12,1);


