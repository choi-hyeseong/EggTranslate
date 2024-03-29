package com.example.demo.member.parent.child.entity

import com.example.demo.member.parent.child.type.Gender
import jakarta.persistence.*

@Entity
class Child(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long?,

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
            : this(null, name, phone, school, grade, className, gender)


}