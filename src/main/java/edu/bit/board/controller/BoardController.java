package edu.bit.board.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.bit.board.page.Criteria;
import edu.bit.board.page.PageMaker;
import edu.bit.board.service.BoardService;
import edu.bit.board.vo.BoardVO;

@Controller
public class BoardController {

	@Inject
	BoardService boardService;

	@RequestMapping("/list")
	public String list(Model model) {
		System.out.println("list()");

		model.addAttribute("list", boardService.selectBoardList());
		return "list";
	}

	@RequestMapping("/write_view")
	public String write(Model model) {
		System.out.println("write_view()");

		return "write_view";
	}

	@RequestMapping("/write")
	public String write(BoardVO boardVO) {
		System.out.println("write()");

		boardService.insertBoard(boardVO);

		return "redirect:list";
	}

	@RequestMapping("/content_view")
	public String write(HttpServletRequest request, Model model) {
		System.out.println("content_view()");

		String bId = request.getParameter("bId");
		model.addAttribute("content_view", boardService.selectBoardOne(bId));

		return "content_view";
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(BoardVO boardVO) {
		System.out.println("update()");

		boardService.updateBoard(boardVO);

		return "redirect:list";
	}

	@RequestMapping("/reply_view")
	public String reply_view(HttpServletRequest request, Model model) {
		System.out.println("reply_view()");

		String bId = request.getParameter("bId");
		model.addAttribute("reply_view", boardService.selectBoardOne(bId));

		return "reply_view";
	}

	// *** 쿼리가 두 개!!
	@RequestMapping("/reply")
	public String reply(BoardVO boardVO) {
		System.out.println("reply()");

		boardService.writeReply(boardVO);

		return "redirect:list";
	}

	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		System.out.println("delete()");

		String bId = request.getParameter("bId");
		boardService.deleteBoard(bId);

		return "redirect:list";
	}

	// paging 처리한 후의 list
	@RequestMapping("/list2") //
	// Criteria criteria는 어디서 넘어오는 걸까..?? 어디서 넘어오는 것도 아닌데..
	// 처음에는 default 생성자로 받아서 객체 생성되고(spring이 해줌)
	// 그 담부터는 list2.jsp에서 for문 돌릴때 값 받아서 생성된다.
	public String list2(Criteria criteria, Model model) {
		System.out.println("list2()");

		// PageMaker는 xml로 하는게 아니라 그냥 new 해준다.
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(criteria);

		System.out.println(criteria.getPerPageNum());
		System.out.println(criteria.getPage());

		// 전체 글 개수 세는 함수 부르기
		int totalCount = boardService.selectCountBoard();
		System.out.println("전체 게시물 수를 구함: " + totalCount);

		// 전체 값 세팅
		pageMaker.setTotalCount(totalCount);

		List<BoardVO> boardList = boardService.selectBoardListPage(criteria);

		model.addAttribute("list", boardList);
		model.addAttribute("pageMaker", pageMaker);
		return "list2";
	}

}
