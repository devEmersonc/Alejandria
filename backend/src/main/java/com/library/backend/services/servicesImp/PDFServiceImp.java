package com.library.backend.services.servicesImp;

import com.library.backend.entities.PDF;
import com.library.backend.repositories.PDFRepository;
import com.library.backend.services.PDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PDFServiceImp implements PDFService {

    @Autowired
    private PDFRepository pdfRepository;

    @Override
    public PDF storeFile(MultipartFile file) throws IOException {
        PDF pdf = new PDF();
        pdf.setFileName(file.getOriginalFilename());
        pdf.setFileType(file.getContentType());
        pdf.setData(file.getBytes());

        return pdfRepository.save(pdf);
    }

    @Override
    public PDF getFile(Long fileId) {
        return pdfRepository.findById(fileId).orElse(null);
    }


}
