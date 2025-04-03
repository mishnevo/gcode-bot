package com.gcode.transfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = "com.gcode.transfer")
@EnableAsync(proxyTargetClass = true)
public class GcodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(GcodeApplication.class, args);
    }

}
