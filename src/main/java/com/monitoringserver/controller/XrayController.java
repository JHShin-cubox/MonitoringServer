package com.monitoringserver.controller;

import com.monitoringserver.dto.ResponseDTO;
import com.monitoringserver.dto.XrayStatDTO;
import com.monitoringserver.service.XrayService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/xray")
public class XrayController {

    XrayService xrayService;
    @Autowired
    public XrayController(XrayService xrayService) {
        this.xrayService = xrayService;
    }


    /**
     * label id를 입력하면 해당 label id의 갯수를 출력
     */
    @GetMapping(value = "/label-id/{labelId}")
    @ApiOperation(value = "label id를 입력하면 해당 label id의 갯수를 출력한다.")
    public ResponseEntity<?> countLabelId(@PathVariable String labelId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .data(List.of(xrayService.getOneByLabelId(labelId)))
                        .build());
    }

    /**
     * ip, 라벨id, labelid의 갯수, score, 비율 출력
     */
    @GetMapping(value = "/label-id")
    @ApiOperation(value = "모든 라벨 아이디의 id, 수량, 비율을 출력한다.")
    public ResponseEntity<?> labelIdStats() {
        List<XrayStatDTO> allStatistics = xrayService.getAllStatistics2();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .data(allStatistics)
                        .build());
    }

    /**
     * label id 별 갯수 출력
     */
    @GetMapping("/label-id/amount")
    public ResponseEntity<?> countAllLabelId(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .data(xrayService.countAllLabelId())
                        .build());
    }


  /*
    @GetMapping
    @RequestMapping("/pc/{ip}")
    public ResponseEntity<?> getPcStatus(@PathVariable String ip){
        return null;
    }
*/

}
