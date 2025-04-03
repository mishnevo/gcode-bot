package com.gcode.transfer.adapter;

import com.gcode.transfer.app.FileHandler;
import com.gcode.transfer.app.SvgDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BackOutbound {

    private final RestTemplate restTemplate;
    private final BackConfig backConfig;

    public String sendSimpleCode(String string) {
        try {
            HttpEntity<String> request = new HttpEntity<>(string);
            var answer = restTemplate.postForObject(backConfig.getUrl() + backConfig.getSimpleCode(), request, SvgDto.class).getSvgContent();
            return answer;
        } catch (HttpServerErrorException httpServerErrorException) {
            log.error(httpServerErrorException.getMessage());
            return null;
        }
    }

    public String sendToStand(String kidCode) {
        try {
            HttpEntity<String> request = new HttpEntity<>(kidCode);
            return restTemplate.postForObject(backConfig.getUrl() + backConfig.getToStand(), request, SvgDto.class).getSvgContent();
        } catch (HttpServerErrorException httpServerErrorException) {
            log.error(httpServerErrorException.getMessage());
            return null;
        }
    }
}
