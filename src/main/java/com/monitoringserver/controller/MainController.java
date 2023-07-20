package com.monitoringserver.controller;


import com.monitoringserver.service.DeviceInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final DeviceInfoService deviceInfoService;

    @GetMapping(value = "/")
    public String DashBoard(Model model){
        model.addAttribute("sideHL","대시보드"); //사이드바 하이라이트
        model.addAttribute("viewerInfo",deviceInfoService.getViewerInfo());
        model.addAttribute("xrayInfo",deviceInfoService.getXrayInfo());
        System.out.println(deviceInfoService.getXrayInfo());
        return "index";
    }
}
