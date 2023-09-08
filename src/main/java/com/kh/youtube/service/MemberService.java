package com.kh.youtube.service;

import com.kh.youtube.domain.Member;
import com.kh.youtube.repo.MemberDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MemberService {
    @Autowired
    private MemberDAO dao;

    public List<Member> showAll() {
        return dao.findAll(); // SELECT * FROM MEMBER
    }

    public Member show(String id) {
        return dao.findById(id).orElse(null); // SELECT * FROM MEMBER WHERE id=?
    }

    public Member create(Member member) {
        log.info("member : " + member);
        return  dao.save(member); // INSERT INTO MEMBER (ID, PASSWORD, NAME, AUTHORITY) VALUES(?,?,?, 'ROLE_USER')
    }


    public Member update(Member member) {
        Member target = dao.findById(member.getId()).orElse(null);
        if(target!=null) {
            return dao.save(member); // 이미 데이터가 있는경우 수정, 없는경우 생성으로 작동한다. UPDATE MEMBER SET ID =?, PASSWORD=?, NAME=?, AUTHORITY=?
        }
        return null;
    }

    // DELETE FROM MEMBER WHERE ID=?
    public Member delete(String id) {
        Member target = dao.findById(id).orElse(null);
        dao.delete(target);
        return target;
    }

}
