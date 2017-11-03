U-lab 互联互通项目说明

1 项目
Jfinal+beetl+maven

2部署环境
因为系统使用3D地图（echarts-x），服务器必须使用tomcat

3 github项目地址
后台代码：https://github.com/zuoqingbei/ulab.git
静态页面代码（ui）：https://github.com/zuoqingbei/ui.git
文档地址：https://github.com/zuoqingbei/ulab_doc.git

4 项目结构

 -java
 
 	-com.ulab.aop    		---定义全局拦截器
 	-com.ulab.config 		---项目启动配置文件
 	-com.ulab.controller	---controller转发器
 	-com.ulab.core			---全局静态变量及 BaseController
 	-com.ulab.model			---实体类
 	-com.ulab.util			---工具类
 	
 -src/main/rescurce			---配置文件
 
 -src/main/webapp
 
 	-layout					---布局文件
 	
 	