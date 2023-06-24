package hcmuaf.edu.vn.bookingtravel.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
@EnableScheduling
public class BookingTravelApiApplication extends SpringBootServletInitializer {
    @Value("${server.port}")
    private String serverPort;

    public static void main(String[] args) {
        SpringApplication.run(BookingTravelApiApplication.class, args);
    }

   @EventListener({ApplicationReadyEvent.class})
   public void applicationReadyEvent() {
       String url = "http://localhost:" + serverPort + "/booking-travel/swagger-ui/#/";
       if (Desktop.isDesktopSupported()) {
           Desktop desktop = Desktop.getDesktop();
           try {
               desktop.browse(new URI(url));
           } catch (IOException | URISyntaxException e) {
               e.printStackTrace();
           }
       } else {
           Runtime runtime = Runtime.getRuntime();
           String[] command;

           String operatingSystemName = System.getProperty("os.name").toLowerCase();
           if (operatingSystemName.contains("nix") || operatingSystemName.contains("nux")) {
               String[] browsers = {"opera", "google-chrome", "epiphany", "firefox", "mozilla", "konqueror", "netscape", "links", "lynx"};
               StringBuilder stringBuffer = new StringBuilder();

               for (int i = 0; i < browsers.length; i++) {
                   if (i == 0) stringBuffer.append(String.format("%s \"%s\"", browsers[i], url));
                   else stringBuffer.append(String.format(" || %s \"%s\"", browsers[i], url));
               }
               command = new String[]{"sh", "-c", stringBuffer.toString()};
           } else if (operatingSystemName.contains("win")) {
               command = new String[]{"rundll32 url.dll,FileProtocolHandler " + url};

           } else if (operatingSystemName.contains("mac")) {
               command = new String[]{"open " + url};
           } else {
               System.out.println("an unknown operating system!!");
               return;
           }

           try {
               if (command.length > 1) runtime.exec(command); // linux
               else runtime.exec(command[0]); // windows or mac
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }
}
