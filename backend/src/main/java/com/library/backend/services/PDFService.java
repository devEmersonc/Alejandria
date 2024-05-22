package com.library.backend.services;

import com.library.backend.entities.PDF;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PDFService {

    PDF storeFile(MultipartFile file, Long user_id) throws IOException;

    PDF getFile(Long fileId);

    List<PDF> getAllBooks();
}
