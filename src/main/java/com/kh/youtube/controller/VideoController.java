package com.kh.youtube.controller;

import com.kh.youtube.domain.*;
import com.kh.youtube.service.CommentLikeService;
import com.kh.youtube.service.VideoCommentService;
import com.kh.youtube.service.VideoLikeService;
import com.kh.youtube.service.VideoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/*")
@Log4j2
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class VideoController {
    @Value("${spring.servlet.multipart.location}") // application.properties에 있는 변수
    private String uploadPath;

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
    public ResponseEntity<Video> createVideo(MultipartFile video, MultipartFile image, String title, String desc, String categoryCode) {
        log.info("video : " + video);
        log.info("image : " + image);
        log.info("title : " + title);
        log.info("desc : " + desc);
        log.info("categoryCode : " + categoryCode);
        // video_title, video_desc, video_url, video_photo, category_code

        // 업로드 처리
        // 비디오의 실제 파일 이름
        String originalVideo = video.getOriginalFilename();
        String originalImage = image.getOriginalFilename();
        log.info("original : " + originalVideo);
        String realVideo = originalVideo.substring(originalVideo.lastIndexOf("/")+1);
        String realImage = originalImage.substring(originalImage.lastIndexOf("/")+1);

        log.info("realVideo : " + realVideo);

        // UUID
        String uuid = UUID.randomUUID().toString();

        // 실제로 저장할 파일 명 (위치 포함)
        String saveVideo = uploadPath + File.separator + uuid + "_" + realVideo;
        Path pathVideo = Paths.get(saveVideo);
        String saveImage = uploadPath + File.separator + uuid + "_" + realImage;
        Path pathImage = Paths.get(saveImage);
        try {
            video.transferTo(pathVideo);
            image.transferTo(pathImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Video vo = new Video();
        vo.setVideoTitle(title);
        vo.setVideoDesc(desc);
        vo.setVideoUrl(saveVideo);
        vo.setVideoPhoto(saveImage);
        Category category = new Category();
        category.setCategoryCode(Integer.parseInt(categoryCode));
        vo.setCategory(category);
        Channel channel = new Channel();
        channel.setChannelCode(22);
        vo.setChannel(channel);
        Member member = new Member();
        member.setId("test");
        vo.setMember(member);

//        return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.OK).body(videoService.create(vo));
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
