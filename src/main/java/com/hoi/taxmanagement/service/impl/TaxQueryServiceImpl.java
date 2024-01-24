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
    private final RestTemplateUtil restTemplateUtil;

    @Override
    public TaxPurchaseResponse purchaseApi(String search, String sort, int size, String authorization) {
        Map<String, String> param = new HashMap<>();
        param.put("search", search);
        param.put("sort", sort);
        param.put("size", String.valueOf(size));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, authorization);
        return restTemplateUtil.get(TAX_URL + TAX_PURCHASE, param, headers, TaxPurchaseResponse.class).getBody();
    }

    @Override
    public ResponseEntity<ByteArrayResource> exportApi(String nbmst, String khhdon, String shdon, String khmshdon, String authorization) {
        Map<String, String> param = new HashMap<>();
        param.put("nbmst", nbmst);
        param.put("khhdon", khhdon);
        param.put("shdon", shdon);
        param.put("khmshdon", khmshdon);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, authorization);
        return restTemplateUtil.getFile(TAX_URL + TAX_EXPORT_XML, param, headers);
    }
}
