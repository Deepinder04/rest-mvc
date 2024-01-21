package project.first.spring.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static project.first.spring.Utils.Constants.HOME_PATH;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(HOME_PATH)
public class HomeController {
}
