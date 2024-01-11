package com.example.demo.user.parent.child.entity

import com.example.demo.user.parent.child.type.Gender
import com.example.demo.user.parent.entity.Parent
import jakarta.persistence.*

@Entity
class Child(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = -1,

    @Column(nullable = false, length = 50)
    var name : String,

    @Column(nullable = false, length = 35)
    var phone : String,

    @Column(nullable = false, length = 25)
    var school : String,

    @Column(nullable = false)
    var grade : Int,

    @Column(nullable = false, length = 15)
    var className: String,

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    var gender : Gender,

) {

    constructor(name: String, phone: String, school: String, grade: Int, className: String, gender: Gender)
            : this(-1, name, phone, school, grade, className, gender)


}