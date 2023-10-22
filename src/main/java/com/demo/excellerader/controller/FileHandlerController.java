package com.demo.excellerader.controller;

import com.demo.excellerader.dto.FollowerDto;
import com.demo.excellerader.dto.RecordFileDto;
import com.demo.excellerader.service.FileHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/action")
@RequiredArgsConstructor
public class FileHandlerController {
    private final FileHandlerService fileHandlerService;

    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<String> upload(@RequestPart(required = true) MultipartFile multipartFile) throws IOException {
        String fileName=multipartFile.getOriginalFilename();
        fileHandlerService.uploadFile(fileName,multipartFile.getInputStream());
        return ResponseEntity.ok("File Uploded");
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/recordFiles")
    ResponseEntity<List<RecordFileDto>> recordFiles(){
        return ResponseEntity.ok(fileHandlerService.getUploadedFile());
    }

    @PostMapping("/specificRecords")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    ResponseEntity<List<FollowerDto>> specificRecords(@RequestParam(name="fileId") long fileId){
        return ResponseEntity.ok(fileHandlerService.getSpecificRecords(fileId));
    }

    @PostMapping("/eraseRecords")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<String> RemoveSpecificRecords(@RequestParam(name="fileId") long fileId){
        fileHandlerService.removeSpecificRecords(fileId);
        return ResponseEntity.ok("Record Removed");
    }

}
