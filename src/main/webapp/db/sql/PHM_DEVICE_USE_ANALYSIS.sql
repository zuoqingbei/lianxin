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

Date: 2017-11-16 10:28:09
*/


-- ----------------------------
-- Table structure for PHM_DEVICE_USE_ANALYSIS
-- ----------------------------
DROP TABLE "LHJX01"."PHM_DEVICE_USE_ANALYSIS";
CREATE TABLE "LHJX01"."PHM_DEVICE_USE_ANALYSIS" (
"DESCRIPTION" VARCHAR2(255 BYTE) NULL ,
"SORT" VARCHAR2(255 BYTE) NULL ,
"ID" VARCHAR2(255 BYTE) NULL ,
"DEVICE_INFO_SNCODE" VARCHAR2(255 BYTE) NULL ,
"STATUS" VARCHAR2(255 BYTE) NULL ,
"IMAGEURL" VARCHAR2(255 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "LHJX01"."PHM_DEVICE_USE_ANALYSIS"."DESCRIPTION" IS '描述';
COMMENT ON COLUMN "LHJX01"."PHM_DEVICE_USE_ANALYSIS"."SORT" IS '排序';
COMMENT ON COLUMN "LHJX01"."PHM_DEVICE_USE_ANALYSIS"."ID" IS '主键';
COMMENT ON COLUMN "LHJX01"."PHM_DEVICE_USE_ANALYSIS"."DEVICE_INFO_SNCODE" IS '设备信息DEVICE_INFO的id';
COMMENT ON COLUMN "LHJX01"."PHM_DEVICE_USE_ANALYSIS"."STATUS" IS '状态';
COMMENT ON COLUMN "LHJX01"."PHM_DEVICE_USE_ANALYSIS"."IMAGEURL" IS '图片地址';

-- ----------------------------
-- Records of PHM_DEVICE_USE_ANALYSIS
-- ----------------------------
INSERT INTO "LHJX01"."PHM_DEVICE_USE_ANALYSIS" VALUES ('开关门频率', 'sort13', '13', '20171026BX004', '轻微', 'status/kgmfs.jpg');
INSERT INTO "LHJX01"."PHM_DEVICE_USE_ANALYSIS" VALUES ('开关门频率', 'sort14', '14', '20171026BX006', '严重', 'status/kgmfs.jpg');
INSERT INTO "LHJX01"."PHM_DEVICE_USE_ANALYSIS" VALUES ('开关门频率', 'sort15', '15', '20171026BX005', '中等', 'status/kgmfs.jpg');
INSERT INTO "LHJX01"."PHM_DEVICE_USE_ANALYSIS" VALUES ('开关门频率', 'sort16', '16', '20171026BX007', '轻微', 'status/kgmfs.jpg');
INSERT INTO "LHJX01"."PHM_DEVICE_USE_ANALYSIS" VALUES ('开关门频率', 'sort17', '17', '20171026BX008', '严重', 'status/kgmfs.jpg');
INSERT INTO "LHJX01"."PHM_DEVICE_USE_ANALYSIS" VALUES ('开关门频率', 'sort18', '18', '20171026BX009', '轻微', 'status/kgmfs.jpg');
INSERT INTO "LHJX01"."PHM_DEVICE_USE_ANALYSIS" VALUES ('开关门频率', 'sort19', '19', '20171026BX010', '中等', 'status/kgmfs.jpg');
INSERT INTO "LHJX01"."PHM_DEVICE_USE_ANALYSIS" VALUES ('开关门频率', 'sort1', '1', '20171026BX001', '严重', 'status/kgmfs.jpg');
INSERT INTO "LHJX01"."PHM_DEVICE_USE_ANALYSIS" VALUES ('开门时间过长', 'sort2', '2', '20171026BX001', '中等', 'status/kmsjgc.png');
INSERT INTO "LHJX01"."PHM_DEVICE_USE_ANALYSIS" VALUES ('负载过大', 'sort3', '3', '20171026BX001', '轻微', 'status/fzgd.png');
INSERT INTO "LHJX01"."PHM_DEVICE_USE_ANALYSIS" VALUES ('按键时间过短', 'sort4', '4', '20171026BX001', '良好', 'status/ajsjgd.jpg');
INSERT INTO "LHJX01"."PHM_DEVICE_USE_ANALYSIS" VALUES ('假日模式使用不当', 'sort5', '5', '20171026BX001', '严重', 'status/ajsjgd.jpg');
INSERT INTO "LHJX01"."PHM_DEVICE_USE_ANALYSIS" VALUES ('开关门频率', 'sort6', '6', '20171026BX002', '严重', 'status/kgmfs.jpg');
INSERT INTO "LHJX01"."PHM_DEVICE_USE_ANALYSIS" VALUES ('开门时间过长', 'sort7', '7', '20171026BX002', '中等', 'status/kmsjgc.png');
INSERT INTO "LHJX01"."PHM_DEVICE_USE_ANALYSIS" VALUES ('负载过大', 'sort8', '8', '20171026BX002', '轻微', 'status/fzgd.png');
INSERT INTO "LHJX01"."PHM_DEVICE_USE_ANALYSIS" VALUES ('按键时间过短', 'sort9', '9', '20171026BX002', '良好', 'status/ajsjgd.jpg');
INSERT INTO "LHJX01"."PHM_DEVICE_USE_ANALYSIS" VALUES ('按键时间过短', 'sort10', '10', '20171026BX003', '良好', 'status/ajsjgd.jpg');
INSERT INTO "LHJX01"."PHM_DEVICE_USE_ANALYSIS" VALUES ('开关门频率3', 'sort11', '11', '20171026BX003', '严重', 'status/kgmfs.jpg');
INSERT INTO "LHJX01"."PHM_DEVICE_USE_ANALYSIS" VALUES ('开门时间过长3', 'sort12', '12', '20171026BX003', '轻微', 'status/kmsjgc.png');
INSERT INTO "LHJX01"."PHM_DEVICE_USE_ANALYSIS" VALUES ('正常模式使用不当', 'sort12', '20', '20171026BX001', '轻微', 'status/ajsjgd.jpg');
