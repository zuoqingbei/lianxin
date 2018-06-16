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

Date: 2017-12-11 09:29:47
*/


-- ----------------------------
-- Table structure for T_B_DB_PARTITION
-- ----------------------------
DROP TABLE "LHJX01"."T_B_DB_PARTITION";
CREATE TABLE "LHJX01"."T_B_DB_PARTITION" (
"ID" VARCHAR2(20 BYTE) NULL ,
"IS_PTLABNAME" CHAR(2 BYTE) NULL ,
"LAB_CODE" VARCHAR2(100 BYTE) NULL ,
"PTLABNAME_FILED" VARCHAR2(100 BYTE) NULL ,
"IS_PT" CHAR(2 BYTE) NULL ,
"PT_FILED" VARCHAR2(100 BYTE) NULL ,
"DEL_FLAG" CHAR(2 BYTE) NULL ,
"CONFIG_NAME" VARCHAR2(20 BYTE) NOT NULL ,
"PTLABNAME_VALUE" VARCHAR2(100 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE "LHJX01"."T_B_DB_PARTITION" IS '数据库分区对照表';
COMMENT ON COLUMN "LHJX01"."T_B_DB_PARTITION"."IS_PTLABNAME" IS '是否按照实验室分区 0:是 1-否';
COMMENT ON COLUMN "LHJX01"."T_B_DB_PARTITION"."LAB_CODE" IS '实验室编码';
COMMENT ON COLUMN "LHJX01"."T_B_DB_PARTITION"."PTLABNAME_FILED" IS '按照实验室分区列名称';
COMMENT ON COLUMN "LHJX01"."T_B_DB_PARTITION"."IS_PT" IS '是否通过年月日分区 0:是 1-否';
COMMENT ON COLUMN "LHJX01"."T_B_DB_PARTITION"."PT_FILED" IS '年月分区列名称';
COMMENT ON COLUMN "LHJX01"."T_B_DB_PARTITION"."DEL_FLAG" IS '0-未删除 1-已删除';
COMMENT ON COLUMN "LHJX01"."T_B_DB_PARTITION"."CONFIG_NAME" IS '数据库配置名称';

-- ----------------------------
-- Records of T_B_DB_PARTITION
-- ----------------------------
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('1', '0 ', 'CQBXX001', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'chongqingxingshiAB');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('2', '0 ', 'CQBXX002', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'chongqingxingshiAB');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('3', '0 ', 'CQBXX003', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'chongqingxingshiCD');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('4', '0 ', 'CQBXX004', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'chongqingxingshiCD');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('5', '0 ', 'CQBXX005', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'guizhouxingshiC');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('6', '0 ', 'CQBXC001', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'chongqingkekao');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('7', '0 ', 'CQBXC002', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'chongqingkekao');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('8', '0 ', 'CQBXC003', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'chongqingchouyangBC');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('9', '0 ', 'CQBXC004', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'chongqingchouyangBC');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('10', '0 ', 'CQBXC005', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'chongqingchouyangBC');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('11', '0 ', 'CQBXK001', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'chongqingkekao');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('12', '0 ', 'GZBXX001', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'guizhouxingshiAB');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('13', '0 ', 'GZBXX002', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'guizhouxingshiAB');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('14', '0 ', 'GZBXA001', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', null);
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('15', '0 ', 'WHLGX001', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'wuhanxingshi');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('16', '0 ', 'WHLGX002', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'wuhanxingshi');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('17', '0 ', 'WHLGK001', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'wuhankekao');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('18', '0 ', 'WHLGK002', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'wuhankekao');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('19', '0 ', 'DLBXX001', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'dalianxingshiab');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('20', '0 ', 'DLBXX002', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'dalianxingshiab');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('21', '0 ', 'DLBXX003', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'dalianxingshicd');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('22', '0 ', 'DLBXX004', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'dalianxingshicd');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('23', '0 ', 'DLBXK001', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'daliankekaoab');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('24', '0 ', 'DLBXK002', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'daliankekaoab');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('25', '0 ', 'DLBXC001', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'dalianchouyang');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('26', '0 ', 'DLBXC002', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'dalianchouyang');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('27', '0 ', 'DLBXC003', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'dalianchouyang');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('28', '0 ', 'DLBXC004', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'dalianchouyang');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('29', '0 ', 'DLBXC005', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'dalianchouyang');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('30', '0 ', 'SYBXC001', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'shenyangchouyang');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('31', '0 ', 'SYBXC002', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'shenyangchouyang');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('32', '0 ', 'SYBXX001', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'shenyangxingshi');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('33', '0 ', 'SYBXX002', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'shenyangxingshi');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('34', '0 ', 'SYBXX003', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'shenyangxingshi');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('35', '0 ', 'SYBXX004', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'shenyangxingshi');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('36', '0 ', 'SYBXK001', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'shenyangkekao');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('37', '0 ', 'SYBXK002', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'shenyangkekao');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('38', '0 ', 'SDBXX001', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'foshanxingshi01');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('39', '0 ', 'SDBXX002', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'foshanxingshi01');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('40', '0 ', 'SDBXX003', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'foshanxingshi01');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('41', '0 ', 'SDBXX004', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'foshanxingshi02');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('42', '0 ', 'SDBXX005', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'foshanxingshi02');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('43', '0 ', 'SDBXX006', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'foshanxingshi02');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('44', '0 ', 'SDBXK001', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'foshankekao01');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('45', '0 ', 'SDBXK002', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'foshankekao01');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('46', '0 ', 'SDBXK003', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'foshankekao02');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('47', '0 ', 'SDBXC001', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'foshanchouyang01');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('48', '0 ', 'SDBXC002', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'foshanchouyang01');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('49', '0 ', 'SDBXC003', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', 'foshanchouyang02');
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('50', '0 ', 'SDBXC004', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', null);
INSERT INTO "LHJX01"."T_B_DB_PARTITION" VALUES ('51', '0 ', 'SDBXC005', 'ptlabname', '0 ', 'pt', '0 ', 'chongqinghive', null);

-- ----------------------------
-- Checks structure for table T_B_DB_PARTITION
-- ----------------------------
ALTER TABLE "LHJX01"."T_B_DB_PARTITION" ADD CHECK ("CONFIG_NAME" IS NOT NULL);

-- ----------------------------
-- Foreign Key structure for table "LHJX01"."T_B_DB_PARTITION"
-- ----------------------------
ALTER TABLE "LHJX01"."T_B_DB_PARTITION" ADD FOREIGN KEY ("CONFIG_NAME") REFERENCES "LHJX01"."T_B_DB_CONFIG" ("CONFIG_NAME");
