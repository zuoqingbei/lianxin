package com.hailian.modules.credit.notice.model;

import java.util.ArrayList;
import java.util.List;

import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.util.DateUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
/**
 * 白名单model层
* @author doushuihai  
* @date 2018年9月3日上午11:48:31  
* @TODO
 */
@ModelBind(table = "credit_notice")
public class NoticeModel extends BaseProjectModel<NoticeModel> {
	private static final long serialVersionUID = 1L;
	public static final NoticeModel dao = new NoticeModel();
	
	
	/**
	 * 逻辑删除
	* @author doushuihai  
	* @date 2018年9月3日下午2:59:58  
	* @TODO
	 */
	public void delete(Integer id, Integer userid) {
		String now = DateUtils.getNow(DateUtils.DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS);
		String sql="update credit_custom_info set del_flag=1,update_by=?,update_date=? where id=?";
		List<Object> params=new ArrayList<Object>();
		params.add(userid);
		params.add(now);
		params.add(id);
		Db.update(sql,params.toArray());
		
	}
	public List<NoticeModel> getNotice(Integer id,BaseProjectController c){
		StringBuffer sql=new StringBuffer("select * from credit_notice t");
		sql.append(" left join credit_notice_log t2 on t.id=t2.notice_id ");
		sql.append(" where 1=1 and t.del_flag=0 ");
		List<Object> params=new ArrayList<Object>();
		if(id !=null){
			sql.append(" and t.id=?");
			params.add(id);
		}
		if(!c.isAdmin(c.getSessionUser())){
			sql.append(" and t.create_by=? ");
			params.add(c.getSessionUser().getUserid());//传入的参数
		}
		sql.append(" order by t.create_date desc ");
		List<NoticeModel> find = NoticeModel.dao.find(sql.toString(),params.toArray());
		return find;
	}
	/**
	 * 公告展示
	* @author doushuihai  
	* @date 2018年10月30日上午10:54:31  
	* @TODO
	 */
	public Page<NoticeModel> getPage(Paginator paginator, BaseProjectController c,String status,Integer userid) {
				List<Object> params=new ArrayList<Object>();
				StringBuffer sql=new StringBuffer(" from credit_notice t ");
				sql.append(" left join credit_notice_log t2 on t.id=t2.notice_id ");
				sql.append(" where 1=1 and t.del_flag=0 ");
				if (StringUtil.isNotEmpty(status)) {
					sql.append("and t2.read_unread = ? ");
					params.add(status);//传入的参数
				}
				if(!c.isAdmin(c.getSessionUser())){
					sql.append(" and t.create_by=? ");
					params.add(userid);//传入的参数
				}
				if(StringUtil.isNotEmpty(userid+"")){
					sql.append(" and t2.user_id=? ");
					params.add(userid);//传入的参数
				}
				// 排序
				sql.append(" order by t2.read_unread desc,t.create_date desc");
				Page<NoticeModel> page = NoticeModel.dao
						.paginate(paginator, "select t.*,t2.read_unread,t2.label", sql.toString(),params.toArray());
				return page;
	}

}
