package md.usm.laborator2;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class APoiBNM {
    public static void main(String[] args) throws Exception {
        createWorkBook();
        List<ValCurs> valCurs = new ArrayList<>();
        List<String> listOfDates = ReadCsvFile.parserCSV();

        for (String str : listOfDates) {
            valCurs.add(BNMGet.sendGet(str));
        }

        for (ValCurs temp : valCurs) {
            openWorkBook(temp);
        }
    }

    private static void createWorkBook() throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        FileOutputStream out = new FileOutputStream("src/main/resources/ExcelValutes.xlsx");

        workbook.write(out);
        out.close();
    }


    private static void openWorkBook(ValCurs valCurs) throws Exception {
        File file = new File("ExcelValutes.xlsx");
        FileInputStream fIP = new FileInputStream(file);

        HSSFWorkbook workbook = new HSSFWorkbook(fIP);
        HSSFSheet spreadsheet = workbook.createSheet(valCurs.getDate());

        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        HSSFRow row;

        Map<String, Object[]> valutes = new TreeMap<>();

        valutes.put("*", new Object[]{"Name", "Date"});

        valutes.put("+", new Object[]{valCurs.getName(), valCurs.getDate()});

        valutes.put("/", new Object[]{"ID", "NumCode", "CharCode", "Nominal",
                "Name", "Value"});

        for (Valute valute : valCurs.getList()) {
            valutes.put(valute.getId(), new Object[]{
                    valute.getId(),
                    valute.getNumCode(),
                    valute.getСharCode(),
                    valute.getNominal() + "",
                    valute.getName(),
                    Double.toString(valute.getValue())});
        }

        Set<String> keyid = valutes.keySet();
        int rowid = 0;

        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);

            Object[] objectArr = valutes.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                spreadsheet.autoSizeColumn(cellid);

                if (rowid == 1 || rowid == 3) {
                    cell.setCellStyle(style);
                }
                cell.setCellValue((String) obj);
            }
        }

        FileOutputStream out = new FileOutputStream("ExcelValutes.xlsx");
        workbook.write(out);
        out.close();
        System.out.println("Excel file written successfully");
    }
}
