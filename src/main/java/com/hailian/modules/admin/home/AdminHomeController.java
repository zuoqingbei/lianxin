package com.hailian.modules.admin.home;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.jfinal.component.db.SQLUtils;
import com.hailian.modules.CommonController;
import com.hailian.modules.admin.article.TbArticle;
import com.hailian.modules.admin.comment.TbComment;
import com.hailian.modules.admin.pageview.TbPageView;
import com.hailian.system.department.DepartmentSvc;
import com.hailian.system.user.SysUser;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Page;

@ControllerBind(controllerKey = "/admin/home")
public class AdminHomeController extends BaseProjectController {

	private static final String path = "/pages/system/user/";

	public void index() {
		SysUser user = (SysUser) getSessionUser();
		if (user == null) {
			redirect(CommonController.firstPage);
			return;
		}
		/*setAttr("nowUser", user);

		// 最新文件
		Page<TbArticle> articlePage = TbArticle.dao.paginate(new Paginator(1, 10), "select t.*,f.name as folderName " //
				, " from tb_article t left join tb_folder f on f.id = t.folder_id " //
						+ " where t.status = 1 and t.type in (11,12) " // 查询状态为显示，类型是预览和正常的文章
						+ "  and f.site_id=" + getBackSite().getId() + " order by t.update_time desc,t.id desc");
		setAttr("articles", articlePage.getList());

		// 最新评论
		Page<TbComment> commentPage = TbComment.dao.paginate(new Paginator(1, 10), "select t.*,a.title articleName ", //
				" from tb_comment t " //
						+ " left join tb_article a on a.id = t.article_id where 1=1 order by t.id desc  ");
		setAttr("comments", commentPage.getList());

		// 最新用户
		Page<SysUser> userPage = SysUser.dao.paginate(new Paginator(1, 10), "select t.*,d.name as departname ", //
				" from sys_user t left join sys_department d on d.id = t.departid " //
						+ " where 1 = 1 and userid != 1 order by userid desc ");
		setAttr("users", userPage.getList());

		// 最新访问用户
		Page<TbPageView> pageViewPage = TbPageView.dao.paginate(new Paginator(1, 10), "select t.*", //
				" from tb_pageview t order by id desc ");
		setAttr("pageViews", pageViewPage.getList());

		render(path + "user_list.html");*/
		list();
	}
	public void list() {
		SysUser model = getModelByAttr(SysUser.class);
		SQLUtils sql = new SQLUtils(" from sys_user t " //
				+ " left join sys_department d on d.id = t.departid " //
				+ " where 1 = 1 and userid != 1 ");
		if (model.getAttrValues().length != 0) {
			sql.whereLike("username", model.getStr("username"));
			sql.whereLike("realname", model.getStr("realname"));
			sql.whereEquals("usertype", model.getInt("usertype"));
			sql.whereEquals("departid", model.getInt("departid"));
		}
		// 排序
		String orderBy = getBaseForm().getOrderBy();
		if (StrUtils.isEmpty(orderBy)) {
			sql.append(" order by userid desc");
		} else {
			sql.append(" order by ").append(orderBy);
		}
		Page<SysUser> page = SysUser.dao.paginate(getPaginator(), "select t.*,d.name as departname ", sql.toString()
				.toString());
		// 下拉框
		setAttr("departSelect", new DepartmentSvc().selectDepart(model.getInt("departid")));
		setAttr("page", page);
		setAttr("attr", model);
		render(path + "user_list.html");
	}
}
