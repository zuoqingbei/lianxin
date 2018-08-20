package com.hailian.system.file;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.site.TbSite;
import com.hailian.system.file.util.FileUploadUtils;
import com.jfinal.upload.UploadFile;

/**
 * 上传公共方法
 */
@ControllerBind(controllerKey = "/system/upload")
public class UploadController extends BaseProjectController {

	// FTP上传到文件服务器
	public void index() {
		TbSite site = getBackSite();
		List<UploadFile> list = getFiles(FileUploadUtils.getUploadTmpPath(site), 10 * 1024 * 1024);
		// 上传路径设置
		String uploadPath = getPara("uploadPath");

		Map<String, String> map = new HashMap<String, String>();
		map.put("flag", "2"); // 失败
		for (UploadFile uploadFile : list) {
			String oldFileName = uploadFile.getFileName();
			String contentType = uploadFile.getContentType();

			String fileName = uploadHandler(site, uploadFile.getFile(), "upload" + File.separator + uploadPath);

			map.put("contentType", contentType); // 类型
			map.put("fileName", fileName); // 文件名
			map.put("oldFileName", oldFileName); // 原文件名
			map.put("flag", "1"); // 成功

			try {
				getResponse().getWriter().print(JSONObject.toJSON(map));
				// 等一会儿
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		renderNull();
	}

}
