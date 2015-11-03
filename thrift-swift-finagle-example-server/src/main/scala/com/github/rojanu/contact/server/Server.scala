package com.github.rojanu.contact.server

import com.github.rojanu.config.Config
import com.github.rojanu.contact.server.config.AppConfig


object Server {
  final def main(args: Array[String]) {
    if (args.length < 1) {
      System.err.println("path to config folder required.")
      System.exit(-10)
    }
    run(args(0), parallel = true)
  }

  def runEmbedded(configPath: String): ContactManager = {
    run(configPath, parallel = true)
  }

  def runEmbedded(config: AppConfig): ContactManager = {
    run(config, parallel = true)
  }

  def run(configPath: String, parallel: Boolean): ContactManager = {
    val config: AppConfig = Config.load(classOf[AppConfig], configPath)
    run(config, parallel)
  }

  def run(config: AppConfig, parallel: Boolean): ContactManager = {
    val server = new ContactManager(config)
    if (parallel) {
      new Thread() {
        override def run(): Unit = {
          server.main(Array("-admin.port=:"+ config.serverConfig.adminPort))
        }
      }.start()
    } else {
      server.main(Array("-admin.port=:"+ config.serverConfig.adminPort))
    }
    server
  }
}
