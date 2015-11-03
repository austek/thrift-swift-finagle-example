package com.twitter.server.admin

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.rojanu.server.AbstractFinagleServerWithAdminInterface
import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import com.twitter.io.Buf
import com.twitter.server.Admin
import com.twitter.server.AdminHttpServer.Route
import com.twitter.server.util.HttpUtils._
import com.twitter.util.Future

import scala.collection.JavaConversions

trait AppHealthCheck extends Admin { self: AbstractFinagleServerWithAdminInterface =>
  val objectMapper = new ObjectMapper()
  implicit class ObjectWithJson(val o: Object) {
    def toJson = objectMapper.writeValueAsString(o)
  }
  override protected def routes: Seq[Route] = {
    super.routes ++ Seq(
      Route(
        path = "/admin/app-health", handler = new Service[Request, Response] {
          override def apply(request: Request): Future[Response] = {
            try {
              newOk(JavaConversions.mapAsJavaMap(self.appHealth).toJson)
            } catch {
              case ex: Exception => newResponse(
                contentType = "application/json;charset=UTF-8",
                content = Buf.Utf8(ex.getMessage)
              )
            }
          }
        }, alias = "AppHealth", group = Some("App"), includeInIndex = true)
    )
  }
}
