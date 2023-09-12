package com.kh.youtube.repo;

import com.kh.youtube.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoDAO extends JpaRepository<Video, Integer> {

    // 채널별 목록보기
    @Query(value = "SELECT * FROM VIDEO WHERE CHANNEL_CODE = :id", nativeQuery = true)
    List<Video> showChannelVideo(int id);

}
