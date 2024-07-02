package kyungmin.tcpip.project.client;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@WebServlet(name = "hello" , urlPatterns = "/glgl")
@Slf4j
public class ServletPractice extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      log.info("request = {}" , request);
      log.info("response = {}" , response);
  }
}
