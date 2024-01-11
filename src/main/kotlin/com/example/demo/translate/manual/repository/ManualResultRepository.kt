package com.example.demo.translate.manual.repository

import com.example.demo.translate.manual.entity.ManualResult
import org.springframework.data.jpa.repository.JpaRepository

interface ManualResultRepository :JpaRepository<ManualResult, Long> {
}