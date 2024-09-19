-- --------------------------------------------------------
-- Host:                         192.168.121.27
-- Server version:               PostgreSQL 12.14 (Ubuntu 12.14-1.pgdg22.04+1) on x86_64-pc-linux-gnu, compiled by gcc (Ubuntu 11.3.0-1ubuntu1~22.04) 11.3.0, 64-bit
-- Server OS:                    
-- HeidiSQL Version:             12.7.0.6850
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES  */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping structure for table brta.appointment_timeslots
CREATE TABLE IF NOT EXISTS "appointment_timeslots" (
	"appointment_timeslot_id" BIGINT NOT NULL,
	"slot_name_bn" VARCHAR(100) NOT NULL,
	"slot_name_en" VARCHAR(100) NOT NULL,
	"slot_start_time" TIME NOT NULL,
	"slot_end_time" TIME NOT NULL,
	"is_active" BOOLEAN NOT NULL,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("appointment_timeslot_id")
);

-- Dumping data for table brta.appointment_timeslots: 0 rows
/*!40000 ALTER TABLE "appointment_timeslots" DISABLE KEYS */;
/*!40000 ALTER TABLE "appointment_timeslots" ENABLE KEYS */;

-- Dumping structure for table brta.c_blood_groups
CREATE TABLE IF NOT EXISTS "c_blood_groups" (
	"blood_group_id" BIGINT NOT NULL,
	"name_en" VARCHAR(20) NOT NULL,
	"name_bn" VARCHAR(20) NOT NULL,
	"is_active" BOOLEAN NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NULL DEFAULT NULL,
	PRIMARY KEY ("blood_group_id"),
	CONSTRAINT "c_blood_groups_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_blood_groups_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.c_blood_groups: 0 rows
/*!40000 ALTER TABLE "c_blood_groups" DISABLE KEYS */;
/*!40000 ALTER TABLE "c_blood_groups" ENABLE KEYS */;

-- Dumping structure for table brta.c_countries
CREATE TABLE IF NOT EXISTS "c_countries" (
	"country_id" BIGINT NOT NULL,
	"name_en" VARCHAR(255) NOT NULL,
	"name_bn" VARCHAR(255) NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("country_id"),
	CONSTRAINT "c_countries_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_countries_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.c_countries: 1 rows
/*!40000 ALTER TABLE "c_countries" DISABLE KEYS */;
INSERT INTO "c_countries" ("country_id", "name_en", "name_bn", "is_active", "create_user_id", "update_user_id", "created_date", "updated_date", "deleted_date", "version_no") VALUES
	(10, 'Bangladesh', 'বাংলাদেশ', 'true', 2, 2, '2024-09-04 10:58:56', NULL, NULL, 1);
/*!40000 ALTER TABLE "c_countries" ENABLE KEYS */;

-- Dumping structure for table brta.c_designations
CREATE TABLE IF NOT EXISTS "c_designations" (
	"designation_id" BIGINT NOT NULL,
	"name_en" VARCHAR(100) NOT NULL,
	"name_bn" VARCHAR(100) NOT NULL,
	"level_number" INTEGER NOT NULL,
	"parent_designation_id" BIGINT NULL DEFAULT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NULL DEFAULT NULL,
	PRIMARY KEY ("designation_id"),
	CONSTRAINT "c_designations_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_designations_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fkbrayjcoy84om1fwb3hbkuop8i" FOREIGN KEY ("parent_designation_id") REFERENCES "c_designations" ("designation_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.c_designations: 14 rows
/*!40000 ALTER TABLE "c_designations" DISABLE KEYS */;
INSERT INTO "c_designations" ("designation_id", "name_en", "name_bn", "level_number", "parent_designation_id", "is_active", "create_user_id", "update_user_id", "created_date", "updated_date", "deleted_date", "version_no") VALUES
	(6, 'Chairman', 'চেয়ারম্যান', 1, NULL, 'true', 2, NULL, '2024-09-05 12:49:03', NULL, NULL, 1),
	(18, 'Assistant Accountant', 'হিসাব সহকারি', 4, NULL, 'true', 2, NULL, '2024-09-10 09:53:03.18876', '2024-09-10 09:53:03.18876', NULL, 1),
	(21, 'Porter Chambers', 'Whilemina Haynes', 133, NULL, 'false', 2, NULL, '2024-09-10 09:55:06.506052', '2024-09-10 09:55:06.506052', NULL, 1),
	(22, 'Vera Mckay', 'Lana Murphy', 569, NULL, 'false', 2, NULL, '2024-09-10 09:55:30.027754', '2024-09-10 09:55:30.027754', NULL, 1),
	(34, 'QC', 'কিউসি', 20, NULL, 'true', 2, NULL, '2024-09-12 12:41:59', NULL, NULL, 1),
	(35, 'Computer Operator', 'Computer Operator', 18, NULL, 'true', 2, NULL, '2024-09-12 17:47:23', NULL, NULL, 1),
	(9, 'Director', 'পরিচালক', 2, 6, 'true', 2, NULL, '2024-09-05 12:49:03', NULL, NULL, 1),
	(47, 'Hayes Garcia', 'Fuller Good', 557, NULL, 'false', 2, NULL, '2024-09-15 10:19:00.808317', '2024-09-15 10:19:00.808317', NULL, 1),
	(46, 'Barbara White', 'Diana Stein', 797, NULL, 'false', 2, 2, '2024-09-15 10:18:53.76058', '2024-09-15 10:36:42.902402', NULL, 1),
	(17, 'Accounts Officer', 'হিসাব রক্ষন কর্মকর্তা', 6, NULL, 'false', 2, 2, '2024-09-10 09:50:29.180284', '2024-09-15 10:37:35.106663', NULL, 1),
	(48, 'Daria Fowler', 'Illana Whitehead', 888, NULL, 'true', 2, 2, '2024-09-15 11:51:41.409084', '2024-09-15 11:56:41.527949', NULL, 0),
	(49, 'Zeph Shaffer', 'Kylee Mejia', 555, NULL, 'true', 2, NULL, '2024-09-15 12:32:28.636977', '2024-09-15 12:32:28.636977', NULL, 0),
	(50, 'Kiona Mcdaniel', 'Lenore Tyler', 913, NULL, 'true', 2, NULL, '2024-09-17 03:21:24.551564', '2024-09-17 03:21:24.551564', NULL, 0),
	(51, 'Griffith Mack', 'Astra Dyer', 54, NULL, 'true', 2, NULL, '2024-09-17 04:14:47.13131', '2024-09-17 04:14:47.13131', NULL, 0),
	(55, 'Griffith Mack', 'Astra Dyer', 54, NULL, 'true', 2, NULL, '2024-09-17 04:14:47.13131', '2024-09-17 04:14:47.13131', NULL, 0),
	(57, 'Idona Baldwin', 'Blaze Mcclure', 875, NULL, 'true', 2, NULL, '2024-09-17 05:56:42.196168', '2024-09-17 05:56:42.196168', NULL, 0),
	(56, 'Astra Dyer', 'Astra Dye', 3, NULL, 'true', 2, 2, '2024-09-17 04:14:47.13131', '2024-09-17 06:30:40.027869', NULL, 3);
/*!40000 ALTER TABLE "c_designations" ENABLE KEYS */;

-- Dumping structure for table brta.c_document_types
CREATE TABLE IF NOT EXISTS "c_document_types" (
	"document_type_id" BIGINT NOT NULL,
	"name_en" VARCHAR(100) NOT NULL,
	"name_bn" VARCHAR(100) NOT NULL,
	"document_format" VARCHAR(255) NOT NULL,
	"document_size" INTEGER NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("document_type_id"),
	CONSTRAINT "c_document_types_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_document_types_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.c_document_types: 0 rows
/*!40000 ALTER TABLE "c_document_types" DISABLE KEYS */;
/*!40000 ALTER TABLE "c_document_types" ENABLE KEYS */;

-- Dumping structure for table brta.c_email_templates
CREATE TABLE IF NOT EXISTS "c_email_templates" (
	"email_template_id" BIGINT NOT NULL,
	"service_id" INTEGER NOT NULL,
	"template_name" TEXT NOT NULL,
	"subject_bn" TEXT NOT NULL,
	"subject_en" TEXT NOT NULL,
	"emai_body_bn" TEXT NOT NULL,
	"emai_body_en" TEXT NOT NULL,
	"btn_text_bn" VARCHAR(255) NULL DEFAULT NULL,
	"btn_text_en" VARCHAR(255) NULL DEFAULT NULL,
	"btn_url" VARCHAR(255) NULL DEFAULT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("email_template_id"),
	CONSTRAINT "c_email_templates_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_email_templates_service_id_fkey" FOREIGN KEY ("service_id") REFERENCES "c_services" ("service_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_email_templates_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.c_email_templates: 0 rows
/*!40000 ALTER TABLE "c_email_templates" DISABLE KEYS */;
/*!40000 ALTER TABLE "c_email_templates" ENABLE KEYS */;

-- Dumping structure for table brta.c_fiscal_years
CREATE TABLE IF NOT EXISTS "c_fiscal_years" (
	"fiscal_year_id" BIGINT NOT NULL,
	"start_date" TIMESTAMP NOT NULL,
	"end_date" TIMESTAMP NOT NULL,
	"name_bn" VARCHAR(100) NOT NULL,
	"name_en" VARCHAR(100) NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("fiscal_year_id"),
	CONSTRAINT "c_fiscal_years_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_fiscal_years_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.c_fiscal_years: 0 rows
/*!40000 ALTER TABLE "c_fiscal_years" DISABLE KEYS */;
/*!40000 ALTER TABLE "c_fiscal_years" ENABLE KEYS */;

-- Dumping structure for table brta.c_fuel_types
CREATE TABLE IF NOT EXISTS "c_fuel_types" (
	"fuel_type_id" BIGINT NOT NULL,
	"name_en" VARCHAR(100) NOT NULL,
	"name_bn" VARCHAR(100) NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("fuel_type_id"),
	CONSTRAINT "c_fuel_types_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_fuel_types_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.c_fuel_types: 0 rows
/*!40000 ALTER TABLE "c_fuel_types" DISABLE KEYS */;
/*!40000 ALTER TABLE "c_fuel_types" ENABLE KEYS */;

-- Dumping structure for table brta.c_genders
CREATE TABLE IF NOT EXISTS "c_genders" (
	"gender_id" BIGINT NOT NULL,
	"name_en" VARCHAR(20) NOT NULL,
	"name_bn" VARCHAR(20) NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("gender_id"),
	CONSTRAINT "c_genders_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_genders_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.c_genders: 0 rows
/*!40000 ALTER TABLE "c_genders" DISABLE KEYS */;
/*!40000 ALTER TABLE "c_genders" ENABLE KEYS */;

-- Dumping structure for table brta.c_locations
CREATE TABLE IF NOT EXISTS "c_locations" (
	"location_id" BIGINT NOT NULL,
	"name_en" VARCHAR(100) NULL DEFAULT NULL,
	"name_bn" VARCHAR(100) NULL DEFAULT NULL,
	"location_type_id" INTEGER NULL DEFAULT NULL,
	"parent_location_id" INTEGER NULL DEFAULT NULL,
	"is_active" BOOLEAN NULL DEFAULT true,
	"create_user_id" BIGINT NULL DEFAULT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NULL DEFAULT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NULL DEFAULT NULL,
	PRIMARY KEY ("location_id"),
	CONSTRAINT "c_locations_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_locations_location_type_id_fkey" FOREIGN KEY ("location_type_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_locations_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.c_locations: 0 rows
/*!40000 ALTER TABLE "c_locations" DISABLE KEYS */;
/*!40000 ALTER TABLE "c_locations" ENABLE KEYS */;

-- Dumping structure for table brta.c_notificaiton_templates
CREATE TABLE IF NOT EXISTS "c_notificaiton_templates" (
	"notificaiton_template_id" BIGINT NOT NULL,
	"service_id" INTEGER NOT NULL,
	"title_bn" TEXT NOT NULL,
	"title_en" TEXT NOT NULL,
	"message_bn" TEXT NOT NULL,
	"message_en" TEXT NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("notificaiton_template_id"),
	CONSTRAINT "c_notificaiton_templates_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_notificaiton_templates_service_id_fkey" FOREIGN KEY ("service_id") REFERENCES "c_services" ("service_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_notificaiton_templates_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.c_notificaiton_templates: 0 rows
/*!40000 ALTER TABLE "c_notificaiton_templates" DISABLE KEYS */;
/*!40000 ALTER TABLE "c_notificaiton_templates" ENABLE KEYS */;

-- Dumping structure for table brta.c_office_exam_centers
CREATE TABLE IF NOT EXISTS "c_office_exam_centers" (
	"office_exam_center_id" BIGINT NOT NULL,
	"org_id" BIGINT NOT NULL,
	"thana_id" BIGINT NOT NULL,
	"address_bn" VARCHAR(100) NOT NULL,
	"address_en" VARCHAR(100) NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("office_exam_center_id"),
	CONSTRAINT "c_office_exam_centers_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_office_exam_centers_org_id_fkey" FOREIGN KEY ("org_id") REFERENCES "c_organizations" ("org_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_office_exam_centers_thana_id_fkey" FOREIGN KEY ("thana_id") REFERENCES "c_locations" ("location_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_office_exam_centers_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.c_office_exam_centers: 0 rows
/*!40000 ALTER TABLE "c_office_exam_centers" DISABLE KEYS */;
/*!40000 ALTER TABLE "c_office_exam_centers" ENABLE KEYS */;

-- Dumping structure for table brta.c_office_jurisdictions
CREATE TABLE IF NOT EXISTS "c_office_jurisdictions" (
	"office_jurisdiction_id" BIGINT NOT NULL,
	"org_id" BIGINT NOT NULL,
	"thana_id" BIGINT NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("office_jurisdiction_id"),
	CONSTRAINT "c_office_jurisdictions_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_office_jurisdictions_org_id_fkey" FOREIGN KEY ("org_id") REFERENCES "c_organizations" ("org_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_office_jurisdictions_thana_id_fkey" FOREIGN KEY ("thana_id") REFERENCES "c_locations" ("location_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_office_jurisdictions_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.c_office_jurisdictions: 0 rows
/*!40000 ALTER TABLE "c_office_jurisdictions" DISABLE KEYS */;
/*!40000 ALTER TABLE "c_office_jurisdictions" ENABLE KEYS */;

-- Dumping structure for table brta.c_organizations
CREATE TABLE IF NOT EXISTS "c_organizations" (
	"org_id" BIGINT NOT NULL,
	"name_en" VARCHAR(255) NOT NULL,
	"name_bn" VARCHAR(255) NOT NULL,
	"office_type_id" BIGINT NOT NULL,
	"parent_org_id" BIGINT NULL DEFAULT NULL,
	"location_id" BIGINT NOT NULL,
	"post_code" VARCHAR(255) NOT NULL,
	"address_bn" VARCHAR(255) NOT NULL,
	"address_en" VARCHAR(255) NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	"version" INTEGER NULL DEFAULT NULL,
	PRIMARY KEY ("org_id"),
	CONSTRAINT "c_organizations_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_organizations_division_id_fkey" FOREIGN KEY ("location_id") REFERENCES "c_locations" ("location_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_organizations_office_type_id_fkey" FOREIGN KEY ("office_type_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_organizations_parent_org_id_fkey" FOREIGN KEY ("parent_org_id") REFERENCES "c_organizations" ("org_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_organizations_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.c_organizations: 0 rows
/*!40000 ALTER TABLE "c_organizations" DISABLE KEYS */;
/*!40000 ALTER TABLE "c_organizations" ENABLE KEYS */;

-- Dumping structure for table brta.c_services
CREATE TABLE IF NOT EXISTS "c_services" (
	"service_id" BIGINT NOT NULL,
	"name_en" VARCHAR(255) NOT NULL,
	"name_bn" VARCHAR(255) NOT NULL,
	"parent_service_id" BIGINT NULL DEFAULT NULL,
	"service_code" VARCHAR NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("service_id"),
	CONSTRAINT "c_services_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_services_parent_service_id_fkey" FOREIGN KEY ("parent_service_id") REFERENCES "c_services" ("service_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_services_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.c_services: 0 rows
/*!40000 ALTER TABLE "c_services" DISABLE KEYS */;
/*!40000 ALTER TABLE "c_services" ENABLE KEYS */;

-- Dumping structure for table brta.c_service_fine_remissions
CREATE TABLE IF NOT EXISTS "c_service_fine_remissions" (
	"fine_remission_id" BIGINT NOT NULL,
	"service_id" BIGINT NOT NULL,
	"fine_remission_start_date" TIMESTAMP NOT NULL,
	"fine_remission_end_date" TIMESTAMP NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("fine_remission_id"),
	CONSTRAINT "c_service_fine_remissions_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_service_fine_remissions_service_id_fkey" FOREIGN KEY ("service_id") REFERENCES "c_services" ("service_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_service_fine_remissions_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.c_service_fine_remissions: 0 rows
/*!40000 ALTER TABLE "c_service_fine_remissions" DISABLE KEYS */;
/*!40000 ALTER TABLE "c_service_fine_remissions" ENABLE KEYS */;

-- Dumping structure for table brta.c_statuses
CREATE TABLE IF NOT EXISTS "c_statuses" (
	"status_id" BIGINT NOT NULL,
	"status_group_id" BIGINT NOT NULL,
	"status_code" VARCHAR(100) NOT NULL,
	"name_en" VARCHAR(100) NOT NULL,
	"name_bn" VARCHAR(100) NOT NULL,
	"color_name" VARCHAR(100) NULL DEFAULT NULL,
	"data" JSON NULL DEFAULT NULL,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NULL DEFAULT NULL,
	"is_active" BOOLEAN NULL DEFAULT true,
	"priority" INTEGER NULL DEFAULT NULL,
	PRIMARY KEY ("status_id"),
	CONSTRAINT "c_statuses_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_statuses_status_group_id_fkey" FOREIGN KEY ("status_group_id") REFERENCES "c_status_groups" ("status_group_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_statuses_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.c_statuses: 7 rows
/*!40000 ALTER TABLE "c_statuses" DISABLE KEYS */;
INSERT INTO "c_statuses" ("status_id", "status_group_id", "status_code", "name_en", "name_bn", "color_name", "data", "create_user_id", "update_user_id", "created_date", "updated_date", "deleted_date", "version_no", "is_active", "priority") VALUES
	(5, 57, 'Ut libero vero recus', 'Grace Spencer', 'Jorden Barr', 'Price Ayala', NULL, 3, NULL, '2024-09-19 04:44:26.737147', '2024-09-19 04:44:26.737147', NULL, 0, 'true', 17),
	(6, 57, 'Voluptate eum fugiat', 'Todd Abbott', 'Juliet Salinas', 'Nolan Benjamin', NULL, 3, NULL, '2024-09-19 07:14:59.465238', '2024-09-19 07:14:59.465238', NULL, 0, 'true', 99),
	(8, 57, 'Consequatur labore ', 'Miranda Dillard', 'Cherokee White', 'Allen Fisher', NULL, 3, NULL, '2024-09-19 07:15:33.72332', '2024-09-19 07:15:33.724345', NULL, 0, 'true', 87),
	(7, 57, 'Est nulla architecto', 'Martin Kaufman', 'Jaden Bauer', 'Farrah Livingston', NULL, 3, 3, '2024-09-19 07:15:07.21334', '2024-09-19 08:58:58.808202', NULL, 2, 'true', 12),
	(9, 58, 'Consectetur ut aliq', 'Maxine Baird', 'Selma Roman', 'Morgan Pugh', NULL, 3, 3, '2024-09-19 07:18:30.773312', '2024-09-19 09:01:33.724488', NULL, 1, 'true', 83),
	(1, 57, 'Beatae adipisicing e', 'Jessica Holt', 'Aurelia Beasley', 'Helen Stanley', NULL, 3, 3, '2024-09-19 04:13:15.405067', '2024-09-19 10:08:55.298635', NULL, 1, 'false', 87),
	(2, 58, 'Green', 'WOrking', 'WOrking', 'Green', NULL, 3, 3, '2024-09-19 04:43:59.634117', '2024-09-19 12:34:09.804295', NULL, 17, 'false', 52);
/*!40000 ALTER TABLE "c_statuses" ENABLE KEYS */;

-- Dumping structure for table brta.c_status_groups
CREATE TABLE IF NOT EXISTS "c_status_groups" (
	"status_group_id" BIGINT NOT NULL,
	"status_group_code" VARCHAR(100) NOT NULL,
	"name_en" VARCHAR(100) NOT NULL,
	"name_bn" VARCHAR(100) NOT NULL,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NULL DEFAULT NULL,
	"created_date" TIMESTAMP NULL DEFAULT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"is_active" BOOLEAN NULL DEFAULT true,
	"status_group_status_group_id" BIGINT NULL DEFAULT NULL,
	UNIQUE "ukp9ophs7irrmui55twv4tfydey" ("status_group_status_group_id"),
	PRIMARY KEY ("status_group_id"),
	CONSTRAINT "c_status_groups_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_status_groups_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fk3tbxw59hdh27pokdlc91rkju1" FOREIGN KEY ("status_group_status_group_id") REFERENCES "c_status_groups" ("status_group_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.c_status_groups: 4 rows
/*!40000 ALTER TABLE "c_status_groups" DISABLE KEYS */;
INSERT INTO "c_status_groups" ("status_group_id", "status_group_code", "name_en", "name_bn", "create_user_id", "update_user_id", "deleted_date", "version_no", "created_date", "updated_date", "is_active", "status_group_status_group_id") VALUES
	(53, 'Nulla illum ex nequ', 'Wayne Gill', 'Kimberley Townsend', 3, NULL, NULL, 0, '2024-09-18 08:44:59.853093', '2024-09-18 08:44:59.853093', 'false', NULL),
	(58, 'Earum in dolores ex ', 'Application Status', 'আবেদনের অবস্থা', 3, 3, NULL, 2, '2024-09-18 10:09:59.574468', '2024-09-18 10:11:36.018543', 'true', NULL),
	(57, 'Natus laudantium si', 'Card Delivery Status', 'কার্ড ডেলিভারি স্ট্যাটাস', 3, 3, NULL, 1, '2024-09-18 10:09:55.763271', '2024-09-18 10:12:02.232423', 'true', NULL),
	(52, 'Ea qui omnis ducimus', 'Reed Warner', 'Guinevere Battle', 3, 3, NULL, 1, '2024-09-18 08:44:57.254987', '2024-09-18 10:12:09.295821', 'false', NULL),
	(59, 'Dolore ipsa modi ne', 'Heather Colon', 'Clark Schmidt', 3, NULL, NULL, 0, '2024-09-19 06:52:24.471633', '2024-09-19 06:52:24.471633', 'false', NULL),
	(60, 'Obcaecati unde optio', 'Payment Status', 'পেমেন্ট স্ট্যাটাস', 3, 3, NULL, 1, '2024-09-19 06:52:27.726455', '2024-09-19 09:00:16.8428', 'true', NULL);
/*!40000 ALTER TABLE "c_status_groups" ENABLE KEYS */;

-- Dumping structure for table brta.c_vehicle_brands
CREATE TABLE IF NOT EXISTS "c_vehicle_brands" (
	"brand_id" BIGINT NOT NULL,
	"name_en" VARCHAR(100) NOT NULL,
	"name_bn" VARCHAR(100) NOT NULL,
	"maker_id" BIGINT NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("brand_id"),
	CONSTRAINT "c_vehicle_brands_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_vehicle_brands_maker_id_fkey" FOREIGN KEY ("maker_id") REFERENCES "c_vehicle_makers" ("maker_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_vehicle_brands_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.c_vehicle_brands: 0 rows
/*!40000 ALTER TABLE "c_vehicle_brands" DISABLE KEYS */;
/*!40000 ALTER TABLE "c_vehicle_brands" ENABLE KEYS */;

-- Dumping structure for table brta.c_vehicle_classes
CREATE TABLE IF NOT EXISTS "c_vehicle_classes" (
	"vehicle_class_id" BIGINT NOT NULL,
	"name_en" VARCHAR(100) NOT NULL,
	"name_bn" VARCHAR(100) NOT NULL,
	"vehicle_type_id" BIGINT NOT NULL,
	"vehicle_class_code" VARCHAR(100) NOT NULL,
	"cc_min" INTEGER NULL DEFAULT NULL,
	"cc_max" INTEGER NULL DEFAULT NULL,
	"seat_min" SMALLINT NULL DEFAULT NULL,
	"seat_max" SMALLINT NULL DEFAULT NULL,
	"loaded_weight_min_kg" INTEGER NULL DEFAULT NULL,
	"loaded_weight_max_kg" INTEGER NULL DEFAULT NULL,
	"motor_capacity_min_kw" INTEGER NULL DEFAULT NULL,
	"motor_capacity_max_kw" INTEGER NULL DEFAULT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("vehicle_class_id"),
	CONSTRAINT "c_vehicle_classes_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_vehicle_classes_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_vehicle_classes_vehicle_type_id_fkey" FOREIGN KEY ("vehicle_type_id") REFERENCES "c_vehicle_types" ("vehicle_type_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.c_vehicle_classes: 0 rows
/*!40000 ALTER TABLE "c_vehicle_classes" DISABLE KEYS */;
/*!40000 ALTER TABLE "c_vehicle_classes" ENABLE KEYS */;

-- Dumping structure for table brta.c_vehicle_colors
CREATE TABLE IF NOT EXISTS "c_vehicle_colors" (
	"vehicle_color_id" BIGINT NOT NULL,
	"name_en" VARCHAR(100) NOT NULL,
	"name_bn" VARCHAR(100) NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("vehicle_color_id"),
	CONSTRAINT "c_vehicle_colors_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_vehicle_colors_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.c_vehicle_colors: 0 rows
/*!40000 ALTER TABLE "c_vehicle_colors" DISABLE KEYS */;
/*!40000 ALTER TABLE "c_vehicle_colors" ENABLE KEYS */;

-- Dumping structure for table brta.c_vehicle_makers
CREATE TABLE IF NOT EXISTS "c_vehicle_makers" (
	"maker_id" BIGINT NOT NULL,
	"name_en" VARCHAR(100) NOT NULL,
	"name_bn" VARCHAR(100) NOT NULL,
	"country_id" BIGINT NOT NULL,
	"address" VARCHAR(255) NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("maker_id"),
	CONSTRAINT "c_vehicle_makers_country_id_fkey" FOREIGN KEY ("country_id") REFERENCES "c_countries" ("country_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_vehicle_makers_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_vehicle_makers_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.c_vehicle_makers: 0 rows
/*!40000 ALTER TABLE "c_vehicle_makers" DISABLE KEYS */;
/*!40000 ALTER TABLE "c_vehicle_makers" ENABLE KEYS */;

-- Dumping structure for table brta.c_vehicle_routes
CREATE TABLE IF NOT EXISTS "c_vehicle_routes" (
	"route_id" BIGINT NOT NULL,
	"route_permit_type_id" BIGINT NOT NULL,
	"route_name_bn" VARCHAR NOT NULL,
	"route_name_en" VARCHAR NOT NULL,
	"min_district" SMALLINT NOT NULL,
	"max_district" SMALLINT NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("route_id"),
	CONSTRAINT "c_vehicle_routes_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_vehicle_routes_route_permit_type_id_fkey" FOREIGN KEY ("route_permit_type_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_vehicle_routes_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.c_vehicle_routes: 0 rows
/*!40000 ALTER TABLE "c_vehicle_routes" DISABLE KEYS */;
/*!40000 ALTER TABLE "c_vehicle_routes" ENABLE KEYS */;

-- Dumping structure for table brta.c_vehicle_route_districts
CREATE TABLE IF NOT EXISTS "c_vehicle_route_districts" (
	"v_route_disctrict_id" BIGINT NOT NULL,
	"route_id" BIGINT NOT NULL,
	"district_id" BIGINT NOT NULL,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("v_route_disctrict_id"),
	CONSTRAINT "c_vehicle_route_districts_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_vehicle_route_districts_district_id_fkey" FOREIGN KEY ("district_id") REFERENCES "c_locations" ("location_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_vehicle_route_districts_route_id_fkey" FOREIGN KEY ("route_id") REFERENCES "c_vehicle_routes" ("route_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_vehicle_route_districts_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.c_vehicle_route_districts: 0 rows
/*!40000 ALTER TABLE "c_vehicle_route_districts" DISABLE KEYS */;
/*!40000 ALTER TABLE "c_vehicle_route_districts" ENABLE KEYS */;

-- Dumping structure for table brta.c_vehicle_types
CREATE TABLE IF NOT EXISTS "c_vehicle_types" (
	"vehicle_type_id" BIGINT NOT NULL,
	"name_en" VARCHAR(100) NOT NULL,
	"name_bn" VARCHAR(100) NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("vehicle_type_id"),
	CONSTRAINT "c_vehicle_types_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "c_vehicle_types_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.c_vehicle_types: 0 rows
/*!40000 ALTER TABLE "c_vehicle_types" DISABLE KEYS */;
/*!40000 ALTER TABLE "c_vehicle_types" ENABLE KEYS */;

-- Dumping structure for table brta.dl_applicants
CREATE TABLE IF NOT EXISTS "dl_applicants" (
	"dl_applicant_id" BIGINT NOT NULL,
	"service_request_id" BIGINT NULL DEFAULT NULL,
	"nid_number" VARCHAR(25) NOT NULL,
	"dl_language_id" INTEGER NOT NULL,
	"blood_group_id" INTEGER NOT NULL,
	"edu_qualification_id" INTEGER NOT NULL,
	"occupation_id" INTEGER NOT NULL,
	"nationality_id" INTEGER NOT NULL,
	"marital_status_id" INTEGER NOT NULL,
	"spouse_name" VARCHAR(70) NULL DEFAULT NULL,
	"spouse_contact_no" VARCHAR(20) NULL DEFAULT NULL,
	"is_other_cityzenship" BOOLEAN NOT NULL DEFAULT false,
	"office_phone_number" VARCHAR(20) NULL DEFAULT NULL,
	"residence_phone_number" VARCHAR(20) NULL DEFAULT NULL,
	"emergency_contact_name" VARCHAR(20) NOT NULL,
	"emergency_contact_number" VARCHAR(20) NOT NULL,
	"emergency_contact_relationship_id" INTEGER NOT NULL,
	"emergency_contact_email" VARCHAR(100) NOT NULL,
	"present_address_id" INTEGER NOT NULL,
	"permanent_address_id" INTEGER NOT NULL,
	"cd_option_id" INTEGER NOT NULL,
	"cd_address_id" INTEGER NOT NULL,
	"dl_status_id" INTEGER NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NOT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("dl_applicant_id"),
	CONSTRAINT "dl_applicants_blood_group_id_fkey" FOREIGN KEY ("blood_group_id") REFERENCES "c_blood_groups" ("blood_group_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_applicants_cd_address_id_fkey" FOREIGN KEY ("cd_address_id") REFERENCES "s_cd_addresses" ("cd_address_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_applicants_cd_option_id_fkey" FOREIGN KEY ("cd_option_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_applicants_dl_language_id_fkey" FOREIGN KEY ("dl_language_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_applicants_dl_status_id_fkey" FOREIGN KEY ("dl_status_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_applicants_edu_qualification_id_fkey" FOREIGN KEY ("edu_qualification_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_applicants_emergency_contact_relationship_id_fkey" FOREIGN KEY ("emergency_contact_relationship_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_applicants_marital_status_id_fkey" FOREIGN KEY ("marital_status_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_applicants_nationality_id_fkey" FOREIGN KEY ("nationality_id") REFERENCES "c_countries" ("country_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_applicants_occupation_id_fkey" FOREIGN KEY ("occupation_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_applicants_permanent_address_id_fkey" FOREIGN KEY ("permanent_address_id") REFERENCES "s_addresses" ("address_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_applicants_present_address_id_fkey" FOREIGN KEY ("present_address_id") REFERENCES "s_addresses" ("address_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_applicants_service_request_id_fkey" FOREIGN KEY ("service_request_id") REFERENCES "s_service_requests" ("service_request_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.dl_applicants: 0 rows
/*!40000 ALTER TABLE "dl_applicants" DISABLE KEYS */;
/*!40000 ALTER TABLE "dl_applicants" ENABLE KEYS */;

-- Dumping structure for table brta.dl_informations
CREATE TABLE IF NOT EXISTS "dl_informations" (
	"dl_info_id" BIGINT NOT NULL,
	"applicant_id" BIGINT NOT NULL,
	"dl_number" VARCHAR(50) NOT NULL,
	"dl_reference_number" VARCHAR(50) NOT NULL,
	"dl_language_id" INTEGER NOT NULL,
	"application_type_id" INTEGER NOT NULL,
	"license_type_id" INTEGER NOT NULL,
	"blood_group_id" INTEGER NOT NULL,
	"dl_vehicle_class_id" INTEGER NOT NULL,
	"issued_office_id" INTEGER NOT NULL,
	"issue_date" TIMESTAMP NOT NULL,
	"expire_date" TIMESTAMP NOT NULL,
	"edu_qualification_id" INTEGER NOT NULL,
	"occupation_id" INTEGER NOT NULL,
	"nationality_id" INTEGER NOT NULL,
	"marital_status_id" INTEGER NOT NULL,
	"spouse_name" VARCHAR(70) NULL DEFAULT NULL,
	"spouse_contact_no" VARCHAR(20) NULL DEFAULT NULL,
	"is_other_cityzenship" BOOLEAN NOT NULL,
	"office_phone_number" VARCHAR(20) NULL DEFAULT NULL,
	"residence_phone_number" VARCHAR(20) NULL DEFAULT NULL,
	"emergency_contact_name" VARCHAR(70) NOT NULL,
	"emergency_contact_number" VARCHAR(20) NOT NULL,
	"emergency_contact_relationship_id" INTEGER NOT NULL,
	"emergency_contact_email" VARCHAR(100) NOT NULL,
	"present_address_id" INTEGER NOT NULL,
	"permanent_address_id" INTEGER NOT NULL,
	"status_id" INTEGER NOT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("dl_info_id"),
	CONSTRAINT "dl_informations_applicant_id_fkey" FOREIGN KEY ("applicant_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_informations_application_type_id_fkey" FOREIGN KEY ("application_type_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_informations_blood_group_id_fkey" FOREIGN KEY ("blood_group_id") REFERENCES "c_blood_groups" ("blood_group_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_informations_dl_language_id_fkey" FOREIGN KEY ("dl_language_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_informations_dl_vehicle_class_id_fkey" FOREIGN KEY ("dl_vehicle_class_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_informations_edu_qualification_id_fkey" FOREIGN KEY ("edu_qualification_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_informations_emergency_contact_relationship_id_fkey" FOREIGN KEY ("emergency_contact_relationship_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_informations_issued_office_id_fkey" FOREIGN KEY ("issued_office_id") REFERENCES "c_organizations" ("org_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_informations_license_type_id_fkey" FOREIGN KEY ("license_type_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_informations_license_type_id_fkey1" FOREIGN KEY ("license_type_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_informations_marital_status_id_fkey" FOREIGN KEY ("marital_status_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_informations_nationality_id_fkey" FOREIGN KEY ("nationality_id") REFERENCES "c_countries" ("country_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_informations_occupation_id_fkey" FOREIGN KEY ("occupation_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_informations_permanent_address_id_fkey" FOREIGN KEY ("permanent_address_id") REFERENCES "s_addresses" ("address_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_informations_present_address_id_fkey" FOREIGN KEY ("present_address_id") REFERENCES "s_addresses" ("address_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "dl_informations_status_id_fkey" FOREIGN KEY ("status_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.dl_informations: 0 rows
/*!40000 ALTER TABLE "dl_informations" DISABLE KEYS */;
/*!40000 ALTER TABLE "dl_informations" ENABLE KEYS */;

-- Dumping structure for table brta.d_instructor_applicants
CREATE TABLE IF NOT EXISTS "d_instructor_applicants" (
	"dil_applicant_id" BIGINT NOT NULL,
	"service_request_id" BIGINT NOT NULL,
	"dl_info_id" INTEGER NOT NULL,
	"nationality_id" INTEGER NOT NULL,
	"roll_no" INTEGER NOT NULL,
	"office_id" INTEGER NOT NULL,
	"exam_venue_id" INTEGER NOT NULL,
	"exam_test_time_id" INTEGER NOT NULL,
	"issue_date" TIMESTAMP NOT NULL,
	"expire_date" TIMESTAMP NOT NULL,
	"dl_language_id" INTEGER NOT NULL,
	"exam_date" TIMESTAMP NOT NULL,
	"exam_status_id" INTEGER NOT NULL,
	"blood_group_id" INTEGER NOT NULL,
	"edu_qualification_id" INTEGER NOT NULL,
	"technical_edu_id" INTEGER NULL DEFAULT NULL,
	"previosly_disqualified" BOOLEAN NOT NULL DEFAULT false,
	"disqualified_reason" VARCHAR NULL DEFAULT NULL,
	"disqualifier_authority_id" INTEGER NULL DEFAULT NULL,
	"is_goog_character" BOOLEAN NOT NULL DEFAULT true,
	"fit_as_instructor" BOOLEAN NOT NULL DEFAULT true,
	"present_address_id" INTEGER NOT NULL,
	"permanent_address_id" INTEGER NOT NULL,
	"cd_address_id" INTEGER NULL DEFAULT NULL,
	"dil_status_id" INTEGER NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("dil_applicant_id"),
	CONSTRAINT "d_instructor_applicants_blood_group_id_fkey" FOREIGN KEY ("blood_group_id") REFERENCES "c_blood_groups" ("blood_group_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_applicants_cd_address_id_fkey" FOREIGN KEY ("cd_address_id") REFERENCES "s_cd_addresses" ("cd_address_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_applicants_dil_status_id_fkey" FOREIGN KEY ("dil_status_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_applicants_disqualifier_authority_id_fkey" FOREIGN KEY ("disqualifier_authority_id") REFERENCES "c_organizations" ("org_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_applicants_dl_info_id_fkey" FOREIGN KEY ("dl_info_id") REFERENCES "dl_informations" ("dl_info_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_applicants_dl_language_id_fkey" FOREIGN KEY ("dl_language_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_applicants_edu_qualification_id_fkey" FOREIGN KEY ("edu_qualification_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_applicants_exam_status_id_fkey" FOREIGN KEY ("exam_status_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_applicants_exam_test_time_id_fkey" FOREIGN KEY ("exam_test_time_id") REFERENCES "s_dl_exam_test_times" ("exam_test_time_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_applicants_exam_venue_id_fkey" FOREIGN KEY ("exam_venue_id") REFERENCES "c_office_exam_centers" ("office_exam_center_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_applicants_nationality_id_fkey" FOREIGN KEY ("nationality_id") REFERENCES "c_countries" ("country_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_applicants_office_id_fkey" FOREIGN KEY ("office_id") REFERENCES "c_organizations" ("org_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_applicants_permanent_address_id_fkey" FOREIGN KEY ("permanent_address_id") REFERENCES "s_addresses" ("address_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_applicants_present_address_id_fkey" FOREIGN KEY ("present_address_id") REFERENCES "s_addresses" ("address_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_applicants_service_request_id_fkey" FOREIGN KEY ("service_request_id") REFERENCES "s_service_requests" ("service_request_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_applicants_technical_edu_id_fkey" FOREIGN KEY ("technical_edu_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.d_instructor_applicants: 0 rows
/*!40000 ALTER TABLE "d_instructor_applicants" DISABLE KEYS */;
/*!40000 ALTER TABLE "d_instructor_applicants" ENABLE KEYS */;

-- Dumping structure for table brta.d_instructor_license_histories
CREATE TABLE IF NOT EXISTS "d_instructor_license_histories" (
	"dil_history_id" BIGINT NOT NULL,
	"dil_info_id" BIGINT NOT NULL,
	"applicant_id" BIGINT NOT NULL,
	"dl_info_id" INTEGER NOT NULL,
	"nationality_id" INTEGER NOT NULL,
	"office_id" INTEGER NOT NULL,
	"issue_date" TIMESTAMP NOT NULL,
	"expire_date" TIMESTAMP NOT NULL,
	"dil_language_id" INTEGER NOT NULL,
	"blood_group_id" INTEGER NOT NULL,
	"edu_qualification_id" INTEGER NOT NULL,
	"technical_edu_id" INTEGER NULL DEFAULT NULL,
	"status_id" INTEGER NOT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("dil_info_id"),
	CONSTRAINT "d_instructor_license_histories_applicant_id_fkey" FOREIGN KEY ("applicant_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_license_histories_blood_group_id_fkey" FOREIGN KEY ("blood_group_id") REFERENCES "c_blood_groups" ("blood_group_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_license_histories_dil_info_id_fkey" FOREIGN KEY ("dil_info_id") REFERENCES "d_instructor_license_infos" ("dil_info_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_license_histories_dil_language_id_fkey" FOREIGN KEY ("dil_language_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_license_histories_dl_info_id_fkey" FOREIGN KEY ("dl_info_id") REFERENCES "dl_informations" ("dl_info_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_license_histories_edu_qualification_id_fkey" FOREIGN KEY ("edu_qualification_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_license_histories_nationality_id_fkey" FOREIGN KEY ("nationality_id") REFERENCES "c_countries" ("country_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_license_histories_office_id_fkey" FOREIGN KEY ("office_id") REFERENCES "c_organizations" ("org_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_license_histories_status_id_fkey" FOREIGN KEY ("status_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_license_histories_technical_edu_id_fkey" FOREIGN KEY ("technical_edu_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.d_instructor_license_histories: 0 rows
/*!40000 ALTER TABLE "d_instructor_license_histories" DISABLE KEYS */;
/*!40000 ALTER TABLE "d_instructor_license_histories" ENABLE KEYS */;

-- Dumping structure for table brta.d_instructor_license_infos
CREATE TABLE IF NOT EXISTS "d_instructor_license_infos" (
	"dil_info_id" BIGINT NOT NULL,
	"applicant_id" BIGINT NOT NULL,
	"dl_info_id" INTEGER NOT NULL,
	"nationality_id" INTEGER NOT NULL,
	"office_id" INTEGER NOT NULL,
	"issue_date" TIMESTAMP NOT NULL,
	"expire_date" TIMESTAMP NOT NULL,
	"dil_language_id" INTEGER NOT NULL,
	"blood_group_id" INTEGER NOT NULL,
	"edu_qualification_id" INTEGER NOT NULL,
	"technical_edu_id" INTEGER NULL DEFAULT NULL,
	"status_id" INTEGER NOT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("dil_info_id"),
	CONSTRAINT "d_instructor_license_infos_applicant_id_fkey" FOREIGN KEY ("applicant_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_license_infos_blood_group_id_fkey" FOREIGN KEY ("blood_group_id") REFERENCES "c_blood_groups" ("blood_group_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_license_infos_dil_language_id_fkey" FOREIGN KEY ("dil_language_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_license_infos_dl_info_id_fkey" FOREIGN KEY ("dl_info_id") REFERENCES "dl_informations" ("dl_info_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_license_infos_edu_qualification_id_fkey" FOREIGN KEY ("edu_qualification_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_license_infos_nationality_id_fkey" FOREIGN KEY ("nationality_id") REFERENCES "c_countries" ("country_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_license_infos_office_id_fkey" FOREIGN KEY ("office_id") REFERENCES "c_organizations" ("org_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_license_infos_status_id_fkey" FOREIGN KEY ("status_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_instructor_license_infos_technical_edu_id_fkey" FOREIGN KEY ("technical_edu_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.d_instructor_license_infos: 0 rows
/*!40000 ALTER TABLE "d_instructor_license_infos" DISABLE KEYS */;
/*!40000 ALTER TABLE "d_instructor_license_infos" ENABLE KEYS */;

-- Dumping structure for table brta.d_license_histories
CREATE TABLE IF NOT EXISTS "d_license_histories" (
	"dl_history_id" BIGINT NOT NULL,
	"dl_info_id" BIGINT NOT NULL,
	"applicant_id" BIGINT NOT NULL,
	"license_type_id" INTEGER NOT NULL,
	"application_type_id" INTEGER NOT NULL,
	"issued_office_id" INTEGER NOT NULL,
	"issue_date" TIMESTAMP NOT NULL,
	"expire_date" TIMESTAMP NOT NULL,
	"dl_language_id" INTEGER NOT NULL,
	"blood_group_id" INTEGER NOT NULL,
	"edu_qualification_id" INTEGER NOT NULL,
	"occupation_id" INTEGER NOT NULL,
	"nationality_id" INTEGER NOT NULL,
	"marital_status_id" INTEGER NOT NULL,
	"spouse_name" VARCHAR(70) NULL DEFAULT NULL::character varying,
	"spouse_contact_no" VARCHAR(20) NULL DEFAULT NULL::character varying,
	"is_other_cityzenship" BOOLEAN NOT NULL DEFAULT false,
	"office_phone_number" VARCHAR(20) NULL DEFAULT NULL::character varying,
	"residence_phone_number" VARCHAR(20) NULL DEFAULT NULL::character varying,
	"emergency_contact_name" VARCHAR(20) NOT NULL,
	"emergency_contact_number" VARCHAR(20) NOT NULL,
	"emergency_contact_relationship_id" INTEGER NOT NULL,
	"emergency_contact_email" VARCHAR(100) NOT NULL,
	"present_address_id" INTEGER NOT NULL,
	"permanent_address_id" INTEGER NOT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("dl_history_id"),
	CONSTRAINT "d_license_histories_applicant_id_fkey" FOREIGN KEY ("applicant_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_license_histories_application_type_id_fkey" FOREIGN KEY ("application_type_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_license_histories_blood_group_id_fkey" FOREIGN KEY ("blood_group_id") REFERENCES "c_blood_groups" ("blood_group_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_license_histories_dl_language_id_fkey" FOREIGN KEY ("dl_language_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_license_histories_edu_qualification_id_fkey" FOREIGN KEY ("edu_qualification_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_license_histories_emergency_contact_relationship_id_fkey" FOREIGN KEY ("emergency_contact_relationship_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_license_histories_issued_office_id_fkey" FOREIGN KEY ("issued_office_id") REFERENCES "c_organizations" ("org_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_license_histories_license_type_id_fkey" FOREIGN KEY ("license_type_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_license_histories_marital_status_id_fkey" FOREIGN KEY ("marital_status_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_license_histories_nationality_id_fkey" FOREIGN KEY ("nationality_id") REFERENCES "c_countries" ("country_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_license_histories_occupation_id_fkey" FOREIGN KEY ("occupation_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_license_histories_permanent_address_id_fkey" FOREIGN KEY ("permanent_address_id") REFERENCES "s_addresses" ("address_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "d_license_histories_present_address_id_fkey" FOREIGN KEY ("present_address_id") REFERENCES "s_addresses" ("address_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.d_license_histories: 0 rows
/*!40000 ALTER TABLE "d_license_histories" DISABLE KEYS */;
/*!40000 ALTER TABLE "d_license_histories" ENABLE KEYS */;

-- Dumping structure for table brta.f_driving_related_service_fees
CREATE TABLE IF NOT EXISTS "f_driving_related_service_fees" (
	"dr_service_fees_id" BIGINT NOT NULL,
	"service_id" BIGINT NOT NULL,
	"is_yearly_fee" BOOLEAN NOT NULL DEFAULT true,
	"main_fee" INTEGER NOT NULL,
	"late_fee" INTEGER NOT NULL DEFAULT 0,
	"vat_percentage" SMALLINT NOT NULL,
	"sd_percentage" SMALLINT NULL DEFAULT NULL,
	"effective_start_date" TIMESTAMP NOT NULL,
	"effective_end_date" TIMESTAMP NULL DEFAULT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("dr_service_fees_id"),
	CONSTRAINT "f_driving_related_service_fees_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "f_driving_related_service_fees_service_id_fkey" FOREIGN KEY ("service_id") REFERENCES "c_services" ("service_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "f_driving_related_service_fees_service_id_fkey1" FOREIGN KEY ("service_id") REFERENCES "c_services" ("service_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "f_driving_related_service_fees_service_id_fkey2" FOREIGN KEY ("service_id") REFERENCES "c_services" ("service_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "f_driving_related_service_fees_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.f_driving_related_service_fees: 0 rows
/*!40000 ALTER TABLE "f_driving_related_service_fees" DISABLE KEYS */;
/*!40000 ALTER TABLE "f_driving_related_service_fees" ENABLE KEYS */;

-- Dumping structure for table brta.f_driving_related_service_fees_histories
CREATE TABLE IF NOT EXISTS "f_driving_related_service_fees_histories" (
	"dr_service_fees_history_id" BIGINT NOT NULL,
	"service_id" BIGINT NOT NULL,
	"is_yearly_fee" BOOLEAN NOT NULL DEFAULT true,
	"main_fee" INTEGER NOT NULL,
	"late_fee" INTEGER NOT NULL DEFAULT 0,
	"vat_percentage" SMALLINT NOT NULL,
	"sd_percentage" SMALLINT NULL DEFAULT NULL,
	"effective_start_date" TIMESTAMP NOT NULL,
	"effective_end_date" TIMESTAMP NULL DEFAULT NULL,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("dr_service_fees_history_id"),
	CONSTRAINT "f_driving_related_service_fees_histories_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "f_driving_related_service_fees_histories_service_id_fkey" FOREIGN KEY ("service_id") REFERENCES "c_services" ("service_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "f_driving_related_service_fees_histories_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.f_driving_related_service_fees_histories: 0 rows
/*!40000 ALTER TABLE "f_driving_related_service_fees_histories" DISABLE KEYS */;
/*!40000 ALTER TABLE "f_driving_related_service_fees_histories" ENABLE KEYS */;

-- Dumping structure for table brta.f_route_permit_service_fees
CREATE TABLE IF NOT EXISTS "f_route_permit_service_fees" (
	"rp_service_fees_id" BIGINT NOT NULL,
	"service_id" INTEGER NOT NULL,
	"route_permit_type_id" INTEGER NOT NULL,
	"route_id" INTEGER NULL DEFAULT NULL,
	"min_route_number" INTEGER NULL DEFAULT NULL,
	"max_route_number" INTEGER NULL DEFAULT NULL,
	"is_yearly_fee" BOOLEAN NOT NULL DEFAULT true,
	"main_fee" INTEGER NOT NULL,
	"late_fee" INTEGER NOT NULL DEFAULT 0,
	"vat_percentage" SMALLINT NOT NULL,
	"sd_percentage" SMALLINT NULL DEFAULT NULL,
	"effective_start_date" TIMESTAMP NOT NULL,
	"effective_end_date" TIMESTAMP NULL DEFAULT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("rp_service_fees_id"),
	CONSTRAINT "f_route_permit_service_fees_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "f_route_permit_service_fees_route_id_fkey" FOREIGN KEY ("route_id") REFERENCES "c_vehicle_routes" ("route_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "f_route_permit_service_fees_route_permit_type_id_fkey" FOREIGN KEY ("route_permit_type_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "f_route_permit_service_fees_service_id_fkey" FOREIGN KEY ("service_id") REFERENCES "c_services" ("service_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "f_route_permit_service_fees_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.f_route_permit_service_fees: 0 rows
/*!40000 ALTER TABLE "f_route_permit_service_fees" DISABLE KEYS */;
/*!40000 ALTER TABLE "f_route_permit_service_fees" ENABLE KEYS */;

-- Dumping structure for table brta.f_route_permit_service_fees_histories
CREATE TABLE IF NOT EXISTS "f_route_permit_service_fees_histories" (
	"rp_service_fees_history_id" BIGINT NOT NULL,
	"service_id" INTEGER NOT NULL,
	"route_permit_type_id" INTEGER NOT NULL,
	"route_id" INTEGER NULL DEFAULT NULL,
	"min_route_number" INTEGER NULL DEFAULT NULL,
	"max_route_number" INTEGER NULL DEFAULT NULL,
	"is_yearly_fee" BOOLEAN NOT NULL DEFAULT true,
	"main_fee" INTEGER NOT NULL,
	"late_fee" INTEGER NOT NULL DEFAULT 0,
	"vat_percentage" SMALLINT NOT NULL,
	"sd_percentage" SMALLINT NULL DEFAULT NULL,
	"effective_start_date" TIMESTAMP NOT NULL,
	"effective_end_date" TIMESTAMP NULL DEFAULT NULL,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("rp_service_fees_history_id"),
	CONSTRAINT "f_route_permit_service_fees_histories_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "f_route_permit_service_fees_histories_route_id_fkey" FOREIGN KEY ("route_id") REFERENCES "c_vehicle_routes" ("route_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "f_route_permit_service_fees_histories_route_permit_type_id_fkey" FOREIGN KEY ("route_permit_type_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "f_route_permit_service_fees_histories_service_id_fkey" FOREIGN KEY ("service_id") REFERENCES "c_services" ("service_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "f_route_permit_service_fees_histories_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.f_route_permit_service_fees_histories: 0 rows
/*!40000 ALTER TABLE "f_route_permit_service_fees_histories" DISABLE KEYS */;
/*!40000 ALTER TABLE "f_route_permit_service_fees_histories" ENABLE KEYS */;

-- Dumping structure for table brta.f_service_fees
CREATE TABLE IF NOT EXISTS "f_service_fees" (
	"service_fees_id" BIGINT NOT NULL,
	"service_id" INTEGER NOT NULL,
	"service_type_id" INTEGER NOT NULL,
	"vehicle_type_id" INTEGER NOT NULL,
	"vehicle_class_id" INTEGER NULL DEFAULT NULL,
	"hire_purchase" BOOLEAN NULL DEFAULT NULL,
	"is_yearly_fee" BOOLEAN NOT NULL DEFAULT true,
	"main_fee" INTEGER NOT NULL,
	"late_fee" INTEGER NULL DEFAULT 0,
	"vat_percentage" SMALLINT NOT NULL,
	"sd_percentage" SMALLINT NULL DEFAULT NULL,
	"effective_start_date" TIMESTAMP NOT NULL,
	"effective_end_date" TIMESTAMP NULL DEFAULT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("service_fees_id"),
	CONSTRAINT " f_service_fees_service_id_fkey" FOREIGN KEY ("service_id") REFERENCES "c_services" ("service_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "f_service_fees_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "f_service_fees_service_type_id_fkey" FOREIGN KEY ("service_type_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "f_service_fees_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "f_service_fees_vehicle_class_id_fkey" FOREIGN KEY ("vehicle_class_id") REFERENCES "c_vehicle_classes" ("vehicle_class_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "f_service_fees_vehicle_type_id_fkey" FOREIGN KEY ("vehicle_type_id") REFERENCES "c_vehicle_types" ("vehicle_type_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.f_service_fees: 0 rows
/*!40000 ALTER TABLE "f_service_fees" DISABLE KEYS */;
/*!40000 ALTER TABLE "f_service_fees" ENABLE KEYS */;

-- Dumping structure for table brta.f_service_fees_histories
CREATE TABLE IF NOT EXISTS "f_service_fees_histories" (
	"service_fees_history_id" BIGINT NOT NULL,
	"service_id" INTEGER NOT NULL,
	"service_type_id" INTEGER NOT NULL,
	"vehicle_type_id" INTEGER NULL DEFAULT NULL,
	"vehicle_class_id" INTEGER NULL DEFAULT NULL,
	"hire_purchase" BOOLEAN NULL DEFAULT NULL,
	"is_yearly_fee" BOOLEAN NOT NULL,
	"main_fee" INTEGER NOT NULL,
	"late_fee" INTEGER NULL DEFAULT NULL,
	"vat_percentage" SMALLINT NOT NULL,
	"sd_percentage" SMALLINT NULL DEFAULT NULL,
	"effective_start_date" TIMESTAMP NOT NULL,
	"effective_end_date" TIMESTAMP NULL DEFAULT NULL,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("service_fees_history_id"),
	CONSTRAINT "f_service_fees_histories_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "f_service_fees_histories_service_id_fkey" FOREIGN KEY ("service_id") REFERENCES "c_services" ("service_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "f_service_fees_histories_service_type_id_fkey" FOREIGN KEY ("service_type_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "f_service_fees_histories_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "f_service_fees_histories_vehicle_class_id_fkey" FOREIGN KEY ("vehicle_class_id") REFERENCES "c_vehicle_classes" ("vehicle_class_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "f_service_fees_histories_vehicle_type_id_fkey" FOREIGN KEY ("vehicle_type_id") REFERENCES "c_vehicle_types" ("vehicle_type_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.f_service_fees_histories: 0 rows
/*!40000 ALTER TABLE "f_service_fees_histories" DISABLE KEYS */;
/*!40000 ALTER TABLE "f_service_fees_histories" ENABLE KEYS */;

-- Dumping structure for table brta.medias
CREATE TABLE IF NOT EXISTS "medias" (
	"media_id" BIGINT NOT NULL,
	"model" VARCHAR NULL DEFAULT NULL,
	"model_id" INTEGER NULL DEFAULT NULL,
	"service_id" INTEGER NULL DEFAULT NULL,
	"status_id" INTEGER NULL DEFAULT NULL,
	"original_name" VARCHAR NULL DEFAULT NULL,
	"folder_path" VARCHAR NULL DEFAULT NULL,
	"file" VARCHAR NULL DEFAULT NULL,
	"extension" VARCHAR(20) NULL DEFAULT NULL,
	"type" VARCHAR(20) NULL DEFAULT NULL,
	"size" INTEGER NULL DEFAULT NULL,
	"created_date" TIMESTAMP NULL DEFAULT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NULL DEFAULT NULL,
	PRIMARY KEY ("media_id"),
	CONSTRAINT "medias_service_id_fkey" FOREIGN KEY ("service_id") REFERENCES "c_services" ("service_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "medias_status_id_fkey" FOREIGN KEY ("status_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.medias: 0 rows
/*!40000 ALTER TABLE "medias" DISABLE KEYS */;
/*!40000 ALTER TABLE "medias" ENABLE KEYS */;

-- Dumping structure for table brta.s_activity_logs
CREATE TABLE IF NOT EXISTS "s_activity_logs" (
	"activity_log_id" BIGINT NOT NULL,
	"subject" VARCHAR(255) NOT NULL,
	"description" VARCHAR(255) NULL DEFAULT NULL::character varying,
	"status_id" INTEGER NOT NULL,
	"model" VARCHAR(255) NOT NULL,
	"model_id" INTEGER NOT NULL,
	"create_user_id" BIGINT NOT NULL,
	"created_date" TIMESTAMP NOT NULL,
	PRIMARY KEY ("activity_log_id"),
	CONSTRAINT "s_activity_logs_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_activity_logs_status_id_fkey" FOREIGN KEY ("status_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.s_activity_logs: 0 rows
/*!40000 ALTER TABLE "s_activity_logs" DISABLE KEYS */;
/*!40000 ALTER TABLE "s_activity_logs" ENABLE KEYS */;

-- Dumping structure for table brta.s_addresses
CREATE TABLE IF NOT EXISTS "s_addresses" (
	"address_id" BIGINT NOT NULL,
	"applicant _id" BIGINT NOT NULL,
	"model" VARCHAR(255) NOT NULL,
	"model_id" INTEGER NOT NULL,
	"holding_house_village" VARCHAR(100) NOT NULL,
	"road_block_sector_colony" VARCHAR(100) NULL DEFAULT NULL,
	"location_id" INTEGER NOT NULL,
	"post_code" VARCHAR(20) NOT NULL,
	"mobile_number" VARCHAR(20) NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("address_id"),
	CONSTRAINT "s_addresses_location_id_fkey" FOREIGN KEY ("location_id") REFERENCES "c_locations" ("location_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.s_addresses: 0 rows
/*!40000 ALTER TABLE "s_addresses" DISABLE KEYS */;
/*!40000 ALTER TABLE "s_addresses" ENABLE KEYS */;

-- Dumping structure for table brta.s_application_dl_classes
CREATE TABLE IF NOT EXISTS "s_application_dl_classes" (
	"application_dl_class_id" BIGINT NOT NULL,
	"dl_applicant_id" BIGINT NULL DEFAULT NULL,
	"dl_class_id" INTEGER NULL DEFAULT NULL,
	"created_date" TIMESTAMP NULL DEFAULT NULL,
	PRIMARY KEY ("application_dl_class_id"),
	CONSTRAINT "s_application_dl_classes_dl_applicant_id_fkey" FOREIGN KEY ("dl_applicant_id") REFERENCES "dl_applicants" ("dl_applicant_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_application_dl_classes_dl_class_id_fkey" FOREIGN KEY ("dl_class_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.s_application_dl_classes: 0 rows
/*!40000 ALTER TABLE "s_application_dl_classes" DISABLE KEYS */;
/*!40000 ALTER TABLE "s_application_dl_classes" ENABLE KEYS */;

-- Dumping structure for table brta.s_cd_addresses
CREATE TABLE IF NOT EXISTS "s_cd_addresses" (
	"cd_address_id" BIGINT NOT NULL,
	"model" VARCHAR NULL DEFAULT NULL,
	"model_id" INTEGER NULL DEFAULT NULL,
	"address_type_id" INTEGER NOT NULL,
	"holding_house_village" VARCHAR(100) NOT NULL,
	"road_block_sector_colony" VARCHAR(100) NULL DEFAULT NULL,
	"location_id" INTEGER NOT NULL,
	"post_code" VARCHAR(20) NOT NULL,
	"mobile_number" VARCHAR(20) NULL DEFAULT NULL,
	"card_status_id" INTEGER NOT NULL,
	"delivery_status_id" INTEGER NULL DEFAULT NULL,
	"delivered_user_id" BIGINT NULL DEFAULT NULL,
	"delivered_date" TIMESTAMP NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("cd_address_id"),
	CONSTRAINT "s_cd_addresses_address_type_id_fkey" FOREIGN KEY ("address_type_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_cd_addresses_card_status_id_fkey" FOREIGN KEY ("card_status_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_cd_addresses_delivered_user_id_fkey" FOREIGN KEY ("delivered_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_cd_addresses_delivery_status_id_fkey" FOREIGN KEY ("delivery_status_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_cd_addresses_location_id_fkey" FOREIGN KEY ("location_id") REFERENCES "c_locations" ("location_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.s_cd_addresses: 0 rows
/*!40000 ALTER TABLE "s_cd_addresses" DISABLE KEYS */;
/*!40000 ALTER TABLE "s_cd_addresses" ENABLE KEYS */;

-- Dumping structure for table brta.s_dl_exam_test_times
CREATE TABLE IF NOT EXISTS "s_dl_exam_test_times" (
	"exam_test_time_id" BIGINT NOT NULL,
	"exam_name_id" INTEGER NOT NULL,
	"exam_time" TIME NOT NULL,
	"office_id" INTEGER NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("exam_test_time_id"),
	CONSTRAINT "s_dl_exam_test_times_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_dl_exam_test_times_exam_name_id_fkey" FOREIGN KEY ("exam_name_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_dl_exam_test_times_office_id_fkey" FOREIGN KEY ("office_id") REFERENCES "c_organizations" ("org_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_dl_exam_test_times_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.s_dl_exam_test_times: 0 rows
/*!40000 ALTER TABLE "s_dl_exam_test_times" DISABLE KEYS */;
/*!40000 ALTER TABLE "s_dl_exam_test_times" ENABLE KEYS */;

-- Dumping structure for table brta.s_dl_info_classes
CREATE TABLE IF NOT EXISTS "s_dl_info_classes" (
	"dl_info_class_id" BIGINT NOT NULL,
	"dl_info_id" INTEGER NULL DEFAULT NULL,
	"dl_class_id" INTEGER NULL DEFAULT NULL,
	"created_date" TIMESTAMP NULL DEFAULT NULL,
	PRIMARY KEY ("dl_info_class_id"),
	CONSTRAINT "s_dl_info_classes_dl_class_id_fkey" FOREIGN KEY ("dl_class_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_dl_info_classes_dl_info_id_fkey" FOREIGN KEY ("dl_info_id") REFERENCES "dl_informations" ("dl_info_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.s_dl_info_classes: 0 rows
/*!40000 ALTER TABLE "s_dl_info_classes" DISABLE KEYS */;
/*!40000 ALTER TABLE "s_dl_info_classes" ENABLE KEYS */;

-- Dumping structure for table brta.s_driving_learner_licenses
CREATE TABLE IF NOT EXISTS "s_driving_learner_licenses" (
	"dll_id" BIGINT NOT NULL,
	"service_request_id" BIGINT NOT NULL,
	"dl_applicant_id" BIGINT NOT NULL,
	"photo_media_id" BIGINT NOT NULL,
	"learner_number" BIGINT NOT NULL,
	"learner_applicant_id" BIGINT NOT NULL,
	"roll_no" INTEGER NOT NULL,
	"office_id" INTEGER NOT NULL,
	"exam_venue_id" INTEGER NOT NULL,
	"issue_date" TIMESTAMP NOT NULL,
	"expire_date" TIMESTAMP NOT NULL,
	"dl_language_id" INTEGER NOT NULL,
	"application_type_id" INTEGER NOT NULL,
	"license_type_id" INTEGER NOT NULL,
	"exam_date" TIMESTAMP NOT NULL,
	"exam_status_id" INTEGER NOT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("dll_id"),
	CONSTRAINT "s_driving_learner_licenses_application_type_id_fkey" FOREIGN KEY ("application_type_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_driving_learner_licenses_dl_applicant_id_fkey" FOREIGN KEY ("dl_applicant_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_driving_learner_licenses_dl_language_id_fkey" FOREIGN KEY ("dl_language_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_driving_learner_licenses_exam_status_id_fkey" FOREIGN KEY ("exam_status_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_driving_learner_licenses_exam_venue_id_fkey" FOREIGN KEY ("exam_venue_id") REFERENCES "c_office_exam_centers" ("office_exam_center_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_driving_learner_licenses_learner_applicant_id_fkey" FOREIGN KEY ("learner_applicant_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_driving_learner_licenses_license_type_id_fkey" FOREIGN KEY ("license_type_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_driving_learner_licenses_office_id_fkey" FOREIGN KEY ("office_id") REFERENCES "c_organizations" ("org_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_driving_learner_licenses_photo_media_id_fkey" FOREIGN KEY ("photo_media_id") REFERENCES "medias" ("media_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_driving_learner_licenses_service_request_id_fkey" FOREIGN KEY ("service_request_id") REFERENCES "s_service_requests" ("service_request_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.s_driving_learner_licenses: 0 rows
/*!40000 ALTER TABLE "s_driving_learner_licenses" DISABLE KEYS */;
/*!40000 ALTER TABLE "s_driving_learner_licenses" ENABLE KEYS */;

-- Dumping structure for table brta.s_service_requests
CREATE TABLE IF NOT EXISTS "s_service_requests" (
	"service_request_id" BIGINT NOT NULL,
	"service_request_no" VARCHAR(25) NOT NULL,
	"service_id" INTEGER NOT NULL,
	"org_id" INTEGER NOT NULL,
	"applicant_id" INTEGER NOT NULL,
	"application_date" TIMESTAMP NOT NULL,
	"volume_no" INTEGER NULL DEFAULT NULL,
	"page_no" INTEGER NULL DEFAULT NULL,
	"forward_date_for_inspection" TIMESTAMP NULL DEFAULT NULL,
	"inspector_id" BIGINT NULL DEFAULT NULL,
	"inspection_status_id" INTEGER NULL DEFAULT NULL,
	"inspection_remarks" VARCHAR(255) NULL DEFAULT NULL,
	"inspection_date" TIMESTAMP NULL DEFAULT NULL,
	"dl_exam_status_id" INTEGER NULL DEFAULT NULL,
	"dl_exam_remarks" VARCHAR NULL DEFAULT NULL,
	"dl_exam_date" TIMESTAMP NULL DEFAULT NULL,
	"forward_date_for_revenue" TIMESTAMP NULL DEFAULT NULL,
	"revenue_checker_id" BIGINT NULL DEFAULT NULL,
	"revenue_status_id" INTEGER NULL DEFAULT NULL,
	"revenue_remarks" VARCHAR NULL DEFAULT NULL,
	"revenue_check_date" TIMESTAMP NULL DEFAULT NULL,
	"approval_id" BIGINT NULL DEFAULT NULL,
	"approval_remarks" VARCHAR NULL DEFAULT NULL,
	"approval_date" TIMESTAMP NULL DEFAULT NULL,
	"rejection_date" TIMESTAMP NULL DEFAULT NULL,
	"application_status_id" INTEGER NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("service_request_id"),
	CONSTRAINT "s_service_requests_applicant_id_fkey" FOREIGN KEY ("applicant_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_service_requests_application_status_id_fkey" FOREIGN KEY ("application_status_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_service_requests_approval_id_fkey" FOREIGN KEY ("approval_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_service_requests_dl_exam_status_id_fkey" FOREIGN KEY ("dl_exam_status_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_service_requests_inspection_status_id_fkey" FOREIGN KEY ("inspection_status_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_service_requests_inspector_id_fkey" FOREIGN KEY ("inspector_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_service_requests_org_id_fkey" FOREIGN KEY ("org_id") REFERENCES "c_organizations" ("org_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_service_requests_org_id_fkey1" FOREIGN KEY ("org_id") REFERENCES "c_organizations" ("org_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_service_requests_revenue_checker_id_fkey" FOREIGN KEY ("revenue_checker_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_service_requests_revenue_status_id_fkey" FOREIGN KEY ("revenue_status_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_service_requests_service_id_fkey" FOREIGN KEY ("service_id") REFERENCES "c_services" ("service_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.s_service_requests: 0 rows
/*!40000 ALTER TABLE "s_service_requests" DISABLE KEYS */;
/*!40000 ALTER TABLE "s_service_requests" ENABLE KEYS */;

-- Dumping structure for table brta.s_users
CREATE TABLE IF NOT EXISTS "s_users" (
	"user_id" BIGINT NOT NULL,
	"name_en" VARCHAR(40) NULL DEFAULT NULL,
	"name_bn" VARCHAR(40) NOT NULL,
	"username" VARCHAR(15) NOT NULL,
	"mobile" VARCHAR(13) NOT NULL,
	"email" VARCHAR(40) NULL DEFAULT NULL::character varying,
	"password" VARCHAR(100) NULL DEFAULT NULL,
	"user_type_id" INTEGER NULL DEFAULT NULL,
	"designation_id" BIGINT NULL DEFAULT NULL,
	"profile_completed" BOOLEAN NOT NULL DEFAULT false,
	"is_active" BOOLEAN NULL DEFAULT true,
	"create_user_id" BIGINT NULL DEFAULT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NULL DEFAULT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	"id" BIGINT NOT NULL,
	PRIMARY KEY ("user_id"),
	UNIQUE "ukg6w3g55j7mm7jfji66cc4w16q" ("username"),
	UNIQUE "uk2lcv26kt28p27enwkw01c2s1g" ("email"),
	UNIQUE "uk9a6yixj907hge84vkailkahu9" ("mobile"),
	UNIQUE "ukqdah6j9ojqf45gj7yfrsftfqh" ("email", "mobile")
);

-- Dumping data for table brta.s_users: 2 rows
/*!40000 ALTER TABLE "s_users" DISABLE KEYS */;
INSERT INTO "s_users" ("user_id", "name_en", "name_bn", "username", "mobile", "email", "password", "user_type_id", "designation_id", "profile_completed", "is_active", "create_user_id", "update_user_id", "created_date", "updated_date", "deleted_date", "version_no", "id") VALUES
	(3, 'Rasel Kazi', 'রাসেল কাজী', 'rasel', '01638584623', 'rasel@gmail.com', '$2y$10$P4UTnMnwFj15aia9Bnk5Re52JQIqYpo7TK9lFnKCg4dqaIVciPxeu', NULL, NULL, 'false', 'true', NULL, NULL, NULL, NULL, NULL, 1, 2),
	(2, 'Ruhul Amin', 'রুহুল আমিন', 'ruhul', '01638584622', 'ruhul@gmail.com', '$2y$10$P4UTnMnwFj15aia9Bnk5Re52JQIqYpo7TK9lFnKCg4dqaIVciPxeu', NULL, NULL, 'false', 'true', NULL, NULL, NULL, NULL, NULL, 1, 3);
/*!40000 ALTER TABLE "s_users" ENABLE KEYS */;

-- Dumping structure for table brta.s_user_details
CREATE TABLE IF NOT EXISTS "s_user_details" (
	"user_detail_id" BIGINT NOT NULL,
	"user_id" BIGINT NOT NULL,
	"passport_number" VARCHAR NULL DEFAULT NULL,
	"birth_reg_number" VARCHAR NULL DEFAULT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("user_detail_id"),
	CONSTRAINT "s_user_details_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_user_details_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_user_details_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.s_user_details: 0 rows
/*!40000 ALTER TABLE "s_user_details" DISABLE KEYS */;
/*!40000 ALTER TABLE "s_user_details" ENABLE KEYS */;

-- Dumping structure for table brta.s_user_email_histories
CREATE TABLE IF NOT EXISTS "s_user_email_histories" (
	"user_email_id" BIGINT NOT NULL,
	"user_id" BIGINT NOT NULL,
	"email" VARCHAR(100) NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("user_email_id"),
	CONSTRAINT "s_user_email_histories_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.s_user_email_histories: 0 rows
/*!40000 ALTER TABLE "s_user_email_histories" DISABLE KEYS */;
/*!40000 ALTER TABLE "s_user_email_histories" ENABLE KEYS */;

-- Dumping structure for table brta.s_user_mobile_histories
CREATE TABLE IF NOT EXISTS "s_user_mobile_histories" (
	"user_mobile_id" BIGINT NOT NULL,
	"user_id" BIGINT NOT NULL,
	"mobile_number" VARCHAR(20) NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"created_date" TIMESTAMP NOT NULL,
	"version_no" INTEGER NOT NULL DEFAULT 1,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	PRIMARY KEY ("user_mobile_id"),
	CONSTRAINT "s_user_mobile_histories_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.s_user_mobile_histories: 0 rows
/*!40000 ALTER TABLE "s_user_mobile_histories" DISABLE KEYS */;
/*!40000 ALTER TABLE "s_user_mobile_histories" ENABLE KEYS */;

-- Dumping structure for table brta.s_user_nid_infos
CREATE TABLE IF NOT EXISTS "s_user_nid_infos" (
	"user_nid_info_id" BIGINT NOT NULL,
	"user_id" BIGINT NOT NULL,
	"nid_number" VARCHAR(100) NOT NULL,
	"dob" DATE NOT NULL,
	"name_bn" VARCHAR(100) NOT NULL,
	"name_en" VARCHAR(100) NOT NULL,
	"father_or_husband_name_en" VARCHAR(100) NULL DEFAULT NULL,
	"father_or_husband_name_bn" VARCHAR(100) NOT NULL,
	"mother_name_en" VARCHAR(100) NULL DEFAULT NULL,
	"mother_name_bn" VARCHAR(100) NOT NULL,
	"address_en" VARCHAR(100) NULL DEFAULT NULL,
	"address_bn" VARCHAR(100) NOT NULL,
	"gender_id" INTEGER NOT NULL,
	"mobile" VARCHAR(20) NOT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("user_nid_info_id"),
	CONSTRAINT "s_user_nid_infos_gender_id_fkey" FOREIGN KEY ("gender_id") REFERENCES "c_genders" ("gender_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_user_nid_infos_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.s_user_nid_infos: 0 rows
/*!40000 ALTER TABLE "s_user_nid_infos" DISABLE KEYS */;
/*!40000 ALTER TABLE "s_user_nid_infos" ENABLE KEYS */;

-- Dumping structure for table brta.s_user_office_histories
CREATE TABLE IF NOT EXISTS "s_user_office_histories" (
	"user_office_history_id" BIGINT NOT NULL,
	"user_id" BIGINT NOT NULL,
	"role_id" INTEGER NOT NULL,
	"designation_id" INTEGER NOT NULL,
	"org_id" INTEGER NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NOT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("user_office_history_id"),
	CONSTRAINT "s_user_office_histories_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_user_office_histories_designation_id_fkey" FOREIGN KEY ("designation_id") REFERENCES "c_designations" ("designation_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_user_office_histories_org_id_fkey" FOREIGN KEY ("org_id") REFERENCES "c_organizations" ("org_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_user_office_histories_role_id_fkey" FOREIGN KEY ("role_id") REFERENCES "u_roles" ("role_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_user_office_histories_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_user_office_histories_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.s_user_office_histories: 0 rows
/*!40000 ALTER TABLE "s_user_office_histories" DISABLE KEYS */;
/*!40000 ALTER TABLE "s_user_office_histories" ENABLE KEYS */;

-- Dumping structure for table brta.s_user_organizations
CREATE TABLE IF NOT EXISTS "s_user_organizations" (
	"user_org_id" BIGINT NOT NULL,
	"user_id" BIGINT NOT NULL,
	"org_id" INTEGER NOT NULL,
	"role_id" INTEGER NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("user_org_id"),
	CONSTRAINT "s_user_offices_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_user_offices_org_id_fkey" FOREIGN KEY ("org_id") REFERENCES "c_organizations" ("org_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_user_offices_role_id_fkey" FOREIGN KEY ("role_id") REFERENCES "u_roles" ("role_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_user_offices_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "s_user_offices_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.s_user_organizations: 0 rows
/*!40000 ALTER TABLE "s_user_organizations" DISABLE KEYS */;
/*!40000 ALTER TABLE "s_user_organizations" ENABLE KEYS */;

-- Dumping structure for table brta.s_user_password_histories
CREATE TABLE IF NOT EXISTS "s_user_password_histories" (
	"user_password_id" BIGINT NOT NULL,
	"user_id" BIGINT NOT NULL,
	"password" VARCHAR(255) NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("user_password_id"),
	CONSTRAINT "s_user_password_histories_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.s_user_password_histories: 0 rows
/*!40000 ALTER TABLE "s_user_password_histories" DISABLE KEYS */;
/*!40000 ALTER TABLE "s_user_password_histories" ENABLE KEYS */;

-- Dumping structure for table brta.s_user_tin_infos
CREATE TABLE IF NOT EXISTS "s_user_tin_infos" (
	"user_tin_info_id" BIGINT NOT NULL,
	"user_id" BIGINT NOT NULL,
	"tin_number" VARCHAR(100) NOT NULL,
	"tin_person_or_company_name" VARCHAR(255) NOT NULL,
	"tin_zone" VARCHAR(100) NOT NULL,
	"tin_circle" VARCHAR(100) NULL DEFAULT NULL,
	"tin_type" VARCHAR(100) NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("user_tin_info_id"),
	CONSTRAINT "s_user_tin_infos_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.s_user_tin_infos: 0 rows
/*!40000 ALTER TABLE "s_user_tin_infos" DISABLE KEYS */;
/*!40000 ALTER TABLE "s_user_tin_infos" ENABLE KEYS */;

-- Dumping structure for table brta.u_permissions
CREATE TABLE IF NOT EXISTS "u_permissions" (
	"permission_id" BIGINT NOT NULL,
	"name" VARCHAR(100) NOT NULL,
	"permission_code" VARCHAR(100) NOT NULL,
	"type" INTEGER NOT NULL,
	"parent_permission_id" INTEGER NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("permission_id")
);

-- Dumping data for table brta.u_permissions: 0 rows
/*!40000 ALTER TABLE "u_permissions" DISABLE KEYS */;
/*!40000 ALTER TABLE "u_permissions" ENABLE KEYS */;

-- Dumping structure for table brta.u_roles
CREATE TABLE IF NOT EXISTS "u_roles" (
	"role_id" BIGINT NOT NULL,
	"name_bn" VARCHAR(100) NOT NULL,
	"name_en" VARCHAR(100) NULL DEFAULT NULL::character varying,
	"role_code" VARCHAR(100) NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("role_id"),
	CONSTRAINT "u_roles_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "u_roles_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.u_roles: 0 rows
/*!40000 ALTER TABLE "u_roles" DISABLE KEYS */;
INSERT INTO "u_roles" ("role_id", "name_bn", "name_en", "role_code", "is_active", "create_user_id", "update_user_id", "created_date", "updated_date", "deleted_date", "version_no") VALUES
	(3, 'Admin', NULL, 'admin', 'true', 2, NULL, '2024-09-05 16:26:30', NULL, NULL, 1),
	(6, 'User', NULL, 'user', 'true', 2, NULL, '2024-09-05 16:27:03', NULL, NULL, 1);
/*!40000 ALTER TABLE "u_roles" ENABLE KEYS */;

-- Dumping structure for table brta.u_role_permissions
CREATE TABLE IF NOT EXISTS "u_role_permissions" (
	"role_permission_id" BIGINT NOT NULL,
	"role_id" INTEGER NOT NULL,
	"permission_id" INTEGER NOT NULL,
	"created_date" TIMESTAMP NOT NULL,
	PRIMARY KEY ("role_permission_id"),
	CONSTRAINT "u_role_permissions_permission_id_fkey" FOREIGN KEY ("permission_id") REFERENCES "u_permissions" ("permission_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "u_role_permissions_role_id_fkey" FOREIGN KEY ("role_id") REFERENCES "u_roles" ("role_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.u_role_permissions: 0 rows
/*!40000 ALTER TABLE "u_role_permissions" DISABLE KEYS */;
/*!40000 ALTER TABLE "u_role_permissions" ENABLE KEYS */;

-- Dumping structure for table brta.v_duplicate_np_applications
CREATE TABLE IF NOT EXISTS "v_duplicate_np_applications" (
	"v_duplicate_np_application_id" BIGINT NOT NULL,
	"vehicle_info_id" INTEGER NOT NULL,
	"service_request_id" INTEGER NULL DEFAULT NULL,
	"replication_type_id" INTEGER NOT NULL,
	"replication_reason" VARCHAR(100) NOT NULL,
	"has_drc_attatchment" BOOLEAN NOT NULL,
	"has_identity_attatchment" BOOLEAN NOT NULL,
	"has_authorization_attatchment" BOOLEAN NOT NULL,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("v_duplicate_np_application_id"),
	CONSTRAINT "v_duplicate_np_applications_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_duplicate_np_applications_replication_type_id_fkey" FOREIGN KEY ("replication_type_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_duplicate_np_applications_service_request_id_fkey" FOREIGN KEY ("service_request_id") REFERENCES "s_service_requests" ("service_request_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_duplicate_np_applications_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_duplicate_np_applications_vehicle_info_id_fkey" FOREIGN KEY ("vehicle_info_id") REFERENCES "v_vehicle_infos" ("vehicle_info_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.v_duplicate_np_applications: 0 rows
/*!40000 ALTER TABLE "v_duplicate_np_applications" DISABLE KEYS */;
/*!40000 ALTER TABLE "v_duplicate_np_applications" ENABLE KEYS */;

-- Dumping structure for table brta.v_exporters
CREATE TABLE IF NOT EXISTS "v_exporters" (
	"exporter_id" BIGINT NOT NULL,
	"name_bn" VARCHAR(100) NOT NULL,
	"name_en" VARCHAR(100) NOT NULL,
	"country_id" INTEGER NOT NULL,
	"address" VARCHAR(255) NULL DEFAULT NULL::character varying,
	"mobile" VARCHAR(20) NULL DEFAULT NULL::character varying,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("exporter_id"),
	CONSTRAINT "v_exporters_country_id_fkey" FOREIGN KEY ("country_id") REFERENCES "c_countries" ("country_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_exporters_country_id_fkey1" FOREIGN KEY ("country_id") REFERENCES "c_countries" ("country_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_exporters_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_exporters_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.v_exporters: 0 rows
/*!40000 ALTER TABLE "v_exporters" DISABLE KEYS */;
/*!40000 ALTER TABLE "v_exporters" ENABLE KEYS */;

-- Dumping structure for table brta.v_fitness_appointments
CREATE TABLE IF NOT EXISTS "v_fitness_appointments" (
	"fitness_appointment_id" BIGINT NOT NULL,
	"vehicle_info_id" INTEGER NOT NULL,
	"service_request_id" INTEGER NOT NULL,
	"appointment_date" TIMESTAMP NOT NULL,
	"org_id" INTEGER NOT NULL,
	"ft_appointment_timeslot_id" INTEGER NOT NULL,
	"mobile_no" VARCHAR(20) NOT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("fitness_appointment_id"),
	CONSTRAINT "v_fitness_appointments_ft_appointment_timeslot_id_fkey" FOREIGN KEY ("ft_appointment_timeslot_id") REFERENCES "appointment_timeslots" ("appointment_timeslot_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_fitness_appointments_org_id_fkey" FOREIGN KEY ("org_id") REFERENCES "c_organizations" ("org_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_fitness_appointments_service_request_id_fkey" FOREIGN KEY ("service_request_id") REFERENCES "s_service_requests" ("service_request_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_fitness_appointments_vehicle_info_id_fkey" FOREIGN KEY ("vehicle_info_id") REFERENCES "v_vehicle_infos" ("vehicle_info_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.v_fitness_appointments: 0 rows
/*!40000 ALTER TABLE "v_fitness_appointments" DISABLE KEYS */;
/*!40000 ALTER TABLE "v_fitness_appointments" ENABLE KEYS */;

-- Dumping structure for table brta.v_importers
CREATE TABLE IF NOT EXISTS "v_importers" (
	"importer_id" BIGINT NOT NULL,
	"name_bn" VARCHAR(100) NOT NULL,
	"name_en" VARCHAR(100) NOT NULL,
	"country_id" INTEGER NOT NULL,
	"address" VARCHAR(255) NULL DEFAULT NULL,
	"mobile" VARCHAR(20) NULL DEFAULT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("importer_id"),
	CONSTRAINT "v_importers_country_id_fkey" FOREIGN KEY ("country_id") REFERENCES "c_countries" ("country_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_importers_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_importers_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.v_importers: 0 rows
/*!40000 ALTER TABLE "v_importers" DISABLE KEYS */;
/*!40000 ALTER TABLE "v_importers" ENABLE KEYS */;

-- Dumping structure for table brta.v_modification_applications
CREATE TABLE IF NOT EXISTS "v_modification_applications" (
	"v_modification_application_id" BIGINT NOT NULL,
	"vehicle_info_id" INTEGER NOT NULL,
	"service_request_id" INTEGER NULL DEFAULT NULL,
	"engine_number" VARCHAR(100) NOT NULL,
	"body_color_id" INTEGER NOT NULL,
	"tyre_size" VARCHAR(100) NULL DEFAULT NULL,
	"is_air_conditioner" BOOLEAN NOT NULL,
	"cc" INTEGER NOT NULL,
	"v_model" VARCHAR(100) NULL DEFAULT NULL,
	"cylinder" SMALLINT NULL DEFAULT NULL,
	"horse_power" INTEGER NULL DEFAULT NULL,
	"fuel_type_id" INTEGER NOT NULL,
	"engine_making_country_id" INTEGER NOT NULL,
	"gear_box_description" VARCHAR(100) NULL DEFAULT NULL,
	"engine_change_description" VARCHAR(100) NULL DEFAULT NULL,
	"is_driver_connection_standard_id" INTEGER NOT NULL,
	"fault_description" VARCHAR(100) NULL DEFAULT NULL,
	"is_engine_working_properly" BOOLEAN NOT NULL,
	"create_user_id" BIGINT NOT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("v_modification_application_id"),
	CONSTRAINT "v_modification_applications_body_color_id_fkey" FOREIGN KEY ("body_color_id") REFERENCES "c_vehicle_colors" ("vehicle_color_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_modification_applications_create_user_id_fkey" FOREIGN KEY ("create_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_modification_applications_engine_making_country_id_fkey" FOREIGN KEY ("engine_making_country_id") REFERENCES "c_countries" ("country_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_modification_applications_fuel_type_id_fkey" FOREIGN KEY ("fuel_type_id") REFERENCES "c_fuel_types" ("fuel_type_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_modification_applications_service_request_id_fkey" FOREIGN KEY ("service_request_id") REFERENCES "s_service_requests" ("service_request_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_modification_applications_update_user_id_fkey" FOREIGN KEY ("update_user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_modification_applications_vehicle_info_id_fkey" FOREIGN KEY ("vehicle_info_id") REFERENCES "v_vehicle_infos" ("vehicle_info_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.v_modification_applications: 0 rows
/*!40000 ALTER TABLE "v_modification_applications" DISABLE KEYS */;
/*!40000 ALTER TABLE "v_modification_applications" ENABLE KEYS */;

-- Dumping structure for table brta.v_ownership_transfer_applications
CREATE TABLE IF NOT EXISTS "v_ownership_transfer_applications" (
	"ownership_transfer_application_id" BIGINT NOT NULL,
	"vehicle_info_id" INTEGER NOT NULL,
	"service_request_id" INTEGER NULL DEFAULT NULL,
	"owner_type_id" INTEGER NOT NULL,
	"reason_for_transfer_id" INTEGER NOT NULL,
	"buying_price" INTEGER NOT NULL,
	"name" VARCHAR(100) NOT NULL,
	"father_or_husband_name" VARCHAR(100) NOT NULL,
	"mother_name" VARCHAR(100) NOT NULL,
	"gender_id" INTEGER NOT NULL,
	"nationality_id" INTEGER NOT NULL,
	"guardian_name" VARCHAR(100) NULL DEFAULT NULL,
	"passport_no" VARCHAR(100) NULL DEFAULT NULL,
	"birth_certificate_no" VARCHAR(100) NULL DEFAULT NULL,
	"address_id" INTEGER NOT NULL,
	"is_joint_owner" BOOLEAN NOT NULL DEFAULT false,
	"ministry_id" INTEGER NULL DEFAULT NULL,
	"department_id" INTEGER NULL DEFAULT NULL,
	"sub_office_unit_group_id" INTEGER NULL DEFAULT NULL,
	"unit_or_activity_id" INTEGER NULL DEFAULT NULL,
	"in_use" BOOLEAN NULL DEFAULT true,
	"used_by_id" INTEGER NULL DEFAULT NULL,
	"within_organogram" BOOLEAN NULL DEFAULT NULL,
	"acquisition_process_id" INTEGER NULL DEFAULT NULL,
	"acquisition_office" VARCHAR NULL DEFAULT NULL,
	"acquisition_price" INTEGER NULL DEFAULT NULL,
	"date_of_receipt" DATE NULL DEFAULT NULL,
	"remarks" VARCHAR NULL DEFAULT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("ownership_transfer_application_id"),
	CONSTRAINT "v_ownership_transfer_applications_acquisition_process_id_fkey" FOREIGN KEY ("acquisition_process_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_ownership_transfer_applications_address_id_fkey" FOREIGN KEY ("address_id") REFERENCES "s_addresses" ("address_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_ownership_transfer_applications_gender_id_fkey" FOREIGN KEY ("gender_id") REFERENCES "c_genders" ("gender_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_ownership_transfer_applications_nationality_id_fkey" FOREIGN KEY ("nationality_id") REFERENCES "c_countries" ("country_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_ownership_transfer_applications_owner_type_id_fkey" FOREIGN KEY ("owner_type_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_ownership_transfer_applications_reason_for_transfer_id_fkey" FOREIGN KEY ("reason_for_transfer_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_ownership_transfer_applications_service_request_id_fkey" FOREIGN KEY ("service_request_id") REFERENCES "s_service_requests" ("service_request_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_ownership_transfer_applications_vehicle_info_id_fkey" FOREIGN KEY ("vehicle_info_id") REFERENCES "v_vehicle_infos" ("vehicle_info_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.v_ownership_transfer_applications: 0 rows
/*!40000 ALTER TABLE "v_ownership_transfer_applications" DISABLE KEYS */;
/*!40000 ALTER TABLE "v_ownership_transfer_applications" ENABLE KEYS */;

-- Dumping structure for table brta.v_vehicle_ac_histories
CREATE TABLE IF NOT EXISTS "v_vehicle_ac_histories" (
	"vehicle_body_color_history_id" BIGINT NOT NULL,
	"vehicle_info_id" INTEGER NOT NULL,
	"service_request_id" INTEGER NOT NULL,
	"is_air_conditioner" BOOLEAN NOT NULL DEFAULT false,
	"is_active" BOOLEAN NOT NULL DEFAULT false,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("vehicle_body_color_history_id"),
	CONSTRAINT "v_vehicle_ac_histories_service_request_id_fkey" FOREIGN KEY ("service_request_id") REFERENCES "s_service_requests" ("service_request_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_ac_histories_vehicle_info_id_fkey" FOREIGN KEY ("vehicle_info_id") REFERENCES "v_vehicle_infos" ("vehicle_info_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.v_vehicle_ac_histories: 0 rows
/*!40000 ALTER TABLE "v_vehicle_ac_histories" DISABLE KEYS */;
/*!40000 ALTER TABLE "v_vehicle_ac_histories" ENABLE KEYS */;

-- Dumping structure for table brta.v_vehicle_advance_income_taxes
CREATE TABLE IF NOT EXISTS "v_vehicle_advance_income_taxes" (
	"vehicle_ait_id" BIGINT NOT NULL,
	"vehicle_info_id" INTEGER NOT NULL,
	"service_request_id" INTEGER NOT NULL,
	"fiscal_year_id" INTEGER NOT NULL,
	"payment_status_id" INTEGER NOT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("vehicle_ait_id"),
	CONSTRAINT "v_vehicle_advance_income_taxes_fiscal_year_id_fkey" FOREIGN KEY ("fiscal_year_id") REFERENCES "c_fiscal_years" ("fiscal_year_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_advance_income_taxes_payment_status_id_fkey" FOREIGN KEY ("payment_status_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_advance_income_taxes_service_request_id_fkey" FOREIGN KEY ("service_request_id") REFERENCES "s_service_requests" ("service_request_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_advance_income_taxes_vehicle_info_id_fkey" FOREIGN KEY ("vehicle_info_id") REFERENCES "v_vehicle_infos" ("vehicle_info_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.v_vehicle_advance_income_taxes: 0 rows
/*!40000 ALTER TABLE "v_vehicle_advance_income_taxes" DISABLE KEYS */;
/*!40000 ALTER TABLE "v_vehicle_advance_income_taxes" ENABLE KEYS */;

-- Dumping structure for table brta.v_vehicle_body_color_histories
CREATE TABLE IF NOT EXISTS "v_vehicle_body_color_histories" (
	"vehicle_body_color_history_id" BIGINT NOT NULL,
	"vehicle_info_id" INTEGER NOT NULL,
	"service_request_id" INTEGER NOT NULL,
	"body_color_id" INTEGER NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT false,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("vehicle_body_color_history_id"),
	CONSTRAINT "v_vehicle_body_color_histories_body_color_id_fkey" FOREIGN KEY ("body_color_id") REFERENCES "c_vehicle_colors" ("vehicle_color_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_body_color_histories_service_request_id_fkey" FOREIGN KEY ("service_request_id") REFERENCES "s_service_requests" ("service_request_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_body_color_histories_vehicle_info_id_fkey" FOREIGN KEY ("vehicle_info_id") REFERENCES "v_vehicle_infos" ("vehicle_info_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.v_vehicle_body_color_histories: 0 rows
/*!40000 ALTER TABLE "v_vehicle_body_color_histories" DISABLE KEYS */;
/*!40000 ALTER TABLE "v_vehicle_body_color_histories" ENABLE KEYS */;

-- Dumping structure for table brta.v_vehicle_engine_histories
CREATE TABLE IF NOT EXISTS "v_vehicle_engine_histories" (
	"vehicle_engine_history_id" BIGINT NOT NULL,
	"vehicle_info_id" INTEGER NOT NULL,
	"service_request_id" INTEGER NOT NULL,
	"engine_number" VARCHAR(100) NOT NULL,
	"cc" INTEGER NOT NULL,
	"v_model" VARCHAR(100) NULL DEFAULT NULL,
	"cylinder" SMALLINT NULL DEFAULT NULL,
	"horse_power" INTEGER NULL DEFAULT NULL,
	"fuel_type_id" INTEGER NOT NULL,
	"economic_life" TIMESTAMP NULL DEFAULT NULL,
	"remaining_life" TIMESTAMP NULL DEFAULT NULL,
	"engine_making_country_id" INTEGER NOT NULL,
	"gear_box_description" VARCHAR(100) NULL DEFAULT NULL,
	"engine_change_description" VARCHAR(100) NULL DEFAULT NULL,
	"is_driver_connection_standard_id" INTEGER NOT NULL,
	"fault_description" VARCHAR(100) NULL DEFAULT NULL,
	"is_engine_working_properly" BOOLEAN NOT NULL,
	"is_active" BOOLEAN NOT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("vehicle_engine_history_id"),
	CONSTRAINT "v_vehicle_engine_histories_engine_making_country_id_fkey" FOREIGN KEY ("engine_making_country_id") REFERENCES "c_countries" ("country_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_engine_histories_fuel_type_id_fkey" FOREIGN KEY ("fuel_type_id") REFERENCES "c_fuel_types" ("fuel_type_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_engine_histories_service_request_id_fkey" FOREIGN KEY ("service_request_id") REFERENCES "s_service_requests" ("service_request_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_engine_histories_vehicle_info_id_fkey" FOREIGN KEY ("vehicle_info_id") REFERENCES "v_vehicle_infos" ("vehicle_info_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.v_vehicle_engine_histories: 0 rows
/*!40000 ALTER TABLE "v_vehicle_engine_histories" DISABLE KEYS */;
/*!40000 ALTER TABLE "v_vehicle_engine_histories" ENABLE KEYS */;

-- Dumping structure for table brta.v_vehicle_fitness
CREATE TABLE IF NOT EXISTS "v_vehicle_fitness" (
	"vehicle_fitness_id" BIGINT NOT NULL,
	"vehicle_info_id" INTEGER NOT NULL,
	"service_request_id" INTEGER NOT NULL,
	"fitness_valid_start_date" TIMESTAMP NOT NULL,
	"fitness_valid_end_date" TIMESTAMP NOT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("vehicle_fitness_id"),
	CONSTRAINT "v_vehicle_fitness_service_request_id_fkey" FOREIGN KEY ("service_request_id") REFERENCES "s_service_requests" ("service_request_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_fitness_vehicle_info_id_fkey" FOREIGN KEY ("vehicle_info_id") REFERENCES "v_vehicle_infos" ("vehicle_info_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.v_vehicle_fitness: 0 rows
/*!40000 ALTER TABLE "v_vehicle_fitness" DISABLE KEYS */;
/*!40000 ALTER TABLE "v_vehicle_fitness" ENABLE KEYS */;

-- Dumping structure for table brta.v_vehicle_infos
CREATE TABLE IF NOT EXISTS "v_vehicle_infos" (
	"vehicle_info_id" BIGINT NOT NULL,
	"chassis_number" VARCHAR(100) NOT NULL,
	"engine_number" VARCHAR(100) NOT NULL,
	"fitness_issue_date" TIMESTAMP NULL DEFAULT NULL,
	"fitness_expire_date" TIMESTAMP NULL DEFAULT NULL,
	"tax_token_issue_date" TIMESTAMP NULL DEFAULT NULL,
	"tax_token_expire_date" TIMESTAMP NULL DEFAULT NULL,
	"route_permit_issue_date" TIMESTAMP NULL DEFAULT NULL,
	"route_permit_expire_date" TIMESTAMP NULL DEFAULT NULL,
	"bill_of_entry_number" VARCHAR(100) NOT NULL,
	"bill_of_entry_date" TIMESTAMP NOT NULL,
	"bill_of_entry_office_code" VARCHAR(100) NULL DEFAULT NULL,
	"hs_code" VARCHAR(100) NULL DEFAULT NULL,
	"importer_id" INTEGER NOT NULL,
	"maker_id" INTEGER NOT NULL,
	"exporter_id" INTEGER NOT NULL,
	"agent" VARCHAR(255) NULL DEFAULT NULL,
	"product_location" VARCHAR(255) NULL DEFAULT NULL,
	"product_description" VARCHAR(255) NULL DEFAULT NULL,
	"invoice_number" VARCHAR(255) NULL DEFAULT NULL,
	"invoice_date" TIMESTAMP NULL DEFAULT NULL,
	"is_electrict_vehicle" BOOLEAN NOT NULL DEFAULT false,
	"cc_or_kw" INTEGER NOT NULL,
	"manufacturing_year" INTEGER NOT NULL,
	"vehicle_class_id" INTEGER NULL DEFAULT NULL,
	"vehicle_type_id" INTEGER NULL DEFAULT NULL,
	"body_color_id" INTEGER NOT NULL,
	"assembly_operation_id" INTEGER NOT NULL,
	"mileage" INTEGER NULL DEFAULT NULL,
	"unladen_weight" INTEGER NULL DEFAULT NULL,
	"max_laden_weight" INTEGER NULL DEFAULT NULL,
	"is_hire" BOOLEAN NULL DEFAULT false,
	"is_hire_purchase" BOOLEAN NULL DEFAULT false,
	"total_seat" SMALLINT NULL DEFAULT NULL,
	"fuel_id" INTEGER NULL DEFAULT NULL,
	"economic_life" TIMESTAMP NULL DEFAULT NULL,
	"remaining_life" TIMESTAMP NULL DEFAULT NULL,
	"vehicle_price" INTEGER NULL DEFAULT NULL,
	"is_air_conditioner" BOOLEAN NULL DEFAULT false,
	"v_brand_id" INTEGER NULL DEFAULT NULL,
	"v_model" VARCHAR(100) NULL DEFAULT NULL,
	"cylinder" SMALLINT NULL DEFAULT NULL,
	"horse_power" INTEGER NULL DEFAULT NULL,
	"highest_rpm" INTEGER NULL DEFAULT NULL,
	"wheel_base" VARCHAR(100) NULL DEFAULT NULL,
	"standee" INTEGER NULL DEFAULT NULL,
	"tyre_size" VARCHAR(100) NULL DEFAULT NULL,
	"tyre_number" SMALLINT NULL DEFAULT NULL,
	"axle_number" INTEGER NULL DEFAULT NULL,
	"front_axle_1" INTEGER NULL DEFAULT NULL,
	"front_axle_2" INTEGER NULL DEFAULT NULL,
	"central_axle_1" INTEGER NULL DEFAULT NULL,
	"central_axle_2" INTEGER NULL DEFAULT NULL,
	"central_axle_3" INTEGER NULL DEFAULT NULL,
	"rear_axle_1" INTEGER NULL DEFAULT NULL,
	"rear_axle_2" INTEGER NULL DEFAULT NULL,
	"rear_axle_3" INTEGER NULL DEFAULT NULL,
	"status_id" INTEGER NULL DEFAULT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	"page_completed" INTEGER NULL DEFAULT 1,
	PRIMARY KEY ("vehicle_info_id"),
	CONSTRAINT "v_vehicle_infos_assembly_operation_id_fkey" FOREIGN KEY ("assembly_operation_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_infos_body_color_id_fkey" FOREIGN KEY ("body_color_id") REFERENCES "c_vehicle_colors" ("vehicle_color_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_infos_exporter_id_fkey" FOREIGN KEY ("exporter_id") REFERENCES "v_exporters" ("exporter_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_infos_importer_id_fkey" FOREIGN KEY ("importer_id") REFERENCES "v_importers" ("importer_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_infos_maker_id_fkey" FOREIGN KEY ("maker_id") REFERENCES "c_vehicle_makers" ("maker_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_infos_status_id_fkey" FOREIGN KEY ("status_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_infos_v_brand_id_fkey" FOREIGN KEY ("v_brand_id") REFERENCES "c_vehicle_brands" ("brand_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_infos_vehicle_class_id_fkey" FOREIGN KEY ("vehicle_class_id") REFERENCES "c_vehicle_classes" ("vehicle_class_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_infos_vehicle_type_id_fkey" FOREIGN KEY ("vehicle_type_id") REFERENCES "c_vehicle_types" ("vehicle_type_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.v_vehicle_infos: 0 rows
/*!40000 ALTER TABLE "v_vehicle_infos" DISABLE KEYS */;
/*!40000 ALTER TABLE "v_vehicle_infos" ENABLE KEYS */;

-- Dumping structure for table brta.v_vehicle_owners
CREATE TABLE IF NOT EXISTS "v_vehicle_owners" (
	"vehicle_owner_id" BIGINT NOT NULL,
	"vehicle_info_id" INTEGER NOT NULL,
	"service_request_id" INTEGER NOT NULL,
	"name" VARCHAR(100) NOT NULL,
	"father_or_husband_name" VARCHAR(100) NOT NULL,
	"mother_name" VARCHAR(100) NOT NULL,
	"gender_id" INTEGER NOT NULL,
	"nationality_id" INTEGER NOT NULL,
	"guardian_name" VARCHAR(100) NULL DEFAULT NULL,
	"passport_no" VARCHAR(100) NULL DEFAULT NULL,
	"birth_certificate_no" VARCHAR(100) NULL DEFAULT NULL,
	"address_id" INTEGER NOT NULL,
	"is_recondition" BOOLEAN NOT NULL DEFAULT false,
	"is_joint_owner" BOOLEAN NOT NULL DEFAULT false,
	"owner_type_id" INTEGER NOT NULL,
	"ministry_id" INTEGER NULL DEFAULT NULL,
	"department_id" INTEGER NULL DEFAULT NULL,
	"sub_office_unit_group_id" INTEGER NULL DEFAULT NULL,
	"unit_or_activity_id" INTEGER NULL DEFAULT NULL,
	"in_use" BOOLEAN NULL DEFAULT true,
	"used_by_id" INTEGER NULL DEFAULT NULL,
	"within_organogram" BOOLEAN NULL DEFAULT NULL,
	"acquisition_process_id" INTEGER NULL DEFAULT NULL,
	"acquisition_office" VARCHAR NULL DEFAULT NULL,
	"acquisition_price" INTEGER NULL DEFAULT NULL,
	"date_of_receipt" DATE NULL DEFAULT NULL,
	"remarks" VARCHAR NULL DEFAULT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT true,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("vehicle_owner_id"),
	CONSTRAINT "v_vehicle_owners_acquisition_process_id_fkey" FOREIGN KEY ("acquisition_process_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_owners_address_id_fkey" FOREIGN KEY ("address_id") REFERENCES "s_addresses" ("address_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_owners_gender_id_fkey" FOREIGN KEY ("gender_id") REFERENCES "c_genders" ("gender_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_owners_nationality_id_fkey" FOREIGN KEY ("nationality_id") REFERENCES "c_countries" ("country_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_owners_service_request_id_fkey" FOREIGN KEY ("service_request_id") REFERENCES "s_service_requests" ("service_request_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_owners_used_by_id_fkey" FOREIGN KEY ("used_by_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_owners_vehicle_info_id_fkey" FOREIGN KEY ("vehicle_info_id") REFERENCES "v_vehicle_infos" ("vehicle_info_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.v_vehicle_owners: 0 rows
/*!40000 ALTER TABLE "v_vehicle_owners" DISABLE KEYS */;
/*!40000 ALTER TABLE "v_vehicle_owners" ENABLE KEYS */;

-- Dumping structure for table brta.v_vehicle_registrations
CREATE TABLE IF NOT EXISTS "v_vehicle_registrations" (
	"vehicle_reg_id" BIGINT NOT NULL,
	"vehicle_info_id" INTEGER NOT NULL,
	"service_request_id" INTEGER NOT NULL,
	"vehicle_owner_id" INTEGER NOT NULL,
	"reg_office_id" INTEGER NOT NULL,
	"vehicle_type_id" INTEGER NOT NULL,
	"vehicle_class_id" INTEGER NOT NULL,
	"class_number" VARCHAR(10) NOT NULL,
	"vehicle_number" VARCHAR(10) NOT NULL,
	"full_reg_number" VARCHAR(20) NOT NULL,
	"status_id" INTEGER NOT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("vehicle_reg_id"),
	CONSTRAINT "v_vehicle_registrations_reg_office_id_fkey" FOREIGN KEY ("reg_office_id") REFERENCES "c_organizations" ("org_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_registrations_service_request_id_fkey" FOREIGN KEY ("service_request_id") REFERENCES "s_service_requests" ("service_request_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_registrations_status_id_fkey" FOREIGN KEY ("status_id") REFERENCES "c_statuses" ("status_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_registrations_vehicle_class_id_fkey" FOREIGN KEY ("vehicle_class_id") REFERENCES "c_vehicle_classes" ("vehicle_class_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_registrations_vehicle_info_id_fkey" FOREIGN KEY ("vehicle_info_id") REFERENCES "v_vehicle_infos" ("vehicle_info_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_registrations_vehicle_owner_id_fkey" FOREIGN KEY ("vehicle_owner_id") REFERENCES "v_vehicle_owners" ("vehicle_owner_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_registrations_vehicle_type_id_fkey" FOREIGN KEY ("vehicle_type_id") REFERENCES "c_vehicle_types" ("vehicle_type_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.v_vehicle_registrations: 0 rows
/*!40000 ALTER TABLE "v_vehicle_registrations" DISABLE KEYS */;
/*!40000 ALTER TABLE "v_vehicle_registrations" ENABLE KEYS */;

-- Dumping structure for table brta.v_vehicle_route_permits
CREATE TABLE IF NOT EXISTS "v_vehicle_route_permits" (
	"vehicle_rp_id" BIGINT NOT NULL,
	"vehicle_info_id" INTEGER NOT NULL,
	"service_request_id" INTEGER NOT NULL,
	"route_id" INTEGER NOT NULL,
	"rp_valid_start_date" TIMESTAMP NOT NULL,
	"rp_valid_end_date" TIMESTAMP NOT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("vehicle_rp_id"),
	CONSTRAINT "v_vehicle_route_permits_route_id_fkey" FOREIGN KEY ("route_id") REFERENCES "c_vehicle_routes" ("route_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_route_permits_service_request_id_fkey" FOREIGN KEY ("service_request_id") REFERENCES "s_service_requests" ("service_request_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_route_permits_vehicle_info_id_fkey" FOREIGN KEY ("vehicle_info_id") REFERENCES "v_vehicle_infos" ("vehicle_info_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.v_vehicle_route_permits: 0 rows
/*!40000 ALTER TABLE "v_vehicle_route_permits" DISABLE KEYS */;
/*!40000 ALTER TABLE "v_vehicle_route_permits" ENABLE KEYS */;

-- Dumping structure for table brta.v_vehicle_tagging
CREATE TABLE IF NOT EXISTS "v_vehicle_tagging" (
	"vehicle_tag_id" BIGINT NOT NULL,
	"vehicle_reg_id" INTEGER NOT NULL,
	"create_user_id" BIGINT NOT NULL,
	"created_date" TIMESTAMP NOT NULL,
	PRIMARY KEY ("vehicle_tag_id"),
	CONSTRAINT "v_vehicle_tagging_vehicle_reg_id_fkey" FOREIGN KEY ("vehicle_reg_id") REFERENCES "v_vehicle_registrations" ("vehicle_reg_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.v_vehicle_tagging: 0 rows
/*!40000 ALTER TABLE "v_vehicle_tagging" DISABLE KEYS */;
/*!40000 ALTER TABLE "v_vehicle_tagging" ENABLE KEYS */;

-- Dumping structure for table brta.v_vehicle_tax_tokens
CREATE TABLE IF NOT EXISTS "v_vehicle_tax_tokens" (
	"vehicle_tt_id" BIGINT NOT NULL,
	"vehicle_info_id" INTEGER NOT NULL,
	"service_request_id" INTEGER NOT NULL,
	"tt_valid_start_date" TIMESTAMP NOT NULL,
	"tt_valid_end_date" TIMESTAMP NOT NULL,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("vehicle_tt_id"),
	CONSTRAINT "v_vehicle_tax_tokens_service_request_id_fkey" FOREIGN KEY ("service_request_id") REFERENCES "s_service_requests" ("service_request_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_tax_tokens_vehicle_info_id_fkey" FOREIGN KEY ("vehicle_info_id") REFERENCES "v_vehicle_infos" ("vehicle_info_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.v_vehicle_tax_tokens: 0 rows
/*!40000 ALTER TABLE "v_vehicle_tax_tokens" DISABLE KEYS */;
/*!40000 ALTER TABLE "v_vehicle_tax_tokens" ENABLE KEYS */;

-- Dumping structure for table brta.v_vehicle_tyre_histories
CREATE TABLE IF NOT EXISTS "v_vehicle_tyre_histories" (
	"vehicle_tyre_history_id" BIGINT NOT NULL,
	"vehicle_info_id" INTEGER NOT NULL,
	"service_request_id" INTEGER NOT NULL,
	"tyre_size" VARCHAR(100) NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT false,
	"created_date" TIMESTAMP NOT NULL,
	"updated_date" TIMESTAMP NULL DEFAULT NULL,
	"deleted_date" TIMESTAMP NULL DEFAULT NULL,
	"version_no" INTEGER NOT NULL,
	PRIMARY KEY ("vehicle_tyre_history_id"),
	CONSTRAINT "v_vehicle_tyre_histories_service_request_id_fkey" FOREIGN KEY ("service_request_id") REFERENCES "s_service_requests" ("service_request_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "v_vehicle_tyre_histories_vehicle_info_id_fkey" FOREIGN KEY ("vehicle_info_id") REFERENCES "v_vehicle_infos" ("vehicle_info_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.v_vehicle_tyre_histories: 0 rows
/*!40000 ALTER TABLE "v_vehicle_tyre_histories" DISABLE KEYS */;
/*!40000 ALTER TABLE "v_vehicle_tyre_histories" ENABLE KEYS */;

-- Dumping structure for table brta.x_email_audit
CREATE TABLE IF NOT EXISTS "x_email_audit" (
	"id" SERIAL NOT NULL,
	"changed_at" TIMESTAMP NOT NULL,
	"changed_by" VARCHAR(255) NOT NULL,
	"new_email" VARCHAR(255) NOT NULL,
	"old_email" VARCHAR(255) NOT NULL,
	"user_id" BIGINT NOT NULL,
	PRIMARY KEY ("id")
);

-- Dumping data for table brta.x_email_audit: 0 rows
/*!40000 ALTER TABLE "x_email_audit" DISABLE KEYS */;
/*!40000 ALTER TABLE "x_email_audit" ENABLE KEYS */;

-- Dumping structure for table brta.x_f5tokens
CREATE TABLE IF NOT EXISTS "x_f5tokens" (
	"id" BIGINT NOT NULL,
	"auth_token" VARCHAR(255) NULL DEFAULT NULL,
	"expiry_date" TIMESTAMP NOT NULL,
	"token" VARCHAR(255) NOT NULL,
	"user_id" BIGINT NULL DEFAULT NULL,
	PRIMARY KEY ("id"),
	UNIQUE "uk_118lh66eyl9x3rpm3pw7ea76j" ("token"),
	CONSTRAINT "fk4as87e7n36fl2aq4cqja6g2xd" FOREIGN KEY ("user_id") REFERENCES "x_users" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fkim5gyjcl8mtg9fwautxki836d" FOREIGN KEY ("user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.x_f5tokens: 0 rows
/*!40000 ALTER TABLE "x_f5tokens" DISABLE KEYS */;
/*!40000 ALTER TABLE "x_f5tokens" ENABLE KEYS */;

-- Dumping structure for table brta.x_menus
CREATE TABLE IF NOT EXISTS "x_menus" (
	"menu_id" BIGINT NOT NULL,
	"created_at" TIMESTAMPTZ NULL DEFAULT NULL,
	"updated_at" TIMESTAMPTZ NULL DEFAULT NULL,
	"version" INTEGER NOT NULL,
	"created_by" BIGINT NULL DEFAULT NULL,
	"is_active" BOOLEAN NOT NULL,
	"rec_stat" VARCHAR(1) NOT NULL,
	"updated_by" BIGINT NULL DEFAULT NULL,
	"uuid" UUID NOT NULL,
	"menu_code" VARCHAR(255) NULL DEFAULT NULL,
	"menu_name_bn" VARCHAR(255) NULL DEFAULT NULL,
	"menu_name_en" VARCHAR(255) NOT NULL,
	"menu_url" VARCHAR(255) NULL DEFAULT NULL,
	"parent_menu_id" BIGINT NULL DEFAULT NULL,
	"created_date" TIMESTAMPTZ NULL DEFAULT NULL,
	"updated_date" TIMESTAMPTZ NULL DEFAULT NULL,
	"version_no" INTEGER NULL DEFAULT NULL,
	"create_user_id" BIGINT NULL DEFAULT NULL,
	"update_user_id" BIGINT NULL DEFAULT NULL,
	PRIMARY KEY ("menu_id")
);

-- Dumping data for table brta.x_menus: 0 rows
/*!40000 ALTER TABLE "x_menus" DISABLE KEYS */;
/*!40000 ALTER TABLE "x_menus" ENABLE KEYS */;

-- Dumping structure for table brta.x_mobile_audit
CREATE TABLE IF NOT EXISTS "x_mobile_audit" (
	"id" BIGINT NOT NULL,
	"changed_at" TIMESTAMP NOT NULL,
	"changed_by" VARCHAR(255) NOT NULL,
	"new_mobile" VARCHAR(255) NOT NULL,
	"old_mobile" VARCHAR(255) NOT NULL,
	"user_id" BIGINT NOT NULL,
	PRIMARY KEY ("id")
);

-- Dumping data for table brta.x_mobile_audit: 0 rows
/*!40000 ALTER TABLE "x_mobile_audit" DISABLE KEYS */;
/*!40000 ALTER TABLE "x_mobile_audit" ENABLE KEYS */;

-- Dumping structure for table brta.x_privileges
CREATE TABLE IF NOT EXISTS "x_privileges" (
	"id" BIGINT NOT NULL,
	"created_at" TIMESTAMPTZ NULL DEFAULT NULL,
	"updated_at" TIMESTAMPTZ NULL DEFAULT NULL,
	"version" INTEGER NOT NULL,
	"name" VARCHAR(255) NULL DEFAULT NULL,
	"created_date" TIMESTAMPTZ NULL DEFAULT NULL,
	"updated_date" TIMESTAMPTZ NULL DEFAULT NULL,
	"version_no" INTEGER NULL DEFAULT NULL,
	PRIMARY KEY ("id")
);

-- Dumping data for table brta.x_privileges: 0 rows
/*!40000 ALTER TABLE "x_privileges" DISABLE KEYS */;
/*!40000 ALTER TABLE "x_privileges" ENABLE KEYS */;

-- Dumping structure for table brta.x_roles
CREATE TABLE IF NOT EXISTS "x_roles" (
	"id" SERIAL NOT NULL,
	"name_bn" VARCHAR(255) NULL DEFAULT NULL,
	"role_code" VARCHAR(255) NULL DEFAULT NULL,
	"is_active" BOOLEAN NOT NULL,
	"name_en" VARCHAR(60) NULL DEFAULT NULL,
	PRIMARY KEY ("id"),
	UNIQUE "uk_9xs1dtpcsbc23gjuafdmjlpf7" ("name_en"),
	UNIQUE "uklmoyf3pcqv8mfl5njbwxlluwn" ("name_en")
);

-- Dumping data for table brta.x_roles: 2 rows
/*!40000 ALTER TABLE "x_roles" DISABLE KEYS */;
INSERT INTO "x_roles" ("id", "name_bn", "role_code", "is_active", "name_en") VALUES
	(1, NULL, 'admin', 'true', 'ROLE_ADMIN'),
	(2, NULL, 'user', 'true', 'ROLE_USER');
/*!40000 ALTER TABLE "x_roles" ENABLE KEYS */;

-- Dumping structure for table brta.x_role_menus
CREATE TABLE IF NOT EXISTS "x_role_menus" (
	"menu_id" BIGINT NOT NULL,
	"role_id" BIGINT NOT NULL,
	PRIMARY KEY ("menu_id", "role_id"),
	CONSTRAINT "fk3n3kqn43dgjqrci6ewtvmj2fp" FOREIGN KEY ("menu_id") REFERENCES "x_menus" ("menu_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fk9hiacpgg1wr48xhggrd2an9v6" FOREIGN KEY ("role_id") REFERENCES "x_roles" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.x_role_menus: 0 rows
/*!40000 ALTER TABLE "x_role_menus" DISABLE KEYS */;
/*!40000 ALTER TABLE "x_role_menus" ENABLE KEYS */;

-- Dumping structure for table brta.x_role_privileges
CREATE TABLE IF NOT EXISTS "x_role_privileges" (
	"role_id" BIGINT NOT NULL,
	"privilege_id" BIGINT NOT NULL,
	CONSTRAINT "fk5qiucpnrkqbhvbubaft05ssgb" FOREIGN KEY ("role_id") REFERENCES "x_roles" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fknm57fb8m9apueqc9igqmj94qw" FOREIGN KEY ("privilege_id") REFERENCES "x_privileges" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.x_role_privileges: 0 rows
/*!40000 ALTER TABLE "x_role_privileges" DISABLE KEYS */;
/*!40000 ALTER TABLE "x_role_privileges" ENABLE KEYS */;

-- Dumping structure for table brta.x_users
CREATE TABLE IF NOT EXISTS "x_users" (
	"id" SERIAL NOT NULL,
	"created_at" TIMESTAMP NULL DEFAULT NULL,
	"updated_at" TIMESTAMP NULL DEFAULT NULL,
	"version" INTEGER NOT NULL,
	"name_bn" VARCHAR(40) NOT NULL,
	"designation_id" BIGINT NOT NULL,
	"email" VARCHAR(40) NOT NULL,
	"is_active" BOOLEAN NOT NULL,
	"profile_completed" BOOLEAN NOT NULL,
	"mobile" VARCHAR(13) NOT NULL,
	"name_en" VARCHAR(40) NOT NULL,
	"password" VARCHAR(100) NOT NULL,
	"username" VARCHAR(15) NOT NULL,
	"created_date" TIMESTAMPTZ NULL DEFAULT NULL,
	"updated_date" TIMESTAMPTZ NULL DEFAULT NULL,
	"version_no" INTEGER NULL DEFAULT NULL,
	UNIQUE "ukear0riur4ua32yi2k1f39ndft" ("email", "mobile"),
	PRIMARY KEY ("id"),
	UNIQUE "uk_l5ulxord0kiwowmiajec1exsc" ("email", "mobile"),
	UNIQUE "uksan3g8jf24qpisu5du3qj3d0x" ("username"),
	UNIQUE "ukdqax5pjga30knva7d1dul2tin" ("email"),
	UNIQUE "uk34hjlxkawdgiv2cjtia6r56w0" ("mobile")
);

-- Dumping data for table brta.x_users: 1 rows
/*!40000 ALTER TABLE "x_users" DISABLE KEYS */;
INSERT INTO "x_users" ("id", "created_at", "updated_at", "version", "name_bn", "designation_id", "email", "is_active", "profile_completed", "mobile", "name_en", "password", "username", "created_date", "updated_date", "version_no") VALUES
	(2, '2024-08-25 04:14:32.836508', '2024-08-25 04:14:32.836508', 0, 'syste user', 1, 'system@google.com', 'true', 'false', '01711283365', 'System User', '$2a$10$6R8L8HLYOkOS7KAtBb5osOcghsXurL4xtIukzlM/qoRKArdhb/BYi', 'system', NULL, NULL, 1),
	(1, '2024-08-21 16:25:50.092', '2024-08-21 16:25:50.092', 0, 'ডিফল্ট ব্যবহারকারী', 1, 'duser@google.com', 'true', 'false', '01711283364', 'Default User', '$2y$10$P4UTnMnwFj15aia9Bnk5Re52JQIqYpo7TK9lFnKCg4dqaIVciPxeu', 'duser', NULL, NULL, 1);
/*!40000 ALTER TABLE "x_users" ENABLE KEYS */;

-- Dumping structure for table brta.x_user_roles
CREATE TABLE IF NOT EXISTS "x_user_roles" (
	"user_id" BIGINT NOT NULL,
	"role_id" BIGINT NOT NULL,
	PRIMARY KEY ("user_id", "role_id"),
	CONSTRAINT "fk67mrtll9v2vvs35mp46ne3x3p" FOREIGN KEY ("user_id") REFERENCES "s_users" ("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fkr7995e7n87xdhy2atwaa73yk7" FOREIGN KEY ("role_id") REFERENCES "x_roles" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Dumping data for table brta.x_user_roles: 0 rows
/*!40000 ALTER TABLE "x_user_roles" DISABLE KEYS */;
INSERT INTO "x_user_roles" ("user_id", "role_id") VALUES
	(2, 1),
	(3, 1);
/*!40000 ALTER TABLE "x_user_roles" ENABLE KEYS */;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
