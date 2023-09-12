package com.kh.youtube.service;

import com.kh.youtube.domain.CommentLike;
import com.kh.youtube.domain.Member;
import com.kh.youtube.domain.Video;
import com.kh.youtube.domain.VideoComment;
import com.kh.youtube.repo.MemberDAO;
import com.kh.youtube.repo.VideoCommentDAO;
import com.kh.youtube.repo.VideoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoCommentService {

    @Autowired
    private VideoCommentDAO videoCommentDAO;

    @Autowired
    private VideoDAO videoDAO;

    @Autowired
    private MemberDAO memberDAO;

    public List<VideoComment> showall() {
        return videoCommentDAO.findAll();
    }

    public VideoComment show(int id) {
        VideoComment videoComment = videoCommentDAO.findById(id).orElse(null);
        Video video = videoDAO.findById(videoComment.getVideo().getVideoCode()).orElse(null);
        Member member = memberDAO.findById(videoComment.getMember().getId()).orElse(null);
        videoComment.setVideo(video);
        videoComment.setMember(member);
        return videoComment;
    }

    public VideoComment create(VideoComment videoComment) {
        return videoCommentDAO.save(videoComment);
    }

    public VideoComment update(VideoComment videoComment) {
        VideoComment target = videoCommentDAO.findById(videoComment.getCommentCode()).orElse(null);
        if(target!=null) {
            return videoCommentDAO.save(videoComment);
        }
        return null;
    }

    public VideoComment delete(int id) {
        VideoComment target = show(id);
        videoCommentDAO.delete(target);
        return target;
    }

    public List<VideoComment> showCommentByVideo(int id) {
        return videoCommentDAO.showCommentByVideo(id);
    }
}
