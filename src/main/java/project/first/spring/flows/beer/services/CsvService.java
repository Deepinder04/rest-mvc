package project.first.spring.flows.beer.services;

import project.first.spring.flows.beer.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface CsvService {

    List<BeerCSVRecord> convertCSV(File csvFile);
}
