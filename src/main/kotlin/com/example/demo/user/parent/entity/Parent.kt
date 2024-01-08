package com.example.demo.user.parent.entity

import com.example.demo.user.basic.entity.User
import com.example.demo.user.parent.child.entity.Child
import jakarta.persistence.*

@Entity
class Parent(
    userId: String,
    password: String,
    name: String,
    phone: String,
    email: String?,
    language: List<String>,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    //mappedby는 양방향 관계 지정시.
    var children: List<Child>

) : User(userId, password, name, phone, email, language)