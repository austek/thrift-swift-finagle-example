package com.github.rojanu.server

import java.io.{BufferedOutputStream, File, FileNotFoundException, FileOutputStream}
import java.net.InetSocketAddress

import com.github.rojanu.server.config.FinagleServerConfig
import com.twitter.finagle.tracing.Tracer
import com.twitter.finagle.zipkin.thrift.ZipkinTracer
import com.twitter.finagle.{ListeningServer, Thrift, param}
import com.twitter.io.StreamIO
import com.twitter.server.TwitterServer
import com.twitter.server.admin.AppHealthCheck
import com.twitter.util.Await

class AbstractFinagleServerWithAdminInterface(val config: FinagleServerConfig) extends TwitterServer with AppHealthCheck {
  val MAX_CONCURRENT_REQUESTS = 100
  val CLASSPATH_PREFIX = "classpath:"
  protected var server: ListeningServer = null

  def appHealth: Map[String, Object] = Map("service" -> "ok!")


  def start(swiftService: Object): Unit = {
    val tracer: Tracer = config.tracingConfig match {
      case tc if tc != null => ZipkinTracer.mk(config.tracingConfig.server, config.tracingConfig.port, statsReceiver, config.tracingConfig.sampleRate)
      case _ => null
    }
    val addr = new InetSocketAddress(config.serverConfig.port)

    if (config.serverConfig.sslConfig != null) {
      var certificateFilePath = config.relativize(config.serverConfig.sslConfig.certificateFile).getExpandedPath
      var keyFilePath = config.relativize(config.serverConfig.sslConfig.keyFile).getExpandedPath

      if (certificateFilePath.startsWith(CLASSPATH_PREFIX)) {
        certificateFilePath = fromResourcePath(certificateFilePath.substring(CLASSPATH_PREFIX.length)).getAbsolutePath
      }
      if (keyFilePath.startsWith(CLASSPATH_PREFIX)) {
        keyFilePath = fromResourcePath(keyFilePath.substring(CLASSPATH_PREFIX.length)).getAbsolutePath
      }
    }

    val serverName = config.serverConfig.name

    var thriftServer = Thrift.server
      .configured(param.Label(serverName))
    if (tracer != null) {
      thriftServer = thriftServer.configured(param.Tracer(tracer))
    }
    server = thriftServer.serveIface(addr, swiftService)
    onExit {
      server.close()
    }
    Await.ready(server)
  }

  def fromResourcePath(path: String): File = {
    fromResourcePath(Thread.currentThread.getContextClassLoader, path)
  }

  def fromResourcePath(classLoader: ClassLoader, path: String): File = {
    val (basename, ext) = {
      val last = path.split("\\/").last
      last.split('.').reverse match {
        case Array(basename) =>
          (basename, "")
        case Array(ext, base@_*) =>
          (base.reverse.mkString("."), ext)
      }
    }

    classLoader.getResourceAsStream(path) match {
      case null =>
        throw new FileNotFoundException(path)
      case stream =>
        val file = File.createTempFile(basename, "." + ext)
        file.deleteOnExit()
        val fos = new BufferedOutputStream(new FileOutputStream(file), 1 << 20)
        StreamIO.copy(stream, fos)
        fos.flush()
        fos.close()
        stream.close()
        file
    }
  }

  def getServer: ListeningServer = {
    server
  }
}
