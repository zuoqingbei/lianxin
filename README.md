部署服务器需要两个tomcat
1、文件tomcat，端口9982（9980老端口）
webapps/ROOT目录下放report_type与zhengxin_file
2、应用服务器端口9981
应用war改为ROOT.war
3、新服务器上db、文件上传ip都是使用的localhost
4、文件上传工具端口1999
5、服务器需要安装openoffice
用命令行打开cd C:\Program Files\OpenOffice.org 3\program （openoffice安装的路径） 
 输入 soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard