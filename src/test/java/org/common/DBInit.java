package org.common;

import cn.dreampie.PropertiesKit;
import cn.dreampie.mail.MailerPlugin;
import cn.dreampie.sqlinxml.SqlInXmlPlugin;
import cn.dreampie.tablebind.SimpleNameStyles;
import cn.dreampie.tablebind.TableBindPlugin;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.druid.DruidPlugin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ice on 14-9-23.
 */
public class DBInit {
  private static final String DB_CONFIG = "application.properties";

  private DruidPlugin druidPlugin;
  private TableBindPlugin tableBindPlugin;
  private String dbName;
  private List<String> includePaths = new ArrayList<String>();
  private List<String> excludePaths = new ArrayList<String>();


  public DBInit dbName(String dbName) {
    dbName = dbName;
    return this;
  }

  public DBInit include(String... path) {
    includePaths.addAll(Arrays.asList(path));
    return this;
  }

  public DBInit exclude(String... path) {
    excludePaths.addAll(Arrays.asList(path));
    return this;
  }

  public void init() {
//    initFlyway();
    druidPlugin = initDruid(dbName);
    tableBindPlugin = initTableBind(druidPlugin);
    initSqlinxml();
    initMailer();
  }


  private DruidPlugin initDruid(String dbName) {
//    System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());
    PropertiesKit.me().loadPropertyFile(DB_CONFIG);
    dbName = dbName == null ? "default" : dbName;
    DruidPlugin druidPlugin = new DruidPlugin(
        PropertiesKit.me().getProperty("db." + dbName + ".url"),
        PropertiesKit.me().getProperty("db." + dbName + ".user"),
        PropertiesKit.me().getProperty("db." + dbName + ".password"),
        PropertiesKit.me().getProperty("db." + dbName + ".driver"));
    // StatFilter提供JDBC层的统计信息
    druidPlugin.addFilter(new StatFilter());
    // WallFilter的功能是防御SQL注入攻击
    WallFilter wallFilter = new WallFilter();
    wallFilter.setDbType("mysql");
    druidPlugin.addFilter(wallFilter);
    druidPlugin.start();
    return druidPlugin;
  }

//  private FlywayPlugin initFlyway() {
//    FlywayPlugin flywayPlugin = new FlywayPlugin();
//    flywayPlugin.start();
//    return flywayPlugin;
//  }


  private TableBindPlugin initTableBind(DruidPlugin druidPlugin) {
    //Model自动绑定表插件
    TableBindPlugin tableBind = new TableBindPlugin(druidPlugin, SimpleNameStyles.LOWER);
    tableBind.setContainerFactory(new CaseInsensitiveContainerFactory(true)); //忽略字段大小写
    tableBind.addExcludePaths(excludePaths.toArray(new String[excludePaths.size()]));
    tableBind.addIncludePaths(includePaths.toArray(new String[includePaths.size()]));
    tableBind.setShowSql(true);
    tableBind.start();
    return tableBind;
  }

  private SqlInXmlPlugin initSqlinxml() {
    SqlInXmlPlugin sqlInXmlPlugin = new SqlInXmlPlugin();
    sqlInXmlPlugin.start();
    return sqlInXmlPlugin;
  }

  private MailerPlugin initMailer() {
    MailerPlugin mailerPlugin = new MailerPlugin();
    mailerPlugin.start();
    return mailerPlugin;
  }

//  private AkkaPlugin initAkka() {
//    AkkaPlugin akkaPlugin = new AkkaPlugin();
//    akkaPlugin.start();
//    return akkaPlugin;
//  }

  public Connection getConnection() throws SQLException {
    return druidPlugin.getDataSource().getConnection();
  }

  public void shutdown() throws SQLException {
    druidPlugin.stop();
  }

}
