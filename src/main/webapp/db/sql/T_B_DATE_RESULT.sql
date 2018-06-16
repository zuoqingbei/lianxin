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

Date: 2017-12-11 09:29:39
*/


-- ----------------------------
-- Table structure for T_B_DATE_RESULT
-- ----------------------------
DROP TABLE "LHJX01"."T_B_DATE_RESULT";
CREATE TABLE "LHJX01"."T_B_DATE_RESULT" (
"ID" VARCHAR2(20 BYTE) NOT NULL ,
"TYPE" CHAR(2 BYTE) NULL ,
"TYPE_NAME" VARCHAR2(255 BYTE) NULL ,
"PRODUCT_CODE" VARCHAR2(20 BYTE) NULL ,
"PRODUCT_NAME" VARCHAR2(255 BYTE) NULL ,
"NUM" VARCHAR2(20 BYTE) NULL ,
"EXTEND_NAME" VARCHAR2(20 BYTE) NULL ,
"DEL_FLAG" VARCHAR2(255 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE "LHJX01"."T_B_DATE_RESULT" IS '数据结果 ';
COMMENT ON COLUMN "LHJX01"."T_B_DATE_RESULT"."ID" IS '主键';
COMMENT ON COLUMN "LHJX01"."T_B_DATE_RESULT"."TYPE" IS '数据结果类型。0:标准状态 1：能力状态  2:起草数';
COMMENT ON COLUMN "LHJX01"."T_B_DATE_RESULT"."TYPE_NAME" IS '类型名称';
COMMENT ON COLUMN "LHJX01"."T_B_DATE_RESULT"."PRODUCT_CODE" IS '产线编码';
COMMENT ON COLUMN "LHJX01"."T_B_DATE_RESULT"."PRODUCT_NAME" IS '产线名称';
COMMENT ON COLUMN "LHJX01"."T_B_DATE_RESULT"."NUM" IS '数量';
COMMENT ON COLUMN "LHJX01"."T_B_DATE_RESULT"."EXTEND_NAME" IS '扩展名称 type为2时使用';

-- ----------------------------
-- Records of T_B_DATE_RESULT
-- ----------------------------
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('2', '0 ', '国家标准', '21', '冰冷', '76', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('3', '0 ', '行业标准', '21', '冰冷', '38', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('1', '0 ', '国际标准', '21', '冰冷', '63', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('4', '0 ', '企业标准', '21', '冰冷', '192', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('5', '0 ', '国际标准', '22', '洗涤', '44', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('6', '0 ', '国家标准', '22', '洗涤', '52', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('7', '0 ', '行业标准', '22', '洗涤', '26', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('8', '0 ', '企业标准', '22', '洗涤', '189', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('9', '0 ', '国际标准', '23', '家空', '37', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('10', '0 ', '国家标准', '23', '家空', '48', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('11', '0 ', '行业标准', '23', '家空', '24', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('12', '0 ', '企业标准', '23', '家空', '109', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('13', '0 ', '国际标准', '24', '商空', '33', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('14', '0 ', '国家标准', '24', '商空', '43', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('15', '0 ', '行业标准', '24', '商空', '21', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('16', '0 ', '企业标准', '24', '商空', '75', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('17', '0 ', '国际标准', '26', '厨电', '42', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('18', '0 ', '国家标准', '26', '厨电', '56', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('19', '0 ', '行业标准', '26', '厨电', '28', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('20', '0 ', '企业标准', '26', '厨电', '66', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('21', '0 ', '国际标准', '25', '热水器', '32', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('22', '0 ', '国家标准', '25', '热水器', '44', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('23', '0 ', '行业标准', '25', '热水器', '22', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('24', '0 ', '企业标准', '25', '热水器', '137', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('25', '1 ', '完全具备数', '21', '冰冷', '319', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('26', '1 ', '部分具备数', '21', '冰冷', '39', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('27', '1 ', '完全不具备数', '21', '冰冷', '11', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('28', '1 ', '完全具备数', '22', '洗涤', '268', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('29', '1 ', '部分具备数', '22', '洗涤', '37', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('30', '1 ', '完全不具备数', '22', '洗涤', '6', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('31', '1 ', '完全具备数', '23', '家空', '189', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('32', '1 ', '部分具备数', '23', '家空', '22', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('33', '1 ', '完全不具备数', '23', '家空', '7', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('34', '1 ', '完全具备数', '24', '商空', '142', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('35', '1 ', '部分具备数', '24', '商空', '21', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('36', '1 ', '完全不具备数', '24', '商空', '9', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('37', '1 ', '完全具备数', '26', '厨电', '153', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('38', '1 ', '部分具备数', '26', '厨电', '27', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('39', '1 ', '完全不具备数', '26', '厨电', '12', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('40', '1 ', '完全具备数', '25', '热水器', '204', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('41', '1 ', '部分具备数', '25', '热水器', '23', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('42', '1 ', '完全不具备数', '25', '热水器', '8', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('43', '2 ', '牵头起草数', null, null, '0', '国际标准', '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('44', '2 ', '牵头起草数', null, null, '86', '国家标准', '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('45', '2 ', '牵头起草数', null, null, '58', '行业标准', '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('46', '2 ', '牵头起草数', null, null, '768', '企业标准', '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('47', '2 ', '参与起草数', null, null, '90', '国际标准', '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('48', '2 ', '参与起草数', null, null, '172', '国家标准', '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('49', '2 ', '参与起草数', null, null, '114', '行业标准', '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('50', '2 ', '参与起草数', null, null, '0', '企业标准', '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('51', '3 ', '应具备数', '21', '冰冷', '369', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('52', '3 ', '应具备数', '22', '洗涤', '311', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('53', '3 ', '应具备数', '23', '家空', '218', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('54', '3 ', '应具备数', '24', '商空', '172', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('55', '3 ', '应具备数', '26', '厨电', '192', null, '0');
INSERT INTO "LHJX01"."T_B_DATE_RESULT" VALUES ('56', '3 ', '应具备数', '25', '热水器', '235', null, '0');

-- ----------------------------
-- Indexes structure for table T_B_DATE_RESULT
-- ----------------------------

-- ----------------------------
-- Checks structure for table T_B_DATE_RESULT
-- ----------------------------
ALTER TABLE "LHJX01"."T_B_DATE_RESULT" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table T_B_DATE_RESULT
-- ----------------------------
ALTER TABLE "LHJX01"."T_B_DATE_RESULT" ADD PRIMARY KEY ("ID");
