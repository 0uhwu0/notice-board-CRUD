package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.*;

import com.example.demo.domain.*;
import com.example.demo.service.*;

@Controller
@RequestMapping("member")
public class MemberController {

	@Autowired
	private MemberService service;

	@GetMapping("signup")
	public void signupForm() {

	}

	@PostMapping("signup")
	public String signupProcess(Member member, RedirectAttributes rttr) {
		System.out.println(member);
		try {
			service.signup(member);
			rttr.addFlashAttribute("message", "회원 가입되었습니다.");
			return "redirect:/list";
		} catch (Exception e) {
			e.printStackTrace();
			rttr.addFlashAttribute("member", member);
			rttr.addFlashAttribute("message", "회원 가입 중 문제가 발생했습니다.");
			return "redirect:/signup";
		}
	}
	
	@GetMapping("list")
	public void list(Member member, Model model) {
		List<Member> list = service.listMember(member);
		model.addAttribute("memberList", list);
	}
	
	// 경로 : /member/info/?id=a......
	@GetMapping("info")
	public void info(String id, Model model) {
		Member member = service.get(id);
		model.addAttribute("member", member);
	}
}