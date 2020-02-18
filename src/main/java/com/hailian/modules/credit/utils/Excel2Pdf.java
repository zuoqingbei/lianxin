package com.hailian.modules.credit.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;

import com.aspose.cells.License;
import com.aspose.cells.Workbook;
import com.aspose.words.Document;
import com.aspose.words.PdfSaveOptions;

public class Excel2Pdf {
	private static boolean getLicense() {
        boolean result = false;
       try {
           InputStream is = Excel2Pdf.class.getClassLoader().getResourceAsStream("license.xml"); // license.xml应放在..\WebRoot\WEB-INF\classes路径下
           License aposeLic = new License();
          aposeLic.setLicense(is);
          result = true;
       } catch (Exception e) {
         e.printStackTrace();
      }
     return result;
   }

   /**
  * @param wordPath 需要被转换的word全路径带文件名
  * @param pdfPath 转换之后pdf的全路径带文件名
  */
  public static void doc2pdf(String wordPath, String pdfPath) {
     if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
        return;
     }
    try {
        long old = System.currentTimeMillis();
        File file = new File(pdfPath); //新建一个pdf文档
        FileOutputStream os = new FileOutputStream(file);
        Document doc = new Document(wordPath); //Address是将要被转化的word文档
        doc.save(os, com.aspose.words.SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
        long now = System.currentTimeMillis();
        os.close();
        System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒"); //转化用时
        } catch (Exception e) {
            e.printStackTrace();
        }
     }

   /**
   * @param excelPath 需要被转换的excel全路径带文件名
   * @param pdfPath 转换之后pdf的全路径带文件名
   */
    public static void excel2pdf(String excelPath, String pdfPath) {
        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
        return;
       }
      try {
         long old = System.currentTimeMillis();
        Workbook wb = new Workbook(excelPath);// 原始excel路径
        FileOutputStream fileOS = new FileOutputStream(new File(pdfPath));
        wb.save(fileOS, com.aspose.cells.SaveFormat.PDF);
        fileOS.close();
        long now = System.currentTimeMillis();
           System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒"); //转化用时
        } catch (Exception e) {
          e.printStackTrace();
       }
     }
    public static boolean compareDate(String begin,String end) {
    	   if(StringUtils.isBlank(begin)){
    		   return true;
    	   }
    	   if(StringUtils.isBlank(end)){
    		   return false;
    	   }
           long longDate = Long.valueOf(begin.replaceAll("[-\\s:]","").replaceAll("年", "").replaceAll("月", "").replaceAll("日", ""));
           long endDate = Long.valueOf(end.replaceAll("[-\\s:]","").replaceAll("年", "").replaceAll("月", "").replaceAll("日", ""));
           System.out.println(longDate);
           return longDate>endDate;
    }
    public static File doc2pdfNew(File excelFile, String name) {
        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
           return null;
        }
        File f=null;
       try {
    	   String pdfPath="C:/tempFile"+"/"+name+".pdf";
           long old = System.currentTimeMillis();
           f=new File(pdfPath);
           FileOutputStream os = new FileOutputStream(f);
           Document doc = new Document(new FileInputStream(excelFile)); //Address是将要被转化的word文档
           PdfSaveOptions options = new PdfSaveOptions();
           options.setExportDocumentStructure(true);
           options.setPageCount(15);
           doc.save(os, com.aspose.words.SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
           long now = System.currentTimeMillis();
           os.close();
           System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒"); //转化用时
           } catch (Exception e) {
               e.printStackTrace();
           }
       return f;
        }
    public static File excel2pdfNew(File excelFile, String name) {
        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
        return null;
       }
        File f=null;
      try {
    	  String pdfPath="C:/tempFile"+"/"+name+".pdf";
         long old = System.currentTimeMillis();
        Workbook wb = new Workbook(new FileInputStream(excelFile));// 原始excel路径
        f=new File(pdfPath);
        FileOutputStream fileOS = new FileOutputStream(f);
        wb.save(fileOS, com.aspose.cells.SaveFormat.PDF);
        fileOS.close();
        long now = System.currentTimeMillis();
           System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒"); //转化用时
        } catch (Exception e) {
          e.printStackTrace();
       }
      return f;
     }
      public static void main(String[] args) {

          //word 和excel 转为pdf
         /* String filePaths="D:/com/广州安信医药有限公司.xls";
          String fileName="2";
         String pdfPath="D:/com/"+fileName+".pdf";
         //doc2pdf(filePaths, pdfPath);//filePaths需要转换的文件位置 pdfPath为存储位置
         String excel2pdf="D:/com/广州安信医药有限公司.xls";
         excel2pdf(excel2pdf,pdfPath);*/
    	  compareDate("2018年12月31日", "2018年12月31日");
      } 
}
