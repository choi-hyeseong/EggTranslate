package com.example.demo.common.database.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime


//자동으로 접근시 시간 갱신해주는 엔티티
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    var createdDate: LocalDateTime = LocalDateTime.now()
        protected set

    @Column(name = "modified_date")
    @LastModifiedDate
    var modifiedDate: LocalDateTime = LocalDateTime.now()
        protected set

}

