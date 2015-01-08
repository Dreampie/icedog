dreampie
========

dreampie.cn  支持我请在右上角点  Star，thanks

推荐java的restful框架：[Resty](https://github.com/Dreampie/resty)

使用jfinal框架开源工具包https://github.com/Dreampie/jfinal-dreampie,  可以在maven中央库 http://search.maven.org/  输入jfianl-draempie 搜索

并提供akka异步执行集成，多数据源自动orm映射，flyway数据库脚本升级，
shiro+权限系统+freemarker-shiro标签支持，以及其他改进

使用angularjs作为前端框架,

以及+bootstrap+requireJs＋coffeescript＋lesscss集成，自动编译coffeescript和lesscss并监听文件改动，
因为时间原因主要以实现功能为主，欢迎大家指正和优化

https://github.com/Dreampie?tab=repositories 目录下有多款插件，可以在maven里搜索到：

cn.dreampie.jfinal-shiro     https://github.com/Dreampie/jfinal-shiro    shiro插件    

cn.dreampie.jfinal-shiro-freemarker   https://github.com/Dreampie/jfinal-shiro-freemarker    shiro插件实现的freemarker标签库   

cn.dreampie.jfinal-web     https://github.com/Dreampie/jfinal-web   相关web插件，简洁model实现   

cn.dreampie.jfinal-utils        https://github.com/Dreampie/jfinal-utils   部分jfinal工具   

cn.dreampie.jfinal-tablebind        https://github.com/Dreampie/jfinal-tablebind   jfinal的table自动绑定插件，支持多数据源   

cn.dreampie.jfinal-flyway      https://github.com/Dreampie/jfinal-flyway   数据库脚本升级插件，开发中升级应用时，使用脚本同步升级数据库或者回滚  

cn.dreampie.jfinal-captcha      https://github.com/Dreampie/jfinal-captcha   基于jfinal render的超简单验证码插件   

cn.dreampie.jfinal-quartz       https://github.com/Dreampie/jfinal-quartz   基于jfinal 的quartz管理器   

cn.dreampie.jfinal-sqlinxml      https://github.com/Dreampie/jfinal-sqlinxml   基于jfinal 的类似ibatis的sql语句管理方案   

cn.dreampie.jfinal-lesscss       https://github.com/Dreampie/jfinal-lesscss   java实现的lesscsss实时编译插件，可以由于jfinal   

cn.dreampie.jfinal-coffeescript     https://github.com/Dreampie/jfinal-coffeescript   java实现的coffeescript实时编译插件，可以由于jfinal     

cn.dreampie.jfinal-akka    https://github.com/Dreampie/jfinal-akka   java使用akka执行异步任务    

cn.dreampie.jfinal-mailer       https://github.com/Dreampie/jfinal-mailer   使用akka发布邮件的jfinal插件  

cn.dreampie.jfinal-slf4j     https://github.com/Dreampie/jfinal-slf4j   让jfinal使用slf4j的日志api   


使用方式：


1.导入IntelliJ IDEA,VCS->Checkout from Version Control->Github(如果没有在plugin里下载或启用) 
然后输入项目地址 https://github.com/Dreampie/icedog.git 确定


2.导入Eclipse，Import->Git->Projects from Git->URI->然后输入项目地址  https://github.com/Dreampie/icedog.git 确定


可直接使用mvn jetty：run  使用嵌入式数据库h2，数据库可以自动验证生成初始化数据

如有问题请及时联系我 在线访问：http://www.dreampie.cn
![Icedog](http://static.oschina.net/uploads/space/2014/0812/165023_hxZO_946569.png "ICEDOG")
