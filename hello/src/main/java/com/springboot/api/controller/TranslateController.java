package com.springboot.api.controller;

import com.springboot.api.data.entity.Log;
import com.springboot.api.kafka.KafkaResponse;
import com.springboot.api.kafka.MyKafkaConsumer;
import com.springboot.api.kafka.MyKafkaProducer;
import com.springboot.api.service.dto.TranslateRequestDTO;
import com.springboot.api.service.LogService;
import com.springboot.api.service.dto.TranslateResponseDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class TranslateController
{
    @Autowired
    private WebClient webClient;

    @Autowired
    private LogService logService;

//    private final MyKafkaConsumer myKafkaConsumer = new MyKafkaConsumer();
//    private final MyKafkaProducer myKafkaProducer = new MyKafkaProducer();

    @ApiOperation(value = "사투리 번역 POST요청", notes = "RequestBody를 다음과 같이 구성")
    @PostMapping(value = "/api/translate")
    public ResponseEntity<TranslateResponseDTO> translate(@ApiParam(value="dialect, label로 구성됨") @RequestBody TranslateRequestDTO requestDTO) throws NoSuchFieldException
    {
        System.out.println(requestDTO.toString());
        Log log = new Log(requestDTO);

        Mono<TranslateResponseDTO> returnMono =  webClient.post()
                .uri("/translate")
                .body(Mono.just(requestDTO), TranslateRequestDTO.class)
                .retrieve()
                .bodyToMono(TranslateResponseDTO.class);
        TranslateResponseDTO responseDTO = returnMono.block();
        System.out.println(responseDTO.getStandard_form());
        System.out.println(responseDTO.getRegion());
        log.setResult(responseDTO);
        logService.saveLog(log);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

//    @ApiOperation(value = "사투리 번역 POST요청", notes = "RequestBody를 다음과 같이 구성")
//    @PostMapping(value = "/api/translate")
//    public ResponseEntity<TranslateResponseDTO> translate(@ApiParam(value="dialect, label로 구성됨") @RequestBody TranslateRequestDTO requestDTO)
//            throws NoSuchFieldException,InterruptedException
//    {
//        System.out.println(requestDTO.toString());
//
//        myKafkaProducer.send(requestDTO.getLabel(), requestDTO.getDialect());
//        Thread.sleep(100L);
//        KafkaResponse record = myKafkaConsumer.consume();
//
//        System.out.printf("%s : %s\n",record.getKey(),record.getValue());
//
//        TranslateResponseDTO responseDTO = new TranslateResponseDTO();
//        responseDTO.setRegion(record.getKey());
//        responseDTO.setStandard_form(record.getValue());
//
//        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
//    }


}
