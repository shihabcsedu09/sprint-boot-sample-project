package com.cefalo.newstestproject.module.news.controller.rest.v2;

import com.cefalo.newstestproject.module.news.dto.generic.NewsV2DTO;
import com.cefalo.newstestproject.module.news.dto.response.v2.NewsResponseV2DTO;
import com.cefalo.newstestproject.module.news.entity.NewsEntityV2;
import com.cefalo.newstestproject.module.news.service.NewsServiceV2;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v2")
public class NewsFormatControllerV2Impl implements NewsFormatControllerV2 {

    private static Logger ourLogger = LogManager.getLogger(NewsFormatControllerV2Impl.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NewsServiceV2 newsService;


    @Override
    @RequestMapping(method = RequestMethod.GET,
            value = "/news/json/{newsId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public NewsResponseV2DTO showNewsById(@PathVariable(value = "newsId") int newsId) {

        String responseMessage;
        NewsResponseV2DTO aNewsDTO;
        try {
            ourLogger.info("Received news details request : id [ " + newsId + " ] format [ json ]");
            NewsEntityV2 aNewsEntity = newsService.getNewsById(newsId);
            if (aNewsEntity == null) {
                responseMessage = "News with id : " + newsId + " not found !!!";
                ourLogger.info(responseMessage);
                aNewsDTO = new NewsResponseV2DTO(HttpStatus.NO_CONTENT.toString(), responseMessage);
            } else {
                List<NewsV2DTO> newsDTO = new ArrayList<>();
                newsDTO.add(modelMapper.map(aNewsEntity, NewsV2DTO.class));
                responseMessage = "News details for id : " + newsId;
                ourLogger.info(responseMessage);
                aNewsDTO = new NewsResponseV2DTO(HttpStatus.OK.toString(), responseMessage, newsDTO);
            }
        } catch (Exception ex) {
            aNewsDTO = newsErrorProcess(ex);
        }

        return aNewsDTO;
    }


    @Override
    @RequestMapping(method = RequestMethod.GET,
            value = "/news/xml/{newsId}",
            produces = {MediaType.APPLICATION_XML_VALUE})
    public NewsResponseV2DTO showNewsByIdXML(@PathVariable(value = "newsId") int newsId){
        return showNewsById(newsId);
    }


    private NewsResponseV2DTO newsErrorProcess(Exception ex) {
        String responseMessage;
        responseMessage = "Internal Server Error. Try Again Later !!!";
        ourLogger.info(ex.getMessage(), ex);
        return new NewsResponseV2DTO(HttpStatus.INTERNAL_SERVER_ERROR.toString(), responseMessage, ex.getMessage());
    }

}
