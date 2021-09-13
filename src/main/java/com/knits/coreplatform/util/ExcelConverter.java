package com.knits.coreplatform.util;

import com.knits.coreplatform.domain.*;
import com.knits.coreplatform.repository.*;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Helper class that converts data from Excel to Devices and adds them to the database or takes data
 * from the database and makes an Excel file. Have method to check if current input is an Excel file.
 *
 * @author Vassili Moskaljov
 * @version 1.0
 */
@Component
public class ExcelConverter {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERS = {
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
    private static DeviceGroupRepository deviceGroupRepository;
    private static SupplierRepository supplierRepository;
    private static TelemetryRepository telemetryRepository;
    private static StatusRepository statusRepository;
    private static RuleRepository ruleRepository;
    private static DeviceModelRepository deviceModelRepository;
    private static MetadataRepository metadataRepository;
    private static DeviceConfigurationRepository deviceConfigurationRepository;
    private static AlertMessageRepository alertMessageRepository;
    private static final String UNDEFINED = "Undefined";

    @Autowired
    public ExcelConverter(
        DeviceGroupRepository deviceGroup,
        SupplierRepository supplierRepository,
        TelemetryRepository telemetryRepository,
        StatusRepository statusRepository,
        RuleRepository ruleRepository,
        DeviceModelRepository deviceModelRepository,
        MetadataRepository metadataRepository,
        DeviceConfigurationRepository deviceConfigurationRepository,
        AlertMessageRepository alertMessageRepository
    ) {
        ExcelConverter.deviceGroupRepository = deviceGroup;
        ExcelConverter.supplierRepository = supplierRepository;
        ExcelConverter.telemetryRepository = telemetryRepository;
        ExcelConverter.statusRepository = statusRepository;
        ExcelConverter.ruleRepository = ruleRepository;
        ExcelConverter.deviceModelRepository = deviceModelRepository;
        ExcelConverter.metadataRepository = metadataRepository;
        ExcelConverter.deviceConfigurationRepository = deviceConfigurationRepository;
        ExcelConverter.alertMessageRepository = alertMessageRepository;
    }

    /**
     * Method that takes collection of Device and makes an Excel file.
     *
     * @param devices collection of devices to be exported.
     * @return Excel file with collection of devices.
     */
    public static ByteArrayInputStream devicesToExcel(List<Device> devices) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            int colWidth = 16;
            Sheet sheet = workbook.createSheet(SHEET);
            sheet.setDefaultColumnWidth(colWidth);

            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERS.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERS[col]);
            }

            int rowIdx = 1;
            for (Device device : devices) {
                int column = 0;
                Row row = sheet.createRow(rowIdx++);
                row.createCell(column++).setCellValue(device.getName());
                row.createCell(column++).setCellValue(device.getSerialNumber());
                row.createCell(column++).setCellValue(device.getDeviceGroup() == null ? UNDEFINED : device.getDeviceGroup().getName());
                row.createCell(column++).setCellValue(device.getTelemetry() == null ? UNDEFINED : device.getTelemetry().getName());
                row
                    .createCell(column++)
                    .setCellValue(device.getDeviceConfiguration() == null ? UNDEFINED : device.getDeviceConfiguration().getName());
                row.createCell(column++).setCellValue(device.getDeviceModel() == null ? UNDEFINED : device.getDeviceModel().getName());

                Set<Status> statuses = device.getStatuses();
                if (statuses != null) {
                    List<String> statusNames = statuses.stream().map(Status::getName).collect(Collectors.toList());
                    row.createCell(column++).setCellValue(convertToString(statusNames));
                } else {
                    row.createCell(column++).setCellValue(convertToString("Statues are undefined"));
                }

                Set<Rule> rules = device.getRules();
                if (rules != null) {
                    List<String> ruleNames = rules.stream().map(Rule::getName).collect(Collectors.toList());
                    row.createCell(column++).setCellValue(convertToString(ruleNames));
                } else {
                    row.createCell(column++).setCellValue(convertToString("Rules are undefined"));
                }

                Set<AlertMessage> alertMessages = device.getAlertMessages();
                if (alertMessages != null) {
                    List<String> alertMessageNames = alertMessages.stream().map(AlertMessage::getName).collect(Collectors.toList());
                    row.createCell(column++).setCellValue(convertToString(alertMessageNames));
                } else {
                    row.createCell(column++).setCellValue(convertToString("AlertMessages are undefined"));
                }

                Set<Metadata> metaData = device.getMetaData();
                if (metaData != null) {
                    List<String> metaDataNames = metaData.stream().map(Metadata::getName).collect(Collectors.toList());
                    row.createCell(column++).setCellValue(convertToString(metaDataNames));
                } else {
                    row.createCell(column++).setCellValue(convertToString("Metadata is undefined"));
                }
                row
                    .createCell(column)
                    .setCellValue(device.getSupplier() == null ? "Supplier is undefined" : device.getSupplier().getName());
            }

            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Fail to import data to Excel file:" + e.getMessage());
        }
    }

    /**
     * Takes an input and makes sole String of the contents.
     *
     * @param input this could be String or collection of Strings that will be converted to a sole String.
     * @return Sole String from input.
     */
    private static <T> String convertToString(T input) {
        if (((Collection<?>) input).isEmpty()) {
            return UNDEFINED;
        } else {
            StringBuilder result =
                ((Collection<?>) input).stream().collect(StringBuilder::new, (a, b) -> a.append(b).append(","), StringBuilder::append);
            return result.toString().endsWith(",") ? result.substring(0, result.length() - 1) : result.substring(0, result.length());
        }
    }

    /**
     * Check if specified file is an Excel.
     *
     * @param file that's to be checked if it's an Excel file.
     * @return true if file is an Excel.
     */
    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    /**
     * Method takes Excel file as an input and inserts data to the LIST.
     *
     * @param inputStream Excel file with the devices to be extracted.
     * @return list of devices from Excel file.
     */
    public static List<Device> excelToDevices(InputStream inputStream) {
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<Device> devices = new ArrayList<>();
            int rowNumber = 0;
            rowLoop:while (rows.hasNext()) {
                Row currentRow = rows.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Device device = new Device();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    String cellString = currentCell.getStringCellValue();
                    switch (cellIdx) {
                        case 0:
                            if (nullCheck(cellString)) {
                                break rowLoop;
                            } else {
                                device.setName(cellString);
                            }
                            break;
                        case 1:
                            device.setSerialNumber(nullCheck(cellString) ? "" : cellString);
                            break;
                        case 2:
                            if (!nullCheck(cellString)) {
                                Supplier currentSupplier = supplierRepository.findByName(cellString);
                                device.setSupplier(currentSupplier);
                            }
                            break;
                        case 3:
                            if (!nullCheck(cellString)) {
                                DeviceModel deviceModel = deviceModelRepository.findByName(cellString);
                                device.setDeviceModel(deviceModel);
                            }
                            break;
                        case 4:
                            device.setManufacturer(nullCheck(cellString) ? UNDEFINED : cellString);
                            break;
                        case 5:
                            if (!nullCheck(cellString)) {
                                DeviceGroup currentDeviceGroup = deviceGroupRepository.findByName(cellString);
                                device.setDeviceGroup(currentDeviceGroup);
                            }
                            break;
                        case 6:
                            if (!nullCheck(cellString)) {
                                DeviceConfiguration deviceConfiguration = deviceConfigurationRepository.findByName(cellString);
                                if (deviceConfiguration != null) {
                                    device.setDeviceConfiguration(deviceConfiguration);
                                } else {
                                    device.setTelemetry(null);
                                }
                            }
                            break;
                        case 7:
                            if (!nullCheck(cellString)) {
                                Telemetry telemetry = telemetryRepository.findByName(cellString);
                                device.setTelemetry(telemetry);
                            }
                            break;
                        case 8:
                            if (nullCheck(cellString)) {
                                device.setStatuses(Collections.EMPTY_SET);
                            } else {
                                Set<Status> statuses = new HashSet<>();
                                String[] statusList = cellString.split(",");
                                for (String name : statusList) {
                                    Status statusByName = statusRepository.findByName(name.trim());
                                    if (statusByName != null) {
                                        statuses.add(statusByName);
                                    }
                                }
                                device.setStatuses(statuses);
                            }
                            break;
                        case 9:
                            if (nullCheck(cellString)) {
                                device.setAlertMessages(Collections.EMPTY_SET);
                            } else {
                                Set<AlertMessage> alertMessages = new HashSet<>();
                                String[] alertMessageList = cellString.split(",");
                                for (String name : alertMessageList) {
                                    AlertMessage alertMessageByName = alertMessageRepository.findByName(name);
                                    if (alertMessageByName != null) {
                                        alertMessages.add(alertMessageByName);
                                    }
                                }
                                device.setAlertMessages(alertMessages);
                            }
                            break;
                        case 10:
                            if (nullCheck(cellString)) {
                                device.setRules(Collections.EMPTY_SET);
                            } else {
                                Set<Rule> rules = new HashSet<>();
                                String[] ruleList = cellString.split(",");
                                for (String name : ruleList) {
                                    Rule ruleByName = ruleRepository.findByName(name);
                                    if (ruleByName != null) {
                                        rules.add(ruleByName);
                                    }
                                }
                                device.setRules(rules);
                            }
                            break;
                        case 11:
                            if (nullCheck(cellString)) {
                                device.setMetaData(Collections.EMPTY_SET);
                            } else {
                                Set<Metadata> metadata = new HashSet<>();
                                String[] metadataList = cellString.split(",");
                                for (String name : metadataList) {
                                    Metadata metadataByName = metadataRepository.findByName(name);
                                    if (metadataByName != null) {
                                        metadata.add(metadataByName);
                                    }
                                }
                                device.setMetaData(metadata);
                            }
                            break;
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
            throw new RuntimeException("Failed to parse the Excel file: " + e.getMessage());
        }
    }

    /**
     * Check for null or emptiness of the specified String.
     *
     * @param cellString specified String for checking.
     * @return true is String is null or empty after trim() method.
     */
    private static boolean nullCheck(String cellString) {
        return cellString == null || cellString.trim().isEmpty();
    }
}
