package project.first.spring.services;

import project.first.spring.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface CsvService {

    List<BeerCSVRecord> convertCSV(File csvFile);
}
