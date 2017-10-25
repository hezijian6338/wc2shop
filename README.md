###  **项目介绍** 
项目大概完成了商城后台，小程序所有页面和功能，wap的部分功能

面向学习型的开源框架，简洁高效，减少过渡封装，展现技术本质

Springboot作为容器，使用mybatis作为持久层框架

使用官方推荐的thymeleaf做为模板引擎，shiro作为安全框架,主流技术，“一网打尽”

基于注解的sql写法，零XML，极简配置，一键前后台代码生成


QQ交流群 2123957932

###  **功能简介** 


1. 用户管理
2. 角色管理
3. 部门管理
4. 菜单管理
5. 系统日志
6. 代码生成
7.内容管理

商城模块 （店铺管理，商品管理，类别管理，地址管理，专题管理，文章管理，banner管理，优惠劵管理，品牌管理，
购物车管理，评论管理，我的收藏，楼层管理，分类管理，会员管理，订单管理 ，订单日志）

###  **所用框架** 


  前端 
1. Bootstrap
2. jQuery
3. bootstrap-table
4. layer
5. jsTree 

后端
1. SpringBoot 
2. MyBatis
3. Thymeleaf
4. Shiro

1.项目部署，将bootdo.sql导入mysql，修改application-dev.yml文件的数据库信息，
运行bootdo模块的 BootdoApplication类（直接点右键运行）
http://localhost/login 后台  账号 admin admin
http://localhost/taobao/login  wap项目   账号 456789  456789

2.创建小程序的账号，下载开发工具，然后导入项目webchatapp
打开shell或cmd，进入ngrok目录，运行 `ngrok -config ngrok.cfg -subdomain zscat 80`
然后运行小程序
接口.md为 小程序的接口


