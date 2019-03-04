### Rainbow-web介绍:
  * Rainbow-web是对rainbow-framework的使用指南的项目。
 
##安装与使用：

  * 确认先安装了maven3.0环境。
	* 1.首先需要在下rainbow工程,项目地址：https://github.com/accphuangxin/rainbow，
	并在下载目录执行 mvn install
	* 2.下载本工程,在工程目录下执行：mvn install
	* 3.在target目录下有已经打好包的linux版本和windows版本。
	* 4.解压rainbow-web-1.0-SNAPSHOT-20160201001420-win.zip,双击start.bat
	* 5.访问地址:
	 * 访问服务地址:http://localhost:8082/rainbow
  	 * 测试服务地址:http://localhost:8082/rainbow/dispatcherAction/query.do?service=demoService&method=show
* 测试一个访问数据库的service
	* 执行SQL脚本。t_sys_yser.sql 在项目的根录下。
	* 配置数据库地址：在pom.xml中修改
	
	```xml
		<profiles>
		<profile>
			<!-- 开发环境WINDOWS -->
			<id>windows</id>
			<activation>
				<activeByDefault>true</activeByDefault>
			</activation>
			<properties>
				<!-- 	内置sqlite			
				<mvn.jdbc.driverClassName>org.sqlite.JDBC</mvn.jdbc.driverClassName>
				<mvn.jdbc.url>jdbc:sqlite:${webapp.root}/WEB-INF/db/rainbow-web.db</mvn.jdbc.url>
				<mvn.jdbc.username></mvn.jdbc.username>
				<mvn.jdbc.password></mvn.jdbc.password> -->
				<!-- mysql 配置 -->
				<mvn.jdbc.driverClassName>com.mysql.jdbc.Driver</mvn.jdbc.driverClassName>
				<mvn.jdbc.url>jdbc:mysql://localhost:3306/test?characterEncoding=utf8</mvn.jdbc.url>
				<mvn.jdbc.username>***</mvn.jdbc.username>
				<mvn.jdbc.password>****</mvn.jdbc.password>
```

* 重新启动start.bat
	* 访问地址：
		* 批量添加:
	http://localhost:8082/rainbow/dispatcherAction/execute.do?service=crudService&method=insertBatch
		* 查询:
	http://localhost:8082/rainbow/dispatcherAction/execute.do?service=crudService&method=query
	
	
