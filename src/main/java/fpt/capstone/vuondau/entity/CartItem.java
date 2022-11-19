package fpt.capstone.vuondau.entity;


import fpt.capstone.vuondau.entity.common.EGradeType;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToMany(mappedBy = "cartItem")
    private List<CartItemCourse> cartItemCourses;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public List<CartItemCourse> getCartItemCourses() {
        return cartItemCourses;
    }

    public void setCartItemCourses(List<CartItemCourse> cartItemCourses) {
        this.cartItemCourses = cartItemCourses;
    }
}
