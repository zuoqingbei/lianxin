package com.hailian.modules.credit.utils;

public class FileTypeUtils {
	// 创建图片类型数组
	public static String img[] = { "bmp", "jpg", "jpeg", "png", "tiff", "gif", "pcx", "tga", "exif", "fpx", "svg", "psd",
	"cdr", "pcd", "dxf", "ufo", "eps", "ai", "raw", "wmf" };
	// 创建图片类型数组
	public static String document[] = { "doc", "docx", "xls","xlsx","pdf"};
	public static boolean checkType(String ext){
		boolean flag=false;
		for (int i = 0; i < img.length; i++) {
			if (img[i].equalsIgnoreCase(ext)) {
				flag=true;
			}
		}
		for (int i = 0; i < document.length; i++) {
			if (document[i].equalsIgnoreCase(ext)) {
				flag=true;
			}
		}
		return flag;
	}
}
