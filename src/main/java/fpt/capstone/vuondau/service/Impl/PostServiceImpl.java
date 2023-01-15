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

import java.util.List;
import java.util.Optional;


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
        List<Post> existPostByType = postRepository.findPostByTypeAndIsVisibleIsTrue(pageContentRequest.getType());
        if (existPostByType.size() > 0) {
            throw ApiException.create(HttpStatus.CONFLICT)
                    .withMessage("Bạn không thể thiết lập hiển thị cho trang này. Hiện tại đang có trang hiển thị rồi.");
        }
        post.setVisible(pageContentRequest.getVisible());
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
        List<Post> existPostByType = postRepository.findPostByTypeAndIsVisibleIsTrue(pageContentRequest.getType());
        if (existPostByType.size() > 0 && !post.getVisible()) {
            throw ApiException.create(HttpStatus.CONFLICT)
                    .withMessage("Bạn không thể thiết lập hiển thị cho trang này. Hiện tại đang có trang hiển thị rồi.");
        }
        post.setContent(pageContentRequest.getContent());
        post.setVisible(pageContentRequest.getVisible());
        Post save = postRepository.save(post);

        return ObjectUtil.copyProperties(save, new PageContentResponse(), PageContentResponse.class);
    }

    @Override
    public PageContentResponse renderIntroPage(EPageContent type) {
        Optional<Post> postByType = postRepository.findPostByTypeAndIsVisibleIsTrue(type).stream().findFirst();
        PageContentResponse pageContentResponse = null;
        if (postByType.isPresent()) {
            Post post = postByType.get();
            pageContentResponse = ObjectUtil.copyProperties(post, new PageContentResponse(), PageContentResponse.class);
        }
        return pageContentResponse;
    }
}
