package com.gravador.log.service.impl;

import com.gravador.log.domain.Log;
import com.gravador.log.dto.LogDto;
import com.gravador.log.repository.LogRepository;
import com.gravador.log.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//mport java.awt.print.Pageable;i
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class LogServiceImpl implements LogService {
    private final LogRepository repository;
    private Map<String, Long> linhas = new HashMap<>();
    @SneakyThrows
    @Override
    public void createLog(MultipartFile file) {
        Reader test = new StringReader(new String(file.getBytes()));
        BufferedReader lerArq = new BufferedReader(test);
        int i = 1;
        while (lerArq.ready() && i<100000) {
            i++;
            saveLine(removeDate(lerArq.readLine()));
        }
        test.close();
        saveInDb();
    }

    public void saveInDb(){
        repository.saveAll(linhas.entrySet().stream()
                .map(linha ->
                        new Log(null, linha.getValue(), linha.getKey()))
                .collect(Collectors.toList()));
    }
    private String removeDate(String line){
        if(line != null && !line.isEmpty() && line.contains("|")){
            String[] returnSplit = line.split("\\|");
            return returnSplit[0]+returnSplit[2];
        }
        return line;
    }

    private void saveLine(String linha){
        if(linhas.containsKey(linha)){
            linhas.replace(linha, linhas.get(linha).longValue()+1l);
        }else if (linha != null && !linha.isEmpty())
        {
            linhas.put(linha, 1l);
        }
    }

    @Override
    public List<LogDto> findAllLog(int size) {
        Pageable limit = PageRequest.of(0,size);
        return mapper(repository.findAll(limit));
    }

    private List<LogDto> mapper(Iterable<Log> log){
        return StreamSupport.stream(log.spliterator(), false).map(Log -> new LogDto(Log.getVezes(), Log.getConteudo()))
                .collect(Collectors.toList());
    }
}
