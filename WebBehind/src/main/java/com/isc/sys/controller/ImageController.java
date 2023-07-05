package com.isc.sys.controller;

import com.isc.common.viewObj.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/image")
public class ImageController {


    @Value("${file-save-path}")
    private String fileSavePath;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-");

    @PostMapping("/upload")
    public Result uploadImage(@RequestParam("file")MultipartFile file, HttpServletRequest request){
        String directory = simpleDateFormat.format(new Date());
        System.out.println(directory);
        File dir = new File(fileSavePath);
        if (! dir.exists()){
            dir.mkdir();
            System.out.println("create this file path");
        }
        System.out.println("图片保存位置："+fileSavePath);
        String suffix = ".jpg";
        System.out.println(suffix);
        String newFileName = directory + UUID.randomUUID() + suffix;
        System.out.println(newFileName);
        File newFile = new File(fileSavePath  + newFileName);
        try {
            file.transferTo(newFile);
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/image/" + newFileName;
            System.out.println("url地址："+url);
            System.out.println((fileSavePath.length()));
            System.out.println(newFile.getCanonicalPath());
            System.out.println((newFile.getCanonicalPath()).length());
            System.out.println(url.length());
            return Result.success("上传成功"+url);
        }
        catch (IOException e){
            return Result.fail("传不了！");
        }
    }

}
