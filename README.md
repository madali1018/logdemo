1. [logdemo说明](#logdemo说明)
2. [修改多模块下的版本号](#修改多模块下的版本号)
3. [Logger接口说明](#Logger接口说明)
4. 


## logdemo说明 ##
1. logdemo是一个多model的maven工程
2. log4j使用demo：**maven model 为 log4j.demo**
3. logback使用demo：**maven model 为 logback.demo**
4. log4j2使用demo:**maven model 为 log4j2.demo**
5.  

## 修改多模块下的版本号 ##
1. 修改parent的pom.xml的版本号，如1.0.0
2. 执行命令  **mvn -N versions:update-child-modules**	执行成功后各个子模块中的版本号统一会变成1.0.0
3. 使用该功能必须在parent的pom里添加**versions-maven-plugin插件**
4. 

## Logger接口说明 ##
1. LOGGER的定义
	1. 在log4j和logback的jar里：private static final Logger LOGGER = LoggerFactory.getLogger(Demo.class);
	2. 在log4j2里：private static final Logger LOGGER = LogManager.getLogger(Demo.class);
	3. 1和2的不同在于 log4j和logback的Logger是**org.slf4j.Logger**，而log4j2的Logger是**org.apache.logging.log4j.LogManager**
	4. 
2. 
