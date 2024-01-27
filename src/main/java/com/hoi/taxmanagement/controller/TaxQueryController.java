package com.hoi.taxmanagement.controller;

import com.hoi.taxmanagement.dto.TaxPurchaseResponse;
import com.hoi.taxmanagement.service.TaxQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/query")
@RequiredArgsConstructor
public class TaxQueryController {

    private final TaxQueryService taxQueryService;

    @GetMapping("/purchase")
    public TaxPurchaseResponse purchaseApi(String sort, int size, String search, String state,
                                           @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        return taxQueryService.purchaseApi(search, sort, size, state, authorization);
    }

    @GetMapping("/export")
    public ResponseEntity<ByteArrayResource> downloadFile(String nbmst, String khhdon, String shdon, String khmshdon,
                                                          @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        return taxQueryService.exportApi(nbmst, khhdon, shdon, khmshdon, authorization);
    }
}
