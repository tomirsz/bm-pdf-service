package com.bonsaimanager.pdfservice.Controller;

import com.bonsaimanager.pdfservice.dto.CreatePdfRequest;
import com.bonsaimanager.pdfservice.service.PdfCreator;
import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@AllArgsConstructor
@RequestMapping("/pdf")
public class PdfController {

    private final PdfCreator pdfCreator;

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<byte[]> createPdf(@RequestBody CreatePdfRequest dto) {
        try {
            byte[] documentBody = pdfCreator.createPdf(dto);
            return new ResponseEntity<>(documentBody, HttpStatus.OK);
        } catch (DocumentException | URISyntaxException | IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
