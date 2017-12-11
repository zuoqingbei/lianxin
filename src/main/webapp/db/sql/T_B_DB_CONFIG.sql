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

Date: 2017-12-11 09:29:43
*/


-- ----------------------------
-- Table structure for T_B_DB_CONFIG
-- ----------------------------
DROP TABLE "LHJX01"."T_B_DB_CONFIG";
CREATE TABLE "LHJX01"."T_B_DB_CONFIG" (
"ID" VARCHAR2(20 BYTE) NULL ,
"CONFIG_NAME" VARCHAR2(100 BYTE) NOT NULL ,
"TESTMETADATA" VARCHAR2(100 BYTE) NULL ,
"SENSORINFO" VARCHAR2(100 BYTE) NULL ,
"TESTUNITINFO" VARCHAR2(100 BYTE) NULL ,
"SENSORTYPEINFO" VARCHAR2(100 BYTE) NULL ,
"TESTDATA" VARCHAR2(100 BYTE) NULL ,
"PARTITION" NCHAR(2) DEFAULT 0  NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE "LHJX01"."T_B_DB_CONFIG" IS '数据库配置';
COMMENT ON COLUMN "LHJX01"."T_B_DB_CONFIG"."CONFIG_NAME" IS '数据库配置名称';
COMMENT ON COLUMN "LHJX01"."T_B_DB_CONFIG"."TESTMETADATA" IS '测试数据元数据信息对应应表名称';
COMMENT ON COLUMN "LHJX01"."T_B_DB_CONFIG"."SENSORINFO" IS '传感器信息对应的表';
COMMENT ON COLUMN "LHJX01"."T_B_DB_CONFIG"."TESTUNITINFO" IS '监测单元对应的表';
COMMENT ON COLUMN "LHJX01"."T_B_DB_CONFIG"."SENSORTYPEINFO" IS '传感器类型对应的表';
COMMENT ON COLUMN "LHJX01"."T_B_DB_CONFIG"."TESTDATA" IS '测试数据对应的表';
COMMENT ON COLUMN "LHJX01"."T_B_DB_CONFIG"."PARTITION" IS '是否使用了分区 0-没有 1-使用';

-- ----------------------------
-- Records of T_B_DB_CONFIG
-- ----------------------------
INSERT INTO "LHJX01"."T_B_DB_CONFIG" VALUES ('3', 'chongqinghive', 'db_hive_ulab.s_bxlab_orcl_talend_test_testmetadata', 'db_hive_ulab.s_bxlab_orcl_talend_test_sensorinfo', 'db_hive_ulab.s_bxlab_orcl_talend_test_testunitinfo', 'db_hive_ulab.s_bxlab_orcl_talend_test_sensortypeinfo', 'db_hive_ulab.s_bxlab_orcl_talend_test_testdata', '1 ');
INSERT INTO "LHJX01"."T_B_DB_CONFIG" VALUES ('4', 'jnrsq', 'Tb_TestMetadata', 'tb_sensorinfo', 'Tb_TestUnitInfo', 'Tb_SensorTypeInfo', 'Tb_Testdata', '0 ');
INSERT INTO "LHJX01"."T_B_DB_CONFIG" VALUES ('5', 'zhbrzj', 'Tb_TestMetadata', 'tb_sensorinfo', 'Tb_TestUnitInfo', 'Tb_SensorTypeInfo', 'Tb_Testdata', '0 ');
INSERT INTO "LHJX01"."T_B_DB_CONFIG" VALUES ('7', 'russia', 'Tb_TestMetadata', 'tb_sensorinfo', 'Tb_TestUnitInfo', 'Tb_SensorTypeInfo', 'Tb_Testdata', '0 ');
INSERT INTO "LHJX01"."T_B_DB_CONFIG" VALUES ('1', 'thailand', 'Tb_TestMetadata', 'tb_sensorinfo', 'Tb_TestUnitInfo', 'Tb_SensorTypeInfo', 'Tb_Testdata', '0 ');
INSERT INTO "LHJX01"."T_B_DB_CONFIG" VALUES ('6', 'jnxd', 'Tb_TestMetadata', 'tb_sensorinfo', 'Tb_TestUnitInfo', 'Tb_SensorTypeInfo', 'Tb_Testdata', '0 ');
INSERT INTO "LHJX01"."T_B_DB_CONFIG" VALUES ('2', 'jzkt', 'Tb_TestMetadata', 'tb_sensorinfo', 'Tb_TestUnitInfo', 'Tb_SensorTypeInfo', 'Tb_Testdata', '0 ');

-- ----------------------------
-- Indexes structure for table T_B_DB_CONFIG
-- ----------------------------

-- ----------------------------
-- Checks structure for table T_B_DB_CONFIG
-- ----------------------------
ALTER TABLE "LHJX01"."T_B_DB_CONFIG" ADD CHECK ("CONFIG_NAME" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table T_B_DB_CONFIG
-- ----------------------------
ALTER TABLE "LHJX01"."T_B_DB_CONFIG" ADD PRIMARY KEY ("CONFIG_NAME");
