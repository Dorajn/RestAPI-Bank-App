package com.bank.bank_app.controller;

import com.bank.bank_app.service.FileGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FileGeneratorController {

    @Autowired
    private FileGeneratorService service;

    public FileGeneratorController(FileGeneratorService service) {
        this.service = service;
    }

    @GetMapping("/users/{uid}/account/history/raport")
    public ResponseEntity<?> downLoadPdfFile(@PathVariable int uid){
        return service.createPdfFile(uid);
    }

}
