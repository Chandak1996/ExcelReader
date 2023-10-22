package com.demo.excellerader.repository;

import com.demo.excellerader.model.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface FollowerRepository extends JpaRepository<Follower,Long> {
    List<Follower> findByRecordFileFileId(Long fileId);
    void deleteByRecordFileFileId(Long fileId);
}
