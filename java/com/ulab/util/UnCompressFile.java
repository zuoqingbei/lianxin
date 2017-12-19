package com.ulab.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 
 * @time   2017年12月12日 下午12:38:15
 * @author zuoqb
 * @todo   解压文件
 */
public class UnCompressFile {
	private static UnCompressFile instance = new UnCompressFile();

	private UnCompressFile() {
	}

	public static UnCompressFile getInstance() {
		return instance;
	}

	/**
	 * 解压zip或者rar包的内容到指定的目录下，可以处理其文件夹下包含子文件夹的情况
	 *
	 * @param zipFilename
	 *            要解压的zip或者rar包文件
	 * @param outputDirectory
	 *            解压后存放的目录
	 */
	@SuppressWarnings({ "rawtypes", "resource" })
	public synchronized void unzip(String zipFilename, String outputDirectory) throws IOException {
		File outFile = new File(outputDirectory);
		if (!outFile.exists()) {
			outFile.mkdirs();
		}
		ZipFile zipFile = new ZipFile(zipFilename);
		Enumeration en = zipFile.entries();
		ZipEntry zipEntry = null;
		while (en.hasMoreElements()) {
			zipEntry = (ZipEntry) en.nextElement();
			if (zipEntry.isDirectory()) {
				String dirName = zipEntry.getName();
				System.out.println("=dirName is:=" + dirName + "=end=");
				dirName = dirName.substring(0, dirName.length() - 1);
				File f = new File(outFile.getPath() + File.separator + dirName);
				f.mkdirs();
			} else {
				String strFilePath = outFile.getPath() + File.separator + zipEntry.getName();
				File f = new File(strFilePath);
				//判断文件不存在的话，就创建该文件所在文件夹的目录
				if (!f.exists()) {
					String[] arrFolderName = zipEntry.getName().split("/");
					String strRealFolder = "";
					for (int i = 0; i < (arrFolderName.length - 1); i++) {
						strRealFolder += arrFolderName[i] + File.separator;
					}
					strRealFolder = outFile.getPath() + File.separator + strRealFolder;
					File tempDir = new File(strRealFolder);
					//此处使用.mkdirs()方法，而不能用.mkdir()
					tempDir.mkdirs();
				}
				//////end///
				f.createNewFile();
				InputStream in = zipFile.getInputStream(zipEntry);
				FileOutputStream out = new FileOutputStream(f);
				try {
					int c;
					byte[] by = new byte[BUFFEREDSIZE];
					while ((c = in.read(by)) != -1) {
						out.write(by, 0, c);
					}
					out.flush();
				} catch (IOException e) {
					throw e;
				} finally {
					out.close();
					in.close();
				}
			}
		}
	}

	private static final int BUFFEREDSIZE = 1024;

	public static void main(String[] args) {
		UnCompressFile bean = new UnCompressFile();
		try {
			bean.unzip("I:/海联/文档资料/test.rar", "D:/zzz/test");

			bean.unzip("I:/海联/文档资料/test.zip", "D:/zzz/struts");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
