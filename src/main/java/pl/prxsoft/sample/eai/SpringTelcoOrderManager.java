package pl.prxsoft.sample.eai;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTelcoOrderManager {
    public static void main(String[] args) {
      System.out.println("args = [" + args + "]");
      ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
      HelloService service = context.getBean(HelloService.class);

      System.out.printf("Printing %s", service.sayHello());
    }
}
