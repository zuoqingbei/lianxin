package com.hailian.modules.front.template;

import java.util.List;

import com.hailian.jfinal.base.BaseService;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.admin.image.model.TbImage;
import com.hailian.modules.admin.image.model.TbImageAlbum;
import com.hailian.modules.front.service.FrontImageService;
import com.hailian.util.extend.RandomStrUtils;
import com.jfinal.plugin.activerecord.Page;

/**
 * 模板方法接口
 * 
 * 2016年1月18日 下午6:05:54 flyfox 369191470@qq.com
 */
public class TemplateImageService extends BaseService {

	private final static FrontImageService service = new FrontImageService();

	public String randomAlbumId() {
		return RandomStrUtils.randomAlphanumeric(5);
	}

	public List<TbImageAlbum> albums() {
		return service.getAlbumList();
	}

	public TbImageAlbum album(Integer albumId) {
		return service.getAlbum(albumId);
	}

	public Page<TbImage> images(int pageNo, int pageSize) {
		return service.getImage(new Paginator(pageNo, pageSize));
	}

	public Page<TbImage> images(int pageNo, int pageSize, int albumId) {
		return service.getImage(new Paginator(pageNo, pageSize), albumId);
	}

	public TbImage image(Integer iamgeId) {
		return service.getImage(iamgeId);
	}

	public Page<TbImage> recommendImages(int pageNo, int pageSize) {
		return service.getRecommendImages(new Paginator(pageNo, pageSize));
	}
}