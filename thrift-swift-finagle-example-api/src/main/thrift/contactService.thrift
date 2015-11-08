namespace java com.github.rojanu.contact.api.thiftjava
#@namespace java com.github.rojanu.contact.scala.api.thriftscala


include "basicSservice.thrift"

struct ContactRequest {
  1:  string name;
  2:  string surname;
  3:  string number;
  4:  string email;
  5:  i64 dob;
}

struct Contact {
  1:  string id;
  2:  string name;
  3:  string surname;
  4:  string number;
  5:  string email;
  6:  i64 dob;
}

exception ContactNotFoundException {
}

exception DaoException {
}

service ContactService extends basicSservice.BasicFinagleService {
  Contact create(1:  ContactRequest arg0);
  string delete(1:  string arg0) throws (1: ContactNotFoundException ex1);
  Contact get(1:  string arg0) throws (1: ContactNotFoundException ex1);
  list<Contact> getAll() throws (1: ContactNotFoundException ex1);
  Contact update(1:  string arg0, 2:  ContactRequest arg1) throws (1: ContactNotFoundException ex1);
}
