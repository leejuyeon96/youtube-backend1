package com.kh.youtube.service;

import com.kh.youtube.domain.*;
import com.kh.youtube.repo.MemberDAO;
import com.kh.youtube.repo.VideoDAO;
import com.kh.youtube.repo.VideoLikeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoLikeService {

    @Autowired
    private VideoLikeDAO videoLikeDAO;

    @Autowired
    private VideoDAO videoDAO;

    @Autowired
    private MemberDAO memberDAO;

    public List<VideoLike> showall() {
        return videoLikeDAO.findAll();
    }

    public VideoLike show(int id) {
        VideoLike videoLike = videoLikeDAO.findById(id).orElse(null);
        Video video = videoDAO.findById(videoLike.getVideo().getVideoCode()).orElse(null);
        Member member = memberDAO.findById(videoLike.getMember().getId()).orElse(null);
        videoLike.setVideo(video);
        videoLike.setMember(member);
        return videoLike;
    }

    public VideoLike create(VideoLike videoLike) {
        return videoLikeDAO.save(videoLike);
    }

    public VideoLike update(VideoLike videoLike) {
        VideoLike target = videoLikeDAO.findById(videoLike.getVLikeCode()).orElse(null);
        if(target!=null) {
            return videoLikeDAO.save(videoLike);
        }
        return null;
    }

    public VideoLike delete(int id) {
        VideoLike target = show(id);
        videoLikeDAO.delete(target);
        return target;
    }
}
