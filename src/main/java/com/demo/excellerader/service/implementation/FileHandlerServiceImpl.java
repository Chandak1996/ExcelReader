package com.demo.excellerader.service.implementation;

import com.demo.excellerader.dto.FollowerDto;
import com.demo.excellerader.dto.RecordFileDto;
import com.demo.excellerader.model.Follower;
import com.demo.excellerader.model.RecordFile;
import com.demo.excellerader.repository.FileRecordRepository;
import com.demo.excellerader.repository.FollowerRepository;
import com.demo.excellerader.service.FileHandlerService;
import com.demo.excellerader.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileHandlerServiceImpl implements FileHandlerService {
    private final FollowerRepository followerRepository;
    private final FileRecordRepository fileRecordRepository;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;

    @Override
    public void uploadFile(String fileName,InputStream inputStream) {
        List<Follower> dataList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            RecordFile recordFile = new RecordFile();
            recordFile.setFileName(fileName);
            recordFile.setUploadTime(new Date());
            recordFile.setLastAccessTime(new Date());
            recordFile.setLastUser(jwtService.getLoggedInUserId());
            recordFile = fileRecordRepository.save(recordFile);
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            int currentRowNumber = 0;

            for (Row row : sheet) {
                if (currentRowNumber == 0) {
                    currentRowNumber++;
                    continue;
                }

                Map<String, Object> rowData = new LinkedHashMap<>();
                for (Cell cell : row) {
                    int columnIndex = cell.getColumnIndex();
                    Row headerRow = sheet.getRow(0);

                    String columnName = headerRow.getCell(columnIndex).getStringCellValue();

                    switch (cell.getCellType()) {
                        case NUMERIC:
                            rowData.put(columnName, cell.getNumericCellValue());
                            break;
                        case STRING:
                            rowData.put(columnName, cell.getStringCellValue());
                            break;
                    }
                }

                String json = objectMapper.writeValueAsString(rowData);
                Follower follower = objectMapper.readValue(json, Follower.class);
                follower.setRecordFile(recordFile);
                dataList.add(follower);

                currentRowNumber++;
            }
            followerRepository.saveAll(dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<RecordFileDto> getUploadedFile(){
        List<RecordFile> files = fileRecordRepository.findAll();
        List<RecordFileDto>  filesDto = files.stream()
                .map(f -> modelMapper.map(f, RecordFileDto.class))
                .collect(Collectors.toList());
        return filesDto;
    }

    @Override
    public List<FollowerDto> getSpecificRecords(long fileId){
        RecordFile file= null;
        file = fileRecordRepository.findByFileId(fileId);
        List<Follower> records = null;
        if(file!=null){
            records = followerRepository.findByRecordFileFileId(file.getFileId());
        }
        List<FollowerDto> recordDto= records.stream().map(r->modelMapper.map(r, FollowerDto.class)).collect(Collectors.toList());
        file.setLastAccessTime(new Date());
        file.setLastUser(jwtService.getLoggedInUserId());
        fileRecordRepository.save(file);

        return recordDto;
    }

    public void removeSpecificRecords(long fileId){
        followerRepository.deleteByRecordFileFileId(fileId);
        fileRecordRepository.deleteByFileId(fileId);
    }
}
