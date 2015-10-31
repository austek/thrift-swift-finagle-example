package com.example.contact.server

import com.example.contact.server.config.AppConfig
import com.example.contact.server.dao.impl.InMemoryRepository
import com.example.server.AbstractFinagleServerWithAdminInterface

class ContactManager(override val config: AppConfig) extends AbstractFinagleServerWithAdminInterface(config) {
  val service = new ContactServiceImpl(new InMemoryRepository)

  def main() {
    start(service)
  }
}
