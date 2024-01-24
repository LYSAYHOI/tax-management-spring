package com.hoi.taxmanagement.service;

import com.hoi.taxmanagement.dto.TaxPurchaseResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

public interface TaxQueryService {
    TaxPurchaseResponse purchaseApi(String search, String sort, int size, String authorization);

    ResponseEntity<ByteArrayResource> exportApi(String nbmst, String khhdon, String shdon, String khmshdon, String authorization);
}
