package com.hoi.taxmanagement.controller;

import com.hoi.taxmanagement.service.ExcelMergeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/excel-merge")
@RequiredArgsConstructor
public class ExcelMergeController {

    private final ExcelMergeService excelMergeService;

    @PostMapping
    public ResponseEntity<ByteArrayResource> uploadMultipleFiles(@RequestPart("files") List<MultipartFile> files) {
        ByteArrayResource mergedFileSource = excelMergeService.excelMerge(files);
        // You can perform further processing and return an appropriate response
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=mergedFile.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(mergedFileSource.contentLength())
                .body(mergedFileSource);
    }
}
