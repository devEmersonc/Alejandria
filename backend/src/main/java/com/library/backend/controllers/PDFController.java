package com.library.backend.controllers;

import com.library.backend.entities.PDF;
import com.library.backend.services.PDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "http://localhost:4200")
public class PDFController {

    @Autowired
    private PDFService pdfService;

    @GetMapping("/books")
    public List<PDF> getAllBooks(){
        return pdfService.getBooks();
    }

    @PostMapping("/upload/{user_id}/{title}/{author}/{category}")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("image") MultipartFile image, @PathVariable Long user_id, @PathVariable String title, @PathVariable String author, @PathVariable String category) {
        Map<String, String> response = new HashMap<>();

        if(!image.isEmpty()){
            String imageName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename().replace(" ", "");
            Path imagePath = Paths.get("uploads_files").resolve(imageName).toAbsolutePath();

            try {
                Files.copy(image.getInputStream(), imagePath);
                pdfService.storeFile(file, title, author, user_id, imageName, category);
                response.put("message", "Archivo PDF cargado correctamente.");
            } catch (Exception e) {
                response.put("message", "Falló la carga del archivo.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/download-file/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id){
        PDF pdf = pdfService.getFile(id);

        if(pdf != null){
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + pdf.getFileName() + "\"")
                    .body(pdf.getData());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/view/image/{imageName:.+}")
    public ResponseEntity<Resource> viewImage(@PathVariable String imageName){
        Path filePath = Paths.get("uploads_files").resolve(imageName).toAbsolutePath();
        Resource recurso = null;

        try{
            recurso = new UrlResource((filePath.toUri()));
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        if(!recurso.exists() && recurso.isReadable()){
            filePath = Paths.get("src/main/resources/static/images").resolve("user_icon.png").toAbsolutePath();

            try{
                recurso = new UrlResource(filePath.toUri());
            }catch (MalformedURLException e){
                e.printStackTrace();
            }
        }

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");

        return new ResponseEntity<Resource>(recurso, header, HttpStatus.OK);
    }
}
