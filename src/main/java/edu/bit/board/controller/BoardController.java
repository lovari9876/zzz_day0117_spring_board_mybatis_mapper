package edu.bit.board.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

	// *** Äõ¸®°¡ µÎ °³!!
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

}
