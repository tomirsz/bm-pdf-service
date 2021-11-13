package com.bonsaimanager.pdfservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfRequest {

    private long id;
    private String latinName;
    private String name;
}
