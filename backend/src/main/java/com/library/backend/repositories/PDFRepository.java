package com.library.backend.repositories;

import com.library.backend.entities.PDF;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PDFRepository extends JpaRepository<PDF, Long> {

    List<PDF> findByCategory(String category);
}
