package com.demo.excellerader.repository;

import com.demo.excellerader.dto.RecordFileDto;
import com.demo.excellerader.model.Follower;
import com.demo.excellerader.model.RecordFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface FileRecordRepository extends JpaRepository<RecordFile,Long> {
    RecordFile findByFileId(Long fileId);
    void deleteByFileId(Long fileId);
}
