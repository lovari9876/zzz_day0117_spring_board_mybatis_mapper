package edu.bit.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import edu.bit.board.vo.BoardVO;

// 전에 dao 만들고 @Repository 붙여서 영속계층을 만들었다면,
// mapper class가 이제 영속계층!!(persistent interface)
public interface BoardMapper {
	@Select("select bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent from mvc_board order by bGroup desc, bStep asc")
	public List<BoardVO> selectBoardList();

	@Insert("insert into mvc_board (bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent) values (mvc_board_seq.nextval, #{bName}, #{bTitle}, #{bContent}, 0, mvc_board_seq.currval, 0, 0 )")
	public void insertBoard(BoardVO param);

	@Select("select * from mvc_board where bId = #{bId}")
	public BoardVO selectBoardOne(String bId);

	@Select("select count(*) from mvc_board")
	public int selectAllBoard();

	@Update("update mvc_board set bStep = bStep + 1 where bGroup = #{bGroup} and bStep > #{bStep}")
	public void updateShape(BoardVO boardVO);

	@Insert("insert into mvc_board (bId, bName, bTitle, bContent, bGroup, bStep, bIndent) values (mvc_board_seq.nextval, #{bName}, #{bTitle},#{bContent}, #{bGroup}, #{bStep}+1, #{bIndent}+1)")
	public void insertReply(BoardVO boardVO);

	@Update("update mvc_board set bName = #{boardVO.bName}, bTitle = #{boardVO.bTitle}, bContent = #{boardVO.bContent} where bId = #{boardVO.bId}")
	public void updateBoard(@Param("boardVO") BoardVO boardVO);

	@Delete("delete mvc_board where bId = #{bId}")
	public void deleteBoard(String bId);

	// @Param 안적어도 어느정도 허용하는 중...
}
