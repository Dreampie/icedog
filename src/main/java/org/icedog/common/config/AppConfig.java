package org.icedog.common.config;


import cn.dreampie.akka.AkkaPlugin;
import cn.dreampie.coffeescript.CoffeeScriptPlugin;
import cn.dreampie.flyway.FlywayPlugin;
import cn.dreampie.lesscss.LessCssPlugin;
import cn.dreampie.log.Slf4jLogFactory;
import cn.dreampie.mail.MailerPlugin;
import cn.dreampie.quartz.QuartzPlugin;
import cn.dreampie.shiro.core.ShiroInterceptor;
import cn.dreampie.shiro.core.ShiroPlugin;
import cn.dreampie.sqlinxml.SqlInXmlPlugin;
import cn.dreampie.tablebind.SimpleNameStyles;
import cn.dreampie.tablebind.TableBindPlugin;
import cn.dreampie.web.JFConfig;
import cn.dreampie.web.cache.CacheRemoveInterceptor;
import cn.dreampie.web.handler.FakeStaticHandler;
import cn.dreampie.web.handler.ResourceHandler;
import cn.dreampie.web.handler.SkipHandler;
import cn.dreampie.web.handler.xss.AttackHandler;
import cn.dreampie.web.render.JsonErrorRenderFactory;
import cn.dreampie.web.route.AutoBindRoutes;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.*;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.AnsiSqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import org.icedog.common.shiro.MyJdbcAuthzService;
import org.icedog.common.web.controller.Controller;

/**
 * Created with IntelliJ IDEA.
 * User: wangrenhui
 * Date: 13-12-18
 * Time: 下午6:28
 * API引导式配置
 */
public class AppConfig extends JFConfig {
  /**
   * 供Shiro插件使用。
   */
  Routes routes;

  /**
   * 配置常量
   */
  public void configConstant(Constants constants) {
    loadPropertyFile("application.properties");
    constants.setDevMode(getPropertyToBoolean("devMode", false));
    //set log to slf4j
    Logger.setLoggerFactory(new Slf4jLogFactory());
    constants.setErrorRenderFactory(new JsonErrorRenderFactory());
  }

  /**
   * 配置路由
   */
  public void configRoute(Routes routes) {
    this.routes = routes;


    AutoBindRoutes autoBindRoutes = new AutoBindRoutes();
    routes.add(autoBindRoutes);
  }

  /**
   * 配置插件
   */
  public void configPlugin(Plugins plugins) {

    //数据库版本控制插件
    plugins.add(new FlywayPlugin());
    //配置druid连接池
    DruidPlugin druidDefault = new DruidPlugin(getProperty("db.default.url"), getProperty("db.default.user"), getProperty("db.default.password"), getProperty("db.default.driver"));
    // StatFilter提供JDBC层的统计信息
    druidDefault.addFilter(new StatFilter());
    // WallFilter的功能是防御SQL注入攻击
    WallFilter wallDefault = new WallFilter();
    wallDefault.setDbType("h2");
    druidDefault.addFilter(wallDefault);

    druidDefault.setInitialSize(getPropertyToInt("db.default.poolInitialSize"));
    druidDefault.setMaxPoolPreparedStatementPerConnectionSize(getPropertyToInt("db.default.poolMaxSize"));
    druidDefault.setTimeBetweenConnectErrorMillis(getPropertyToInt("db.default.connectionTimeoutMillis"));
    plugins.add(druidDefault);

    //Model自动绑定表插件
    TableBindPlugin tableBindDefault = new TableBindPlugin(druidDefault, SimpleNameStyles.LOWER);
    tableBindDefault.setContainerFactory(new CaseInsensitiveContainerFactory(true)); //忽略字段大小写
//    tableBindDefault.addExcludePaths("cn.dreampie.function.shop");
//    tableBindDefault.addIncludePaths("cn.dreampie.function.default");
    tableBindDefault.setShowSql(getPropertyToBoolean("devMode", false));
    //非mysql的数据库方言
    tableBindDefault.setDialect(new AnsiSqlDialect());
    plugins.add(tableBindDefault);

    //sql语句plugin
    plugins.add(new SqlInXmlPlugin());
    //ehcache缓存
    plugins.add(new EhCachePlugin());
    //shiro权限框架
    plugins.add(new ShiroPlugin(routes, new MyJdbcAuthzService()));
    //akka异步执行插件
    plugins.add(new AkkaPlugin());
    //emailer插件
    plugins.add(new MailerPlugin());
    //lesscsss编译插件
    plugins.add(new LessCssPlugin("/lesscss/", "/style/"));
    //coffeescript编译插件
    plugins.add(new CoffeeScriptPlugin("/coffeescript/", "/javascript/"));
    //quartz 任务
    plugins.add(new QuartzPlugin());
  }

  /**
   * 配置全局拦截器
   */
  public void configInterceptor(Interceptors interceptors) {
    interceptors.add(new ShiroInterceptor());
    //开发时不用开启  避免不能试试看到数据效果
    interceptors.add(new CacheRemoveInterceptor());
    interceptors.add(new CacheInterceptor());
    interceptors.add(new SessionInViewInterceptor());
  }

  /**
   * 配置处理器
   */
  public void configHandler(Handlers handlers) {
    handlers.add(new FakeStaticHandler());
//    handlers.add(new AccessDeniedHandler("/**/*.ftl"));
    handlers.add(new ResourceHandler("/javascript/**", "/images/**", "/css/**", "/lib/**", "/**/*.html"));
    handlers.add(new SkipHandler("/im/**"));
    //防xss攻击
    handlers.add(new AttackHandler());
  }

  @Override
  public void afterJFinalStart() {
    super.afterJFinalStart();
  }

  /**
   * 建议使用 JFinal 手册推荐的方式启动项目
   * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
   */
//    public static void main(String[] args) {
//        JFinal.start("src/main/webapp", 80, "/", 5);
//    }
}
