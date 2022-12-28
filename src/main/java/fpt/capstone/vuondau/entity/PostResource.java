package fpt.capstone.vuondau.entity;

import javax.persistence.*;

@Entity
@Table(name = "post_resource")
public class PostResource extends BaseEntity {

    @EmbeddedId
    private PostResourceKey id;

    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    private Post post;


    @ManyToOne
    @MapsId("resourceId")
    @JoinColumn(name = "resource_id")
    private Resource resource;




    public PostResourceKey getId() {
        return id;
    }

    public void setId(PostResourceKey id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
