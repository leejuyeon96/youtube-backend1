package com.kh.youtube.service;

import com.kh.youtube.domain.Channel;
import com.kh.youtube.domain.Member;
import com.kh.youtube.domain.Subscribe;
import com.kh.youtube.repo.ChannelDAO;
import com.kh.youtube.repo.MemberDAO;
import com.kh.youtube.repo.SubscribeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscribeService {

    @Autowired
    private SubscribeDAO subscribeDAO;

    @Autowired
    private MemberDAO memberDAO;

    @Autowired
    private ChannelDAO channelDAO;

    public List<Subscribe> showall() {
        return subscribeDAO.findAll();
    }

    public Subscribe show(int id) {
        Subscribe subscribe = subscribeDAO.findById(id).orElse(null);
        Member member = memberDAO.findById(subscribe.getMember().getId()).orElse(null);
        Channel channel = channelDAO.findById(subscribe.getChannel().getChannelCode()).orElse(null);
        subscribe.setMember(member);
        subscribe.setChannel(channel);
        return subscribe;
    }

    public Subscribe create(Subscribe subscribe) {
        return subscribeDAO.save(subscribe);
    }

    public Subscribe update(Subscribe subscribe) {
        Subscribe target = subscribeDAO.findById(subscribe.getSubsCode()).orElse(null);
        if(target!=null) {
            return subscribeDAO.save(subscribe);
        }
        return null;
    }

    public Subscribe delete(int id) {
        Subscribe target = show(id);
        subscribeDAO.delete(target);
        return target;
    }

    public List<Subscribe> showSubscribeByMember(String id) {
        return subscribeDAO.showSubscribeByMember(id);
    }
}
