package com.bonsaimanager.pdfservice.service;

import com.bonsaimanager.pdfservice.dto.CreatePdfRequest;
import com.bonsaimanager.pdfservice.dto.PdfRequest;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class PdfCreator {

    public byte[] createPdf(CreatePdfRequest dto) throws DocumentException, URISyntaxException, IOException {

        Document document = new Document();
        document.setPageSize(PageSize.A4);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, output);

        document.open();

        addLogo(document);

        PdfPTable table = new PdfPTable(3);
        addTableHeader(table);
        dto.getData().forEach(data -> addRows(table, data));

        document.add(table);
        document.close();

        return output.toByteArray();
    }

    private void addLogo(Document document) throws URISyntaxException, IOException, DocumentException {
        Path path = Paths.get(ClassLoader.getSystemResource("BMlogo.png").toURI());
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        img.scalePercent(50);
        img.setAlignment(Element.ALIGN_CENTER);
        document.add(img);
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("ID", "LATIN NAME", "NAME")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, PdfRequest data) {
        Font font = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 12);
        Stream.of(String.valueOf(data.getId()), data.getLatinName(), data.getName())
                .forEach(cellValue -> {
                    PdfPCell cell = new PdfPCell(new Phrase(cellValue, font));
                    setCellStyle(table, cell);
                }
        );
    }

    private void setCellStyle(PdfPTable table, PdfPCell cell) {
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(24);
        table.addCell(cell);
    }
}
