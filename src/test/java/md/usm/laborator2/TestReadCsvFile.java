package md.usm.laborator2;

import org.testng.annotations.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestReadCsvFile implements Comparator<LocalDate> {

    @Test
    public void testReadCsvFile(){
        ReadCsvFileForTest readCsvFile = new ReadCsvFileForTest();
        List<String> date = null;
        List<LocalDate> dateFormat = new ArrayList<>();
        List<String> badFormat = new ArrayList<>();
        List<LocalDate> goodDate = new ArrayList<>();
        List<LocalDate> pastDate = new ArrayList<>();
        List<LocalDate> futureDate = new ArrayList<>();
        try {
            date = readCsvFile.parserCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String value : date) {
            try {
                String[] dateInf = value.split("\\.");
                LocalDate year = LocalDate.of(Integer.parseInt(dateInf[2]), Integer.parseInt(dateInf[1]), Integer.parseInt(dateInf[0]));
                dateFormat.add(year);
            } catch (Exception ex) {
                System.err.println("Bad format for date " + value);
                badFormat.add(value);
            }
        }

        for (LocalDate localDate : dateFormat) {
            int result = compare(localDate, LocalDate.of(1994, 1, 1));
            if (result > 0) {
                int result2 = compare(localDate, LocalDate.of(2022, 4, 14));
                if (result2 > 0) {
                    futureDate.add(localDate);
                } else if (result2 < 0) {
                    goodDate.add(localDate);
                }
            } else if (result < 0) {
                pastDate.add(localDate);
            }
        }

        System.out.println("-------------------------[ Result ]-------------------------");
        System.out.println("Bad format date: "+badFormat);
        System.out.println("Past date: "+pastDate);
        System.out.println("Future date: "+futureDate);;
        System.out.println("Good date: "+goodDate);
    }

    @Override
    public int compare(LocalDate localDate, LocalDate date) {
        return localDate.compareTo(date);
    }
}
