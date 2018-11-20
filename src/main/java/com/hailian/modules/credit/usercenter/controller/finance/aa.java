package com.hailian.modules.credit.usercenter.controller.finance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class aa {
		public static void main(String[] args) {
			File file = new File("D:/bb.xlsx");
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
			} catch (FileNotFoundException e2) {
				e2.printStackTrace();
			}
			
			try {
				HSSFWorkbook wb = new HSSFWorkbook();
				wb.createSheet("阿斯顿发生");
				wb.createSheet("二分无法");;
				wb.write(fos);
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				
				if( fos!=null) {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		
		}
}
