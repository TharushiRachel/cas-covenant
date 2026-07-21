package com.itechro.cas.model.dto.applicationform;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class AFTopicConfigUploadRQ implements Serializable {

    private String fileName;

    private MultipartFile multipartFile;

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "AFTopicConfigUploadRQ{" +
                "fileName='" + fileName + '\'' +
                '}';
    }
}
