//package com.kosta.big.board.service;
//
//import com.kosta.big.board.dto.BoardDto;
//import com.kosta.big.board.repository.BoardRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Transactional(readOnly = true)
//@Service
//public class BoardSlaveService {
//
//    @Autowired
//    private BoardRepository boardRepository;
//
//    public List<BoardDto> svcBoardRead(){
//        return boardRepository.findAll()
//                .stream()
//                .map(BoardDto :: entityOf)
//                .collect(Collectors.toList());
//    }
//}
