package com.hoi.taxmanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class TaxPurchaseResponse {

    private List<PurchaseData> datas;
    private String state;
    private int time;
    private int total;
}
