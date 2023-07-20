package com.monitoringserver.controller;


import com.monitoringserver.dto.*;
import com.monitoringserver.service.DeviceInfoService;
import com.monitoringserver.service.OnOffHistoryService;
import com.monitoringserver.service.XrayService;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final XrayService xrayService;
    private final DeviceInfoService deviceInfoService;
    private final OnOffHistoryService onOffHistoryService;

    private static final String UPLOAD_DIR = "classpath:static/img/test/";

    @GetMapping(value = "/")
    public String DashBoard(Model model){
        model.addAttribute("sideHL","대시보드"); //사이드바 하이라이트
        model.addAttribute("viewerInfo",deviceInfoService.getViewerInfo());
        model.addAttribute("xrayInfo",deviceInfoService.getXrayInfo());
        return "index";
    }

    @GetMapping(value = "/xStat")
    public String Xray(Optional<Integer> page, Model model){
        model.addAttribute("sideHL","통계"); //사이드바 하이라이트
        int maxPage = 10;
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,10);
        Page<XrayStatisticDTO> xrayStatList =  xrayService.getAllStatistics(page, pageable);
        model.addAttribute("xrayStatList", xrayStatList);
        model.addAttribute("maxPage", maxPage);
        model.addAttribute("totalCount",xrayService.getStatCount() );
        return "xrayStat";
    }

    @GetMapping(value = "/login")
    public String Login(){
        return "login";
    }

    @GetMapping(value = "/logOut")
    public String Logout(){
        return "logout";
    }

    @GetMapping(value ={"/record","/record/{deviceId}"})
    public String AllRecord(Optional<Integer> page, Model model, @PathVariable(value = "deviceId", required = false) Long deviceId,
                            @RequestParam(name = "type", required = false) String type){
        int maxPage = 10;
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,10);
        Page<OnOffHistoryDTO> recordList = onOffHistoryService.getAllHistory(page, pageable, deviceId, type);
        model.addAttribute("sideHL","이력"); //사이드바 하이라이트
        model.addAttribute("recordLists", recordList);
        model.addAttribute("maxPage", maxPage);
        model.addAttribute("totalCount",onOffHistoryService.getAllCount(deviceId,type));
        model.addAttribute("deviceId",deviceId);
        model.addAttribute("deviceType",type);
        model.addAttribute("selLists",deviceInfoService.getHistorySel());
        return "record";
    }

    @GetMapping("utest")
    public String UpTest(){
        return "utest";
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        System.out.println("123");
        // 파일 업로드 처리 로직
        if (!file.isEmpty()) {
            try {
                // 파일 저장 경로 설정
                String uploadDir = "src/main/resources/static/img/test";
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                String fileExtension = getFileExtension(fileName);
                String generatedFileName = generateFileName(fileExtension);
                Path targetLocation = Path.of(uploadDir).toAbsolutePath().normalize().resolve(generatedFileName);


                return ResponseEntity.ok().body("File uploaded successfully");
            } catch (IOException e) {
                // 파일 업로드 실패 처리
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
            }
        } else {
            // 파일이 없는 경우 처리
            return ResponseEntity.badRequest().body("No file uploaded");
        }
    }
    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    private String generateFileName(String fileExtension) {
        return UUID.randomUUID().toString() + fileExtension;
    }
}
