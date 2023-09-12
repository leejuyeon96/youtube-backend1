package com.kh.youtube.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
public class VideoLike {

    @Id
    @Column(name = "V_LIKE_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "vLikeSequence")
    @SequenceGenerator(name = "vLikeSequence", sequenceName = "SEQ_VIDEO_LIKE", allocationSize = 1)
    private int vLikeCode;

    @Column(name = "V_LIKE_DATE")
    private Date vLikeDate;

    @ManyToOne
    @JoinColumn(name = "VIDEO_CODE")
    private Video video;

    @ManyToOne
    @JoinColumn(name = "id")
    private Member member;
}
