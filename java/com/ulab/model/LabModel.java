package com.ulab.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.core.BaseController;
import com.ulab.core.Constants;

@TableBind(tableName = "t_b_lab_info", pkName = "id")
public class LabModel extends Model<LabModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final LabModel dao = new LabModel();

	/**
	 * 
	 * @time 2017年4月21日 下午2:38:19
	 * @author zuoqb
	 * @todo 查询展示在世界地图上的实验室-Map3D数据 数据是一级 二级 实验室等级 0：四大类 1：一级子类 2：二级子类
	 * @param @return
	 * @return_type List<Record>
	 */
	public List<Record> labShowWorldMap() {
		String pSql = "select id,d.short_name as shortname,'" + Constants.QD_LNG
				+ "' as lng,'" + Constants.QD_LAT
				+ "' as lat,a.num as value from t_b_dictionary d  ";
		pSql += " left join (select lab_type_code,count(1) as num from(select  lab_type_code,code from t_b_lab_info where del_flag="+Constants.DEL_FALG+" and show_in_map="+Constants.SHOW_IN_MAP+" and link_status=1 group by lab_type_code,code) group by lab_type_code ";
		pSql += " )a on d.id=a.lab_type_code where d.type='lab_type' and d.del_flag="+Constants.DEL_FALG+" order by d.order_no ";
		List<Record> parentList = Db.find(pSql);
		for (Record r : parentList) {
			String sql = "select p.short_name as pshortname,'"
					+ Constants.QD_LNG
					+ "' as plng,'"
					+ Constants.QD_LAT
					+ "' as plat,c.name as shortname,c.lng,c.lat,0 as value from t_b_lab_info c";
			sql += "  left join t_b_dictionary p on p.id=c.lab_type_code ";
			sql += "   where c.del_flag="+Constants.DEL_FALG+" and c.show_in_map="+Constants.SHOW_IN_MAP+" and c.lab_type_code='"
					+ r.get("id") + "'";
			List<Record> cList = Db.find(sql);
			r.set("cList", cList);
		}
		return parentList;
	}

	/**
	 * 
	 * @time 2017年4月14日 下午3:14:37
	 * @author zuoqb
	 * @todo 查询实验室总数
	 */
	public Integer findAllCount() {
		Record data = Db
				.findFirst("SELECT nvl(count(1),0) as count from(select  1 from t_b_lab_info lab where  lab.del_flag="
						+ Constants.DEL_FALG + " GROUP BY code ) ");
		return Integer.valueOf(data.getBigDecimal("count").toString());
	}

	/**
	 * 
	 * @time 2017年4月14日 下午3:14:37
	 * @author zuoqb
	 * @todo 查询实验室所分布大洲数量及国家数量
	 */
	public Record labSpread() {
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from  ");
		sb.append("  (select nvl(count(distinct lab.location),0)   as areanum from t_b_lab_info lab where lab.del_flag="
				+ Constants.DEL_FALG  + " )a ");
		sb.append("  left join  ");
		sb.append("  (select nvl(count(distinct  lab.country),0) as countrynum from t_b_lab_info lab where lab.del_flag="
				+ Constants.DEL_FALG  + ")t ");
		sb.append("  on 1=1 ");
		sb.append("  left join  ");
		sb.append("  (select nvl(count(distinct  lab.belong_gl_code),0) as belongnum from t_b_lab_info lab where lab.del_flag="
				+ Constants.DEL_FALG  + " and lab.lab_type_code=4)t2 ");
		sb.append("  on 1=1 ");
		return Db.findFirst(sb.toString());
	}

	/**
	 * 
	 * @time 2017年4月14日 下午8:06:44
	 * @author zuoqb
	 * @todo 按照某维度统计数量
	 * @param @param filed:统计维度
	 * @param @return
	 * @return_type List<Record>
	 */
	public List<Record> labStatisByField(String sqlWhere, String field,String sort) {
		if("properties_code".equals(field)){
			System.out.println(1);
		}
		if(StringUtils.isBlank(sort)){
			sort=" desc ";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(" select d.short_name as name, nvl(count(1),0) as count,d.id from   ");
		sb.append("  (select distinct t.code as labcode,t."+field+",t.del_flag  from t_b_lab_info t ");
		sb.append(" where t.del_flag= " + Constants.DEL_FALG +" and t." + field+ " is not null " + sqlWhere);
		sb.append(" ) lab left join t_b_dictionary d on lab."+ field + "=d.id ");
		sb.append(" group by d.name,d.short_name,d .order_no,d.id order by d.order_no  "+sort);
		List<Record> list=Db.find(sb.toString());
		/*if(Constants.MONI_JOIN_TIYAN&&"properties_code".equals(field)){
			//如果统计实验室性质，需要对用户体验与用户模拟做合并处理
			for(Record obj:list){
				if(Constants.EXPERIENCE_ID.equals(obj.get("id"))||Constants.SIMULATION_ID.equals(obj.get("id"))){
					String inside="";
					obj.set("name", Constants.SIMULATION_EXPERIENCE_JOIN_NAME);
					if(Constants.EXPERIENCE_ID.equals(obj.get("id"))){
						inside=Constants.SIMULATION_ID;
					}else{
						inside=Constants.EXPERIENCE_ID;
					}
					for(Record c:list){
						if(inside.equals(obj.get("id"))){
							int num=Integer.valueOf(obj.get("count")+"")+Integer.valueOf(c.get("count")+"");
							obj.set("count", num);
							list.remove(c);
						}
					}
				}
			}
			return list;
		}else{
			return list;
		}*/
		return list;
	}

	/**
	 * 
	 * @time 2017年4月19日 下午3:17:48
	 * @author zuoqb
	 * @todo 实验室联通统计
	 * @param @return
	 * @return_type Record
	 */
	public Record labLink() {
		StringBuffer sb = new StringBuffer();
		sb.append(" select all_num,link_num,un_link_num,to_char(round(link_num/all_num*100))||'%' as link_rate ");
		sb.append("  from (select count (*) as all_num from(select 1 from t_b_lab_info lab where lab.del_flag="+Constants.DEL_FALG+" group by code) ) a ");
		sb.append("  left join (SELECT count(1) as link_num from(select 1 as link_num from t_b_lab_info lab where lab.del_flag="+Constants.DEL_FALG+" and lab.link_status=1 group by code))b on 1=1  ");
		sb.append("  left join ( SELECT count(1) as un_link_num from(select 1 as un_link_num from t_b_lab_info lab where lab.del_flag="+Constants.DEL_FALG+" and lab.link_status=0  group by code))b on 1=1 ");
		return Db.findFirst(sb.toString());
	}
	/**
	 * 
	 * @time   2018年1月31日 下午2:11:03
	 * @author zuoqb
	 * @todo   TODO
	 * @param  @param pageSize
	 * @param  @param pageNumber
	 * @param  @param c
	 * @param  @return
	 * @return_type   Page<LabModel>
	 */
	public Page<Record> pager(Integer pageSize,Integer pageNumber,BaseController c ){
		String fromSql=" from (select distinct code,name,jiance37_name,lab_type_code,lab_type_name,";
		fromSql+=" belong_gl_code,belong_gl_name,professional_code,professional_name,properties_code,properties_name,";
		fromSql+=" link_status,location,country,city,lng,lat,show_in_map,del_flag,create_date,update_date";
		fromSql+=" from t_b_lab_info  where del_flag=0 ";
		//实验室编码 名称 简称 城市 位置筛选
		if(StringUtils.isNotBlank(c.getPara("name"))){
			fromSql+=" and (code like '%"+c.getPara("name")+"%' or  name like '%"+c.getPara("name")+"%' or jiance37_name like '%"+c.getPara("name")+"%' "
					+ "or location like '%"+c.getPara("name")+"%' or country like '%"+c.getPara("name")+"%' or city like '%"+c.getPara("name")+"%') ";
		}
		//实验室类型筛选
		if(StringUtils.isNotBlank(c.getPara("labType"))){
			fromSql+=" and lab_type_code='"+c.getPara("labType")+"' ";
		}
		//可开展实验类型
		if(StringUtils.isNotBlank(c.getPara("carryCode"))){
			fromSql+=" and code in(select lab_code from t_b_lab_carry where carry_code='"+c.getPara("carryCode")+"' and del_flag=0 ) ";
		}
		//联通状态
		if(StringUtils.isNotBlank(c.getPara("linkStatus"))){
			fromSql+=" and link_status='"+c.getPara("linkStatus")+"' ";
		}
		fromSql+=" order by create_date desc )";
		Page<Record> pager = Db.paginate(pageNumber, pageSize,"select * ",fromSql);
		//统计 产线 以及可开展实验类别信息
		for(Record r:pager.getList()){
			// 根据实验室编码查询该实验室归属产线
			r.set("productList", DicModel.dao.findProductTypeByLabCode(r.getStr("code")));
			//根据实验室编码查询该实验室可开展实验类别
			r.set("labCarryList", LabCarryModel.dao.findCarryByCode(r.getStr("code")));
		};
		return pager;
	}
	/**
	 * 
	 * @time   2018年2月1日 上午9:17:36
	 * @author zuoqb
	 * @todo   根据实验室编码查询实验室信息（包含多个产线及可开展实验类型数据）
	 * @param  @param labCode
	 * @param  @return
	 * @return_type   Record
	 */
	public Record findLabByCode(String labCode){
		String fromSql="select * from (select distinct code,name,jiance37_name,lab_type_code,lab_type_name,";
		fromSql+=" belong_gl_code,belong_gl_name,professional_code,professional_name,properties_code,properties_name,";
		fromSql+=" link_status,location,country,city,lng,lat,show_in_map,del_flag,create_date,update_date";
		fromSql+=" from t_b_lab_info  where del_flag=0 and code='"+labCode+"') ";
		Record labInfo=Db.findFirst(fromSql);
		//统计 产线 以及可开展实验类别信息
		if(labInfo!=null){
			// 根据实验室编码查询该实验室归属产线
			labInfo.set("productList", DicModel.dao.findProductTypeByLabCode(labCode));
			//根据实验室编码查询该实验室可开展实验类别
			labInfo.set("labCarryList", LabCarryModel.dao.findCarryByCode(labCode));
		};
		return labInfo;
	};
	/**
	 * 
	 * @time   2018年2月1日 上午9:34:06
	 * @author zuoqb
	 * @todo   删除实验室信息（逻辑删除）
	 * @param  @param labCode
	 * @param  @return
	 * @return_type   boolean
	 */
	public boolean delLabByCode(String labCode){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String updateDate=sdf.format(new Date());
		int result=0;
		//第一步 删除实验室主表  
		String delLab="update t_b_lab_info set del_flag=1,update_date='"+updateDate+"' where code='"+labCode+"'";
		result=Db.update(delLab);
		//第二步删除可开展实验类型表
		String delCarry="update t_b_lab_carry set del_flag=1 where lab_code='"+labCode+"'";
		result=Db.update(delCarry);
		return result>0;
	}
	/**
	 * 
	 * @time   2018年2月1日 上午10:08:28
	 * @author zuoqb
	 * @todo   自动生成实验室编码
	 * @param  @return
	 * @return_type   String
	 */
	public synchronized String genLabCode(){
		String labCode="";
		Record r=Db.findFirst("select max(code) as code from t_b_lab_info");
		if(r!=null){
			labCode=r.getStr("code");
		}
		labCode=labCode.replaceAll("Lab", "");
		labCode="Lab"+(Integer.parseInt(labCode)+1);
		return labCode;
	}
}
