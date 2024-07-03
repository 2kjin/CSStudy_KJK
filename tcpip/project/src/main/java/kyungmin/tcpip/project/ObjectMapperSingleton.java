package kyungmin.tcpip.project;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperSingleton {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static ObjectMapper getInstance(){
    return objectMapper;
  }


}
