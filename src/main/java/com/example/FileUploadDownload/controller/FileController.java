package com.example.FileUploadDownload.controller;

import com.example.FileUploadDownload.payload.FileResponse;
import com.example.FileUploadDownload.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/upload")
    public ResponseEntity<FileResponse> uploadFile(
            @RequestParam("file") MultipartFile file
    ) {
        String fileName = null;
        try {
            fileName = this.fileService.uploadFile(path, file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new ResponseEntity<FileResponse>(new FileResponse(null, "File is not uploaded"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<FileResponse>(new FileResponse(fileName, "File is successfully uploaded"), HttpStatus.OK);
    }

    @GetMapping(value = "/image/{fileName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadFile(@PathVariable("fileName") String fileName, HttpServletResponse response) throws IOException {
       InputStream resource = this.fileService.getResource(path, fileName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
