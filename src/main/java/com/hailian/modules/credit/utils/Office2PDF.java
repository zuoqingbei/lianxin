/** 
 *  
 */
package com.hailian.modules.credit.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;

import org.apache.commons.io.FileUtils;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import com.hailian.util.DateUtils;
import com.jfinal.upload.UploadFile;



/**
 * 这是一个工具类，主要是为了使Office2003-2007全部格式的文档(.doc|.docx|.xls|.xlsx|.ppt|.pptx)
 * 转化为pdf文件<br>
 * Office2010的没测试<br>
 * 
 * @date 2017-03-03
 * @author jjc
 * 
 */
public class Office2PDF {
	/*public static File convertFileToPdf(File sourceFile,String name,String type, String pdfTempPath) throws Exception {
		File pdfFile = new File(pdfTempPath+"/"+name+".pdf");
//		File pdfFile = new File(pdfTempPath+"/"+name);
		// 临时pdf文件
		if (!pdfFile.getParentFile().exists()) {
			pdfFile.getParentFile().mkdirs();
        }
		InputStream inputStream = null;
		OutputStream outputStream = null;
 
		if (!type.equals("pdf")) {
 
			// 获得文件格式
			DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();
			DocumentFormat pdfFormat = formatReg.getFormatByFileExtension("pdf");
			DocumentFormat docFormat = formatReg.getFormatByFileExtension(type);

 
			*//**
			 * 在此之前需先开启openoffice服务，用命令行打开cd C:\Program Files\OpenOffice.org 3\program （openoffice安装的路径） 
			 * 输入 soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard
			 *//*
			OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
 
			try {
				// stream 流的形式
				inputStream = new FileInputStream(sourceFile);
				outputStream = new FileOutputStream(pdfFile);
				connection.connect();
				DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
				
//				File outputFile = new File(pdfTempPath+"/xx.pdf");
			        // 假如目标路径不存在,则新建该路径
//			        if (!outputFile.getParentFile().exists()) {
//			            outputFile.getParentFile().mkdirs();
//			        }
			    
				converter.convert(inputStream, docFormat, outputStream, pdfFormat);
//				converter.convert(sourceFile,pdfFile);
 
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e.getMessage());
			} finally {
				if (connection != null) {
					connection.disconnect();
					connection = null;
				}
 
				try {
					inputStream.close();
					outputStream.close();
				} catch (IOException e) {
 
					e.printStackTrace();
				}
 
			}
			System.out.println("转化pdf成功....");
 
		} else {
			// 复制pdf文件到新的文件
			FileUtils.copyFile(sourceFile, pdfFile);
 
		}
		return pdfFile;
	}

    public static void main(String[] args) {
    	OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
    	try {
			connection.connect();
			
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(connection);
    	connection.disconnect();
	}*/
   /* public static File toPdf(UploadFile uploadFile) throws Exception{
		String type = FileTypeUtils.getFileType(uploadFile.getFileName());
		String name = FileTypeUtils.getName(uploadFile.getFileName());
		File convertFileToPdf = Office2PDF.convertFileToPdf(uploadFile.getFile(),name,type, "C:/tempFile");
		return convertFileToPdf;
	}*/
    public static File excelPdfNew(UploadFile uploadFile) throws Exception{
    	String originalFile=uploadFile.getOriginalFileName();
		String ext="";
		File convertFileToPdf=null;
		ext=FileTypeUtils.getFileType(originalFile);
		String name = FileTypeUtils.getName(uploadFile.getFileName());
		if("xls".equals(ext.toLowerCase())||"xlsx".equals(ext.toLowerCase())){
			convertFileToPdf = Excel2Pdf.excel2pdfNew(uploadFile.getFile(),name);
			return convertFileToPdf;
		}else if("doc".equals(ext.toLowerCase())||"docx".equals(ext.toLowerCase())){
			convertFileToPdf = Excel2Pdf.doc2pdfNew(uploadFile.getFile(),name);
			return convertFileToPdf;
		}else{
			return convertFileToPdf;
		}
	}
    
    public static File excelPdfNew(File file) throws Exception{
		File convertFileToPdf=null;
		String ext=FileTypeUtils.getFileType(file.getName());
		String name = FileTypeUtils.getName(file.getName());
		if("xls".equals(ext.toLowerCase())||"xlsx".equals(ext.toLowerCase())){
			convertFileToPdf = Excel2Pdf.excel2pdfNew(file,name);
			return convertFileToPdf;
		}else if("doc".equals(ext.toLowerCase())||"docx".equals(ext.toLowerCase())){
			convertFileToPdf = Excel2Pdf.doc2pdfNew(file,name);
			return convertFileToPdf;
		}else{
			return convertFileToPdf;
		}
	}

    /*public static File toPdf(File file) throws Exception{
        String now=DateUtils.getNow(DateUtils.YMDHMS);
        String type = FileTypeUtils.getFileType(file.getName());
        String name = FileTypeUtils.getName(file.getName());
        File convertFileToPdf = Office2PDF.convertFileToPdf(file,name,type, "C:/tempFile");
        return convertFileToPdf;
    }*/
}