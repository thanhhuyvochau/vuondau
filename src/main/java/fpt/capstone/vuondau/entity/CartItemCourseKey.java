package fpt.capstone.vuondau.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartItemCourseKey implements Serializable {

    @Column(name = "cart_item_id")
    private Long cartItemId;
    @Column(name = "course_id")
    private Long courseId;


    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemCourseKey that = (CartItemCourseKey) o;
        return Objects.equals(cartItemId, that.cartItemId) && Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartItemId, courseId);
    }
}
