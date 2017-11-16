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

Date: 2017-11-16 09:42:42
*/


-- ----------------------------
-- Table structure for T_B_LAB_VIDEO
-- ----------------------------
DROP TABLE "LHJX01"."T_B_LAB_VIDEO";
CREATE TABLE "LHJX01"."T_B_LAB_VIDEO" (
"ID" VARCHAR2(100 BYTE) NOT NULL ,
"DATA_CENTER_ID" VARCHAR2(20 BYTE) NULL ,
"LAB_CODE" VARCHAR2(20 BYTE) NULL ,
"VIDEL_URL" VARCHAR2(1000 BYTE) NULL ,
"SHOW_TITLE" VARCHAR2(100 BYTE) NULL ,
"PICTURE" VARCHAR2(200 BYTE) NULL ,
"SHOW_FLAG" VARCHAR2(2 BYTE) NULL ,
"IS_TOP" VARCHAR2(2 BYTE) NULL ,
"ORDER_NUM" NUMBER NULL ,
"DEL_FLAG" VARCHAR2(2 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE "LHJX01"."T_B_LAB_VIDEO" IS '实验室视频监控信息表';
COMMENT ON COLUMN "LHJX01"."T_B_LAB_VIDEO"."ID" IS '主键';
COMMENT ON COLUMN "LHJX01"."T_B_LAB_VIDEO"."DATA_CENTER_ID" IS '所属数据中心';
COMMENT ON COLUMN "LHJX01"."T_B_LAB_VIDEO"."LAB_CODE" IS '实验室编码';
COMMENT ON COLUMN "LHJX01"."T_B_LAB_VIDEO"."VIDEL_URL" IS '视频流地址';
COMMENT ON COLUMN "LHJX01"."T_B_LAB_VIDEO"."SHOW_TITLE" IS '视频显示名称';
COMMENT ON COLUMN "LHJX01"."T_B_LAB_VIDEO"."PICTURE" IS '图片文件';
COMMENT ON COLUMN "LHJX01"."T_B_LAB_VIDEO"."SHOW_FLAG" IS '显示标志位 0：显示 1-不显示';
COMMENT ON COLUMN "LHJX01"."T_B_LAB_VIDEO"."IS_TOP" IS '是否置顶显示 0：不置顶 1：置顶';
COMMENT ON COLUMN "LHJX01"."T_B_LAB_VIDEO"."ORDER_NUM" IS '排序';
COMMENT ON COLUMN "LHJX01"."T_B_LAB_VIDEO"."DEL_FLAG" IS '删除标志位0 正常 1 -删除';

-- ----------------------------
-- Records of T_B_LAB_VIDEO
-- ----------------------------
INSERT INTO "LHJX01"."T_B_LAB_VIDEO" VALUES ('1', '15', 'JZKTC001', 'http://10.130.96.65:6713/mag/hls/db2920c76afa4cf2ae02dd0f414a4bd4/1/live.m3u8', '胶州一期性能', null, '0', '1', '0', '0');
INSERT INTO "LHJX01"."T_B_LAB_VIDEO" VALUES ('2', '15', 'JZKTC002', 'http://10.130.96.65:6713/mag/hls/ab2a11779b444bbdb1ffc6b1178d90db/1/live.m3u8', '胶州一期安全', null, '0', '1', '0', '0');
INSERT INTO "LHJX01"."T_B_LAB_VIDEO" VALUES ('5', '9', 'TGBXA', 'http://10.130.96.65:6713/mag/hls/8f60eef006f34032b3f92079553a5e39/1/live.m3u8', 'RF-A', null, '0', '1', '0', '0');
INSERT INTO "LHJX01"."T_B_LAB_VIDEO" VALUES ('6', '9', 'TGBXB', 'http://10.130.96.65:6713/mag/hls/b055403a5a464b69bebc4b670bfdff60/1/live.m3u8', 'RF-B', null, '0', '1', '0', '0');
INSERT INTO "LHJX01"."T_B_LAB_VIDEO" VALUES ('7', '9', 'TGBXC', 'http://10.130.96.65:6713/mag/hls/72441956200e45c5a9487ce9a8cee4bf/1/live.m3u8', 'RF-C', null, '0', '1', '0', '0');
INSERT INTO "LHJX01"."T_B_LAB_VIDEO" VALUES ('8', '9', 'TGBXD', 'http://10.130.96.65:6713/mag/hls/8d366d48f61e44df92fe1f79fb4b1e17/1/live.m3u8', 'RF-D', null, '0', '1', '0', '0');
INSERT INTO "LHJX01"."T_B_LAB_VIDEO" VALUES ('9', '9', 'TGKTA', 'http://10.130.96.65:6713/mag/hls/e7f6b53231484bca9ff3fd7a7dc820a5/1/live.m3u8', '泰国RF-IN', null, '0', '1', '0', '0');
INSERT INTO "LHJX01"."T_B_LAB_VIDEO" VALUES ('10', '1', 'ZXYSHJ001', 'http://10.130.96.65:6713/mag/hls/7950836d29764a27b0528685f4b4ea59/1/live.m3u8', '运输步入式箱', null, '0', '1', '0', '0');
INSERT INTO "LHJX01"."T_B_LAB_VIDEO" VALUES ('11', '1', 'ZXYSHJ002', 'http://10.130.96.65:6713/mag/hls/7950836d29764a27b0528685f4b4ea59/1/live.m3u8', '运输步入式箱', null, '0', '1', '0', '0');
INSERT INTO "LHJX01"."T_B_LAB_VIDEO" VALUES ('12', '1', 'ZXCLHJ001', 'http://10.130.96.65:6713/mag/hls/38b1530e29c34d51afbdd8d99fe66e32/1/live.m3u8', '材料冷热冲击箱', null, '0', '1', '0', '0');
INSERT INTO "LHJX01"."T_B_LAB_VIDEO" VALUES ('13', '1', 'ZXCLHJ002', 'http://10.130.96.65:6713/mag/hls/acee9928ca2949ba91d1584af3c51bca/1/live.m3u8', '材料交变湿热箱', null, '0', '1', '0', '0');
INSERT INTO "LHJX01"."T_B_LAB_VIDEO" VALUES ('14', '1', 'znjdtyg', 'http://10.130.96.65:6713/mag/hls/3e158a568dd84c2890d095a25517f78b/1/live.m3u8', '体验馆2', null, '0', '1', '0', '0');
INSERT INTO "LHJX01"."T_B_LAB_VIDEO" VALUES ('15', '1', '47', 'http://10.130.96.65:6713/mag/hls/8e715c345586421cae13e2ec9feec755/1/live.m3u8', '体验馆1', null, '0', '1', '0', '0');
INSERT INTO "LHJX01"."T_B_LAB_VIDEO" VALUES ('16', null, 'JNRSQX001', 'http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8', '香港卫视', null, '0', '1', '0', '0');
INSERT INTO "LHJX01"."T_B_LAB_VIDEO" VALUES ('17', '1', null, 'http://10.130.96.65:6713/mag/hls/5e34bf65e1d2463fbc1d80d400a4bb55/1/live.m3u8', '大厅南', null, '0', '0', '0', '0');
INSERT INTO "LHJX01"."T_B_LAB_VIDEO" VALUES ('18', '1', null, 'http://10.130.96.65:6713/mag/hls/647203256def4b9aab2fc403cb1f7bda/1/live.m3u8', '大厅北', null, '0', '0', '0', '0');
INSERT INTO "LHJX01"."T_B_LAB_VIDEO" VALUES ('19', '1', null, 'http://10.130.96.65:6713/mag/hls/28d97f7d5e544cb4898a5362cdee4403/1/live.m3u8', '南通道4', null, '0', '0', '0', '0');
INSERT INTO "LHJX01"."T_B_LAB_VIDEO" VALUES ('20', '1', null, 'http://10.130.96.65:6713/mag/hls/592a4fe7b4fc40d88a9cf5a27db23cfe/1/live.m3u8', '包装运输', null, '0', '0', '0', '0');
INSERT INTO "LHJX01"."T_B_LAB_VIDEO" VALUES ('21', '1', null, 'http://10.130.96.65:6713/mag/hls/ca159b07c48b4111baf5a61550f3602c/1/live.m3u8', '运输南侧', null, '0', '0', '0', '0');
INSERT INTO "LHJX01"."T_B_LAB_VIDEO" VALUES ('22', '9', null, 'http://10.130.96.65:6713/mag/hls/4c00e3f243a5464798a54d6fdd57cc82/1/live.m3u8', 'IPdome', null, '0', '0', '0', '0');
INSERT INTO "LHJX01"."T_B_LAB_VIDEO" VALUES ('23', '9', null, 'http://10.130.96.65:6713/mag/hls/c13c5a6bd40447f38e6fd55310232473/1/live.m3u8', 'RF-CY', null, '0', '0', '0', null);

-- ----------------------------
-- Indexes structure for table T_B_LAB_VIDEO
-- ----------------------------

-- ----------------------------
-- Checks structure for table T_B_LAB_VIDEO
-- ----------------------------
ALTER TABLE "LHJX01"."T_B_LAB_VIDEO" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table T_B_LAB_VIDEO
-- ----------------------------
ALTER TABLE "LHJX01"."T_B_LAB_VIDEO" ADD PRIMARY KEY ("ID");
