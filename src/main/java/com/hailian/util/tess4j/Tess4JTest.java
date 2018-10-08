package com.hailian.util.tess4j;

import java.io.File;
import java.io.IOException;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Tess4JTest {
//	public static void main(String[] args) {
//		
//		String path = "//Users//goufan//git//jfinal_cms-master//target";		//我的项目存放路径
//    	File file = new File("//Users//goufan//Desktop//ceshi.png");
//        ITesseract instance = new Tesseract();
// 
//        /**
//         *  获取项目根路径，例如： D:\IDEAWorkSpace\tess4J
//         */
//        File directory = new File(path);
//        String courseFile = null;
//        try {
//            courseFile = directory.getCanonicalPath();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("courseFile===="+courseFile);
//        //设置训练库的位置
//        instance.setDatapath(courseFile + "//tessdata");
//        instance.setLanguage("eng");//chi_sim ：简体中文， eng	根据需求选择语言库
//        String result = null;
//        try {
//            long startTime = System.currentTimeMillis();
//            result =  instance.doOCR(file);
//            long endTime = System.currentTimeMillis();
//            System.out.println("Time is：" + (endTime - startTime) + " 毫秒");
//        } catch (TesseractException e) {
//            e.printStackTrace();
//        }
//        
//        System.out.println("result: ");
//        System.out.println(result);
//	}
	
//	public static void main(String[] args) {
//		try {
//            File imageFile = new File("//Users//goufan//Desktop//ceshi.png");//图片位置
//            ITesseract instance = new Tesseract();  // JNA Interface Mapping
//            instance.setDatapath("//Users//goufan//Desktop//tessdata");//设置tessdata位置
//            instance.setLanguage("osd");//选择字库文件（只需要文件名，不需要后缀名）
//            String result = instance.doOCR(imageFile);//开始识别
//            System.out.println("图片实际为：7588"+"\t图片识别结果为："+result);//打印图片内容
//        } catch (TesseractException e) {
//            e.printStackTrace();
//        }
//	}
}
