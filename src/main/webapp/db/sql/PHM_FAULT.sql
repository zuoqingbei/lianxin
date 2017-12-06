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

Date: 2017-12-06 15:32:12
*/


-- ----------------------------
-- Table structure for PHM_FAULT
-- ----------------------------
DROP TABLE "LHJX01"."PHM_FAULT";
CREATE TABLE "LHJX01"."PHM_FAULT" (
"ID" VARCHAR2(255 BYTE) NULL ,
"SNCODE" VARCHAR2(255 BYTE) NULL ,
"FAULT_APPEARANCE_CODE" VARCHAR2(255 BYTE) NULL ,
"FAULT_APPEARANCE" VARCHAR2(255 BYTE) NULL ,
"FAULT_RESON_CODE" VARCHAR2(255 BYTE) NULL ,
"FAULT_RESON" VARCHAR2(255 BYTE) NULL ,
"FAULT_SEAT_NUMBER" VARCHAR2(255 BYTE) NULL ,
"FAULT_REPAIR_CODE" VARCHAR2(255 BYTE) NULL ,
"FAULT_REPAIR" VARCHAR2(255 BYTE) NULL ,
"FAULT_DUTY_TYPE" VARCHAR2(255 BYTE) NULL ,
"FAULT_NAME" VARCHAR2(255 BYTE) NULL ,
"FAULT_TIME" DATE NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "LHJX01"."PHM_FAULT"."ID" IS 'ID';
COMMENT ON COLUMN "LHJX01"."PHM_FAULT"."FAULT_APPEARANCE_CODE" IS '故障现象编码';
COMMENT ON COLUMN "LHJX01"."PHM_FAULT"."FAULT_APPEARANCE" IS '故障现象编码';
COMMENT ON COLUMN "LHJX01"."PHM_FAULT"."FAULT_RESON_CODE" IS '故障原因编码';
COMMENT ON COLUMN "LHJX01"."PHM_FAULT"."FAULT_RESON" IS '故障原因';
COMMENT ON COLUMN "LHJX01"."PHM_FAULT"."FAULT_SEAT_NUMBER" IS '位号';
COMMENT ON COLUMN "LHJX01"."PHM_FAULT"."FAULT_REPAIR_CODE" IS '维修措施编码';
COMMENT ON COLUMN "LHJX01"."PHM_FAULT"."FAULT_REPAIR" IS '维修措施';
COMMENT ON COLUMN "LHJX01"."PHM_FAULT"."FAULT_DUTY_TYPE" IS '责任类别';
COMMENT ON COLUMN "LHJX01"."PHM_FAULT"."FAULT_NAME" IS '故障名称';
COMMENT ON COLUMN "LHJX01"."PHM_FAULT"."FAULT_TIME" IS ' 故障发生时间';

-- ----------------------------
-- Records of PHM_FAULT
-- ----------------------------
INSERT INTO "LHJX01"."PHM_FAULT" VALUES (null, '20171026BX001', null, null, null, null, null, null, '冷冻风机维修指导,传感器维修指导', null, '主控板存储器读写功能丧失', TO_DATE('2017-07-29 17:20:13', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LHJX01"."PHM_FAULT" VALUES (null, '20171026BX008', null, null, null, null, null, null, '冷冻风机维修指导,传感器维修指导', null, '主控板存储器读写功能丧失', TO_DATE('2017-07-29 17:20:13', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LHJX01"."PHM_FAULT" VALUES (null, '20171026BX009', null, null, null, null, null, null, '冷冻风机维修指导,传感器维修指导', null, '主控板存储器读写功能丧失', TO_DATE('2017-07-29 17:20:13', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LHJX01"."PHM_FAULT" VALUES (null, '20171026BX010', null, null, null, null, null, null, '冷冻风机维修指导,传感器维修指导', null, '主控板存储器读写功能丧失', TO_DATE('2017-07-29 17:20:13', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LHJX01"."PHM_FAULT" VALUES (null, '20171026BX002', null, null, null, null, null, null, '冷冻风机维修指导,传感器维修指导', null, '主控板存储器读写功能丧失', TO_DATE('2017-07-29 17:20:13', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LHJX01"."PHM_FAULT" VALUES (null, '20171026BX003', null, null, null, null, null, null, '冷冻风机维修指导,传感器维修指导', null, '主控板存储器读写功能丧失', TO_DATE('2017-07-29 17:20:13', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LHJX01"."PHM_FAULT" VALUES (null, '20171026BX004', null, null, null, null, null, null, '冷冻风机维修指导,传感器维修指导', null, '主控板存储器读写功能丧失', TO_DATE('2017-07-29 17:20:13', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LHJX01"."PHM_FAULT" VALUES (null, '20171026BX005', null, null, null, null, null, null, '冷冻风机维修指导,传感器维修指导', null, '主控板存储器读写功能丧失', TO_DATE('2017-07-29 17:20:13', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LHJX01"."PHM_FAULT" VALUES (null, '20171026BX006', null, null, null, null, null, null, '冷冻风机维修指导,传感器维修指导', null, '主控板存储器读写功能丧失', TO_DATE('2017-07-29 17:20:13', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LHJX01"."PHM_FAULT" VALUES (null, '20171026BX007', null, null, null, null, null, null, '冷冻风机维修指导,传感器维修指导', null, '主控板存储器读写功能丧失', TO_DATE('2017-07-29 17:20:13', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LHJX01"."PHM_FAULT" VALUES (null, '20171026BX003', null, null, null, null, null, null, '冷藏风机维修指导,电磁阀维修指导', null, '冷藏风机输出功能失效', TO_DATE('2017-08-02 17:20:13', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LHJX01"."PHM_FAULT" VALUES (null, '20171026BX004', null, null, null, null, null, null, '冷藏风机维修指导,电磁阀维修指导', null, '冷藏风机输出功能失效', TO_DATE('2017-08-02 17:20:13', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LHJX01"."PHM_FAULT" VALUES (null, '20171026BX005', null, null, null, null, null, null, '冷藏风机维修指导,电磁阀维修指导', null, '冷藏风机输出功能失效', TO_DATE('2017-08-02 17:20:13', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LHJX01"."PHM_FAULT" VALUES (null, '20171026BX006', null, null, null, null, null, null, '冷藏风机维修指导,电磁阀维修指导', null, '冷藏风机输出功能失效', TO_DATE('2017-08-02 17:20:13', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LHJX01"."PHM_FAULT" VALUES (null, '20171026BX007', null, null, null, null, null, null, '冷藏风机维修指导,电磁阀维修指导', null, '冷藏风机输出功能失效', TO_DATE('2017-08-02 17:20:13', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LHJX01"."PHM_FAULT" VALUES (null, '20171026BX001', null, null, null, null, null, null, '冷藏风机维修指导,电磁阀维修指导', null, '冷藏风机输出功能失效', TO_DATE('2017-08-02 17:20:13', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LHJX01"."PHM_FAULT" VALUES (null, '20171026BX008', null, null, null, null, null, null, '冷藏风机维修指导,电磁阀维修指导', null, '冷藏风机输出功能失效', TO_DATE('2017-08-02 17:20:13', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LHJX01"."PHM_FAULT" VALUES (null, '20171026BX009', null, null, null, null, null, null, '冷藏风机维修指导,电磁阀维修指导', null, '冷藏风机输出功能失效', TO_DATE('2017-08-02 17:20:13', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LHJX01"."PHM_FAULT" VALUES (null, '20171026BX010', null, null, null, null, null, null, '冷藏风机维修指导,电磁阀维修指导', null, '冷藏风机输出功能失效', TO_DATE('2017-08-02 17:20:13', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LHJX01"."PHM_FAULT" VALUES (null, '20171026BX002', null, null, null, null, null, null, '冷藏风机维修指导,电磁阀维修指导', null, '冷藏风机输出功能失效', TO_DATE('2017-08-02 17:20:13', 'YYYY-MM-DD HH24:MI:SS'));
