namespace java com.github.rojanu.service
#@namespace scala com.github.rojanu.service.thriftscala

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
