package com.example.demo.controller;

import java.time.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.*;

import com.example.demo.domain.*;
import com.example.demo.service.*;

@Controller
@RequestMapping("/")
public class BoardController {

	@Autowired
	private BoardService service;
	
	// 경로 : http://localhost:8080?page=3
	// 경로 : http://localhost:8080/list?page=5
	// 게시물 목록
	// @RequestMapping(value = {"/", "list"}, method = RequestMethod.GET)

	// 테이블 형식으로 전체 게시물 보는 역할
	@GetMapping({"/", "list"})
	public String list(Model model) {
		// 1. request param 수집 / 가공
		// 2. business logic 처리
		List<Board> list = service.listBoard();
		// 3. add attribute
		model.addAttribute("boardList", list);
		
		// 4. forward / redirect
		return "list";
	}
	
	// 원하는 id의 게시물 보기
	@GetMapping("/id/{id}")
	public String board(@PathVariable("id") Integer id, Model model) {
		// 1. request param
		// 2. business logic
		Board board = service.getBoard(id);
		// 3. add attribute
		model.addAttribute("board", board);
		// 4. forward / redirect
		return "get";
	}
	
	// 수정하기 전 게시물 보기
	@GetMapping("/modify/{id}")
	public String modifyForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("board", service.getBoard(id));
		return "modify";
	}
	
	// 수정하기
	// @RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
	@PostMapping("/modify/{id}")
	public String modifyProcess(Board board, RedirectAttributes rttr) {
		
		boolean ok = service.modify(board);
		
		if (ok) {
			// 해당 게시물 보기로 리디렉션
			rttr.addFlashAttribute("message", board.getId() + "번 게시물이 수정되었습니다.");
			return "redirect:/id/" + board.getId(); 
		} else {
			// 수정 form 으로 리디렉션
//			rttr.addAttribute("fail", "fail");
			rttr.addFlashAttribute("message", board.getId() + "번 게시물이 수정되지 않았습니다.");
			return "redirect:/modify/" + board.getId();
		}
	}
	
	// 삭제하기
	@PostMapping("remove")
	public String remove(Integer id, RedirectAttributes rttr) {
		boolean ok = service.remove(id);
		if (ok) {
			// query String 에 추가
//			rttr.addAttribute("success", "remove");
			
			// 모델에 추가
			rttr.addFlashAttribute("message", id + "번 게시물이 삭제되었습니다.");
			return "redirect:/list";
		} else {
			return "redirect:/id" + id;
		}
	}
	
	// 인서트하기
	@GetMapping("add")
	public void addForm(Board board) {
		// 게시물 작성 form (view)로 포워드

		
	}
	
	@PostMapping("add")
	public String addProcess(Board board, RedirectAttributes rttr) {
		// 새 게시물 db에 추가
		boolean ok = service.add(board);
		
		if (ok) {
			rttr.addFlashAttribute("message", board.getId() + "번 게시물이 등록되었습니다.");
			return "redirect:/list";
//			return "redirect:/id/" + board.getId();
		} else {
			rttr.addFlashAttribute("message", board.getId() + "게시물 등록 중 문제가 발생하였습니다.");
			return "redirect:/add";
		}
	}
	
}
