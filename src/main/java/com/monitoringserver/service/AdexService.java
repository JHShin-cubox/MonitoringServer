package com.monitoringserver.service;

import com.jcraft.jsch.*;
import com.monitoringserver.dto.AdexDTO;
import com.monitoringserver.dto.AdexLabelDTO;
import com.monitoringserver.dto.AdexStatusDTO;
import com.monitoringserver.dto.SearchDto;
import com.monitoringserver.mapper.AdexMapper;
import lombok.RequiredArgsConstructor;
import net.minidev.json.parser.JSONParser;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

@Service
@RequiredArgsConstructor
public class AdexService {

    private final AdexMapper adexMapper;

    public void createBbox(){
        SwingUtilities.invokeLater(() -> {
            String folderPath = "C:/project/sshtest"; // json데이터 및 원본사진 폴더
            String outputPath = "C:/project/sshtest/bbox"; // 작업 완료 폴더
//            String folderPath = "/home/cubox/image/"; // json데이터 및 원본사진 폴더 실서버변경
//            String outputPath = "/home/cubox/image/bbox"; // 작업 완료 폴더 실서버변경

            Integer fontSize = 50;
            org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
            Path dirPath = Paths.get(folderPath);
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath, "*.png")) {
                for (Path filePath : stream) {
                    String fileName = filePath.getFileName().toString();
                    String jsonFileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".json";
                    Path jsonFilePath = dirPath.resolve(jsonFileName);
                    if(adexMapper.duplicateCheck(fileName)==null){
                        if (Files.exists(jsonFilePath)) {
                            try (FileReader reader = new FileReader(jsonFilePath.toFile())) {
                                Integer topLabelNum = 0;
                                Integer labelNum = 1;
                                JSONObject json = (JSONObject) parser.parse(reader);
                                JSONArray dataArray = (JSONArray) json.get("data");

                                BufferedImage image = ImageIO.read(filePath.toFile());

                                Graphics2D g2d = image.createGraphics();
                                g2d.setFont(new Font("Arial", Font.PLAIN, 20));
                                if(dataArray != null){
                                    for (int i = 0; i < dataArray.size(); i++) {
                                        JSONObject element = (JSONObject) dataArray.get(i);
                                        JSONArray bbox = (JSONArray) element.get("bbox");
                                        Long labelId = (Long) element.get("label_id");
                                        String label = adexMapper.getLabelName(labelId);
                                        if(label==null){
                                            topLabelNum ++;
                                        }
                                        if(label!=null){

                                            int x1 = Math.toIntExact((long) bbox.get(0));
                                            int y1 = Math.toIntExact((long) bbox.get(1));
                                            int x2 = Math.toIntExact((long) bbox.get(2));
                                            int y2 = Math.toIntExact((long) bbox.get(3));

                                            Random rand = new Random();
                                            int r = rand.nextInt(256);
                                            int g = rand.nextInt(256);
                                            int b = rand.nextInt(256);
                                            Color randomColor = new Color(r, g, b);

                                            g2d.setColor(randomColor);
                                            g2d.setStroke(new BasicStroke(6));
                                            Font font = new Font("NanumGothic", Font.BOLD, fontSize);
                                            g2d.setFont(font);
                                            g2d.drawRect(x1, y1, x2 - x1, y2 - y1);

                                            g2d.setColor(randomColor);
                                            g2d.drawString(labelNum+"."+label, 0, fontSize + (i-topLabelNum) * (fontSize+20));
                                            labelNum++;
                                        }
                                    }
                                    g2d.dispose(); // 그래픽 컨텍스트 해제

                                    String outputImagePath = outputPath + "/" + fileName;
                                    ImageIO.write(image, "png", new File(outputImagePath));
                                    String lId = fileName.substring(0,4);
                                    adexMapper.insertAdex(fileName,lId);
                                    System.out.println(fileName +": 데이터 있음");
                                }
                                else{
                                    try {
                                        Files.copy(filePath, Paths.get(outputPath).resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    adexMapper.insertAdex(fileName,"123");
                                    System.out.println("데이터 없음");
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void createBbox2(String folderPath, String outputPath,CountDownLatch latch){
        SwingUtilities.invokeLater(() -> {
            Integer fontSize = 50;
            org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
            Path dirPath = Paths.get(folderPath);
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath, "*.png")) {
                for (Path filePath : stream) {
                    String fileName = filePath.getFileName().toString();
                    String jsonFileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".json";
                    Path jsonFilePath = dirPath.resolve(jsonFileName);
                    if(adexMapper.duplicateCheck(fileName)==null) {
                        if (Files.exists(jsonFilePath)) {
                            try (FileReader reader = new FileReader(jsonFilePath.toFile())) {
                                Integer topLabelNum = 0;
                                Integer labelNum = 1;
                                JSONObject json = (JSONObject) parser.parse(reader);
                                JSONArray dataArray = (JSONArray) json.get("data");

                                BufferedImage image = ImageIO.read(filePath.toFile());

                                Graphics2D g2d = image.createGraphics();
                                g2d.setFont(new Font("Arial", Font.PLAIN, 20));
                                if (dataArray != null) {
                                    for (int i = 0; i < dataArray.size(); i++) {
                                        JSONObject element = (JSONObject) dataArray.get(i);
                                        JSONArray bbox = (JSONArray) element.get("bbox");
                                        Long labelId = (Long) element.get("label_id");
                                        Double score = (Double) element.get("score");
                                        Double labelScore = Math.round(score * 100.0) / 100.0;
                                        String label = adexMapper.getLabelName(labelId);
                                        if (label == null) {
                                            topLabelNum++;
                                        }
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
                                            g2d.drawString(labelNum + "." + label+ "("+ labelScore+")", 0, fontSize + (i - topLabelNum) * (fontSize + 20));
                                            labelNum++;
                                        }
                                    }
                                }
                                if(labelNum == 1){
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

    public void down(String folderPath) {
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
                    if (adexMapper.duplicateCheck(filenamePng) == null) {
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
