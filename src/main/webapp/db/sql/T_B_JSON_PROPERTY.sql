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

Date: 2017-12-11 09:30:04
*/


-- ----------------------------
-- Table structure for T_B_JSON_PROPERTY
-- ----------------------------
DROP TABLE "LHJX01"."T_B_JSON_PROPERTY";
CREATE TABLE "LHJX01"."T_B_JSON_PROPERTY" (
"ID" VARCHAR2(20 BYTE) NOT NULL ,
"LAB_CODE" VARCHAR2(100 BYTE) NULL ,
"PRO_CODE" VARCHAR2(100 BYTE) NULL ,
"PRO_NAME" VARCHAR2(100 BYTE) NULL ,
"FILE_NAME" VARCHAR2(200 BYTE) NULL ,
"ORDER_NUM" NUMBER NULL ,
"DEL_FLAG" CHAR(2 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE "LHJX01"."T_B_JSON_PROPERTY" IS '对于实验室曲线读取json文件时实验室与台位对照关系';
COMMENT ON COLUMN "LHJX01"."T_B_JSON_PROPERTY"."LAB_CODE" IS '实验室编码';
COMMENT ON COLUMN "LHJX01"."T_B_JSON_PROPERTY"."PRO_CODE" IS '编码';
COMMENT ON COLUMN "LHJX01"."T_B_JSON_PROPERTY"."PRO_NAME" IS '名称';
COMMENT ON COLUMN "LHJX01"."T_B_JSON_PROPERTY"."FILE_NAME" IS '对应曲线文件名称';
COMMENT ON COLUMN "LHJX01"."T_B_JSON_PROPERTY"."ORDER_NUM" IS '排序';
COMMENT ON COLUMN "LHJX01"."T_B_JSON_PROPERTY"."DEL_FLAG" IS '0-正常；1-删除';

-- ----------------------------
-- Records of T_B_JSON_PROPERTY
-- ----------------------------
INSERT INTO "LHJX01"."T_B_JSON_PROPERTY" VALUES ('1', 'USLABKT', 'A1', 'A1', 'USLAB-A1.json', '1', '0 ');
INSERT INTO "LHJX01"."T_B_JSON_PROPERTY" VALUES ('2', 'USLABBX', 'A2', 'A2', 'USLAB-B2.json', '2', '0 ');
INSERT INTO "LHJX01"."T_B_JSON_PROPERTY" VALUES ('3', 'USLABBX', 'A1', 'A1', 'USLAB-B1.json', '1', '0 ');
INSERT INTO "LHJX01"."T_B_JSON_PROPERTY" VALUES ('4', 'USLABBX', 'A3', 'A3', 'USLAB-B3.json', '3', '0 ');
INSERT INTO "LHJX01"."T_B_JSON_PROPERTY" VALUES ('5', 'HR20160830QDZBX005', 'D4', 'Position：P1（ON）', 'HR20160830QDZBX005-D4.json', '1', '0 ');
INSERT INTO "LHJX01"."T_B_JSON_PROPERTY" VALUES ('6', 'HR20160830QDZBX005', 'D5', 'Position：P2（ON）', 'HR20160830QDZBX005-D5.json', '2', '0 ');
INSERT INTO "LHJX01"."T_B_JSON_PROPERTY" VALUES ('7', 'HR20160830QDZBX005', 'D6', 'Position：P3（ON）', 'HR20160830QDZBX005-D6.json', '3', '0 ');
INSERT INTO "LHJX01"."T_B_JSON_PROPERTY" VALUES ('8', 'HR20160407QDZKA001', '2AB', 'Position：P1（ON）', 'HR20160407QDZKA001-2AB.json', '4', '0 ');
INSERT INTO "LHJX01"."T_B_JSON_PROPERTY" VALUES ('9', 'HR20170424QDZBX001', 'B4', 'Position：P1（ON）', 'HR20170424QDZBX001-B4.json', '1', '0 ');
INSERT INTO "LHJX01"."T_B_JSON_PROPERTY" VALUES ('10', 'HR20170424QDZBX001', 'B5', 'Position：P2（ON）', 'HR20170424QDZBX001-B5.json', '2', '0 ');
INSERT INTO "LHJX01"."T_B_JSON_PROPERTY" VALUES ('11', 'HR20170424QDZBX001', 'B6', 'Position：P3（ON）', 'HR20170424QDZBX001-B6.json', '3', '0 ');
INSERT INTO "LHJX01"."T_B_JSON_PROPERTY" VALUES ('12', 'USLABXYJ', 'P1', 'Position：P1（OFF）', 'HR20170424QDZBX001-B6.json', '1', '1 ');
INSERT INTO "LHJX01"."T_B_JSON_PROPERTY" VALUES ('13', 'USLABXYJ', 'P2', 'Position：P2（OFF）', 'HR20160407QDZKA001-2AB.json', '2', '1 ');

-- ----------------------------
-- Indexes structure for table T_B_JSON_PROPERTY
-- ----------------------------

-- ----------------------------
-- Checks structure for table T_B_JSON_PROPERTY
-- ----------------------------
ALTER TABLE "LHJX01"."T_B_JSON_PROPERTY" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table T_B_JSON_PROPERTY
-- ----------------------------
ALTER TABLE "LHJX01"."T_B_JSON_PROPERTY" ADD PRIMARY KEY ("ID");
