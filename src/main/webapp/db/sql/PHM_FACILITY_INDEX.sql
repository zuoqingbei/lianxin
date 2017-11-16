/*
Navicat Oracle Data Transfer
Oracle Client Version : 12.1.0.2.0

Source Server         : 测试
Source Server Version : 110200
Source Host           : 10.138.8.233:1521
Source Schema         : LHJX01

Target Server Type    : ORACLE
Target Server Version : 110200
File Encoding         : 65001

Date: 2017-11-16 10:28:41
*/


-- ----------------------------
-- Table structure for PHM_FACILITY_INDEX
-- ----------------------------
DROP TABLE "LHJX01"."PHM_FACILITY_INDEX";
CREATE TABLE "LHJX01"."PHM_FACILITY_INDEX" (
"ID" NUMBER NOT NULL ,
"设备ID" NUMBER NULL ,
"M_NAME" VARCHAR2(255 BYTE) NOT NULL ,
"FI_ZB_NAME" VARCHAR2(255 BYTE) NULL ,
"FI_NUMBER" FLOAT(30) NULL ,
"FI_DANWEI" VARCHAR2(255 BYTE) NULL ,
"FI_MAX" FLOAT(30) NULL ,
"FI_MIN" FLOAT(30) NULL ,
"FI_IMAGETYPE" VARCHAR2(255 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of PHM_FACILITY_INDEX
-- ----------------------------

-- ----------------------------
-- Indexes structure for table PHM_FACILITY_INDEX
-- ----------------------------

-- ----------------------------
-- Checks structure for table PHM_FACILITY_INDEX
-- ----------------------------
ALTER TABLE "LHJX01"."PHM_FACILITY_INDEX" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "LHJX01"."PHM_FACILITY_INDEX" ADD CHECK ("M_NAME" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table PHM_FACILITY_INDEX
-- ----------------------------
ALTER TABLE "LHJX01"."PHM_FACILITY_INDEX" ADD PRIMARY KEY ("ID");
