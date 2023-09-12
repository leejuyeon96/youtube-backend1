package com.kh.youtube.service;

import com.kh.youtube.domain.Category;
import com.kh.youtube.domain.Channel;
import com.kh.youtube.domain.Member;
import com.kh.youtube.domain.Video;
import com.kh.youtube.repo.CategoryDAO;
import com.kh.youtube.repo.ChannelDAO;
import com.kh.youtube.repo.MemberDAO;
import com.kh.youtube.repo.VideoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {

    @Autowired
    private VideoDAO videoDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private ChannelDAO channelDAO;

    @Autowired
    private MemberDAO memberDAO;

    public List<Video> showall() { return videoDAO.findAll();}

    public Video show(int id) {
        Video video = videoDAO.findById(id).orElse(null);
        Category category = categoryDAO.findById(video.getCategory().getCategoryCode()).orElse(null);
        Channel channel = channelDAO.findById(video.getChannel().getChannelCode()).orElse(null);
        Member member = memberDAO.findById(video.getMember().getId()).orElse(null);
        video.setCategory(category);
        video.setChannel(channel);
        video.setMember(member);
        return video;
    }

    public Video create(Video video) {
        return videoDAO.save(video);
    }

    public Video update(Video video) {
        Video target = videoDAO.findById(video.getVideoCode()).orElse(null);
        if(target!=null) {
            return videoDAO.save(video);
        }
        return null;
    }

    public Video delete(int id) {
        Video target = show(id);
        videoDAO.delete(target);
        return target;
    }

    public List<Video> showChannelVideo(int id) {
        return videoDAO.showChannelVideo(id);
    }
}
