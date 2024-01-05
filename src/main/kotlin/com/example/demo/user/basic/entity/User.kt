package com.example.demo.user.basic.entity

import com.example.demo.common.database.converter.StringFlatter
import com.example.demo.common.database.entity.BaseEntity
import jakarta.persistence.*

// TODO FCM TOKEN
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
open class User(
    @Column(nullable = false, unique = true, length = 30)
    open var userId: String,

    @Column(nullable = false, unique = false, length = 50)
    open var password: String,

    @Column(nullable = false, unique = false, length = 50)
    open var name: String,

    @Column(nullable = false, unique = false, length = 35)
    open var phone: String,

    @Column(nullable = true, unique = false, length = 50)
    open var email: String?,

    @Column
    @Convert(converter = StringFlatter::class)
    open var language: List<String>

) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = -1

}
