package com.monitoringserver.controller;


import com.monitoringserver.dto.AdexDTO;
import com.monitoringserver.dto.AdexLabelDTO;
import com.monitoringserver.dto.AdexStatusDTO;
import com.monitoringserver.dto.SearchDto;
import com.monitoringserver.service.AdexService;
import com.monitoringserver.service.DeviceInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;
import java.util.concurrent.CountDownLatch;

@Controller
@RequiredArgsConstructor
public class AdexController {
    private final DeviceInfoService deviceInfoService;
    private final AdexService adexService;

    @GetMapping(value = "/adex")
    public String AdexBoard(Model model){
        model.addAttribute("recentImage",adexService.getRecentImage());
        model.addAttribute("settings", deviceInfoService.getSettings());
        model.addAttribute("status",adexService.getAdexStatus());
        model.addAttribute("subImage",adexService.getSumImage());
        model.addAttribute("topTen",adexService.getTop10());
        model.addAttribute("lastLid",adexService.getLastLugId());

        return "adex/index";
    }

    @GetMapping(value = "/adex/list")
    public String AdexList(Optional<Integer> page, Model model, HttpServletRequest request, SearchDto searchDto) {
        int maxPage = 10;
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, maxPage);
        Page<AdexDTO> list =  adexService.getAdexList(pageable, searchDto);
        model.addAttribute("uri",request.getRequestURI());
        model.addAttribute("lists",list);
        model.addAttribute("totalCount",adexService.getAdexCount());
        model.addAttribute("status",adexService.getAdexStatus());
        model.addAttribute("topTen",adexService.getTop10());
        model.addAttribute("maxPage", maxPage);
        return "adex/list";
    }

    @GetMapping(value = "/adex/test")
    @ResponseBody
    public void Test(Model model){

    }

    @GetMapping("/adex/down")
    @ResponseBody
    public void downloadMostRecentJsonAndPngFiles() {
//        String folderPath = "C:/project/sshtest";
//        String outputPath = "C:/project/sshtest/bbox";
        String folderPath = "/home/cubox/image/";
        String outputPath = "/home/cubox/image/bbox";
        CountDownLatch latch = new CountDownLatch(1);
        adexService.down(folderPath);
        adexService.createBbox2(folderPath,outputPath,latch);
        try {

            latch.await();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/adex/status")
    @ResponseBody
    public AdexStatusDTO getStatus(){
        return adexService.getAdexStatus();
    }

    @GetMapping("/adex/subImage")
    @ResponseBody
    public List<AdexDTO> getSubImage(){
        return adexService.getSumImage();
    }

    @GetMapping("/adex/topTen")
    @ResponseBody
    public List<AdexLabelDTO> getTopTen(){
        return adexService.getTop10();
    }

    @GetMapping("/adex/listSubImg")
    @ResponseBody
    public List<AdexDTO> getListSubImgage(@RequestParam("luggageId") String luggageId){
        return adexService.getListSubImage(luggageId);
    }

    @GetMapping("/adex/lid")
    @ResponseBody
    public String getLastLid(){
        return adexService.getLastLugId();
    }

    @GetMapping("/adex/imageTest")
    @ResponseBody
    public String uploadPngFiles() {
        String url = "http://xraysite.kr:20600/verify/api/ImageTest/multiimage-form";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        String folderPath2 = "C:/Users/신정호/Documents/testImage/";

        // 폴더에서 PNG 파일들을 읽어들임
        File folder = new File(folderPath2);
        File[] pngFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));

        if (pngFiles != null) {
            for (File file : pngFiles) {
                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
                body.add("image", new FileSystemResource(file));
                body.add("LID", file.getName());
                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
                ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

                // 응답을 처리하거나 로깅할 수 있습니다.
            }
        }
        String folderPath = "C:/project/sshtest";
        String outputPath = "C:/project/sshtest/bbox";
//        String folderPath = "/home/cubox/image/";
//        String outputPath = "/home/cubox/image/bbox";
        CountDownLatch latch = new CountDownLatch(1);
//        adexService.down();
        adexService.createBbox2(folderPath,outputPath,latch);
        try {

            latch.await();

            // 작업이 완료되면 "good"을 반환
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "PNG 파일 업로드 완료";
    }





}
