package com.github.rojanu.client

import com.github.rojanu.config.client.ClientConfig
import com.github.rojanu.service.thriftscala.BasicFinagleService
import com.twitter.util.Future

abstract class AbstractClientFactory[T <: BasicFinagleService[Future]](config: ClientConfig, clazz: Class[T]){
  val factory = new AbstractClient[T](config, clazz) {
  }

  def getCloseableClient: CloseableClient[T] = {
    getCloseableClient(getClass.getSimpleName)
  }

  def getCloseableClient(name: String): CloseableClient[T] = {
    factory.getCloseableClient(name)
  }
}
