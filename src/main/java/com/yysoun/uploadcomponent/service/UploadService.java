package com.yysoun.uploadcomponent.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UploadService {

    private String location = "upload-dir";

    public String createSecretKey() throws NoSuchAlgorithmException {
        String secretKey = null;
        secretKey = this.encryptMD5(String.valueOf(System.currentTimeMillis()));
        secretKey = secretKey.substring(secretKey.length() - 16);
        return secretKey;
    }
    public String encryptMD5(String strIN) throws NoSuchAlgorithmException {
        MessageDigest alg = MessageDigest.getInstance("MD5");
        alg.update(strIN.getBytes());
        byte[] bytes = alg.digest();
        StringBuilder hexString = new StringBuilder();

        for (byte aByte : bytes) {
            String temp = Integer.toHexString(255 & aByte);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }

            hexString.append(temp);
        }

        return hexString.toString();
    }

    public ArrayList<String> store(List<MultipartFile> files) throws NoSuchAlgorithmException {

        ArrayList<String> urls = new ArrayList<>();

        for (int i=0; i< files.size(); i++){

            MultipartFile file = files.get(i);

            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }
            String filename = file.getOriginalFilename();
            String suffixName = filename.substring(filename.lastIndexOf("."));
            File dest = new File(System.getProperties().getProperty("user.dir")+ "/" + Paths.get(location).resolve(Paths.get(this.createSecretKey() + suffixName)).toString());
            if (!dest.getParentFile().exists()){
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
                urls.add("https://www.yyosun.com/typora/"+ dest.getName());
            }catch (IllegalStateException | IOException e){
                e.printStackTrace();
            }
        }
        return urls;
    }
}
