package com.example.demo.user.basic.entity

import com.example.demo.common.database.converter.StringFlatter
import com.example.demo.common.database.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
open class User : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1

    @Column(nullable = false, unique = true, length = 30)
    lateinit var userId: String

    @Column(nullable = false, unique = false, length = 50)
    lateinit var password: String

    @Column(nullable = false, unique = false, length = 50)
    lateinit var name: String

    @Column(nullable = false, unique = false, length = 35)
    lateinit var phone: String

    @Column(nullable = true, unique = false, length = 50)
    lateinit var email: String

    @Column
    @Convert(converter = StringFlatter::class)
    lateinit var language: List<String>
}
