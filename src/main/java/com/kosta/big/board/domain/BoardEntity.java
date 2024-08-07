package com.kosta.big.board.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@RequiredArgsConstructor
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board3")
public class BoardEntity {

    @Id
    @GeneratedValue
    @Column(name = "bseq")
    private Long bseq;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "content", length = 500)
    private String content;

    @Column(name = "regid", length = 50, updatable = false, columnDefinition = "VARCHAR2(20) DEFAULT 'testUser'")
    private String regid;

    @Column(name = "regdate", columnDefinition = "date default sysdate")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regdate;

    @PrePersist
    protected void onCreate() {
        if (this.regid == null) {
            this.regid = "jpauser";
        }
        if (this.regdate == null) {
            this.regdate = new Date();
        }
    }
}
