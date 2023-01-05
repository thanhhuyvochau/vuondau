package fpt.capstone.vuondau.service.Impl;


import fpt.capstone.vuondau.entity.Post;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.EPageContent;
import fpt.capstone.vuondau.entity.request.PageContentRequest;
import fpt.capstone.vuondau.entity.response.PageContentResponse;
import fpt.capstone.vuondau.repository.PostRepository;
import fpt.capstone.vuondau.service.IPostService;

import fpt.capstone.vuondau.util.ObjectUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class PostServiceImpl implements IPostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Long createContentIntroPage(PageContentRequest pageContentRequest) {
        Post post = new Post();
        post.setContent(pageContentRequest.getContent());
        post.setType(pageContentRequest.getType());
        Post save = postRepository.save(post);
        return save.getId();
    }

    @Override
    public PageContentResponse getContentIntroPage(Long id) {

        Post post = postRepository.findById(id).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay content page" + id));
        return ObjectUtil.copyProperties(post, new PageContentResponse(), PageContentResponse.class);
    }

    @Override
    public PageContentResponse updateContentIntroPage(Long id, PageContentRequest pageContentRequest) {
        Post post = postRepository.findById(id).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay content page" + id));
        post.setType(pageContentRequest.getType());
        post.setContent(pageContentRequest.getContent());
        Post save = postRepository.save(post);
        return ObjectUtil.copyProperties(save, new PageContentResponse(), PageContentResponse.class);
    }
}
