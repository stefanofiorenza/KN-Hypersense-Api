package com.knits.coreplatform.util;

import com.knits.coreplatform.domain.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
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
    static String[] HEADERs = {
        "Name",
        "Serial Number",
        "Device Group",
        "Telemetry",
        "Device Configuration",
        "Device Model",
        "Status",
        "Rules",
        "Alert Messages",
        "Metadata",
        "Supplier",
    };
    static String SHEET = "Device";

    public static ByteArrayInputStream devicesToExcel(List<Device> devices) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);
            int colWidth = 16;
            sheet.setDefaultColumnWidth(colWidth);

            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (Device device : devices) {
                Row row = sheet.createRow(rowIdx++);
                int column = 0;
                row.createCell(column++).setCellValue(device.getName());
                row.createCell(column++).setCellValue(device.getSerialNumber());
                row
                    .createCell(column++)
                    .setCellValue(device.getDeviceGroup() == null ? " " : extractData(device.getDeviceGroup().getName()));
                row.createCell(column++).setCellValue(device.getTelemetry().getName());
                row.createCell(column++).setCellValue(device.getDeviceConfiguration() == null);
                row.createCell(column++).setCellValue(device.getDeviceModel() == null);

                // Deal with the statuses
                Set<Status> statuses = device.getStatuses();
                List<String> statusNames = new ArrayList<>();
                statuses.forEach(x -> statusNames.add(x.getName()));
                row.createCell(column++).setCellValue(extractData(device.getStatuses()));

                // Deal with the rules
                Set<Rule> rules = device.getRules();
                List<String> ruleNames = rules.stream().map(Rule::getName).collect(Collectors.toList());
                row.createCell(column++).setCellValue(extractData(ruleNames));

                // Deal with the alertMessages
                Set<AlertMessage> alertMessages = device.getAlertMessages();
                if (alertMessages != null) {
                    List<String> alertMessageNames = alertMessages.stream().map(AlertMessage::getName).collect(Collectors.toList());
                    row.createCell(column++).setCellValue(extractData(alertMessageNames));
                } else {
                    row.createCell(column++).setCellValue(extractData(" "));
                }

                // Deal with the metadata
                Set<Metadata> metaData = device.getMetaData();
                if (metaData != null) {
                    List<String> metaDataNames = metaData.stream().map(Metadata::getName).collect(Collectors.toList());
                    row.createCell(column++).setCellValue(extractData(metaDataNames));
                } else {
                    row.createCell(column++).setCellValue(extractData(" "));
                }

                row.createCell(column++).setCellValue(device.getSupplier() == null);
            }

            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Fail to import data to Excel file:" + e.getMessage());
        }
    }

    private static <T> String extractData(T input) {
        if (((Collection<?>) input).isEmpty()) {
            return " ";
        } else {
            StringBuilder result =
                ((Collection<?>) input).stream()
                    .collect(StringBuilder::new, (a, b) -> a.append(b).append(","), StringBuilder::append)
                    .substring(0, result.length());
        }
    }

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
}
