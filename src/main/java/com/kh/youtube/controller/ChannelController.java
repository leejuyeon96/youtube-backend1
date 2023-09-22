package com.kh.youtube.controller;

import com.kh.youtube.domain.Channel;
import com.kh.youtube.domain.Member;
import com.kh.youtube.domain.Subscribe;
import com.kh.youtube.domain.Video;
import com.kh.youtube.service.ChannelService;
import com.kh.youtube.service.SubscribeService;
import com.kh.youtube.service.VideoService;
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
public class ChannelController {

    @Value("${spring.servlet.multipart.location}") // application.properties에 있는 변수
    private String uploadPath;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private SubscribeService subscribeService;


    // 채널 조회 GET - http://localhost:8081/api/channel/1
    @GetMapping("/channel/{id}")
    public ResponseEntity<Channel> showChannel(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(channelService.show(id));
    }

    // 채널에 있응 영상 조회 GET - http://localhost:8081/api/channel/1/video
    @GetMapping("/channel/{id}/video")
    public ResponseEntity<List<Video>> showChannelVideo(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(videoService.showChannelVideo(id));
    }

    // 채널 추가 POST - http://localhost:8081/api/channel
    @PostMapping("/channel")
    public ResponseEntity<Channel> makeChannel(String name, String desc, MultipartFile photo) {
        String originalPhoto = photo.getOriginalFilename();
        String realPhoto = originalPhoto.substring(originalPhoto.lastIndexOf("/")+1);
        String uuid = UUID.randomUUID().toString();
        String savePhoto = uploadPath + File.separator + uuid + "_" + realPhoto;
        Path pathPhoto = Paths.get(savePhoto);

        try {
            photo.transferTo(pathPhoto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Channel vo = new Channel();
        vo.setChannelPhoto(savePhoto);
        vo.setChannelName(name);
        vo.setChannelDesc(desc);
        Member member = new Member();
        member.setId("test");
        vo.setMember(member);

//        return  ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.OK).body(channelService.create(vo));
    }

    // 채널 수정 PUT - http://localhost:8081/api/channel
    @PutMapping("/channel")
    public ResponseEntity<Channel> updateChannel(@RequestBody Channel channel) {
        return ResponseEntity.status(HttpStatus.OK).body(channelService.update(channel));
    }

    // 채널 삭제 DELETE - http://localhost:8081/api/channel/1
    @DeleteMapping("/channel/{id}")
    public ResponseEntity<Channel> DeleteChannel(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(channelService.delete(id));
    }

    // 내가 구독한 채널 조회 GET - http://localhost:8081/api/subscribe/user1
    @GetMapping("/subscribe/{id}")
    public ResponseEntity<List<Subscribe>> showSubscribeByMember(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(subscribeService.showSubscribeByMember(id));
    }

    // 채널 구독 POST - http://localhost:8081/api/subscribe
    @PostMapping("/subscribe")
    public ResponseEntity<Subscribe> makeSubscribe(@RequestBody Subscribe subscribe) {
        return ResponseEntity.status(HttpStatus.OK).body(subscribeService.create(subscribe));
    }

    // 채널 구독 취소 DELETE - http://localhost:8081/api/subscribe/1
    @DeleteMapping("/subscribe/{id}")
    public ResponseEntity<Subscribe> DeleteSubscribe(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(subscribeService.delete(id));
    }
}
