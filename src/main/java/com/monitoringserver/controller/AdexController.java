/*==================================================================
프로젝트명 : 판독 웹뷰어
작성지 : 신정호
작성일 : 2023년 11월 22일
수정일 : 2023년 11월 22일
용도 : 판독 웹뷰어 컨트롤러
변경 이력 :
- 2023년 11월 22일 : 버그 수정 및 기능 개선
==================================================================*/

package com.monitoringserver.controller;

import com.monitoringserver.dto.AdexDTO;
import com.monitoringserver.dto.AdexLabelDTO;
import com.monitoringserver.dto.AdexStatusDTO;
import com.monitoringserver.service.AdexService;
import com.monitoringserver.service.DeviceInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CountDownLatch;

@Controller
@RequiredArgsConstructor
public class AdexController {
    private final DeviceInfoService deviceInfoService;
    private final AdexService adexService;

    @GetMapping(value = "/view")
    public String AdexBoard(Model model){
        model.addAttribute("recentImage",adexService.getRecentImage());
        model.addAttribute("settings", deviceInfoService.getSettings());
        model.addAttribute("status",adexService.getAdexStatus());
        model.addAttribute("subImage",adexService.getSumImage());
        model.addAttribute("topTen",adexService.getTop10());
        model.addAttribute("lastLid",adexService.getLastLugId());
        return "adex/index";
    }

    @GetMapping(value = "/view/test")
    @ResponseBody
    public void Test(Model model){

    }

    @GetMapping("/view/down")
    @ResponseBody
    public void downloadMostRecentJsonAndPngFiles() {
//        String folderPath = "C:/project/sshtest";
//        String outputPath = "C:/project/sshtest/bbox";
        String folderPath = "/home/cubox/image/";
        String outputPath = "/home/cubox/image/bbox";
        CountDownLatch latch = new CountDownLatch(1); //작업을 순차적으로 완료하기 위해 생성
        adexService.down(folderPath);
        adexService.createBbox(folderPath,outputPath,latch);
        try {latch.await();}
        catch (InterruptedException e) {e.printStackTrace();}
    }

    @GetMapping("/view/status")
    @ResponseBody
    public AdexStatusDTO getStatus(){
        return adexService.getAdexStatus();
    }

    @GetMapping("/view/subImage")
    @ResponseBody
    public List<AdexDTO> getSubImage(){
        return adexService.getSumImage();
    }

    @GetMapping("/view/topTen")
    @ResponseBody
    public List<AdexLabelDTO> getTopTen(){
        return adexService.getTop10();
    }

    @GetMapping("/view/listSubImg")
    @ResponseBody
    public List<AdexDTO> getListSubImgage(@RequestParam("luggageId") String luggageId){
        return adexService.getListSubImage(luggageId);
    }

    @GetMapping("/view/lid")
    @ResponseBody
    public String getLastLid(){
        return adexService.getLastLugId();
    }

}
