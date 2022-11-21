package fpt.capstone.vuondau.entity;


import fpt.capstone.vuondau.entity.common.EGradeType;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Account student;


    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<CartItem> CartItem;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getStudent() {
        return student;
    }

    public void setStudent(Account student) {
        this.student = student;
    }

    public List<fpt.capstone.vuondau.entity.CartItem> getCartItem() {
        return CartItem;
    }

    public void setCartItem(List<fpt.capstone.vuondau.entity.CartItem> cartItem) {
        CartItem = cartItem;
    }

}
