package edu.bit.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.bit.board.mapper.BoardMapper;
import edu.bit.board.page.Criteria;
import edu.bit.board.vo.BoardVO;

@Service
public class BoardService {

	@Autowired
	BoardMapper boardMapper;

	// 게시판 List를 위한 출력하는 비즈니스로직
	public List<BoardVO> selectBoardList() {
		return boardMapper.selectBoardList();
	}

	// 게시판에서 리플을 쓰는 로직: sql문 2개 필요(1. step 1개씩 +1, 2. 댓글작성)
	public void writeReply(BoardVO boardVO) {
		boardMapper.updateShape(boardVO); // 1
		boardMapper.insertReply(boardVO); // 2
	}

	public void insertBoard(BoardVO boardVO) {
		boardMapper.insertBoard(boardVO);
	}

	public BoardVO selectBoardOne(String bId) {
		return boardMapper.selectBoardOne(bId);
	}

	public void updateBoard(BoardVO boardVO) {
		boardMapper.updateBoard(boardVO);
	}

	public void deleteBoard(String bId) {
		boardMapper.deleteBoard(bId);
	}

	public int selectCountBoard() {
		return boardMapper.selectAllBoard();
	}

	public List<BoardVO> selectBoardListPage(Criteria criteria) {
		return boardMapper.selectBoardListPage(criteria);
	}

}
