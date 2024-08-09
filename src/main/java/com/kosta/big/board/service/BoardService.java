package com.kosta.big.board.service;

import com.kosta.big.board.domain.BoardEntity;
import com.kosta.big.board.dto.BoardDto;
import com.kosta.big.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

//    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void svcBoardCreate(List<BoardDto> boards) {
        List<BoardEntity> boardEntity = boards.stream()
                .map(BoardDto :: asEntity)
                .collect(Collectors.toList());

        boardRepository.saveAll(boardEntity);
    }

    @Transactional(readOnly = true)
    public List<BoardDto> svcBoardRead(){
        return boardRepository.findAll()
                .stream()
                .map(BoardDto :: entityOf)
                .collect(Collectors.toList());
    }

    @Transactional
    public BoardDto svcBoardUpdate(Long bseq , BoardDto boardDto){
        BoardEntity boardEntity = boardRepository.findById(bseq)
                .orElseThrow(() -> {
                  return new IllegalStateException("게시글을 찾을 수 없습니다.");
                });
        boardEntity.setTitle(boardDto.getTitle());
        boardEntity.setContents(boardDto.getContents());
        return boardDto.entityOf(boardEntity);
    }

    @Transactional
    public void svcBoardDelete(Long bseq){
        BoardEntity boardEntity = boardRepository.findById(bseq)
                .orElseThrow(() -> {return new IllegalStateException("Bseq를 찾을 수 없습니다.");
                });
        boardRepository.deleteById(bseq);
    }
}
