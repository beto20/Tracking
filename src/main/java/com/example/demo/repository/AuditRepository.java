package com.example.demo.repository;

import com.example.demo.model.entity.AuditDocument;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends ReactiveCrudRepository<AuditDocument, String> {

}
