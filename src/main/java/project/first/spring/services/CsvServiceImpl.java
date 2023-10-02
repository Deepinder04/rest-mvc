package project.first.spring.services;

import com.opencsv.bean.CsvToBeanBuilder;
import project.first.spring.model.BeerCSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class CsvServiceImpl implements CsvService {
    @Override
    public List<BeerCSVRecord> convertCSV(File csvFile) {
        try{
            List<BeerCSVRecord> beerCSVRecords = new CsvToBeanBuilder<BeerCSVRecord>(new FileReader(csvFile))
                    .withType(BeerCSVRecord.class)
                    .build().parse();
            return beerCSVRecords;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
