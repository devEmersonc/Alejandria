package com.library.backend.controllers;

import com.library.backend.entities.PDF;
import com.library.backend.services.PDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<PDF> getAllBooks(){
        return pdfService.getAllBooks();
    }

    @PostMapping("/upload/{user_id}")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long user_id) {
        try {
            pdfService.storeFile(file, user_id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Archivo PDF cargado correctamente.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Falló la carga del archivo.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
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
}
