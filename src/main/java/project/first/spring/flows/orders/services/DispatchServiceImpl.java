package project.first.spring.flows.orders.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DispatchServiceImpl implements DispatchService {

    @Override
    public void process(String payload) {
        log.info("Going to process order with payload - {}", payload);
    }
}
