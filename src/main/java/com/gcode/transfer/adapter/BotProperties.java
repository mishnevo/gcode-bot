package com.gcode.transfer.adapter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@ConfigurationProperties("bot")
@Configuration
public class BotProperties {
    private String name;
    @Getter
    private String token;
    @Getter
    private String baseUrl;
    @Getter
    private String mediaUrl;

    public String name() {
        return name;
    }

    public String token() {
        return token;
    }


}