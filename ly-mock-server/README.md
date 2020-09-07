kpi-dashboard

    项目基于springboot1.5.10.RELEASE、springcloud Edgware.SR3构建，
    在网关、服务注册中心基础上实现了指标、合同明细、代理商明细的查询和下载操作。

本地运行环境：

    1.本地ide需装好lombok插件,redis,mysql,eureka已修改为服务器配置，
    2.首先保证本地maven运行生成jar包成功，
    3.启动server-zuul,service-user,service-portal,service-dashboard,
      web-portal,web-dashboard,其顺序可以随意。
    4.同时也支持上述服务部署至docker中启动。
    5.浏览器输入http://kpi.bop.weibo.com/api-portal/,用本人邮箱/密码登陆。

目前实际用到的项目结构和端口规划

    common

    server-zuul            8081

    service-portal-api
    service-portal         8101
    web-portal             8201

    core-user
    service-user-api
    service-user           8102

    core-dashboard
    service-dashboard-api
    service-dashboard      8104
    web-dashboard          8204
    
    bop-schedule           8105
