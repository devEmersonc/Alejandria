package com.library.backend.services;

import com.library.backend.entities.PDF;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PDFService {

    PDF storeFile(MultipartFile file) throws IOException;

    PDF getFile(Long fileId);
}
