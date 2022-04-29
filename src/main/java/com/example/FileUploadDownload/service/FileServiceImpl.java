package com.example.FileUploadDownload.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{
    @Override
    public String uploadFile(String path, MultipartFile multipartFile) throws IOException {

        String fileName=multipartFile.getOriginalFilename();

        String randomID= UUID.randomUUID().toString();
        String fileName1=randomID.concat(fileName.substring(fileName.lastIndexOf(".")));


        String filePath=path+File.separator+fileName1;


        File file=new File(path);

        if(!file.exists()) {
            file.mkdir();

        }

        Files.copy(multipartFile.getInputStream(), Paths.get(filePath));

        return fileName;
    }

    @Override
    public InputStream getResource(String path, String filename) throws FileNotFoundException {
        String fullPath=path+File.separator+filename;
        InputStream is=new FileInputStream((fullPath));
        return is;
    }


}
