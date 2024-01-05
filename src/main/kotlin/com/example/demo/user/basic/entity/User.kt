package com.example.demo.user.basic.entity

import com.example.demo.common.database.converter.StringFlatter
import com.example.demo.common.database.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
open class User : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = -1

    @Column(nullable = false, unique = true, length = 30)
    open lateinit var userId: String

    @Column(nullable = false, unique = false, length = 50)
    open lateinit var password: String

    @Column(nullable = false, unique = false, length = 50)
    open lateinit var name: String

    @Column(nullable = false, unique = false, length = 35)
    open lateinit var phone: String

    @Column(nullable = true, unique = false, length = 50)
    open lateinit var email: String

    @Column
    @Convert(converter = StringFlatter::class)
    open lateinit var language: List<String>
}
