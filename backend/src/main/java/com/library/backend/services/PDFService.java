package com.library.backend.services;

import com.library.backend.entities.PDF;
import com.library.backend.models.PDFDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PDFService {

    PDF storeFile(MultipartFile file, String title, String author, Long user_id, String imageName) throws IOException;

    PDF getFile(Long fileId);

    List<PDFDTO> getAllBooks();


}
