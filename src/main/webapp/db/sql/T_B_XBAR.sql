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

Date: 2017-12-11 09:30:52
*/


-- ----------------------------
-- Table structure for T_B_XBAR
-- ----------------------------
DROP TABLE "LHJX01"."T_B_XBAR";
CREATE TABLE "LHJX01"."T_B_XBAR" (
"ID" VARCHAR2(20 BYTE) NOT NULL ,
"XH_CODE" VARCHAR2(255 BYTE) NULL ,
"XH_NAME" VARCHAR2(255 BYTE) NULL ,
"TYPE" VARCHAR2(255 BYTE) NULL ,
"NUM" VARCHAR2(255 BYTE) NULL ,
"RESULT" VARCHAR2(255 BYTE) NULL ,
"ORDER_NO" NUMBER NULL ,
"GW_CODE" VARCHAR2(255 BYTE) NULL ,
"GW_NAME" VARCHAR2(255 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE "LHJX01"."T_B_XBAR" IS 'xbar数据';
COMMENT ON COLUMN "LHJX01"."T_B_XBAR"."XH_CODE" IS '型号编码';
COMMENT ON COLUMN "LHJX01"."T_B_XBAR"."XH_NAME" IS '型号名称';
COMMENT ON COLUMN "LHJX01"."T_B_XBAR"."TYPE" IS '类型 1：样本平均值 2：样本标准差';
COMMENT ON COLUMN "LHJX01"."T_B_XBAR"."RESULT" IS '检验结果为“1”说明异常，用红色表示';
COMMENT ON COLUMN "LHJX01"."T_B_XBAR"."GW_CODE" IS '工位编码';
COMMENT ON COLUMN "LHJX01"."T_B_XBAR"."GW_NAME" IS '工位名称';

-- ----------------------------
-- Records of T_B_XBAR
-- ----------------------------
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('41', '23', '035W', '1', '4.701575611', '0', '1', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('42', '23', '035W', '1', '4.701296486', '0', '2', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('43', '23', '035W', '1', '4.744442148', '0', '3', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('44', '23', '035W', '1', '4.77662128', '0', '4', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('45', '23', '035W', '1', '4.715891334', '0', '5', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('46', '23', '035W', '1', '4.73445689', '0', '6', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('47', '23', '035W', '1', '4.70111052', '0', '7', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('48', '23', '035W', '1', '4.7261854', '0', '8', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('49', '23', '035W', '1', '4.72676069', '0', '9', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('50', '23', '035W', '1', '4.716173841', '0', '10', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('51', '23', '035W', '1', '4.710434007', '0', '11', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('52', '23', '035W', '1', '4.71661994', '0', '12', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('53', '23', '035W', '1', '4.655257038', '0', '13', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('54', '23', '035W', '1', '4.689640264', '0', '14', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('55', '23', '035W', '1', '4.707530168', '0', '15', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('56', '23', '035W', '1', '4.730336781', '0', '16', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('57', '23', '035W', '1', '4.762465356', '0', '17', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('58', '23', '035W', '1', '4.725750139', '0', '18', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('59', '23', '035W', '1', '4.685355486', '0', '19', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('60', '23', '035W', '1', '4.698534355', '0', '20', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('61', '23', '035W', '2', '0.143036833', '0', '1', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('62', '23', '035W', '2', '0.130612316', '0', '2', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('63', '23', '035W', '2', '0.129637879', '0', '3', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('64', '23', '035W', '2', '0.153697736', '0', '4', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('65', '23', '035W', '2', '0.162042418', '0', '5', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('66', '23', '035W', '2', '0.149615752', '0', '6', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('67', '23', '035W', '2', '0.148184434', '0', '7', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('68', '23', '035W', '2', '0.13754494', '0', '8', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('69', '23', '035W', '2', '0.160152742', '0', '9', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('70', '23', '035W', '2', '0.173872506', '0', '10', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('71', '23', '035W', '2', '0.141441925', '0', '11', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('72', '23', '035W', '2', '0.114309851', '0', '12', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('73', '23', '035W', '2', '0.196483442', '0', '13', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('74', '23', '035W', '2', '0.15404253', '0', '14', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('75', '23', '035W', '2', '0.167697619', '0', '15', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('76', '23', '035W', '2', '0.161053395', '0', '16', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('77', '23', '035W', '2', '0.156452311', '0', '17', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('78', '23', '035W', '2', '0.137780131', '0', '18', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('79', '23', '035W', '2', '0.137584862', '0', '19', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('80', '23', '035W', '2', '0.130368507', '0', '20', '25', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('81', '23', '035W', '1', '1.548410812', '0', '1', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('82', '23', '035W', '1', '1.500376261', '0', '2', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('83', '23', '035W', '1', '1.46526924', '0', '3', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('84', '23', '035W', '1', '1.554832986', '0', '4', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('85', '23', '035W', '1', '1.619629805', '0', '5', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('86', '23', '035W', '1', '1.482724997', '0', '6', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('87', '23', '035W', '1', '1.526903377', '0', '7', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('88', '23', '035W', '1', '1.564636131', '0', '8', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('89', '23', '035W', '1', '1.5264939', '0', '9', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('90', '23', '035W', '1', '1.576349671', '0', '10', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('91', '23', '035W', '1', '1.55722255', '0', '11', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('92', '23', '035W', '1', '1.519720552', '0', '12', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('93', '23', '035W', '1', '1.603749881', '0', '13', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('94', '23', '035W', '1', '1.575975596', '0', '14', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('95', '23', '035W', '1', '1.560646064', '0', '15', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('96', '23', '035W', '1', '1.622162318', '0', '16', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('97', '23', '035W', '1', '1.502152944', '0', '17', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('98', '23', '035W', '1', '1.630318829', '0', '18', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('99', '23', '035W', '1', '1.502055548', '0', '19', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('100', '23', '035W', '1', '1.456052907', '0', '20', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('101', '23', '035W', '2', '0.254974352', '0', '1', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('102', '23', '035W', '2', '0.269678167', '0', '2', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('103', '23', '035W', '2', '0.23683348', '0', '3', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('104', '23', '035W', '2', '0.292945239', '0', '4', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('105', '23', '035W', '2', '0.24088701', '0', '5', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('106', '23', '035W', '2', '0.215043988', '0', '6', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('107', '23', '035W', '2', '0.207721022', '0', '7', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('108', '23', '035W', '2', '0.222860871', '0', '8', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('109', '23', '035W', '2', '0.258613385', '0', '9', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('110', '23', '035W', '2', '0.313471792', '0', '10', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('111', '23', '035W', '2', '0.19966663', '0', '11', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('112', '23', '035W', '2', '0.272456412', '0', '12', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('113', '23', '035W', '2', '0.284520363', '0', '13', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('114', '23', '035W', '2', '0.249863472', '0', '14', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('115', '23', '035W', '2', '0.267288443', '0', '15', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('116', '23', '035W', '2', '0.290970145', '0', '16', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('117', '23', '035W', '2', '0.221779015', '0', '17', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('118', '23', '035W', '2', '0.243253154', '0', '18', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('119', '23', '035W', '2', '0.235863452', '0', '19', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('120', '23', '035W', '2', '0.26275637', '0', '20', '26', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('121', '23', '035W', '1', '3.142733333', '0', '1', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('122', '23', '035W', '1', '3.1548', '0', '2', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('123', '23', '035W', '1', '3.086166667', '0', '3', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('124', '23', '035W', '1', '3.111433333', '0', '4', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('125', '23', '035W', '1', '3.116366667', '0', '5', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('126', '23', '035W', '1', '3.127933333', '0', '6', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('127', '23', '035W', '1', '3.121533333', '0', '7', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('128', '23', '035W', '1', '3.138266667', '0', '8', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('129', '23', '035W', '1', '3.155', '0', '9', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('130', '23', '035W', '1', '3.0867', '0', '10', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('131', '23', '035W', '1', '3.154033333', '0', '11', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('132', '23', '035W', '1', '3.166733333', '0', '12', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('133', '23', '035W', '1', '3.1565', '0', '13', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('134', '23', '035W', '1', '3.106566667', '0', '14', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('135', '23', '035W', '1', '3.1152', '0', '15', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('136', '23', '035W', '1', '3.1308', '0', '16', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('137', '23', '035W', '1', '3.119533333', '0', '17', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('138', '23', '035W', '1', '3.159166667', '0', '18', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('139', '23', '035W', '1', '3.101233333', '0', '19', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('140', '23', '035W', '1', '3.1583', '0', '20', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('141', '23', '035W', '2', '0.162673554', '0', '1', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('142', '23', '035W', '2', '0.160189198', '0', '2', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('143', '23', '035W', '2', '0.126677457', '0', '3', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('144', '23', '035W', '2', '0.137763086', '0', '4', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('145', '23', '035W', '2', '0.148042977', '0', '5', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('146', '23', '035W', '2', '0.107493042', '0', '6', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('147', '23', '035W', '2', '0.158521778', '0', '7', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('148', '23', '035W', '2', '0.136053218', '0', '8', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('149', '23', '035W', '2', '0.143657879', '0', '9', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('150', '23', '035W', '2', '0.163086639', '0', '10', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('151', '23', '035W', '2', '0.120043809', '0', '11', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('152', '23', '035W', '2', '0.139205116', '0', '12', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('153', '23', '035W', '2', '0.135750214', '0', '13', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('154', '23', '035W', '2', '0.147326446', '0', '14', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('155', '23', '035W', '2', '0.160645766', '0', '15', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('156', '23', '035W', '2', '0.142674068', '0', '16', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('157', '23', '035W', '2', '0.143804401', '0', '17', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('158', '23', '035W', '2', '0.144796429', '0', '18', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('159', '23', '035W', '2', '0.125938322', '0', '19', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('160', '23', '035W', '2', '0.128875548', '0', '20', '27', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('161', '23', '035W', '1', '2.368680178', '0', '1', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('162', '23', '035W', '1', '2.312608304', '0', '2', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('163', '23', '035W', '1', '2.325922528', '0', '3', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('164', '23', '035W', '1', '2.262251415', '0', '4', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('165', '23', '035W', '1', '2.28947236', '0', '5', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('166', '23', '035W', '1', '2.269675361', '0', '6', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('167', '23', '035W', '1', '2.267889327', '0', '7', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('168', '23', '035W', '1', '2.339260178', '0', '8', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('169', '23', '035W', '1', '2.286743423', '0', '9', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('170', '23', '035W', '1', '2.230192368', '0', '10', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('171', '23', '035W', '1', '2.264850215', '0', '11', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('172', '23', '035W', '1', '2.299173909', '0', '12', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('173', '23', '035W', '1', '2.350454283', '0', '13', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('174', '23', '035W', '1', '2.30158256', '0', '14', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('175', '23', '035W', '1', '2.398106669', '0', '15', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('176', '23', '035W', '1', '2.289836335', '0', '16', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('177', '23', '035W', '1', '2.362038877', '0', '17', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('178', '23', '035W', '1', '2.254816529', '0', '18', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('179', '23', '035W', '1', '2.332348048', '0', '19', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('267', '24', '035X', '2', '0.134972896', '0', '7', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('268', '24', '035X', '2', '0.152925514', '0', '8', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('269', '24', '035X', '2', '0.150739351', '0', '9', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('270', '24', '035X', '2', '0.189877345', '0', '10', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('271', '24', '035X', '2', '0.169588285', '0', '11', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('272', '24', '035X', '2', '0.161833943', '0', '12', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('273', '24', '035X', '2', '0.144118224', '0', '13', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('274', '24', '035X', '2', '0.146038365', '0', '14', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('275', '24', '035X', '2', '0.151596523', '0', '15', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('276', '24', '035X', '2', '0.165093041', '0', '16', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('277', '24', '035X', '2', '0.150224706', '0', '17', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('278', '24', '035X', '2', '0.170602249', '0', '18', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('279', '24', '035X', '2', '0.182589519', '0', '19', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('280', '24', '035X', '2', '0.176456069', '0', '20', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('281', '24', '035X', '1', '1.491702695', '0', '1', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('282', '24', '035X', '1', '1.556981746', '0', '2', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('283', '24', '035X', '1', '1.529284368', '0', '3', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('284', '24', '035X', '1', '1.516639312', '0', '4', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('285', '24', '035X', '1', '1.534244101', '0', '5', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('286', '24', '035X', '1', '1.458905776', '0', '6', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('287', '24', '035X', '1', '1.558205144', '0', '7', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('288', '24', '035X', '1', '1.523194455', '0', '8', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('289', '24', '035X', '1', '1.514054912', '0', '9', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('290', '24', '035X', '1', '1.561737876', '0', '10', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('291', '24', '035X', '1', '1.530962086', '0', '11', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('292', '24', '035X', '1', '1.495169134', '0', '12', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('293', '24', '035X', '1', '1.542404696', '0', '13', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('294', '24', '035X', '1', '1.606157199', '0', '14', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('295', '24', '035X', '1', '1.509897241', '0', '15', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('296', '24', '035X', '1', '1.53707192', '0', '16', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('297', '24', '035X', '1', '1.42390844', '0', '17', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('298', '24', '035X', '1', '1.58454127', '0', '18', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('299', '24', '035X', '1', '1.42492522', '0', '19', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('300', '24', '035X', '1', '1.569550145', '0', '20', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('301', '24', '035X', '2', '0.268865321', '0', '1', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('302', '24', '035X', '2', '0.27234611', '0', '2', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('303', '24', '035X', '2', '0.212046284', '0', '3', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('304', '24', '035X', '2', '0.208457364', '0', '4', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('305', '24', '035X', '2', '0.229430796', '0', '5', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('306', '24', '035X', '2', '0.243083347', '0', '6', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('307', '24', '035X', '2', '0.180598562', '0', '7', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('308', '24', '035X', '2', '0.219509343', '0', '8', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('309', '24', '035X', '2', '0.311350195', '0', '9', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('310', '24', '035X', '2', '0.213488423', '0', '10', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('311', '24', '035X', '2', '0.286284589', '0', '11', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('312', '24', '035X', '2', '0.238831242', '0', '12', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('313', '24', '035X', '2', '0.254827412', '0', '13', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('314', '24', '035X', '2', '0.21621438', '0', '14', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('315', '24', '035X', '2', '0.251916924', '0', '15', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('316', '24', '035X', '2', '0.275118377', '0', '16', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('317', '24', '035X', '2', '0.205482737', '0', '17', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('318', '24', '035X', '2', '0.272164445', '0', '18', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('319', '24', '035X', '2', '0.24868798', '0', '19', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('320', '24', '035X', '2', '0.238344226', '0', '20', '31', 'LED检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('321', '24', '035X', '1', '2.966886574', '0', '1', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('322', '24', '035X', '1', '2.995453099', '0', '2', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('323', '24', '035X', '1', '2.993678498', '0', '3', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('324', '24', '035X', '1', '3.004050283', '0', '4', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('325', '24', '035X', '1', '3.055128052', '0', '5', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('326', '24', '035X', '1', '3.007405694', '0', '6', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('327', '24', '035X', '1', '3.006770726', '0', '7', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('328', '24', '035X', '1', '2.98642404', '0', '8', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('329', '24', '035X', '1', '3.014851129', '0', '9', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('330', '24', '035X', '1', '2.96423205', '0', '10', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('331', '24', '035X', '1', '3.048028647', '0', '11', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('332', '24', '035X', '1', '2.997517167', '0', '12', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('333', '24', '035X', '1', '3.03935984', '0', '13', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('334', '24', '035X', '1', '3.057533718', '0', '14', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('335', '24', '035X', '1', '3.028215319', '0', '15', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('336', '24', '035X', '1', '3.004273624', '0', '16', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('337', '24', '035X', '1', '2.990889691', '0', '17', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('338', '24', '035X', '1', '3.063427003', '0', '18', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('339', '24', '035X', '1', '3.008077273', '0', '19', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('340', '24', '035X', '1', '2.981352635', '0', '20', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('341', '24', '035X', '2', '0.225955817', '0', '1', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('342', '24', '035X', '2', '0.190900596', '0', '2', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('343', '24', '035X', '2', '0.20429714', '0', '3', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('344', '24', '035X', '2', '0.187666457', '0', '4', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('345', '24', '035X', '2', '0.199481685', '0', '5', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('346', '24', '035X', '2', '0.215647471', '0', '6', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('347', '24', '035X', '2', '0.204808366', '0', '7', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('348', '24', '035X', '2', '0.202254688', '0', '8', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('349', '24', '035X', '2', '0.172014243', '0', '9', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('350', '24', '035X', '2', '0.19948724', '0', '10', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('351', '24', '035X', '2', '0.248298325', '0', '11', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('352', '24', '035X', '2', '0.192856545', '0', '12', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('353', '24', '035X', '2', '0.225156171', '0', '13', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('354', '24', '035X', '2', '0.181765813', '0', '14', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('355', '24', '035X', '2', '0.229138625', '0', '15', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('356', '24', '035X', '2', '0.200646089', '0', '16', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('357', '24', '035X', '2', '0.184508166', '0', '17', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('358', '24', '035X', '2', '0.146437103', '0', '18', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('359', '24', '035X', '2', '0.228830109', '0', '19', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('360', '24', '035X', '2', '0.231463553', '0', '20', '32', '过零信号检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('361', '24', '035X', '1', '2.521199845', '0', '1', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('362', '24', '035X', '1', '2.481037095', '0', '2', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('363', '24', '035X', '1', '2.397581289', '0', '3', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('364', '24', '035X', '1', '2.539177279', '0', '4', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('365', '24', '035X', '1', '2.468954739', '0', '5', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('366', '24', '035X', '1', '2.538462289', '0', '6', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('367', '24', '035X', '1', '2.528492744', '0', '7', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('368', '24', '035X', '1', '2.505557475', '0', '8', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('369', '24', '035X', '1', '2.57514432', '0', '9', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('370', '24', '035X', '1', '2.52231246', '0', '10', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('371', '24', '035X', '1', '2.493151165', '0', '11', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('372', '24', '035X', '1', '2.439952912', '0', '12', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('373', '24', '035X', '1', '2.531235084', '0', '13', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('374', '24', '035X', '1', '2.487349095', '0', '14', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('375', '24', '035X', '1', '2.518905145', '0', '15', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('376', '24', '035X', '1', '2.629766967', '0', '16', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('377', '24', '035X', '1', '2.463673286', '0', '17', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('378', '24', '035X', '1', '2.504036473', '0', '18', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('379', '24', '035X', '1', '2.521563501', '0', '19', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('380', '24', '035X', '1', '2.444345896', '0', '20', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('381', '24', '035X', '2', '0.257949331', '0', '1', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('382', '24', '035X', '2', '0.257584156', '0', '2', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('383', '24', '035X', '2', '0.279750025', '0', '3', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('384', '24', '035X', '2', '0.265856572', '0', '4', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('385', '24', '035X', '2', '0.255002397', '0', '5', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('386', '24', '035X', '2', '0.278175006', '0', '6', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('387', '24', '035X', '2', '0.26075391', '0', '7', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('388', '24', '035X', '2', '0.267553322', '0', '8', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('389', '24', '035X', '2', '0.231015517', '0', '9', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('390', '24', '035X', '2', '0.206746651', '0', '10', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('391', '24', '035X', '2', '0.261000471', '0', '11', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('392', '24', '035X', '2', '0.285901572', '0', '12', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('393', '24', '035X', '2', '0.255479841', '0', '13', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('394', '24', '035X', '2', '0.282979489', '0', '14', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('395', '24', '035X', '2', '0.265235204', '0', '15', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('396', '24', '035X', '2', '0.258747294', '0', '16', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('397', '24', '035X', '2', '0.261393777', '0', '17', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('398', '24', '035X', '2', '0.186129725', '0', '18', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('399', '24', '035X', '2', '0.231344144', '0', '19', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('400', '24', '035X', '2', '0.171375437', '0', '20', '33', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('401', '24', '035X', '1', '0.01022325', '0', '1', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('402', '24', '035X', '1', '0.009988061', '0', '2', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('403', '24', '035X', '1', '0.010287983', '0', '3', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('404', '24', '035X', '1', '0.010138966', '0', '4', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('405', '24', '035X', '1', '0.009370141', '0', '5', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('406', '24', '035X', '1', '0.009848331', '0', '6', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('407', '24', '035X', '1', '0.009511444', '0', '7', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('408', '24', '035X', '1', '0.009889164', '0', '8', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('409', '24', '035X', '1', '0.010177531', '0', '9', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('410', '24', '035X', '1', '0.00988468', '0', '10', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('411', '24', '035X', '1', '0.010209177', '0', '11', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('412', '24', '035X', '1', '0.010230121', '0', '12', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('413', '24', '035X', '1', '0.009852743', '0', '13', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('414', '24', '035X', '1', '0.010374171', '0', '14', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('415', '24', '035X', '1', '0.010469695', '0', '15', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('416', '24', '035X', '1', '0.009906647', '0', '16', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('417', '24', '035X', '1', '0.009394422', '0', '17', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('418', '24', '035X', '1', '0.010543981', '0', '18', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('419', '24', '035X', '1', '0.009927676', '0', '19', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('420', '24', '035X', '1', '0.010324567', '0', '20', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('421', '24', '035X', '2', '0.002091839', '0', '1', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('422', '24', '035X', '2', '0.001981196', '0', '2', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('423', '24', '035X', '2', '0.001395471', '0', '3', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('424', '24', '035X', '2', '0.001370786', '0', '4', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('425', '24', '035X', '2', '0.001801523', '0', '5', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('426', '24', '035X', '2', '0.001474578', '0', '6', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('427', '24', '035X', '2', '0.001471569', '0', '7', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('428', '24', '035X', '2', '0.001991443', '0', '8', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('429', '24', '035X', '2', '0.001348981', '0', '9', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('430', '24', '035X', '2', '0.001857832', '0', '10', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('431', '24', '035X', '2', '0.001288632', '0', '11', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('432', '24', '035X', '2', '0.001333315', '0', '12', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('433', '24', '035X', '2', '0.001248572', '0', '13', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('434', '24', '035X', '2', '0.001460417', '0', '14', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('435', '24', '035X', '2', '0.001755233', '0', '15', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('436', '24', '035X', '2', '0.001523397', '0', '16', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('437', '24', '035X', '2', '0.001845999', '0', '17', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('438', '24', '035X', '2', '0.001274817', '0', '18', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('439', '24', '035X', '2', '0.001812759', '0', '19', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('440', '24', '035X', '2', '0.001737628', '0', '20', '34', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('441', '21', 'WY-SA', '1', '103.1272325', '0', '1', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('442', '21', 'WY-SA', '1', '103.1139692', '0', '2', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('443', '21', 'WY-SA', '1', '102.9076675', '0', '3', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('444', '21', 'WY-SA', '1', '103.304169', '0', '4', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('445', '21', 'WY-SA', '1', '102.8947527', '0', '5', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('446', '21', 'WY-SA', '1', '103.432969', '0', '6', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('447', '21', 'WY-SA', '1', '103.1100226', '0', '7', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('448', '21', 'WY-SA', '1', '103.7412963', '0', '8', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('449', '21', 'WY-SA', '1', '103.1954713', '0', '9', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('450', '21', 'WY-SA', '1', '103.1573336', '0', '10', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('451', '21', 'WY-SA', '1', '103.0281405', '0', '11', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('452', '21', 'WY-SA', '1', '103.4055074', '0', '12', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('453', '21', 'WY-SA', '1', '102.855713', '0', '13', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('454', '21', 'WY-SA', '1', '103.6468393', '0', '14', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('455', '21', 'WY-SA', '1', '103.3830756', '0', '15', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('456', '21', 'WY-SA', '1', '103.4525884', '0', '16', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('457', '21', 'WY-SA', '1', '103.3372266', '0', '17', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('458', '21', 'WY-SA', '1', '103.1848131', '0', '18', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('459', '21', 'WY-SA', '1', '102.9488295', '0', '19', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('460', '21', 'WY-SA', '1', '103.4012834', '0', '20', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('461', '21', 'WY-SA', '2', '1.333243899', '0', '1', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('462', '21', 'WY-SA', '2', '1.367163651', '0', '2', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('463', '21', 'WY-SA', '2', '1.315303924', '0', '3', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('464', '21', 'WY-SA', '2', '1.340236258', '0', '4', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('465', '21', 'WY-SA', '2', '1.47934982', '0', '5', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('466', '21', 'WY-SA', '2', '1.694413366', '0', '6', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('467', '21', 'WY-SA', '2', '1.49185717', '0', '7', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('468', '21', 'WY-SA', '2', '1.946040317', '0', '8', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('469', '21', 'WY-SA', '2', '1.323536183', '0', '9', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('470', '21', 'WY-SA', '2', '1.682917075', '0', '10', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('471', '21', 'WY-SA', '2', '1.383178831', '0', '11', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('472', '21', 'WY-SA', '2', '1.210261074', '0', '12', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('473', '21', 'WY-SA', '2', '1.503420574', '0', '13', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('474', '21', 'WY-SA', '2', '1.530766082', '0', '14', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('475', '21', 'WY-SA', '2', '1.173624748', '0', '15', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('476', '21', 'WY-SA', '2', '1.335164916', '0', '16', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('477', '21', 'WY-SA', '2', '1.407903266', '0', '17', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('478', '21', 'WY-SA', '2', '1.441140283', '0', '18', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('479', '21', 'WY-SA', '2', '1.324803758', '0', '19', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('480', '21', 'WY-SA', '2', '1.39962569', '0', '20', '36', '断开温度检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('483', '22', '200E', '1', '9.119744762', '0', '3', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('484', '22', '200E', '1', '9.133660259', '0', '4', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('485', '22', '200E', '1', '9.117989858', '0', '5', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('486', '22', '200E', '1', '9.079342043', '0', '6', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('487', '22', '200E', '1', '9.10041166', '0', '7', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('488', '22', '200E', '1', '9.089707488', '0', '8', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('489', '22', '200E', '1', '9.098923736', '0', '9', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('490', '22', '200E', '1', '9.076108494', '0', '10', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('491', '22', '200E', '1', '9.117003803', '0', '11', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('492', '22', '200E', '1', '9.139100881', '0', '12', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('493', '22', '200E', '1', '9.104191321', '0', '13', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('494', '22', '200E', '1', '9.112090682', '0', '14', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('495', '22', '200E', '1', '9.130289324', '0', '15', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('496', '22', '200E', '1', '9.113926132', '0', '16', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('497', '22', '200E', '1', '9.095866261', '0', '17', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('498', '22', '200E', '1', '9.097233759', '0', '18', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('499', '22', '200E', '1', '9.102329055', '0', '19', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('500', '22', '200E', '1', '9.071967039', '0', '20', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('501', '22', '200E', '2', '0.099888158', '0', '1', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('502', '22', '200E', '2', '0.102206203', '0', '2', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('503', '22', '200E', '2', '0.090151611', '0', '3', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('504', '22', '200E', '2', '0.073440604', '0', '4', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('505', '22', '200E', '2', '0.108002162', '0', '5', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('506', '22', '200E', '2', '0.115020048', '0', '6', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('507', '22', '200E', '2', '0.101983876', '0', '7', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('508', '22', '200E', '2', '0.076781037', '0', '8', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('509', '22', '200E', '2', '0.09162795', '0', '9', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('510', '22', '200E', '2', '0.113822411', '0', '10', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('511', '22', '200E', '2', '0.073671037', '0', '11', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('512', '22', '200E', '2', '0.106551993', '0', '12', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('513', '22', '200E', '2', '0.102361726', '0', '13', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('481', '22', '200E', '1', '9.096166586', '0', '1', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('482', '22', '200E', '1', '9.130578923', '0', '2', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('514', '22', '200E', '2', '0.109944842', '0', '14', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('515', '22', '200E', '2', '0.083457537', '0', '15', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('516', '22', '200E', '2', '0.101404683', '0', '16', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('517', '22', '200E', '2', '0.116496156', '0', '17', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('518', '22', '200E', '2', '0.105802585', '0', '18', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('519', '22', '200E', '2', '0.124579273', '0', '19', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('520', '22', '200E', '2', '0.105872098', '0', '20', '37', '绕组值检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('1', '18', 'WY77H-C1', '1', '74.145', '0', '1', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('2', '18', 'WY77H-C1', '1', '74.1824', '0', '2', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('3', '18', 'WY77H-C1', '1', '74.014', '0', '3', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('4', '18', 'WY77H-C1', '1', '74.189', '0', '4', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('5', '18', 'WY77H-C1', '1', '74.098', '0', '5', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('6', '18', 'WY77H-C1', '1', '74.0019', '0', '6', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('7', '18', 'WY77H-C1', '1', '74.077', '0', '7', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('8', '18', 'WY77H-C1', '1', '74.093', '0', '8', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('9', '18', 'WY77H-C1', '1', '74.022', '0', '9', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('10', '18', 'WY77H-C1', '1', '74.062', '0', '10', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('11', '18', 'WY77H-C1', '1', '74.098', '0', '11', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('12', '18', 'WY77H-C1', '1', '74.15', '0', '12', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('13', '18', 'WY77H-C1', '1', '74.164', '0', '13', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('14', '18', 'WY77H-C1', '1', '74.149', '0', '14', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('15', '18', 'WY77H-C1', '1', '73.873', '0', '15', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('16', '18', 'WY77H-C1', '1', '74.003', '0', '16', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('17', '18', 'WY77H-C1', '1', '74.021', '0', '17', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('18', '18', 'WY77H-C1', '1', '74.104', '0', '18', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('19', '18', 'WY77H-C1', '1', '73.976', '0', '19', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('20', '18', 'WY77H-C1', '1', '74.006', '0', '20', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('21', '18', 'WY77H-C1', '2', '0.69024589382', '0', '1', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('22', '18', 'WY77H-C1', '2', '0.6322656391', '0', '2', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('23', '18', 'WY77H-C1', '2', '0.64557859101', '0', '3', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('24', '18', 'WY77H-C1', '2', '0.72095545471', '0', '4', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('25', '18', 'WY77H-C1', '2', '0.62224422761', '0', '5', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('26', '18', 'WY77H-C1', '2', '0.57454188162', '0', '6', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('27', '18', 'WY77H-C1', '2', '0.72819896826', '0', '7', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('28', '18', 'WY77H-C1', '2', '0.65539794373', '0', '8', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('29', '18', 'WY77H-C1', '2', '0.59162163721', '0', '9', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('30', '18', 'WY77H-C1', '2', '0.74586807943', '0', '10', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('31', '18', 'WY77H-C1', '2', '0.66469101199', '0', '11', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('32', '18', 'WY77H-C1', '2', '0.66355333644', '0', '12', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('33', '18', 'WY77H-C1', '2', '0.68320693583', '0', '13', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('34', '18', 'WY77H-C1', '2', '0.63045518268', '0', '14', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('35', '18', 'WY77H-C1', '2', '0.7115007613', '0', '15', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('36', '18', 'WY77H-C1', '2', '0.63704732393', '0', '16', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('37', '18', 'WY77H-C1', '2', '0.66306527247', '0', '17', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('38', '18', 'WY77H-C1', '2', '0.65472949152', '0', '18', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('39', '18', 'WY77H-C1', '2', '0.75693694279', '0', '19', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('40', '18', 'WY77H-C1', '2', '0.78583674764', '0', '20', '35', '断开温度检测
');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('180', '23', '035W', '1', '2.380631347', '0', '20', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('181', '23', '035W', '2', '0.285117585', '0', '1', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('182', '23', '035W', '2', '0.295288303', '0', '2', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('183', '23', '035W', '2', '0.350479776', '0', '3', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('184', '23', '035W', '2', '0.280633567', '0', '4', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('185', '23', '035W', '2', '0.230364358', '0', '5', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('186', '23', '035W', '2', '0.302497143', '0', '6', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('187', '23', '035W', '2', '0.28788175', '0', '7', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('188', '23', '035W', '2', '0.272219538', '0', '8', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('189', '23', '035W', '2', '0.350346146', '0', '9', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('190', '23', '035W', '2', '0.303008239', '0', '10', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('191', '23', '035W', '2', '0.332287124', '0', '11', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('192', '23', '035W', '2', '0.344736908', '0', '12', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('193', '23', '035W', '2', '0.257992258', '0', '13', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('194', '23', '035W', '2', '0.361778873', '0', '14', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('195', '23', '035W', '2', '0.322575983', '0', '15', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('196', '23', '035W', '2', '0.291348834', '0', '16', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('197', '23', '035W', '2', '0.277471358', '0', '17', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('198', '23', '035W', '2', '0.298015451', '0', '18', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('199', '23', '035W', '2', '0.355629446', '0', '19', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('200', '23', '035W', '2', '0.296583762', '0', '20', '28', '蜂鸣器检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('201', '23', '035W', '1', '0.011040517', '0', '1', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('202', '23', '035W', '1', '0.011633263', '0', '2', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('203', '23', '035W', '1', '0.011071774', '0', '3', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('204', '23', '035W', '1', '0.011151241', '0', '4', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('205', '23', '035W', '1', '0.010706943', '0', '5', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('206', '23', '035W', '1', '0.01147889', '0', '6', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('207', '23', '035W', '1', '0.011271552', '0', '7', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('208', '23', '035W', '1', '0.011168213', '0', '8', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('209', '23', '035W', '1', '0.011093912', '0', '9', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('210', '23', '035W', '1', '0.011264841', '0', '10', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('211', '23', '035W', '1', '0.011086512', '0', '11', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('212', '23', '035W', '1', '0.011012987', '0', '12', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('213', '23', '035W', '1', '0.010369932', '0', '13', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('214', '23', '035W', '1', '0.011217061', '0', '14', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('215', '23', '035W', '1', '0.010681006', '0', '15', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('216', '23', '035W', '1', '0.011099994', '0', '16', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('217', '23', '035W', '1', '0.010306432', '0', '17', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('218', '23', '035W', '1', '0.011002851', '0', '18', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('219', '23', '035W', '1', '0.010954806', '0', '19', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('220', '23', '035W', '1', '0.011454469', '0', '20', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('221', '23', '035W', '2', '0.001211325', '0', '1', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('222', '23', '035W', '2', '0.001657383', '0', '2', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('223', '23', '035W', '2', '0.001661784', '0', '3', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('224', '23', '035W', '2', '0.001135786', '0', '4', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('225', '23', '035W', '2', '0.00186592', '0', '5', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('226', '23', '035W', '2', '0.00171469', '0', '6', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('227', '23', '035W', '2', '0.001642826', '0', '7', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('228', '23', '035W', '2', '0.00175616', '0', '8', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('229', '23', '035W', '2', '0.001313013', '0', '9', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('230', '23', '035W', '2', '0.001521448', '0', '10', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('231', '23', '035W', '2', '0.001134525', '0', '11', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('232', '23', '035W', '2', '0.001733561', '0', '12', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('233', '23', '035W', '2', '0.001774001', '0', '13', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('234', '23', '035W', '2', '0.001485553', '0', '14', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('235', '23', '035W', '2', '0.001938531', '0', '15', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('236', '23', '035W', '2', '0.001480642', '0', '16', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('237', '23', '035W', '2', '0.001836485', '0', '17', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('238', '23', '035W', '2', '0.001558988', '0', '18', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('239', '23', '035W', '2', '0.00121956', '0', '19', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('240', '23', '035W', '2', '0.001603954', '0', '20', '29', '待机电流检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('241', '24', '035X', '1', '4.85020126', '0', '1', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('242', '24', '035X', '1', '4.855178918', '0', '2', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('243', '24', '035X', '1', '4.88728402', '0', '3', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('244', '24', '035X', '1', '4.85954493', '0', '4', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('245', '24', '035X', '1', '4.863958185', '0', '5', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('246', '24', '035X', '1', '4.838751405', '0', '6', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('247', '24', '035X', '1', '4.848197448', '0', '7', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('248', '24', '035X', '1', '4.831816704', '0', '8', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('249', '24', '035X', '1', '4.859481519', '0', '9', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('250', '24', '035X', '1', '4.870882962', '0', '10', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('251', '24', '035X', '1', '4.862841872', '0', '11', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('252', '24', '035X', '1', '4.859217468', '0', '12', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('253', '24', '035X', '1', '4.853477174', '0', '13', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('254', '24', '035X', '1', '4.896495126', '0', '14', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('255', '24', '035X', '1', '4.800897602', '0', '15', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('256', '24', '035X', '1', '4.866576465', '0', '16', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('257', '24', '035X', '1', '4.870936069', '0', '17', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('258', '24', '035X', '1', '4.908187635', '0', '18', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('259', '24', '035X', '1', '4.853776927', '0', '19', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('260', '24', '035X', '1', '4.81042702', '0', '20', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('261', '24', '035X', '2', '0.16779415', '0', '1', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('262', '24', '035X', '2', '0.204977', '0', '2', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('263', '24', '035X', '2', '0.103605292', '0', '3', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('264', '24', '035X', '2', '0.17053668', '0', '4', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('265', '24', '035X', '2', '0.178325257', '0', '5', '30', '门锁检测');
INSERT INTO "LHJX01"."T_B_XBAR" VALUES ('266', '24', '035X', '2', '0.144107218', '0', '6', '30', '门锁检测');

-- ----------------------------
-- Indexes structure for table T_B_XBAR
-- ----------------------------

-- ----------------------------
-- Checks structure for table T_B_XBAR
-- ----------------------------
ALTER TABLE "LHJX01"."T_B_XBAR" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table T_B_XBAR
-- ----------------------------
ALTER TABLE "LHJX01"."T_B_XBAR" ADD PRIMARY KEY ("ID");
