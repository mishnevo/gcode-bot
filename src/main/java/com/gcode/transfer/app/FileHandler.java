package com.gcode.transfer.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
public class FileHandler {

    public Optional<File> createFile(byte[] fileBytes) {
        try {
            File file = File.createTempFile("temp", "png");
            if (file.exists()) {
                file.delete();
            }
            try (FileOutputStream stream = new FileOutputStream(file)) {
                stream.write(fileBytes);
            }
            return Optional.of(file);
        } catch (IOException e) {
            log.error("Failed to create file", e);
        }
        return Optional.empty();
    }
}
