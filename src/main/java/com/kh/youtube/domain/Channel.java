package com.kh.youtube.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert
public class Channel {

    @Id
    @Column(name="CHANNEL_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "channelSequence")
    @SequenceGenerator(name = "channelSequence", sequenceName = "SEQ_CHANNEL", allocationSize = 1)
    private int channelCode;

    @Column(name = "CHANNEL_NAME")
    private String channelName;

    @Column(name = "CHANNEL_DESC")
    private String channelDesc;

    @Column(name = "CHANNEL_DATE")
    private Date channelDate;

    @Column(name = "CHANNEL_PHOTO")
    private String channelPhoto;

    @ManyToOne // Channel 엔티티와 Member 엔티티를 다대일 관계로 설정
    @JoinColumn(name = "id")
    private Member member;
}
