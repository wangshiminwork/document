package com.wsm.document.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @name: TestController
 * @Author: wangshimin
 * @Date: 2019/4/11  10:44
 * @Description:
 */
@Controller
public class TestController {
    @GetMapping("/")
    public String test(){
        return "upload";
    }
    @ResponseBody
    @RequestMapping("/fileUploadLocal")
    public String fileUploadLocal(MultipartFile file){
        //判断文件是否为空
        if(file.isEmpty()){
            return "文件为空，上传失败!";
        }
        try{
            //获得文件的字节流
            byte[] bytes=file.getBytes();
            //获得path对象，也即是文件保存的路径对象
            String contextPath="F://images//";
            Path path= Paths.get(contextPath+file.getOriginalFilename());
            //调用静态方法完成将文件写入到目标路径
            Files.write(path,bytes);
            return "恭喜上传成功!";
        }catch (Exception e){
            e.printStackTrace();
            return  "上传失败";
        }
    }

    @RequestMapping("/download")
    public String downloadFile(HttpServletResponse response) {
        String fileName = "aim_test.txt";// 设置文件名，根据业务需要替换成要下载的文件名
        if (fileName != null) {
            //设置文件路径
            String realPath = "F://images//";
            File file = new File(realPath , fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }
}
