package project.first.spring.flows.home.pagerender.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import project.first.spring.Utilities.Utils.JsonHelper;
import project.first.spring.Utilities.common.IPageRenderer;
import project.first.spring.Utilities.dao.ConfigDAO;
import project.first.spring.Utilities.enums.ConfigCategory;
import project.first.spring.Utilities.enums.ConfigType;
import project.first.spring.Utilities.response.SbApiResponse;
import project.first.spring.flows.home.model.HomeScreenDto;

import static project.first.spring.Utilities.Constants.*;
import static project.first.spring.flows.home.pagerender.Constants.HomeScreenConstants.HOME_SCREEN_BODY;

@RequiredArgsConstructor
@Component
public class HomeScreenPageRender implements IPageRenderer<HomeScreenDto> {

    private final ConfigDAO configDAO;
    private final JsonHelper jsonHelper;

    @Override
    public SbApiResponse renderPageFromData(HomeScreenDto data) throws JsonProcessingException {
        return SbApiResponse.buildSuccess(getHomeScreen());
    }

    private Object getHomeScreen() throws JsonProcessingException {
        JSONObject homeScreenJson = new JSONObject();
        JSONObject header = new JSONObject(configDAO.findByConfigTypeAndConfigCategory(ConfigType.CONSTANTS, ConfigCategory.HEADER).getData());
        JSONObject footer = new JSONObject(configDAO.findByConfigTypeAndConfigCategory(ConfigType.CONSTANTS, ConfigCategory.FOOTER).getData());
        JSONObject body = new JSONObject(configDAO.findByConfigTypeAndConfigCategory(ConfigType.CONSTANTS, ConfigCategory.HOME_SCREEN).getData());
        JSONObject collection = new JSONObject(configDAO.findByConfigTypeAndConfigCategory(ConfigType.CONSTANTS, ConfigCategory.COLLECTIONS).getData());
        JSONArray collections = (JSONArray) collection.get("collections");
        
        homeScreenJson.put(HEADER, header);
        homeScreenJson.put(FOOTER, footer);
        homeScreenJson.put(HOME_SCREEN_BODY,body);
        homeScreenJson.put(COLLECTIONS, collections);

        return jsonHelper.getObjectMapper().readTree(homeScreenJson.toString());
    }
}
