package com.example.demo.file.entity

import com.example.demo.common.database.entity.BaseEntity
import com.example.demo.file.util.FileUtil
import com.example.demo.user.basic.entity.User
import jakarta.persistence.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Entity
class ConvertFile( // 괄호 안에는 기본 생성자들
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long?,

    @Column(nullable = false, length = 255)
    var saveName : String,

    var savePath : String,

    ) : BaseEntity() {

    @PreRemove
    fun deleteFile() {
        CoroutineScope(Dispatchers.IO).launch {
            FileUtil.deleteFile(savePath)
        }
    }
}