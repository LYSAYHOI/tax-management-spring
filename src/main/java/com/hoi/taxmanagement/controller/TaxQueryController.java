package com.hoi.taxmanagement.controller;

import com.hoi.taxmanagement.dto.TaxPurchaseResponse;
import com.hoi.taxmanagement.service.TaxQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/query")
@RequiredArgsConstructor
public class TaxQueryController {

    private final TaxQueryService taxQueryService;

    @GetMapping("/purchase")
    public TaxPurchaseResponse purchaseApi(String sort, int size, String search) {
        return taxQueryService.purchaseApi(search, sort, size);
    }

    @GetMapping("/export")
    public ResponseEntity<ByteArrayResource> downloadFile(String nbmst, String khhdon, String shdon, String khmshdon) {
        return taxQueryService.exportApi(nbmst, khhdon, shdon, khmshdon);
    }
}
