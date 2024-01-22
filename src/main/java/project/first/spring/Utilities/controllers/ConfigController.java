package project.first.spring.Utilities.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.first.spring.Utilities.Utils.JsonHelper;
import project.first.spring.Utilities.dao.ConfigDAO;
import project.first.spring.Utilities.entities.Config;
import project.first.spring.Utilities.enums.ConfigCategory;
import project.first.spring.Utilities.enums.ConfigType;

import java.util.Objects;

import static project.first.spring.Utilities.Constants.UTILITY_CONTROLLER_PATH;

@RestController
@RequestMapping(UTILITY_CONTROLLER_PATH)
@RequiredArgsConstructor
public class ConfigController {

    private final ConfigDAO configDAO;
    private final JsonHelper jsonHelper;

    @PostMapping("/insert/config")
    ResponseEntity<Config> updateConfig(@RequestParam("type") ConfigType type, @RequestParam("category") ConfigCategory category, @RequestBody Object data){
        Config config = configDAO.findByConfigTypeAndConfigCategory(type, category);

        if(Objects.isNull(config)){
            config = Config.builder()
                    .configCategory(category)
                    .configType(type)
                    .data(jsonHelper.toJson(data))
                    .build();
        } else config.setData(jsonHelper.toJson(data));

        configDAO.save(config);

        return ResponseEntity.ok(config);
    }
}
