package fpt.capstone.vuondau.entity;


import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    private String name ;
    private String email ;
    private Long phoneNumber  ;

    @OneToOne(cascade =  CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account ;

}