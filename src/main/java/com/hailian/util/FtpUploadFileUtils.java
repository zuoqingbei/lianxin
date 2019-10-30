package com.hailian.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.hailian.modules.credit.utils.FileTypeUtils;
/**
 * ftp文件上传
* @author doushuihai  
* @date 2018年8月28日上午9:46:27  
* @TODO
 */
public class FtpUploadFileUtils {
	//文件上传
	public static boolean storeFtpFile(String now,List<File> filelist,String storePath,String url,int port,String userName,String password) throws FileNotFoundException {
		return storeMoreFtpFile(now, filelist, storePath, url, port, userName, password);
	}
	  public static boolean storeMoreFtpFile(String now,List<File> filelist,String storePath,String url,int port,String userName,String password) throws FileNotFoundException  {
			FileInputStream fis = null;
			boolean result = true;
			try {
				for(File file:filelist){
					fis = new FileInputStream(file);
					String filename=file.getName();
					String type = FileTypeUtils.getFileType(filename);
					String name = FileTypeUtils.getName(filename);
					name=URLEncoder.encode(name, "UTF-8");
					String ftpName=name+now+"."+type;
					String s=uploadFile(file, ftpName);
					if(!"success".equals(s)){
						result=false;
						return result;
					}
				}
			} catch (Exception e) {
				 e.printStackTrace();
				 throw new RuntimeException(e);
			} finally {
				try {
					// 判断输入流是否存在
					if (null != fis) {
						// 关闭输入流
						fis.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return result;
		}
	  
	 /* public static boolean storeMoreFtpFile2(String now,List<File> filelist,String storePath,String url,int port,String userName,String password) throws FileNotFoundException  {
			FileInputStream fis = null;
			boolean result = true;
			try {
				for(File file:filelist){
					fis = new FileInputStream(file);
					String filename=file.getName();
					String type = FileTypeUtils.getFileType(filename);
					String name = FileTypeUtils.getName(filename);
					CharacterParser c=new CharacterParser();
					name=c.getSpelling(name);
					//name=URLEncoder.encode(name, "UTF-8");
					String ftpName=name+now+"."+type;
					String s=uploadFile(file, ftpName);
					if(!"success".equals(s)){
						result=false;
						return result;
					}
				}
			} catch (Exception e) {
				 e.printStackTrace();
				 throw new RuntimeException(e);
			} finally {
				try {
					// 判断输入流是否存在
					if (null != fis) {
						// 关闭输入流
						fis.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return result;
		}*/
	  public static boolean storeMoreFtpFile3(String now,List<File> filelist,String storePath,String url,int port,String userName,String password) throws FileNotFoundException  {
			FileInputStream fis = null;
			boolean result = true;
			try {
				for(File file:filelist){
					fis = new FileInputStream(file);
					String filename=file.getName();
					String type = FileTypeUtils.getFileType(filename);
					String name = FileTypeUtils.getName(filename);
					CharacterParser c=new CharacterParser();
					name=c.getSpelling(name);
					//name=URLEncoder.encode(name, "UTF-8");
					String ftpName=name+now+"."+type;
					String s=uploadFile(file, ftpName);
					if(!"success".equals(s)){
						result=false;
						return result;
					}
				}
			} catch (Exception e) {
				 e.printStackTrace();
				 throw new RuntimeException(e);
			} finally {
				try {
					// 判断输入流是否存在
					if (null != fis) {
						// 关闭输入流
						fis.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return result;
		}
	/*public static FTPClient getFtpConnection(String url,int port,String userName,String password,Long sleep){
		FTPClient ftp = new FTPClient();
		ftp.setControlEncoding("GBK");
        FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
        conf.setServerLanguageCode("zh");  
		//ftp.setControlEncoding("UTF-8");
        ftp.setDataTimeout(10000);       //设置传输超时时间为60秒 
        ftp.setConnectTimeout(10000);       //连接超时为60秒
		ftp.setPassiveNatWorkaround(true);
		try {
			// 连接至服务器，端口默认为21时，可直接通过URL连接
			Thread.sleep(sleep);
			ftp.connect(url, port);
			// 登录服务器
			boolean loginFlag = ftp.login(userName, password);
			if(loginFlag){
				// 判断返回码是否合法
				boolean replyCodeFlag = FTPReply.isPositiveCompletion(ftp.getReplyCode());
				if (!replyCodeFlag) {
					// 不合法时断开连接
					if(ftp.isConnected()) {
						ftp.logout();
						ftp.disconnect();
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ftp;
	}
	public static FTPClient getFtpConnection(String url,int port,String userName,String password){
		FTPClient ftp = getFtpConnection(url, port, userName, password, 10L);
		boolean replyCodeFlag = FTPReply.isPositiveCompletion(ftp.getReplyCode());
		try {
			if (!replyCodeFlag) {
				// 不合法时断开连接
				//尝试连接3次
				for(int x=1;x<4;x++){
					ftp=getFtpConnection(url, port, userName, password, (x-1)*3000l);
					if(FTPReply.isPositiveCompletion(ftp.getReplyCode())){
						break;
					}else{
						if(ftp.isConnected()) {
							ftp.logout();
							ftp.disconnect();
						}
					}
				}
			}
		} catch (Exception e) {
		}
		return ftp;
	}*/
	public static String uploadFile(File file,String fileName) throws ClientProtocolException, IOException {
		String url=Config.getStr("fileUploadUrl");//"http://localhost:1999/lianxin/uploadDoc";
		String fileUploadPath= Config.getStr("fileUploadPath");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String dateStr=sdf.format(new Date());
		fileUploadPath=fileUploadPath+File.separator+dateStr;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse httpResponse = null;
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000000).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.addBinaryBody("file", file);
        multipartEntityBuilder.addTextBody("fileUploadPath", fileUploadPath);
        multipartEntityBuilder.addTextBody("fileName",URLEncoder.encode(fileName, "UTF-8") );
        HttpEntity httpEntity = multipartEntityBuilder.build();
        httpPost.setEntity(httpEntity);
 
        httpResponse = httpClient.execute(httpPost);
        HttpEntity responseEntity = httpResponse.getEntity();
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
            StringBuffer buffer = new StringBuffer();
            String str = "";
            while (!StringUtils.isEmpty(str = reader.readLine())) {
                buffer.append(str);
            }
            System.out.println(buffer.toString());
            return buffer.toString();
        }
 
        httpClient.close();
        if (httpResponse != null) {
            httpResponse.close();
        }
        return null;
	}
	
  
    /**
     * 保存文件到临时目录
     * @param inputStream 文件输入流
     * @param fileName 文件名
     */
   /* public static String saveFile(InputStream inputStream, String fileName) {
    	String fileUploadPath= Config.getStr("fileUploadPath");
        OutputStream os = null;
        String filePath="";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String dateStr=sdf.format(new Date());
        try {
            // 保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            File tempFile = new File(fileUploadPath+ File.separator +dateStr);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            filePath=tempFile.getPath();
            os = new FileOutputStream(tempFile.getPath() + File.separator+ fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }*/
	/*public static boolean storeMoreFtpFile(String now,List<File> filelist,String storePath,String url,int port,String userName,String password) throws FileNotFoundException  {
		FileInputStream fis = null;
		boolean result = true;
		try {
			for(File file:filelist){
				fis = new FileInputStream(file);
				String filename=file.getName();
				String type = FileTypeUtils.getFileType(filename);
				String name = FileTypeUtils.getName(filename);
				String ftpName=name+now+"."+type;
				String p=saveFile(fis, ftpName);
				System.out.println(p);
			}
		} catch (Exception e) {
			 e.printStackTrace();
			 throw new RuntimeException(e);
		} finally {
			try {
				// 判断输入流是否存在
				if (null != fis) {
					// 关闭输入流
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}*/
	/*public static boolean storeMoreFtpFile(String now,List<File> filelist,String storePath,String url,int port,String userName,String password)  {

		FileInputStream fis = null;
		boolean result = false;
		FTPClient ftp = getFtpConnection(url, port, userName, password);
		try {
			// 判断返回码是否合法
            boolean replyCodeFlag = FTPReply.isPositiveCompletion(ftp.getReplyCode());
			if (!replyCodeFlag) {
				// 不合法时断开连接
				ftp.disconnect();
				throw  new RuntimeException( "此时ftp登录账号:"+userName +",密码:"+password+"; ftp返回码合法与否="+replyCodeFlag);
				// 结束程序
				//return result;
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
		} catch (Exception e) {
			 e.printStackTrace();
			 throw new RuntimeException(e);
		} finally {
			try {
				// 判断输入流是否存在
				if (null != fis) {
					// 关闭输入流
					fis.close();
				}
				// 判断连接是否存在
				if (ftp.isConnected()) {
                    ftp.logout();
					// 断开连接
					ftp.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}*/
	//多文件上传
	/*public static boolean storeMoreFtpFile(String now,List<File> filelist,String storePath,String url,int port,String userName,String password)  {

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
			boolean loginFlag = ftp.login(userName, password);
			// 判断返回码是否合法
            boolean replyCodeFlag = FTPReply.isPositiveCompletion(ftp.getReplyCode());
			if (!replyCodeFlag) {
				// 不合法时断开连接
				ftp.disconnect();
				throw  new RuntimeException( "此时ftp登录账号:"+userName +",密码:"+password+"; loginFlag="+loginFlag+"; ftp返回码合法与否="+replyCodeFlag);
				// 结束程序
				//return result;
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
		} catch (Exception e) {
			 e.printStackTrace();
			 throw new RuntimeException(e);
		} finally {
			try {
				// 判断输入流是否存在
				if (null != fis) {
					// 关闭输入流
					fis.close();
				}
				// 判断连接是否存在
				if (ftp.isConnected()) {
                    ftp.logout();
					// 断开连接
					ftp.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}*/
}
