package com.example.demo.docs.repository

import com.example.demo.docs.entity.Document
import org.springframework.data.jpa.repository.JpaRepository

interface DocumentRepository : JpaRepository<Document, Long>