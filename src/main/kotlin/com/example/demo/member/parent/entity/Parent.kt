package com.example.demo.member.parent.entity

import com.example.demo.member.user.entity.User
import com.example.demo.member.parent.child.entity.Child
import jakarta.persistence.*

@Entity
class Parent(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @JoinColumn(name = "parent_id")
    @OneToMany(cascade = [CascadeType.ALL] , fetch = FetchType.EAGER, orphanRemoval = true)
    //mappedby는 양방향 관계 지정시.
    var children: MutableList<Child> = mutableListOf(),

    @OneToOne
    @JoinColumn(name = "user_id")
    var user: User
) {

}


