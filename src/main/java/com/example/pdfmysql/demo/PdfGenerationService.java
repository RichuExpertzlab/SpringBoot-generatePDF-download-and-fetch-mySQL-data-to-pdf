package com.example.pdfmysql.demo;

import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PdfGenerationService {

    @Autowired
    private YourEntityRepository yourEntityRepository;

    public void generatePdf(String filename) {
        List<YourEntity> dataList = yourEntityRepository.findAll();

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Data from MySQL database:");
                contentStream.newLine();
                contentStream.setFont(PDType1Font.HELVETICA, 12);

                int y = 680; // Initial y-coordinate for the first record

                for (YourEntity entity : dataList) {
                    contentStream.newLineAtOffset(0, -20); // Move down by 20 units for each record
                    contentStream.showText(entity.getId() + ": " + entity.getData()); // Display data
                }

                contentStream.endText();
            }

            document.save(filename);
            System.out.println("PDF generated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
