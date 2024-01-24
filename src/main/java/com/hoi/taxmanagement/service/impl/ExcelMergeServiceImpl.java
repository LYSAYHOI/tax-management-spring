package com.hoi.taxmanagement.service.impl;

import com.hoi.taxmanagement.service.ExcelMergeService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelMergeServiceImpl implements ExcelMergeService {

    private static final int COMMON_ROW_NUMBER_START = 0;
    private static final int COMMON_ROW_NUMBER_END = 6;

    @Override
    public ByteArrayResource excelMerge(List<MultipartFile> excelFiles) {
        try {
            // Create a new workbook and copy modified data
            Workbook newWorkbook = new XSSFWorkbook();
            Sheet newSheet = newWorkbook.createSheet("New Sheet");
            for (int i = 0; i < excelFiles.size(); i++) {
                copyWorkbookData(newSheet, excelFiles.get(i), i == 0 ? COMMON_ROW_NUMBER_START : COMMON_ROW_NUMBER_END);
            }
            return createWorkbookByte(newWorkbook);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void copyWorkbookData(Sheet newSheet, MultipartFile file, int fromRow) {
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int rowIndex = fromRow; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                Row newRow = newSheet.createRow(newSheet.getLastRowNum() + 1);

                // Skip empty rows
                if (row != null) {
                    Iterator<Cell> cellIterator = row.cellIterator();
                    List<String> rowData = new ArrayList<>();

                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        Cell newCell = newRow.createCell(cell.getColumnIndex(), cell.getCellType());
                        // Copy value and type
                        copyCellValue(cell, newCell);
                        // Copy cell style
                        copyStyle(cell, newCell);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void copyCellValue(Cell cell, Cell newCell) {
        // Copy cell value and style
        switch (cell.getCellType()) {
            case STRING:
                newCell.setCellValue(cell.getStringCellValue());
                break;
            case NUMERIC:
                newCell.setCellValue(cell.getNumericCellValue());
                break;
            case BOOLEAN:
                newCell.setCellValue(cell.getBooleanCellValue());
                break;
            case FORMULA:
                newCell.setCellFormula(cell.getCellFormula());
                break;
            default:
                // Handle other cell types if needed
                break;
        }
    }

    private void copyStyle(Cell cell, Cell newCell) {
        CellStyle origStyle = cell.getCellStyle();
        CellStyle newStyle = newCell.getSheet().getWorkbook().createCellStyle();
        newStyle.cloneStyleFrom(origStyle);
        newCell.setCellStyle(newStyle);
    }

    private ByteArrayResource createWorkbookByte(Workbook workbook) throws IOException {
        // Write the workbook to a ByteArrayOutputStream
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            return new ByteArrayResource(outputStream.toByteArray());
        }
    }
}
