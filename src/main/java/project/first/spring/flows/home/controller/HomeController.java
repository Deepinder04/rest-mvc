package project.first.spring.flows.home.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import project.first.spring.Utilities.Constants;
import project.first.spring.Utilities.common.IPageRenderer;
import project.first.spring.Utilities.response.SbApiResponse;
import project.first.spring.flows.home.model.HomeScreenDto;

import static project.first.spring.Utilities.Constants.HOME_PATH;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(HOME_PATH)
@CrossOrigin(value = "*")
public class HomeController {

    private final IPageRenderer<HomeScreenDto> homeScreenPageRender;

    @GetMapping
    public SbApiResponse getHomeScreen() throws JsonProcessingException {
        HomeScreenDto homeScreenDto = new HomeScreenDto();
        return homeScreenPageRender.renderPageFromData(homeScreenDto);
    }
}
