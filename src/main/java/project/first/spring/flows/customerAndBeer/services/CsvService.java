package project.first.spring.flows.customerAndBeer.services;

import project.first.spring.flows.customerAndBeer.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface CsvService {

    List<BeerCSVRecord> convertCSV(File csvFile);
}
