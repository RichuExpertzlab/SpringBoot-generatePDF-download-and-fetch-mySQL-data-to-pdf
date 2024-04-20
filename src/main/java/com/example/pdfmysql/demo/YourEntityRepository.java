package com.example.pdfmysql.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface YourEntityRepository extends JpaRepository<YourEntity, Long> {
    // Add custom query methods if needed
}
