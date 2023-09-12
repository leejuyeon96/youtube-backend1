package com.kh.youtube.service;

import com.kh.youtube.domain.CommentLike;
import com.kh.youtube.domain.Member;
import com.kh.youtube.domain.VideoComment;
import com.kh.youtube.repo.CommentLikeDAO;
import com.kh.youtube.repo.MemberDAO;
import com.kh.youtube.repo.VideoCommentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentLikeService {

    @Autowired
    private CommentLikeDAO commentLikeDAO;

    @Autowired
    private VideoCommentDAO videoCommentDAO;

    @Autowired
    private MemberDAO memberDAO;

    public List<CommentLike> showall() {
        return commentLikeDAO.findAll();
    }

    public CommentLike show(int id) {
        CommentLike commentLike = commentLikeDAO.findById(id).orElse(null);
        VideoComment videoComment = videoCommentDAO.findById(commentLike.getComment().getCommentCode()).orElse(null);
        Member member = memberDAO.findById(commentLike.getMember().getId()).orElse(null);
        commentLike.setComment(videoComment);
        commentLike.setMember(member);
        return commentLike;
    }

    public CommentLike create(CommentLike commentLike) {
        return commentLikeDAO.save(commentLike);
    }

    public CommentLike update(CommentLike commentLike) {
        CommentLike target = commentLikeDAO.findById(commentLike.getCommLikeCode()).orElse(null);
        if(target!=null) {
            return commentLikeDAO.save(commentLike);
        }
        return null;
    }

    public CommentLike delete(int id) {
        CommentLike target = show(id);
        commentLikeDAO.delete(target);
        return target;
    }
}