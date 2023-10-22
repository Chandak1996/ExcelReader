package com.demo.excellerader.service;

import com.demo.excellerader.dto.FollowerDto;
import com.demo.excellerader.dto.RecordFileDto;

import java.io.InputStream;
import java.util.List;

public interface FileHandlerService {
    public void uploadFile(String fileName,InputStream inputStream);
    public List<RecordFileDto> getUploadedFile();

    public List<FollowerDto> getSpecificRecords(long fileId);

    public void removeSpecificRecords(long fileId);
}
