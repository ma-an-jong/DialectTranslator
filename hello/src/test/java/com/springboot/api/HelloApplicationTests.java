package com.springboot.api;

import com.springboot.api.controller.ReportController;
import com.springboot.api.controller.TranslateController;
import com.springboot.api.service.dto.ReportRequestDTO;
import com.springboot.api.service.dto.TranslateRequestDTO;
import com.springboot.api.service.dto.TranslateResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HelloApplicationTests {

    @Autowired
    private TranslateController translateController;

    @Autowired
    private ReportController reportController;

    private static TranslateRequestDTO requestDTO;


    @BeforeAll
    static void init()
    {
        requestDTO = new TranslateRequestDTO("안녕하숩깡",3);
    }
    @Test
    void contextLoads() {
    }

    @Test
    void translateTest()
    {
        try {
            TranslateResponseDTO translatedDTO = translateController.translate(requestDTO).getBody();
            System.out.println(translatedDTO.getRegion() + " " + translatedDTO.getStandard_form());
            assertThat(translatedDTO.getRegion()).isEqualTo("제주도");
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    void reportTest()
    {
        String content = "성공 사례 테스트를 실행하면 테스트는 통과한다.";
        String successMessage  = "의견이 접수되었습니다.";
        ReportRequestDTO reportRequestDTO = new ReportRequestDTO(content);
        ResponseEntity<String> stringResponseEntity = reportController.enrollReport(reportRequestDTO);
        assertThat(stringResponseEntity.getBody()).isEqualTo(successMessage);
    }

}
