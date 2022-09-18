package com.gravador.log.service;

import com.gravador.log.dto.LogDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface LogService {

    void createLog(MultipartFile file);

    List<LogDto> findAllLog(int size);


}
