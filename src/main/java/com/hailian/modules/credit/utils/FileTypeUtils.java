package com.hailian.modules.credit.utils;

public class FileTypeUtils {
	// 创建图片类型数组
	public static String img[] = { "bmp", "jpg", "jpeg", "png", "tiff", "gif", "pcx", "tga", "exif", "fpx", "svg", "psd",
	"cdr", "pcd", "dxf", "ufo", "eps", "ai", "raw", "wmf" };
	// 创建图片类型数组
	public static String document1[] = { "doc", "docx"};
	public static String document2[] = { "xls","xlsx"};
	public static String document3[] = { "pdf"};
	public static boolean checkType(String ext){
		boolean flag=false;
		for (int i = 0; i < img.length; i++) {
			if (img[i].equalsIgnoreCase(ext)) {
				flag=true;
			}
		}
		for (int i = 0; i < document1.length; i++) {
			if (document1[i].equalsIgnoreCase(ext)) {
				flag=true;
			}
		}
		for (int i = 0; i < document2.length; i++) {
			if (document2[i].equalsIgnoreCase(ext)) {
				flag=true;
			}
		}
		for (int i = 0; i < document3.length; i++) {
			if (document3[i].equalsIgnoreCase(ext)) {
				flag=true;
			}
		}
		return flag;
	}
	public static String getType(String ext){
		String flag="";
		for (int i = 0; i < img.length; i++) {
			if (img[i].equalsIgnoreCase(ext)) {
				flag="img";
			}
		}
		for (int i = 0; i < document1.length; i++) {
			if (document1[i].equalsIgnoreCase(ext)) {
				flag="word";
			}
		}
		for (int i = 0; i < document2.length; i++) {
			if (document2[i].equalsIgnoreCase(ext)) {
				flag="excel";
			}
		}
		for (int i = 0; i < document3.length; i++) {
			if (document3[i].equalsIgnoreCase(ext)) {
				flag="pdf";
			}
		}
		return flag;
		
	}
}
