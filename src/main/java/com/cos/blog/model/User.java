package com.cos.blog.model;

import com.cos.blog.RoleType;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;


import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
// @DynamicInsert // insert시에 null인 필드를 제외시켜준다.
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false, length = 30)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false,length = 50)
    private String email;

    //@ColumnDefault("user")
    //DB는 RoleType이라는게 없어서 STRING이라고 명시해줘야한다.
    @Enumerated(EnumType.STRING)
    private RoleType role; //타입이 강제되므로 String보다 Enum이 좋다.

    @CreationTimestamp
    private Timestamp createDate;
}
