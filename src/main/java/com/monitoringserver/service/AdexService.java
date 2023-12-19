/*==================================================================
프로젝트명 : 판독 웹뷰어
작성지 : 신정호
작성일 : 2023년 11월 22일
수정일 : 2023년 11월 22일
용도 : 판독 웹뷰어 서비스
변경 이력 :
- 2023년 11월 22일 : 버그 수정 및 기능 개선
==================================================================*/

package com.monitoringserver.service;

import com.jcraft.jsch.*;
import com.monitoringserver.dto.AdexDTO;
import com.monitoringserver.dto.AdexLabelDTO;
import com.monitoringserver.dto.AdexStatusDTO;
import com.monitoringserver.dto.SearchDto;
import com.monitoringserver.mapper.AdexMapper;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.nio.file.*;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

@Service
@RequiredArgsConstructor
public class AdexService {

    private final AdexMapper adexMapper;

    //사진 Bounding Box 만드는 함수
    public void createBbox(String folderPath, String outputPath, CountDownLatch latch){
        SwingUtilities.invokeLater(() -> {
            Integer fontSize = 50;
            org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
            Path dirPath = Paths.get(folderPath);
            // 파일 확장자를 png만 검사함 ( 이유 png파일과 json파일의 이름이 같음
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath, "*.png")) {
                for (Path filePath : stream) {
                    String fileName = filePath.getFileName().toString();
                    String jsonFileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".json"; //확장자를 png에서 json으로 변경
                    Path jsonFilePath = dirPath.resolve(jsonFileName);
                    if(adexMapper.duplicateCheck(fileName)==null) { //중복성 체크 DB안에 데이터가 있으면 실행하지 않음
                        if (Files.exists(jsonFilePath)) {
                            try (FileReader reader = new FileReader(jsonFilePath.toFile())) {
                                Integer topLabelNum = 0;
                                Integer labelNum = 1;
                                JSONObject json = (JSONObject) parser.parse(reader);
                                JSONArray dataArray = (JSONArray) json.get("data");

                                BufferedImage image = ImageIO.read(filePath.toFile());
                                Graphics2D g2d = image.createGraphics();
                                g2d.setFont(new Font("Arial", Font.PLAIN, 20));

                                if (dataArray != null) { //데이터의 값이 있을때
                                    for (int i = 0; i < dataArray.size(); i++) {
                                        JSONObject element = (JSONObject) dataArray.get(i);
                                        JSONArray bbox = (JSONArray) element.get("bbox");
                                        Long labelId = (Long) element.get("label_id");
                                        Double score = (Double) element.get("score");
                                        Double labelScore = Math.round(score * 100.0) / 100.0;
                                        String label = adexMapper.getLabelName(labelId);
                                        if (label == null) {topLabelNum++;} //라벨링의 순서를 정확하게 하기 위한 변수
                                        if (label != null) {

                                            int x1 = Math.toIntExact((long) bbox.get(0));
                                            int y1 = Math.toIntExact((long) bbox.get(1));
                                            int x2 = Math.toIntExact((long) bbox.get(2));
                                            int y2 = Math.toIntExact((long) bbox.get(3));

                                            int r; int g; int b;
                                            if(labelNum == 1){r = 255; g = 0; b = 0;}
                                            else if(labelNum == 2){r = 0; g = 0; b = 255;}
                                            else if(labelNum == 3){r = 0; g = 255; b = 0;}
                                            else if(labelNum == 4){r = 255; g = 12; b = 136;}
                                            else if(labelNum == 5){r = 164; g = 0; b = 255;}
                                            else{
                                                Random rand = new Random();
                                                r = rand.nextInt(256);
                                                g = rand.nextInt(256);
                                                b = rand.nextInt(256);
                                            }
                                            Color labelColor = new Color(r, g, b);

                                            g2d.setColor(labelColor);
                                            g2d.setStroke(new BasicStroke(6));
                                            Font font = new Font("NanumGothic", Font.BOLD, fontSize);
                                            g2d.setFont(font);
                                            g2d.drawRect(x1, y1, x2 - x1, y2 - y1);

                                            g2d.setColor(labelColor);
                                            //라벨 그리기
                                            g2d.drawString(labelNum + "." + label+ "("+ labelScore+")", 0, fontSize + (i - topLabelNum) * (fontSize + 20));
                                            labelNum++;
                                        }
                                    }
                                }
                                if(labelNum == 1){ //감지된 데이터가 없으면 실행 ( 최초 labelNum 값은 1이고 감지된 물품이 있을때마다 +1한다 그러므로 labelNum값이 1이면 감지된 물품이 없다)
                                    g2d.setColor(new Color(0, 0, 0));
                                    g2d.setStroke(new BasicStroke(6));
                                    Font font = new Font("NanumGothic", Font.BOLD, fontSize);
                                    g2d.setFont(font);
                                    g2d.drawString("위해물품 없음", 0, fontSize);
                                }
                                g2d.dispose(); // 그래픽 컨텍스트 해제

                                String outputImagePath = outputPath + "/" + fileName;
                                ImageIO.write(image, "png", new File(outputImagePath));
                                String[] lId = fileName.split("_");

                                adexMapper.insertAdex(fileName, lId[0]);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            latch.countDown();
        });
    }

    public AdexDTO getRecentImage(){return adexMapper.getRecentImage();}

    public AdexStatusDTO getAdexStatus(){return adexMapper.getAdexStatus();}
    public List<AdexDTO> getSumImage(){return adexMapper.getSumImage();}
    public List<AdexLabelDTO> getTop10(){return adexMapper.getTop10();}

    public Page<AdexDTO> getAdexList(Pageable pageable, SearchDto searchDto){
        searchDto.setOffset(pageable.getOffset());
        searchDto.setPageSize(pageable.getPageSize());
        List<AdexDTO> list = adexMapper.getAdexList(searchDto);
        Integer totalCount = adexMapper.getAdexCount();
        return new PageImpl<>(list, pageable, totalCount);
    }
    public Integer getAdexCount(){return adexMapper.getAdexCount();}

    public List<AdexDTO> getListSubImage(String luggageId){return adexMapper.getSubImageList(luggageId);}
    public String getLastLugId(){return adexMapper.getLastLugId();}

    public void down(String folderPath) { //31 GPU서버에서 사진파일과 Json데이터롤 다운받기 위한 서비스
        JSch jsch = new JSch();
        try {
            Session session = jsch.getSession("root", "172.16.150.31");
            session.setPassword("cubox2023!");
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            String remoteDirectory = "/home/cubox/image";
            Vector<ChannelSftp.LsEntry> files = channelSftp.ls(remoteDirectory);

            for (ChannelSftp.LsEntry file : files) {
                if (!file.getAttrs().isDir()) {
                    String filename = file.getFilename();

                    // 확장자를 모두 ".png"으로 변경
                    String filenamePng = filename.replaceAll("\\.[^.]+$", ".png");
                    if (adexMapper.duplicateCheck(filenamePng) == null) { //DB에 데이터 이미 존재하면 실행 안함
                        if (filename.endsWith(".png") || filename.endsWith(".json")) {
                            String remoteFilePath = remoteDirectory + "/" + filename;
                            channelSftp.get(remoteFilePath, folderPath);
                            System.out.println("Downloaded file: " + filename);
                        }
                    }
                }
            }

            channelSftp.disconnect();
            session.disconnect();
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        }
    }


}
