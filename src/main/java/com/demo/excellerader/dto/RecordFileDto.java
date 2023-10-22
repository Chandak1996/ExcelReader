package com.demo.excellerader.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordFileDto {
    private Long fileId;
    private String fileName;
    private Date uploadTime;
    private Date lastAccessTime;
    private Long lastUser;
}
