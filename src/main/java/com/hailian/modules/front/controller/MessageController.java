package com.hailian.modules.front.controller;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.CommonController;
import com.hailian.modules.admin.comment.CommentService;
import com.hailian.modules.admin.comment.TbComment;
import com.hailian.modules.front.interceptor.FrontInterceptor;
import com.hailian.system.user.SysUser;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;

/**
 * 我的消息
 * 
 * 2015年3月10日 下午5:38:24 flyfox 369191470@qq.com
 */
@ControllerBind(controllerKey = "/front/message")
public class MessageController extends BaseProjectController {

	public static final String path = "/message/";

	/**
	 * 我的消息
	 */
	@Before(FrontInterceptor.class)
	public void index() {
		// 活动目录
		setAttr("folders_selected", "message");

		SysUser user = (SysUser) getSessionUser();
		if (user == null) {
			redirect(CommonController.firstPage);
			return;
		}

		String sql = " from tb_comment t " //
				+ " left join tb_article art on art.id = t.article_id " //
				+ " left join tb_folder tf on tf.id = art.folder_id " //
				+ " where ( t.create_id = ? or t.reply_userid = ? ) and tf.site_id = ? " //
				+ " order by t.create_time desc ";
		Page<TbComment> pages = TbComment.dao.paginate(getPaginator(), //
				"select t.*,art.title,art.create_id as article_create_id " //
				, sql, user.getUserid(), user.getUserid(), getSessionSite().getSiteId());
		// 更新状态为已读
		new CommentService().updateCommentStatusRead(user.getUserid());

		setAttr("page", pages);
		renderAuto(path + "show_message.html");
	}
}
