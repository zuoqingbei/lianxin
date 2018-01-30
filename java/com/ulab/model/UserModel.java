package com.ulab.model;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.ulab.core.Constants;
import com.ulab.util.MD5Util;

@TableBind(tableName = "t_b_user", pkName = "id")
public class UserModel extends Model<UserModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final UserModel dao = new UserModel();

	
	/**
	 * 
	 * @time   2018年1月30日 下午12:40:03
	 * @author zuoqb
	 * @todo   根据用户登陆名  密码 查询用户
	 * @param  @param loginName
	 * @param  @return
	 * @return_type   UserModel
	 */
	public UserModel getUserByLoginName(String loginName) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from t_b_user where login_name='"+loginName+"' and del_flag='"+Constants.DEL_FALG+"'");
		return UserModel.dao.findFirst(sb.toString());
	}

	/**
	 * 
	 * @time   2018年1月30日 下午12:40:03
	 * @author zuoqb
	 * @todo   根据用户登陆名  密码 登陆
	 * @param  @param loginName
	 * @param  @param pwd
	 * @param  @return
	 * @return_type   UserModel
	 */
	public Map<String,Object> login(String loginName,String pwd) {
		Map<String,Object> map=new HashMap<String,Object>();
		UserModel user=getUserByLoginName(loginName);
		if(user==null){
			map.put("success", false);
			map.put("msg", "用户不存在！");
		}else{
			if("1".equals(user.getStr("forbid"))){
				map.put("success", false);
				map.put("msg", "用户禁止登陆，请联系管理员！");
			}else{
				//判断密码是否正确
				if(MD5Util.getStringMD5(pwd).equals(user.getStr("pwd"))){
					map.put("success", true);
					map.put("msg", "登陆成功！");
					map.put("user", user);
				}else{
					map.put("success", false);
					map.put("msg", "密码错误！");
				}
			}
		}
		return map;
	}
	/**
	 * 
	 * @time   2018年1月30日 下午12:54:17
	 * @author zuoqb
	 * @todo   分页查询用户
	 * @param  @param pageSize
	 * @param  @param pageNumber
	 * @param  @return
	 * @return_type   Page<UserModel>
	 */
	public Page<UserModel> paginates(Integer pageSize,Integer pageNumber){
		Page<UserModel> pager = UserModel.dao.paginate(pageNumber, pageSize,"select *", "from T_B_USER where del_flag='"+Constants.DEL_FALG+"'");
		return pager;
	}
}
