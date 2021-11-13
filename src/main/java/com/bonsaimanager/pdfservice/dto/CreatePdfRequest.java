package com.bonsaimanager.pdfservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreatePdfRequest {

    private List<PdfRequest> data;
}
