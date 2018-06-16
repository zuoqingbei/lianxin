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

Date: 2017-12-11 09:30:41
*/


-- ----------------------------
-- Table structure for T_B_PROVIDER_DIC
-- ----------------------------
DROP TABLE "LHJX01"."T_B_PROVIDER_DIC";
CREATE TABLE "LHJX01"."T_B_PROVIDER_DIC" (
"ID" VARCHAR2(20 BYTE) NOT NULL ,
"PARENT_ID" VARCHAR2(20 BYTE) NULL ,
"NAME" VARCHAR2(255 BYTE) NULL ,
"DIC_LEVEL" CHAR(10 BYTE) NULL ,
"ORDER_NO" NUMBER NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE "LHJX01"."T_B_PROVIDER_DIC" IS '检测字典 供应商 型号';
COMMENT ON COLUMN "LHJX01"."T_B_PROVIDER_DIC"."NAME" IS '名称';
COMMENT ON COLUMN "LHJX01"."T_B_PROVIDER_DIC"."DIC_LEVEL" IS '等级 1：供应商 2：型号 3:工位';

-- ----------------------------
-- Records of T_B_PROVIDER_DIC
-- ----------------------------
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('23', '9', '035W', '2         ', '1');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('24', '9', '035X', '2         ', '2');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('25', '23', '门锁检测', '3         ', '1');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('26', '23', 'LED检测', '3         ', '2');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('27', '23', '过零信号检测', '3         ', '3');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('28', '23', '蜂鸣器检测', '3         ', '4');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('29', '23', '待机电流检测', '3         ', '5');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('30', '24', '门锁检测', '3         ', '1');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('31', '24', 'LED检测', '3         ', '2');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('32', '24', '过零信号检测', '3         ', '3');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('33', '24', '蜂鸣器检测', '3         ', '4');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('34', '24', '待机电流检测', '3         ', '5');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('35', '18', '断开温度检测', '3         ', '1');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('36', '21', '断开温度检测', '3         ', '1');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('37', '22', '绕组值检测', '3         ', '1');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('1', '0', '常州福兰德电器', '1         ', '11');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('2', '0', '章丘海尔电机', '1         ', '12');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('3', '0', '海尔智能电子', '1         ', '30');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('4', '0', '广州万宝集团', '1         ', '40');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('5', '0', '黄石东贝压缩机', '1         ', '50');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('6', '0', '斐雪派克电器', '1         ', '60');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('7', '0', '重庆合信包装', '1         ', '70');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('8', '0', '青岛宝井钢材', '1         ', '80');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('9', '0', '广州捷普电子', '1         ', '10');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('18', '1', 'WY77H-C1', '2         ', '1');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('21', '1', 'WY-SA', '2         ', '4');
INSERT INTO "LHJX01"."T_B_PROVIDER_DIC" VALUES ('22', '2', '200E', '2         ', '1');

-- ----------------------------
-- Indexes structure for table T_B_PROVIDER_DIC
-- ----------------------------

-- ----------------------------
-- Checks structure for table T_B_PROVIDER_DIC
-- ----------------------------
ALTER TABLE "LHJX01"."T_B_PROVIDER_DIC" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table T_B_PROVIDER_DIC
-- ----------------------------
ALTER TABLE "LHJX01"."T_B_PROVIDER_DIC" ADD PRIMARY KEY ("ID");
