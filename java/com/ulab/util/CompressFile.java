package com.ulab.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
/**
 * 
 * @time   2017年12月12日 上午11:55:12
 * @author zuoqb
 * @todo   文件压缩
 */
public class CompressFile {
	private static CompressFile instance = new CompressFile();
	 private CompressFile() {
	 }
	 public static CompressFile getInstance() {
	  return instance;
	 }
	 /**
	  * 压缩文件或者文件目录到指定的zip或者rar包
	  *
	  * @param inputFilename
	  *            要压缩的文件或者文件夹，如果是文件夹的话，会将文件夹下的所有文件包含子文件夹的内容进行压缩
	  * @param zipFilename
	  *            生成的zip或者rar文件的名称
	  */
	 public synchronized void zip(String inputFilename, String zipFilename)
	   throws IOException {
	  zip(new File(inputFilename), zipFilename);
	 }
	/**
	  * 压缩文件或者文件目录到指定的zip或者rar包，内部调用
	  *
	  * @param inputFile
	  *            参数为文件类型的要压缩的文件或者文件夹
	  * @param zipFilename
	  *            生成的zip或者rar文件的名称
	  * @return void
	  */
	 private synchronized void zip(File inputFile, String zipFilename)
	   throws IOException {
	  ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
	    zipFilename));
	  try {
	   zip(inputFile, out, "");
	  } catch (IOException e) {
	   throw e;
	  } finally {
	   out.close();
	  }
	 }
	/**
	  * 压缩文件或者文件目录到指定的zip或者rar包
	  *
	  * @param inputFile
	  *            参数为文件类型的要压缩的文件或者文件夹
	  * @param out
	  *            输出流
	  * @param base
	  *            基文件夹
	  * @return void
	  */
	 private synchronized void zip(File inputFile, ZipOutputStream out,
	   String base) throws IOException {
	  if (inputFile.isDirectory()) {
	   File[] inputFiles = inputFile.listFiles();
	   out.putNextEntry(new ZipEntry(base + "/"));
	   base = base.length() == 0 ? "" : base + "/";
	   for (int i = 0; i < inputFiles.length; i++) {
	    zip(inputFiles[i], out, base + inputFiles[i].getName());
	   }
	  } else {
	   if (base.length() > 0) {
	    out.putNextEntry(new ZipEntry(base));
	   } else {
	    out.putNextEntry(new ZipEntry(inputFile.getName()));
	   }
	   FileInputStream in = new FileInputStream(inputFile);
	   try {
	    int c;
	    byte[] by = new byte[BUFFEREDSIZE];
	    while ((c = in.read(by)) != -1) {
	     out.write(by, 0, c);
	    }
	   } catch (IOException e) {
	    throw e;
	   } finally {
	    in.close();
	   }
	  }
	 }
	 private static final int BUFFEREDSIZE = 1024;
	 public static void main(String[] args) {
	  CompressFile bean = new CompressFile();
	  try {
	   bean.zip("I:\\海联\\文档资料\\威海出租车天玥.docx", "I:/海联/文档资料/test.rar");
	   
	   bean.zip("I:\\海联\\文档资料\\威海出租车天玥.docx", "I:/海联/文档资料/test.zip");
	  } catch (IOException e) {
	   e.printStackTrace();
	  }
	 }
}
