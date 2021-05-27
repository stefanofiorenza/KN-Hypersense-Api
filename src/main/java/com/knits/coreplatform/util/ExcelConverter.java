package com.knits.coreplatform.util;

import com.knits.coreplatform.domain.Device;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * Helper class that converts data from Excel to Device object and vice versa.
 *
 * @author Vassili Moskaljov
 * @version 1.0
 */
public class ExcelConverter {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "NR", "", "", "", "", "", "", "" };
    static String SHEET = "Device";

    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<Device> excelToDevices(InputStream inputStream) {
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<Device> devices = new ArrayList<>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                //ignore header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Device device = new Device();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            device.setName(currentCell.getStringCellValue());
                            break;
                        //Add all required fields for device to be set.
                        default:
                            break;
                    }

                    cellIdx++;
                }
                devices.add(device);
            }
            workbook.close();

            return devices;
        } catch (IOException e) {
            throw new RuntimeException("Fail to parse Excel file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream devicesToExcel(List<Device> devices) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            //Create header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (Device device : devices) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue("#" + rowIdx);
                row.createCell(1).setCellValue(device.getName());
                row.createCell(2).setCellValue(device.getSerialNumber());
                row.createCell(3).setCellValue(device.getDeviceGroup().getName());
                row.createCell(4).setCellValue(device.getTelemetry().getName());
                row.createCell(5).setCellValue(device.getDeviceConfiguration().getName());
                row.createCell(6).setCellValue(device.getDeviceModel().getId());
                row.createCell(7).setCellValue(device.getStatuses().hashCode());
                row.createCell(8).setCellValue("R:" + device.getRules().hashCode());
                row.createCell(9).setCellValue("A:" + device.getAlertMessages().hashCode());
                row.createCell(10).setCellValue("M:" + device.getMetaData().hashCode());
                row.createCell(11).setCellValue(device.getSupplier().getName());
                // add all necessary fields.
            }

            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Fail to import data to Excel file:" + e.getMessage());
        }
    }
}
