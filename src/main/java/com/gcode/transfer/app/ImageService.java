package com.gcode.transfer.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImageService {
    private final FileHandler fileHandler;
    public File convertSvgStringToPng(String svgContent) throws Exception {
        PNGTranscoder transcoder = new PNGTranscoder();
        var pngFile = fileHandler.createFile(new byte[]{}).get();
        try (InputStream in = new ByteArrayInputStream(svgContent.getBytes());
             OutputStream out = new FileOutputStream(pngFile)) {
            TranscoderInput input = new TranscoderInput(in);
            TranscoderOutput output = new TranscoderOutput(out);
            transcoder.transcode(input, output);
        }
        return pngFile;
    }
}
