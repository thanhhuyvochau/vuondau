package fpt.capstone.vuondau.entity;

import javax.persistence.*;

@Entity
@Table(name = "cart_item_course")
public class CartItemCourse {

    @EmbeddedId
    private CartItemCourseKey id;

    @ManyToOne
    @MapsId("cartItemId")
    @JoinColumn(name = "cart_item_id")
    private CartItem cartItem;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;


    @Column(nullable = true, columnDefinition = "bit default 0")
    private Boolean isAllowed ;

    public CartItemCourseKey getId() {
        return id;
    }

    public void setId(CartItemCourseKey id) {
        this.id = id;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Boolean getIsAllowed() {
        return isAllowed;
    }

    public void setIsAllowed(Boolean allowed) {
        isAllowed = allowed;
    }
}
