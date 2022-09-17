package com.gravador.log.controller;

import com.gravador.log.dto.LogDto;
import com.gravador.log.service.LogService;
import io.swagger.v3.oas.annotations.headers.Header;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.catalina.core.ApplicationContext;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LogController {

    private final LogService service;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void gravarLog(@RequestParam("file") MultipartFile multipartFile) {
        service.createLog(multipartFile);
    }

    @GetMapping
    public ResponseEntity<List<LogDto>> encontrarLog() {
        return ResponseEntity.ok(service.findAllLog());
    }

}
