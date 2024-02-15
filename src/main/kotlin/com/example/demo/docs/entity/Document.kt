package com.example.demo.docs.entity

import com.example.demo.common.database.entity.BaseEntity
import com.example.demo.docs.type.DocumentType
import com.example.demo.file.util.FileUtil
import com.example.demo.member.user.entity.User
import jakarta.persistence.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Entity
class Document (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long?,

    @Enumerated(value = EnumType.STRING)
    val type : DocumentType,

    @Column
    val originName : String,

    @Column
    val savePath : String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user : User?,
) : BaseEntity() {
    @PreRemove
    fun deleteFile() {
        CoroutineScope(Dispatchers.IO).launch {
            FileUtil.deleteFile(savePath)
        }
    }
}