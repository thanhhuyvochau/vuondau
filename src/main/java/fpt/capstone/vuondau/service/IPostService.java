package fpt.capstone.vuondau.service;


import fpt.capstone.vuondau.entity.common.EPageContent;
import fpt.capstone.vuondau.entity.request.PageContentRequest;
import fpt.capstone.vuondau.entity.response.PageContentResponse;

public interface IPostService {


    Long createContentIntroPage(PageContentRequest content);

    PageContentResponse getContentIntroPage(Long id);

    PageContentResponse updateContentIntroPage(Long id, PageContentRequest content);

    PageContentResponse renderIntroPage(EPageContent type );
}