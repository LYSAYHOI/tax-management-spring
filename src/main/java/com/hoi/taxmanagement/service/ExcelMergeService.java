package com.hoi.taxmanagement.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExcelMergeService {
    ByteArrayResource excelMerge(List<MultipartFile> excelFiles);
}
