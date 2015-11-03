package com.github.rojanu.contact.server

import com.github.rojanu.contact.server.config.AppConfig
import com.github.rojanu.contact.server.dao.impl.InMemoryRepository
import com.github.rojanu.server.AbstractFinagleServerWithAdminInterface

class ContactManager(override val config: AppConfig) extends AbstractFinagleServerWithAdminInterface(config) {
  val service = new ContactServiceImpl(new InMemoryRepository)

  def main() {
    start(service)
  }
}
