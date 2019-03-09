package com.hailian.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import com.hailian.modules.credit.utils.FileTypeUtils;
/**
 * ftp文件上传
* @author doushuihai  
* @date 2018年8月28日上午9:46:27  
* @TODO
 */
public class FtpUploadFileUtils {
	public static boolean storeFile(String fileName, File file,String storePath,String url,int port,String userName,String password) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(file);
		boolean result = false;
		FTPClient ftp = new FTPClient();
		ftp.setControlEncoding("UTF-8");
		ftp.setPassiveNatWorkaround(true);
		try {
			// 连接至服务器，端口默认为21时，可直接通过URL连接
			ftp.connect(url, port);
			// 登录服务器
			ftp.login(userName, password);
			// 判断返回码是否合法
			if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
				// 不合法时断开连接
				ftp.disconnect();
				// 结束程序
				return result;
			}
			// 判断ftp目录是否存在，如果不存在则创建目录，包括创建多级目录
			ftp.enterLocalActiveMode();
//			ftp.enterLocalPassiveMode(); 
			// 判断ftp目录是否存在，如果不存在则创建目录，包括创建多级目录
			String s = "/"+storePath;
			String[] dirs = s.split("/");
			ftp.changeWorkingDirectory(s);			
	            //按顺序检查目录是否存在，不存在则创建目录  
	            for(int i=1; dirs!=null&&i<dirs.length; i++) {  
	                if(!ftp.changeWorkingDirectory(dirs[i])) {  
	                    if(ftp.makeDirectory(dirs[i])) {  
	                        if(!ftp.changeWorkingDirectory(dirs[i])) {  
	                            return false;  
	                        }  
	                    }else {  
	                        return false;                         
	                    }  
	                }  
	            }  
			// 设置文件操作目录
			ftp.changeWorkingDirectory(storePath);

//			ftp.makeDirectory(storePath);
			// 设置文件操作目录
			ftp.changeWorkingDirectory(storePath);
			// 设置文件类型，二进制
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 设置缓冲区大小
			ftp.setBufferSize(3072);
			// 上传文件
			result = ftp.storeFile(fileName, fis);
			// 关闭输入流
			fis.close();
			// 登出服务器
			ftp.logout();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 判断输入流是否存在
				if (null != fis) {
					// 关闭输入流
					fis.close();
				}
				// 判断连接是否存在
				if (ftp.isConnected()) {
					// 断开连接
					ftp.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	//单文件上传
	public static boolean storeFtpFile(String now,List<File> filelist,String storePath,String url,int port,String userName,String password) throws FileNotFoundException {
		FileInputStream fis = null;
		boolean result = false;
		FTPClient ftp = new FTPClient();
		
		ftp.setPassiveNatWorkaround(true);
		try {
			ftp.setRemoteVerificationEnabled(false);
			// 连接至服务器，端口默认为21时，可直接通过URL连接
			ftp.connect(url, port);
			// 登录服务器
			ftp.login(userName, password);
			// 判断返回码是否合法
			if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
				// 不合法时断开连接
				ftp.disconnect();
				// 结束程序
				return result;
			}
			// 判断ftp目录是否存在，如果不存在则创建目录，包括创建多级目录
//			ftp.enterLocalActiveMode();
			ftp.setControlEncoding("UTF-8");
			ftp.enterLocalPassiveMode(); 
			ftp.setFileTransferMode(ftp.BINARY_FILE_TYPE);
			// 判断ftp目录是否存在，如果不存在则创建目录，包括创建多级目录
			String s = "/"+storePath;
			String[] dirs = s.split("/");
			ftp.changeWorkingDirectory("/");			
	            //按顺序检查目录是否存在，不存在则创建目录  
            for(int i=1; dirs!=null&&i<dirs.length; i++) {  
                if(!ftp.changeWorkingDirectory(dirs[i])) {  
                    if(ftp.makeDirectory(dirs[i])) {  
                        if(!ftp.changeWorkingDirectory(dirs[i])) {  
                            return false;  
                        }  
                    }else {  
                        return false;                         
                    }  
                }  
            }  
			// 设置文件操作目录
			boolean changeWorkingDirectory = ftp.changeWorkingDirectory(s);
			// 设置文件操作目录
			// 设置文件类型，二进制
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 设置缓冲区大小
			ftp.setBufferSize(3072);
			// 上传文件
			for(File file:filelist){
				fis = new FileInputStream(file);
				String filename=file.getName();
				String type = FileTypeUtils.getFileType(filename);
				String name = FileTypeUtils.getName(filename);
				String ftpName=now+"."+type;
//				result = ftp.storeFile(ftpName, fis);
				result = ftp.storeFile(new String(ftpName.getBytes("UTF-8"),"iso-8859-1"), fis);
			}
			
			// 关闭输入流
			fis.close();
			// 登出服务器
			ftp.logout();
		} catch (IOException e) {
			try {
				e.printStackTrace();
				// 判断输入流是否存在
				if (null != fis) {
					// 关闭输入流
					fis.close();
				}
				// 判断连接是否存在
				if (ftp.isConnected()) {
					// 断开连接
					ftp.disconnect();
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				// 判断输入流是否存在
				if (null != fis) {
					// 关闭输入流
					fis.close();
				}
				// 判断连接是否存在
				if (ftp.isConnected()) {
					// 断开连接
					ftp.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	//多文件上传
	public static boolean storeMoreFtpFile(String now,List<File> filelist,String storePath,String url,int port,String userName,String password) throws FileNotFoundException {
		FileInputStream fis = null;
		boolean result = false;
		FTPClient ftp = new FTPClient();
		ftp.setControlEncoding("GBK");
        FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
        conf.setServerLanguageCode("zh");  
		//ftp.setControlEncoding("UTF-8");
		ftp.setPassiveNatWorkaround(true);
		try {
			// 连接至服务器，端口默认为21时，可直接通过URL连接
			ftp.connect(url, port);
			// 登录服务器
			ftp.login(userName, password);
			// 判断返回码是否合法
			if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
				// 不合法时断开连接
				ftp.disconnect();
				// 结束程序
				return result;
			}
			// 判断ftp目录是否存在，如果不存在则创建目录，包括创建多级目录
//			ftp.enterLocalActiveMode();
						ftp.enterLocalPassiveMode(); 
			// 判断ftp目录是否存在，如果不存在则创建目录，包括创建多级目录
			String s = "/"+storePath;
			String[] dirs = s.split("/");
			ftp.changeWorkingDirectory("/");			
	            //按顺序检查目录是否存在，不存在则创建目录  
	            for(int i=1; dirs!=null&&i<dirs.length; i++) {  
	                if(!ftp.changeWorkingDirectory(dirs[i])) {  
	                    if(ftp.makeDirectory(dirs[i])) {  
	                        if(!ftp.changeWorkingDirectory(dirs[i])) {  
	                            return false;  
	                        }  
	                    }else {  
	                        return false;                         
	                    }  
	                }  
	            }  
			// 设置文件操作目录
			ftp.changeWorkingDirectory(storePath);

//			ftp.makeDirectory(storePath);
			// 设置文件操作目录
			ftp.changeWorkingDirectory(storePath);
			// 设置文件类型，二进制
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 设置缓冲区大小
			ftp.setBufferSize(3072);
			// 上传文件
			for(File file:filelist){
				fis = new FileInputStream(file);
				String filename=file.getName();
				String type = FileTypeUtils.getFileType(filename);
				String name = FileTypeUtils.getName(filename);
				String ftpName=name+now+"."+type;
				result = ftp.storeFile(ftpName, fis);
			}
			
			// 关闭输入流
			if(fis!=null)
			fis.close();
			// 登出服务器
			ftp.logout();
		} catch (IOException e) {
			try {
				// 判断输入流是否存在
				if (null != fis) {
					// 关闭输入流
					fis.close();
				}
				// 判断连接是否存在
				if (ftp.isConnected()) {
					// 断开连接
					ftp.disconnect();
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				// 判断输入流是否存在
				if (null != fis) {
					// 关闭输入流
					fis.close();
				}
				// 判断连接是否存在
				if (ftp.isConnected()) {
					// 断开连接
					ftp.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
