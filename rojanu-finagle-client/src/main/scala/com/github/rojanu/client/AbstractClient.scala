package com.github.rojanu.client

import com.github.rojanu.config.client.ClientConfig
import com.github.rojanu.service.BasicFinagleService
import com.twitter.finagle.stats.{InMemoryStatsReceiver, StatsReceiver}
import com.twitter.finagle.thrift.ThriftClientRequest
import com.twitter.finagle.tracing.Tracer
import com.twitter.finagle.zipkin.thrift.ZipkinTracer
import com.twitter.finagle.{Service, Thrift, param}

class AbstractClient[T <: BasicFinagleService](config: ClientConfig, clazz: Class[T]) {
  private var closeableClient: CloseableClient[T] = null
  val statsReceiver: StatsReceiver = new InMemoryStatsReceiver

  def getCloseableClient: CloseableClient[T] = {
    getCloseableClient(getClass.getSimpleName)
  }

  def getCloseableClient(name: String): CloseableClient[T] = {
    if (closeableClient != null) {
      return closeableClient
    }
    val tracer: Tracer = config.tracingConfig match {
      case tc if tc != null => ZipkinTracer.mk(
        config.tracingConfig.server,
        config.tracingConfig.port,
        statsReceiver,
        config.tracingConfig.sampleRate)
      case _ => null
    }
    try {
      var thriftClient: Thrift.Client = Thrift.client
      if (tracer != null) {
        thriftClient = thriftClient.configured(param.Tracer(tracer))
      }

      val thriftClientRequestService: Service[ThriftClientRequest, Array[Byte]] =
        thriftClient.newClient(config.serverConfig.hosts, name).toService

      val contactService: T = thriftClient.newIface(config.serverConfig.hosts, name, clazz)
      closeableClient = new CloseableClient(thriftClientRequestService, contactService)
      closeableClient
    } catch {
      case ex: Exception => {
        ex.printStackTrace()
        throw new Exception("error in creating the transport", ex)
      }
    }
  }
}
