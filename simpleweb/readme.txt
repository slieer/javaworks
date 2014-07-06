一个不错的javascript格式化工具
http://www.uedsky.com/sky/tool/javascript-html-formater-compressor.html

一些流行且免费的Maven仓库工具有：
　　标准Maven代理codehaus—— http://maven-proxy.codehaus.org/ 
　　Dead simple Maven Proxy (DSMP) - http://www.pdark.de/dsmp/ 
　　Proximity - http://proximity.abstracthorizon.org/ 
　　Artifactory - http://www.jfrog.org/sites/artifactory/latest/ 

maven仓库管理器Nexus远程部署要点
maven2默认的远程仓库是http://repo1.maven.org/maven2，所需要的jar如果找不到，就要手工安装了:

mvn install:install-file -Dfile=./jta-1_0_1B-classes.zip  -DgroupId=javax.transaction -DartifactId=jta -Dversion=1.0.1B  -Dpackaging=jar



