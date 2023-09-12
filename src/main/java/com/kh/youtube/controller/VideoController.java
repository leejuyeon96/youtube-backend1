package com.kh.youtube.controller;

import com.kh.youtube.domain.*;
import com.kh.youtube.service.CommentLikeService;
import com.kh.youtube.service.VideoCommentService;
import com.kh.youtube.service.VideoLikeService;
import com.kh.youtube.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/*")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoLikeService videoLikeService;

    @Autowired
    private VideoCommentService videoCommentService;

    @Autowired
    private CommentLikeService commentLikeService;

    // 영상 전체 조회 GET - http://localhost:8081/api/video
    @GetMapping("/video")
    public ResponseEntity<List<Video>> showVideoList() {
        return ResponseEntity.status(HttpStatus.OK).body(videoService.showall());
    }

    // 영상 추가 POST - http://localhost:8081/api/video
    @PostMapping("/video")
    public ResponseEntity<Video> createVideo(@RequestBody Video video) {
        return ResponseEntity.status(HttpStatus.OK).body(videoService.create(video));
    }

    // 영상 수정 PUT - http://localhost:8081/api/video
    @PutMapping("/video")
    public ResponseEntity<Video> updateVideo(@RequestBody Video video) {
        return ResponseEntity.status(HttpStatus.OK).body(videoService.update(video));
    }

    // 영상 삭제 DELETE - http://localhost:8081/api/video/1
    @DeleteMapping("/video/{id}")
    public ResponseEntity<Video> deleteVideo(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(videoService.delete(id));
    }

    // 영상 1개 조회 GET - http://localhost:8081/api/video/1
    @GetMapping("/video/{id}")
    public ResponseEntity<Video> selectVideo(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(videoService.show(id));
    }

    // 영상 1개에 따른 댓글 전체 조회 GET - http://localhost:8081/api/video/1/comment
    @GetMapping("/video/{id}/comment")
    public ResponseEntity<List<VideoComment>> showCommentByVideo(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(videoCommentService.showCommentByVideo(id));
    }

    // 좋아요 추가 POST - http://localhost:8081/api/video/like
    @PostMapping("/video/like")
    public ResponseEntity<VideoLike> likeVideo(@RequestBody VideoLike videoLike) {
        return ResponseEntity.status(HttpStatus.OK).body(videoLikeService.create(videoLike));
    }

    // 좋아요 취소 DELETE - http://localhost:8081/api/video/like/1
    @DeleteMapping("/video/like/{id}")
    public ResponseEntity<VideoLike> cancelLikeVideo(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(videoLikeService.delete(id));
    }

    // 댓글 추가 POST - http://localhost:8081/api/video/comment
    @PostMapping("/video/comment")
    public ResponseEntity<VideoComment> postComment(@RequestBody VideoComment videoComment) {
        return ResponseEntity.status(HttpStatus.OK).body(videoCommentService.create(videoComment));
    }

    // 댓글 수정 PUT - http://localhost:8081/api/video/comment
    @PutMapping("/video/comment")
    public ResponseEntity<VideoComment> updateComment(@RequestBody VideoComment videoComment) {
        return ResponseEntity.status(HttpStatus.OK).body(videoCommentService.update(videoComment));
    }

    // 댓글 삭제 DELETE - http://localhost:8081/api/video/comment/1
    @DeleteMapping("/video/comment/{id}")
    public ResponseEntity<VideoComment> deleteComment(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(videoCommentService.delete(id));
    }

    // 댓글 좋아요 추가 POST - http://localhost:8081/api/video/comment/like
    @PostMapping("/video/comment/like")
    public ResponseEntity<CommentLike> likeComment(@RequestBody CommentLike commentLike) {
        return ResponseEntity.status(HttpStatus.OK).body(commentLikeService.create(commentLike));
    }

    // 댓글 좋아요 취소 DELETE - http://localhost:8081/api/video/comment/like/1
    @PostMapping("/video/comment/like/{id}")
    public ResponseEntity<CommentLike> likeComment(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(commentLikeService.delete(id));
    }
}
