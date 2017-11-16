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

Date: 2017-11-16 10:27:45
*/


-- ----------------------------
-- Table structure for PHM_DEVICE_INFO
-- ----------------------------
DROP TABLE "LHJX01"."PHM_DEVICE_INFO";
CREATE TABLE "LHJX01"."PHM_DEVICE_INFO" (
"SNCODE" VARCHAR2(255 BYTE) NULL ,
"NAME" VARCHAR2(255 BYTE) NULL ,
"DIMENSION" VARCHAR2(255 BYTE) NULL ,
"LONGITUDE" VARCHAR2(255 BYTE) NULL ,
"IMAGE" VARCHAR2(255 BYTE) NULL ,
"ADDRESS" VARCHAR2(255 BYTE) NULL ,
"STATUS" VARCHAR2(255 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE "LHJX01"."PHM_DEVICE_INFO" IS '设备基础信息';
COMMENT ON COLUMN "LHJX01"."PHM_DEVICE_INFO"."DIMENSION" IS '纬度';
COMMENT ON COLUMN "LHJX01"."PHM_DEVICE_INFO"."LONGITUDE" IS '经度';
COMMENT ON COLUMN "LHJX01"."PHM_DEVICE_INFO"."IMAGE" IS '图片';
COMMENT ON COLUMN "LHJX01"."PHM_DEVICE_INFO"."ADDRESS" IS '地址';
COMMENT ON COLUMN "LHJX01"."PHM_DEVICE_INFO"."STATUS" IS '健康状态';

-- ----------------------------
-- Records of PHM_DEVICE_INFO
-- ----------------------------
INSERT INTO "LHJX01"."PHM_DEVICE_INFO" VALUES ('20171026BX001', '盛世美颜迪丽热巴', '39.6', '116.1', 'asserts/video/3theA.mp4', '北京市海淀区高雄1路1号', '良好');
INSERT INTO "LHJX01"."PHM_DEVICE_INFO" VALUES ('20171026BX008', '杭州', '30.1', '120', 'asserts/video/3theA.mp4', '杭州', '严重');
INSERT INTO "LHJX01"."PHM_DEVICE_INFO" VALUES ('20171026BX009', '海南', '20', '110', 'asserts/video/3theA.mp4', '海南', '轻微');
INSERT INTO "LHJX01"."PHM_DEVICE_INFO" VALUES ('20171026BX010', '乌鲁木齐', '43.5', '87.4', 'asserts/video/3theA.mp4', '乌鲁木齐', '一般');
INSERT INTO "LHJX01"."PHM_DEVICE_INFO" VALUES ('20171026BX002', '拉萨', '30.1', '91.1', 'asserts/video/3theA.mp4', '拉萨', '一般');
INSERT INTO "LHJX01"."PHM_DEVICE_INFO" VALUES ('20171026BX003', '武汉', '30.6', '114.3', 'asserts/video/3theA.mp4', '武汉', '严重');
INSERT INTO "LHJX01"."PHM_DEVICE_INFO" VALUES ('20171026BX004', '郑州', '34', '113', 'asserts/video/3theA.mp4', '郑州', '良好');
INSERT INTO "LHJX01"."PHM_DEVICE_INFO" VALUES ('20171026BX005', '青岛', '36', '120', 'asserts/video/3theA.mp4', '青岛', '良好');
INSERT INTO "LHJX01"."PHM_DEVICE_INFO" VALUES ('20171026BX006', '上海', '31.2', '121.48', 'asserts/video/3theA.mp4', '上海', '良好');
INSERT INTO "LHJX01"."PHM_DEVICE_INFO" VALUES ('20171026BX007', '重庆', '30.1', '107.7', 'asserts/video/3theA.mp4', '重庆', '轻微');
