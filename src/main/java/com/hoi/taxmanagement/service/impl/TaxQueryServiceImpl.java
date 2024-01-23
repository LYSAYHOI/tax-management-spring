package com.hoi.taxmanagement.service.impl;

import com.hoi.taxmanagement.dto.TaxPurchaseResponse;
import com.hoi.taxmanagement.service.TaxQueryService;
import com.hoi.taxmanagement.util.RestTemplateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TaxQueryServiceImpl implements TaxQueryService {


    private final static String TAX_URL = "https://hoadondientu.gdt.gov.vn:30000/query/invoices";
    private final static String TAX_PURCHASE = "/purchase";
    private final static String TAX_EXPORT_XML = "/export-xml";
    private final static String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMzA5OTE1NTMxIiwidHlwZSI6MiwiZXhwIjoxNzA2MTE0MzczLCJpYXQiOjE3MDYwMjc5NzN9.NYSDiRYGnLiJD-cKCnVIDc9XSvGOro0fHVg92W-JOsEqbsNyAYVAg5I28ddftc3qVK2qjbjqntUWMxYCKvcIJA";

    private final RestTemplateUtil restTemplateUtil;

    @Override
    public TaxPurchaseResponse purchaseApi(String search, String sort, int size) {
        Map<String, String> param = new HashMap<>();
        param.put("search", search);
        param.put("sort", sort);
        param.put("size", String.valueOf(size));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + TOKEN);
        return restTemplateUtil.get(TAX_URL + TAX_PURCHASE, param, headers, TaxPurchaseResponse.class).getBody();
    }

    @Override
    public ResponseEntity<ByteArrayResource> exportApi(String nbmst, String khhdon, String shdon, String khmshdon) {
        Map<String, String> param = new HashMap<>();
        param.put("nbmst", nbmst);
        param.put("khhdon", khhdon);
        param.put("shdon", shdon);
        param.put("khmshdon", khmshdon);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + TOKEN);
        return restTemplateUtil.getFile(TAX_URL + TAX_EXPORT_XML, param, headers);
    }
}
