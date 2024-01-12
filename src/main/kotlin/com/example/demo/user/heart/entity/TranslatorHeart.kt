package com.example.demo.user.heart.entity

import com.example.demo.user.basic.entity.User
import com.example.demo.user.translator.entity.Translator
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class TranslatorHeart(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long?,

    @ManyToOne(cascade = [CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST])
    @JoinColumn(name = "user_id")
    var user : User?,

    @ManyToOne(cascade = [CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST]) //persist만 있어도 되긴한데
    @JoinColumn(name = "translator_id")
    var translator: Translator?
)