namespace java com.github.rojanu.service.thiftjava
#@namespace scala com.github.rojanu.service.thriftscala

exception ThriftException {
  1:  string code;
  2:  string msg;
  string getCode();
  string getMsg();
}

exception ThriftServiceException {
  1:  string code;
  2:  string msg;
  string getCode();
  string getMsg();
}


service BasicFinagleService {
  string getBuildInfo() throws (1: ThriftServiceException ex1);
  string getName();
  string getVersion();
}
