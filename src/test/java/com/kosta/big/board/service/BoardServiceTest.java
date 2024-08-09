package com.kosta.big.board.service;

import com.kosta.big.board.domain.BoardEntity;
import com.kosta.big.board.dto.BoardDto;
import com.kosta.big.board.repository.BoardRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    void setUp() {
        // 테스트 데이터 삽입
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setTitle("Test Title");
        boardEntity.setContents("Test Contents");
        boardEntity.setRegid("testUser");
        boardEntity.setRegdate(new Date());
        boardRepository.save(boardEntity);
    }

    @Test
    @DisplayName("목록 테스트")
    void Read(){
        List<BoardDto> read = boardService.svcBoardRead();
        Assertions.assertNotNull(read);
        Assertions.assertFalse(read.isEmpty(), "리스트가 비어 있습니다.");
        System.out.println("read -> " + read);
    }

    @Test
    @DisplayName("생성 테스트")
    void Create(){
        List<BoardDto> createBoard = Arrays.asList(
                BoardDto.builder().title("게시글제목_1번").contents("게시글내용_1번").build(),
                BoardDto.builder().title("게시글제목_2번").contents("게시글내용_2번").build(),
                BoardDto.builder().title("게시글제목_3번").contents("게시글내용_3번").build()
        );
        boardService.svcBoardCreate(createBoard);
    }

    @Test
    @DisplayName("수정 테스트")
    void Update(){
        Optional<BoardEntity> updateBoard = boardRepository.findById(2542609762118434816L);
        updateBoard.ifPresent( boardEntity -> {
            boardEntity.setTitle("수정_게시글제목_1번");
            boardEntity.setContents("수정_게시글내용_1번");
            boardEntity.setRegdate(new Date());
            boardRepository.save(boardEntity);
        });
    }

    @Test
    @DisplayName("삭제 테스트")
    void Delete(){
        Optional<BoardEntity> deleteBoard = boardRepository.findById(2542609762118434816L);
        deleteBoard.ifPresent( boardEntity -> {
            boardRepository.delete(boardEntity);
        });

        Optional<BoardEntity> failedDeleteBoard = boardRepository.findById(2542609762118434816L);
        Assert.assertFalse(failedDeleteBoard.isPresent());

    }
}
