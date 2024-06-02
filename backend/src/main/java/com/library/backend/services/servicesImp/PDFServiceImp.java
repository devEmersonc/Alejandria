package com.library.backend.services.servicesImp;

import com.library.backend.entities.PDF;
import com.library.backend.entities.User;
import com.library.backend.repositories.PDFRepository;
import com.library.backend.repositories.UserRepository;
import com.library.backend.services.PDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PDFServiceImp implements PDFService {

    @Autowired
    private PDFRepository pdfRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public PDF storeFile(MultipartFile file, String title, String author, Long user_id, String imageName, String category) throws IOException {
        PDF pdf = new PDF();
        User user = userRepository.findById(user_id).orElse(null);

        pdf.setFileName(title);
        pdf.setAuthor(author);
        pdf.setPhoto(imageName);
        pdf.setCategory(category);
        pdf.setFileType(file.getContentType());
        pdf.setData(file.getBytes());
        pdf.setUser(user);

        return pdfRepository.save(pdf);
    }

    @Override
    public PDF getFile(Long fileId) {
        return pdfRepository.findById(fileId).orElse(null);
    }

    @Override
    public List<PDF> getBooks(){
        return pdfRepository.findAll();
    }

    @Override
    public List<PDF> getBooksByCategory(String category){
        return pdfRepository.findByCategory(category);
    }

    @Override
    public void deleteBookById(Long book_id){
        pdfRepository.deleteById(book_id);
    }
}
