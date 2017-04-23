

package com.ulab.core;
/**
 * 
 * @time   2017年4月11日 上午11:00:01
 * @author zuoqb
 * @todo   常量
 */
public class Constants {
	//用户
	public static final String SESSION_USER="session_user";
	public static final String	ATTR_TEMPLTE_DIR					= "tpl_dir";
	public static final String	LAYOUT_TEMPLTE_DIR					= "/layout/";//layout模板路径
	public static final String	TAG_TEMPLTE_DIR					= "/tag/";//tag模板路径
	public static final int SHOW_IN_MAP=1;//显示在地图
	public static final int DEL_FALG=0;//未删除标志
	public static final String QD_LNG="120.355171";//青岛经度
	public static final String QD_LAT="36.082981";//青岛维度
	public static final boolean MONI_JOIN_TIYAN=true;//用户模拟和用户体验是否统计到一个字段上（针对专业能力报表使用）
	public static final String SIMULATION_ID="10";//用户模拟主键（字典）
	public static final String EXPERIENCE_ID="11";//用户体验主键（字典）
	public static final String SIMULATION_EXPERIENCE_JOIN_NAME="用户模拟/体验";//用户模拟 用户体验合并后名称
}
