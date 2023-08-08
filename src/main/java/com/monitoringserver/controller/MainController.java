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

    private static final String UPLOAD_DIR = "classpath:static/img/test/";

    @GetMapping(value = "/")
    public String DashBoard(Model model){
        Double viewerWaitingPercentD; Double viewerReadingPercentD; Double xrayWaitingPercentD; Double xrayReadingPercentD;
        Double trsWaitingPercentD; Double trsReadingPercentD;
        //각 상태값과 총 장비수를 나눠서 퍼센트 계산
        // Nan(0으로 나누는것) 막기위해 장비수가 0이면 나누지 말고 0으로 표시
        if(deviceInfoService.getViewerInfo().size()==0){
            viewerWaitingPercentD = 0d; viewerReadingPercentD = 0d;
        } else {
            viewerWaitingPercentD = ((deviceInfoService.getViewerWaitingCount()/(double)deviceInfoService.getViewerInfo().size())*100);
            viewerReadingPercentD = ((deviceInfoService.getViewerReadingCount()/(double)deviceInfoService.getViewerInfo().size())*100);
        }

        if(deviceInfoService.getXrayInfo().size()==0){
            xrayWaitingPercentD = 0d; xrayReadingPercentD = 0d;
        } else{
            xrayWaitingPercentD = ((deviceInfoService.getXrayWaitingCount()/(double)deviceInfoService.getXrayInfo().size())*100);
            xrayReadingPercentD = ((deviceInfoService.getXrayReadingCount()/(double)deviceInfoService.getXrayInfo().size())*100);
        }

        if(deviceInfoService.getTrsInfo().size()==0){
            trsWaitingPercentD = 0d; trsReadingPercentD = 0d;
        } else {
            trsWaitingPercentD = ((deviceInfoService.getTrsWaitingCount()/(double)deviceInfoService.getTrsInfo().size())*100);
            trsReadingPercentD = ((deviceInfoService.getTrsReadingCount()/(double)deviceInfoService.getTrsInfo().size())*100);
        }
        //각 퍼센트를 소수점 2자리까지 단 퍼센트가 0이거나 100이면 소수점 제외하기
        String viewerWaitingPercent = viewerWaitingPercentD % 1 == 0 ? String.format("%.0f", viewerWaitingPercentD) : String.format("%.2f", viewerWaitingPercentD);
        String viewerReadingPercent = viewerReadingPercentD % 1 == 0 ? String.format("%.0f", viewerReadingPercentD) : String.format("%.2f", viewerReadingPercentD);

        String xrayWaitingPercent = xrayWaitingPercentD % 1 == 0 ? String.format("%.0f", xrayWaitingPercentD) : String.format("%.2f", xrayWaitingPercentD);
        String xrayReadingPercent = xrayReadingPercentD % 1 == 0 ? String.format("%.0f", xrayReadingPercentD) : String.format("%.2f", xrayReadingPercentD);

        String trsWaitingPercent = trsWaitingPercentD % 1 == 0 ? String.format("%.0f", trsWaitingPercentD) : String.format("%.2f", trsWaitingPercentD);
        String trsReadingPercent = trsReadingPercentD % 1 == 0 ? String.format("%.0f", trsReadingPercentD) : String.format("%.2f", trsReadingPercentD);

        String viewerWCount; String viewerRCount; String xrayWCount; String xrayRCount; String trsWCount; String trsRCount;
        
        //만약 상태값 수가 한자리면 앞에 숫자 0을 더함
        Integer viewerWCountO = deviceInfoService.getViewerWaitingCount();
        if(viewerWCountO<10) viewerWCount = "0"+viewerWCountO;
        else viewerWCount = viewerWCountO.toString();

        Integer viewerRCountO = deviceInfoService.getViewerReadingCount();
        if(viewerRCountO<10) viewerRCount = "0"+viewerRCountO;
        else viewerRCount = viewerRCountO.toString();

        Integer xrayWCountO = deviceInfoService.getXrayWaitingCount();
        if(xrayWCountO<10) xrayWCount = "0"+xrayWCountO;
        else xrayWCount = xrayWCountO.toString();

        Integer xrayRCountO = deviceInfoService.getXrayReadingCount();
        if(xrayRCountO<10) xrayRCount = "0"+xrayRCountO;
        else xrayRCount = xrayRCountO.toString();

        Integer trsWCountO = deviceInfoService.getTrsWaitingCount();
        if(trsWCountO<10) trsWCount = "0"+trsWCountO;
        else trsWCount = trsWCountO.toString();

        Integer trsRCountO = deviceInfoService.getTrsReadingCount();
        if(trsRCountO<10) trsRCount = "0"+trsRCountO;
        else trsRCount = trsRCountO.toString();


        model.addAttribute("viewerInfo",deviceInfoService.getViewerInfo());
        model.addAttribute("viewerWaitingCount",viewerWCount);
        model.addAttribute("viewerReadingCount",viewerRCount);
        model.addAttribute("viewerWaitingPercent", viewerWaitingPercent);
        model.addAttribute("viewerReadingPercent", viewerReadingPercent);

        model.addAttribute("xrayInfo",deviceInfoService.getXrayInfo());
        model.addAttribute("xrayWaitingCount",xrayWCount);
        model.addAttribute("xrayReadingCount",xrayRCount);
        model.addAttribute("xrayWaitingPercent", xrayWaitingPercent);
        model.addAttribute("xrayReadingPercent", xrayReadingPercent);

        model.addAttribute("trsInfo",deviceInfoService.getTrsInfo());
        model.addAttribute("trsWaitingCount",trsWCount);
        model.addAttribute("trsReadingCount",trsRCount);
        model.addAttribute("trsWaitingPercent", trsWaitingPercent);
        model.addAttribute("trsReadingPercent", trsReadingPercent);

        model.addAttribute("settings", deviceInfoService.getSettings());

        System.out.println(deviceInfoService.getXrayInfo());
        return "index";
    }

    @GetMapping(value = "/2")
    public String testBoard(Model model){
        return "index2";
    }

}
