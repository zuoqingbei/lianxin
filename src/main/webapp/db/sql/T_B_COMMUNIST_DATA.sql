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

Date: 2017-12-11 09:23:56
*/


-- ----------------------------
-- Table structure for T_B_COMMUNIST_DATA
-- ----------------------------
DROP TABLE "LHJX01"."T_B_COMMUNIST_DATA";
CREATE TABLE "LHJX01"."T_B_COMMUNIST_DATA" (
"ID" VARCHAR2(20 BYTE) NOT NULL ,
"NAME" VARCHAR2(255 BYTE) NULL ,
"PRODUCT_CODE" VARCHAR2(255 BYTE) NULL ,
"PRODUCT_NAME" VARCHAR2(255 BYTE) NULL ,
"LAB_TYPE" VARCHAR2(255 BYTE) NULL ,
"TYPE" CHAR(2 BYTE) NULL ,
"NUM" VARCHAR2(255 BYTE) NULL ,
"DEL_FLAG" VARCHAR2(255 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE "LHJX01"."T_B_COMMUNIST_DATA" IS '多地共产数据（模拟）';
COMMENT ON COLUMN "LHJX01"."T_B_COMMUNIST_DATA"."NAME" IS '时间';
COMMENT ON COLUMN "LHJX01"."T_B_COMMUNIST_DATA"."PRODUCT_CODE" IS '产线编码';
COMMENT ON COLUMN "LHJX01"."T_B_COMMUNIST_DATA"."PRODUCT_NAME" IS '产线名称';
COMMENT ON COLUMN "LHJX01"."T_B_COMMUNIST_DATA"."LAB_TYPE" IS '实验室类型';
COMMENT ON COLUMN "LHJX01"."T_B_COMMUNIST_DATA"."TYPE" IS '数据类型 1：共产 2 量产';

-- ----------------------------
-- Records of T_B_COMMUNIST_DATA
-- ----------------------------
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('1', '201601', '21', '冰冷', null, '1 ', '109', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('2', '201601', '22', '洗涤', null, '1 ', '230', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('3', '201601', '23', '家空', null, '1 ', '391', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('4', '201601', '24', '商空', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('5', '201601', '26', '厨电', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('6', '201601', '25', '热水器', null, '1 ', '225', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('7', '201601', '27', '其它', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('8', '201602', '21', '冰冷', null, '1 ', '112', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('9', '201602', '22', '洗涤', null, '1 ', '226', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('10', '201602', '23', '家空', null, '1 ', '382', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('11', '201602', '24', '商空', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('12', '201602', '26', '厨电', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('13', '201602', '25', '热水器', null, '1 ', '220', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('14', '201602', '27', '其它', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('15', '201603', '21', '冰冷', null, '1 ', '113', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('16', '201603', '22', '洗涤', null, '1 ', '224', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('17', '201603', '23', '家空', null, '1 ', '376', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('18', '201603', '24', '商空', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('19', '201603', '26', '厨电', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('20', '201603', '25', '热水器', null, '1 ', '216', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('21', '201603', '27', '其它', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('22', '201604', '21', '冰冷', null, '1 ', '115', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('23', '201604', '22', '洗涤', null, '1 ', '228', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('24', '201604', '23', '家空', null, '1 ', '365', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('25', '201604', '24', '商空', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('26', '201604', '26', '厨电', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('27', '201604', '25', '热水器', null, '1 ', '216', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('28', '201604', '27', '其它', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('29', '201605', '21', '冰冷', null, '1 ', '116', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('30', '201605', '22', '洗涤', null, '1 ', '231', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('31', '201605', '23', '家空', null, '1 ', '370', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('32', '201605', '24', '商空', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('33', '201605', '26', '厨电', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('34', '201605', '25', '热水器', null, '1 ', '212', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('35', '201605', '27', '其它', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('36', '201606', '21', '冰冷', null, '1 ', '120', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('37', '201606', '22', '洗涤', null, '1 ', '236', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('38', '201606', '23', '家空', null, '1 ', '378', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('39', '201606', '24', '商空', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('40', '201606', '26', '厨电', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('41', '201606', '25', '热水器', null, '1 ', '208', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('42', '201606', '27', '其它', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('43', '201607', '21', '冰冷', null, '1 ', '122', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('44', '201607', '22', '洗涤', null, '1 ', '239', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('45', '201607', '23', '家空', null, '1 ', '382', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('46', '201607', '24', '商空', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('47', '201607', '26', '厨电', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('48', '201607', '25', '热水器', null, '1 ', '206', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('49', '201607', '27', '其它', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('50', '201608', '21', '冰冷', null, '1 ', '118', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('51', '201608', '22', '洗涤', null, '1 ', '241', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('52', '201608', '23', '家空', null, '1 ', '386', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('53', '201608', '24', '商空', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('54', '201608', '26', '厨电', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('55', '201608', '25', '热水器', null, '1 ', '208', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('56', '201608', '27', '其它', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('57', '201609', '21', '冰冷', null, '1 ', '116', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('58', '201609', '22', '洗涤', null, '1 ', '238', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('59', '201609', '23', '家空', null, '1 ', '384', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('60', '201609', '24', '商空', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('61', '201609', '26', '厨电', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('62', '201609', '25', '热水器', null, '1 ', '211', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('63', '201609', '27', '其它', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('64', '201610', '21', '冰冷', null, '1 ', '114', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('65', '201610', '22', '洗涤', null, '1 ', '236', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('66', '201610', '23', '家空', null, '1 ', '386', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('67', '201610', '24', '商空', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('68', '201610', '26', '厨电', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('69', '201610', '25', '热水器', null, '1 ', '214', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('70', '201610', '27', '其它', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('71', '201611', '21', '冰冷', null, '1 ', '112', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('72', '201611', '22', '洗涤', null, '1 ', '232', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('73', '201611', '23', '家空', null, '1 ', '388', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('74', '201611', '24', '商空', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('75', '201611', '26', '厨电', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('76', '201611', '25', '热水器', null, '1 ', '218', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('77', '201611', '27', '其它', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('78', '201612', '21', '冰冷', null, '1 ', '106', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('79', '201612', '22', '洗涤', null, '1 ', '226', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('80', '201612', '23', '家空', null, '1 ', '384', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('81', '201612', '24', '商空', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('82', '201612', '26', '厨电', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('83', '201612', '25', '热水器', null, '1 ', '222', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('84', '201612', '27', '其它', null, '1 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('85', '201601', '21', '冰冷', null, '2 ', '107', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('86', '201601', '22', '洗涤', null, '2 ', '224', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('87', '201601', '23', '家空', null, '2 ', '379', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('88', '201601', '24', '商空', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('89', '201601', '26', '厨电', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('90', '201601', '25', '热水器', null, '2 ', '221', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('91', '201601', '27', '其它', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('92', '201602', '21', '冰冷', null, '2 ', '108', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('93', '201602', '22', '洗涤', null, '2 ', '223', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('94', '201602', '23', '家空', null, '2 ', '375', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('95', '201602', '24', '商空', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('96', '201602', '26', '厨电', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('97', '201602', '25', '热水器', null, '2 ', '217', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('98', '201602', '27', '其它', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('99', '201603', '21', '冰冷', null, '2 ', '109', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('100', '201603', '22', '洗涤', null, '2 ', '219', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('101', '201603', '23', '家空', null, '2 ', '369', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('102', '201603', '24', '商空', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('103', '201603', '26', '厨电', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('104', '201603', '25', '热水器', null, '2 ', '213', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('105', '201603', '27', '其它', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('106', '201604', '21', '冰冷', null, '2 ', '112', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('107', '201604', '22', '洗涤', null, '2 ', '225', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('108', '201604', '23', '家空', null, '2 ', '359', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('109', '201604', '24', '商空', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('110', '201604', '26', '厨电', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('111', '201604', '25', '热水器', null, '2 ', '212', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('112', '201604', '27', '其它', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('113', '201605', '21', '冰冷', null, '2 ', '113', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('114', '201605', '22', '洗涤', null, '2 ', '229', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('115', '201605', '23', '家空', null, '2 ', '362', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('116', '201605', '24', '商空', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('117', '201605', '26', '厨电', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('118', '201605', '25', '热水器', null, '2 ', '210', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('119', '201605', '27', '其它', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('120', '201606', '21', '冰冷', null, '2 ', '114', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('121', '201606', '22', '洗涤', null, '2 ', '232', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('122', '201606', '23', '家空', null, '2 ', '372', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('123', '201606', '24', '商空', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('124', '201606', '26', '厨电', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('125', '201606', '25', '热水器', null, '2 ', '203', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('126', '201606', '27', '其它', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('127', '201607', '21', '冰冷', null, '2 ', '118', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('128', '201607', '22', '洗涤', null, '2 ', '233', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('129', '201607', '23', '家空', null, '2 ', '367', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('130', '201607', '24', '商空', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('131', '201607', '26', '厨电', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('132', '201607', '25', '热水器', null, '2 ', '202', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('133', '201607', '27', '其它', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('134', '201608', '21', '冰冷', null, '2 ', '114', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('135', '201608', '22', '洗涤', null, '2 ', '238', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('136', '201608', '23', '家空', null, '2 ', '370', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('137', '201608', '24', '商空', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('138', '201608', '26', '厨电', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('139', '201608', '25', '热水器', null, '2 ', '204', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('140', '201608', '27', '其它', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('141', '201609', '21', '冰冷', null, '2 ', '113', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('142', '201609', '22', '洗涤', null, '2 ', '234', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('143', '201609', '23', '家空', null, '2 ', '372', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('144', '201609', '24', '商空', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('145', '201609', '26', '厨电', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('146', '201609', '25', '热水器', null, '2 ', '205', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('147', '201609', '27', '其它', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('148', '201610', '21', '冰冷', null, '2 ', '112', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('149', '201610', '22', '洗涤', null, '2 ', '233', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('150', '201610', '23', '家空', null, '2 ', '379', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('151', '201610', '24', '商空', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('152', '201610', '26', '厨电', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('153', '201610', '25', '热水器', null, '2 ', '212', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('154', '201610', '27', '其它', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('155', '201611', '21', '冰冷', null, '2 ', '111', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('156', '201611', '22', '洗涤', null, '2 ', '229', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('157', '201611', '23', '家空', null, '2 ', '380', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('158', '201611', '24', '商空', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('159', '201611', '26', '厨电', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('160', '201611', '25', '热水器', null, '2 ', '215', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('161', '201611', '27', '其它', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('162', '201612', '21', '冰冷', null, '2 ', '105', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('163', '201612', '22', '洗涤', null, '2 ', '223', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('164', '201612', '23', '家空', null, '2 ', '379', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('165', '201612', '24', '商空', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('166', '201612', '26', '厨电', null, '2 ', '0', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('167', '201612', '25', '热水器', null, '2 ', '219', '0');
INSERT INTO "LHJX01"."T_B_COMMUNIST_DATA" VALUES ('168', '201612', '27', '其它', null, '2 ', '0', '0');

-- ----------------------------
-- Indexes structure for table T_B_COMMUNIST_DATA
-- ----------------------------

-- ----------------------------
-- Checks structure for table T_B_COMMUNIST_DATA
-- ----------------------------
ALTER TABLE "LHJX01"."T_B_COMMUNIST_DATA" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table T_B_COMMUNIST_DATA
-- ----------------------------
ALTER TABLE "LHJX01"."T_B_COMMUNIST_DATA" ADD PRIMARY KEY ("ID");
