package project.first.spring.flows.Beer.services;

import project.first.spring.flows.Beer.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface CsvService {

    List<BeerCSVRecord> convertCSV(File csvFile);
}
