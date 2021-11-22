package com.yysoun.uploadcomponent.controller;

import com.yysoun.uploadcomponent.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("upload")
public class UploadController {
    @Autowired
    private UploadService uploadService;
    @PostMapping("/typora")
    public ArrayList<String> uploadFile(MultipartHttpServletRequest request) throws NoSuchAlgorithmException {
        List<MultipartFile> files = request.getFiles("file");
        return uploadService.store(files);
    }
}
