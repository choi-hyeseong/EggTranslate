package com.example.demo.docs.repository

import com.example.demo.docs.entity.ConvertDocument
import org.springframework.data.jpa.repository.JpaRepository

interface ConvertDocumentRepository : JpaRepository<ConvertDocument, Long>