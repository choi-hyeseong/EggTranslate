package com.example.demo.docs.repository

import com.example.demo.docs.entity.Document
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

interface DocumentRepository : JpaRepository<Document, Long> {


    @Transactional
    fun deleteAllByUserId(userId : Long)
}