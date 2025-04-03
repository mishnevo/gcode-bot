package com.gcode.transfer.adapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("back")
@Data
public class BackConfig {
    private String url;
    private String simpleCode;
    private String toStand;
}
