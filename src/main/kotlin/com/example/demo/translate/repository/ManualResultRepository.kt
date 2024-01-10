package com.example.demo.translate.repository

import com.example.demo.translate.entity.ManualResult
import org.springframework.data.jpa.repository.JpaRepository

interface ManualResultRepository :JpaRepository<ManualResult, Long> {
}