package project.first.spring.services;

import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import project.first.spring.flows.Beer.model.BeerCSVRecord;
import project.first.spring.flows.Beer.services.CsvService;
import project.first.spring.flows.Beer.services.CsvServiceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class CsvServiceImplTest {

    CsvService csvService = new CsvServiceImpl();

    @Test
    void convertCSV() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
        List<BeerCSVRecord> records = csvService.convertCSV(file);
        System.out.println(records.size());
        assertThat(records.size()).isGreaterThan(0);
    }
}