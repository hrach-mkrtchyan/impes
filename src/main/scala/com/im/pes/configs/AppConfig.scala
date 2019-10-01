package com.im.pes.configs

import java.io.File

import com.typesafe.config.{Config, ConfigFactory}

case class AppConfig(
                      jdbc: JDBCConfig,
                      spark: SparkConfig,
                      service: ServiceConfig
                    )

case class JDBCConfig(
                       hostname: String,
                       port: Int,
                       database: String,
                       username: String,
                       password: String
                     )

case class SparkConfig(
                        master: String,
                        appName: String
                      )

case class ServiceConfig(
                          host: String,
                          port: Int
                        )

object AppConfig {
  def loadFromEnvironment(): AppConfig =
    load(ConfigUtil.loadFromEnvironment)

  def load(config: Config): AppConfig = {
    AppConfig(
      jdbc = JDBCConfig(
        hostname = config.getString("jdbc.hostname"),
        port = config.getInt("jdbc.port"),
        database = config.getString("jdbc.database"),
        username = config.getString("jdbc.username"),
        password = config.getString("jdbc.password")
      ),
      spark = SparkConfig(
        master = config.getString("spark.master"),
        appName = config.getString("spark.appName")
      ),
      service = ServiceConfig(
        host = config.getString("service.host"),
        port = config.getInt("service.port")
      )
    )
  }
}

object ConfigUtil {
  def loadFromEnvironment(): Config = {
    ConfigFactory.load("src/main/resources/application.conf")
  }
}
