package com.example.pdfmysql.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class PdfController {

    @Autowired
    private PdfGenerationService pdfGenerationService;

    @GetMapping("/generate-pdf")
    public String generatePdf() {
        String filename = "demo.pdf";
        pdfGenerationService.generatePdf(filename);
        return "PDF generated successfully! <br><a href=\"/download-pdf\">Download PDF</a>";
    }

    @GetMapping("/download-pdf")
    public ResponseEntity<Resource> downloadPdf() {
        try {
            Path pdfPath = Paths.get("C:/Users/Administrator/Downloads/demo (11)/demo.pdf");
            Resource resource = new UrlResource(pdfPath.toUri());

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            e.printStackTrace(); // Log the exception for debugging
            return ResponseEntity.notFound().build(); // Return 404 Not Found response
        }
    }
}
