package com.library.backend.controllers;

import com.library.backend.entities.PDF;
import com.library.backend.models.PDFDTO;
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

    /*@PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            PDF pdfFile = pdfService.storeFile(file);
            return ResponseEntity.ok().body("Archivo PDF cargado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cargar el archivo PDF.");
        }
    }*/

    @GetMapping("/books")
    public List<PDFDTO> getAllBooks(){
        return pdfService.getAllBooks();
    }

    @PostMapping("/upload/{user_id}/{title}/{author}")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("image") MultipartFile image, @PathVariable Long user_id, @PathVariable String title, @PathVariable String author) {
        Map<String, String> response = new HashMap<>();

        if(!image.isEmpty()){
            String imageName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename().replace(" ", "");
            Path imagePath = Paths.get("uploads").resolve(imageName).toAbsolutePath();

            try {
                Files.copy(image.getInputStream(), imagePath);
                pdfService.storeFile(file, title, author, user_id, imageName);
                response.put("message", "Archivo PDF cargado correctamente.");
            } catch (Exception e) {
                response.put("message", "Falló la carga del archivo.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }
        return ResponseEntity.ok(response);
    }


    /*@PostMapping("/upload/{user_id}/{title}/{author}")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("image") MultipartFile image, @PathVariable Long user_id, @PathVariable String title, @PathVariable String author) {


        try {
            pdfService.storeFile(file, title, author, user_id, image);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Archivo PDF cargado correctamente.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Falló la carga del archivo.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }*/

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
        Path filePath = Paths.get("uploads").resolve(imageName).toAbsolutePath();
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
