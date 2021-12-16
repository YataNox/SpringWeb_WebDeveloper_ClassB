package com.ezen.shop.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.shop.dto.MemberVO;
import com.ezen.shop.dto.QnaVO;
import com.ezen.shop.service.QnaService;

@Controller
public class QnaController {
	@Autowired
	QnaService qs;
	
	@RequestMapping(value="/qnaList")
	public ModelAndView qnaList(Model model, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("loginUser");
		if(mvo == null) {
			mav.setViewName("member/login");
		}else {
	         mav.addObject("qnaList", qs.listQna(mvo.getId()));
	         mav.setViewName("qna/qnaList");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/qnaView")
	public ModelAndView qnaView(Model model, HttpServletRequest request, @RequestParam("qseq") int qseq) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("loginUser");
		if(mvo == null) {
			mav.setViewName("member/login");
		}else {
			mav.addObject("qnaVO", qs.getQna(qseq));
	        mav.setViewName("qna/qnaView");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/qnaWriteForm")
	public String qnaView(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("loginUser");
		if(mvo == null) {
			return "member/login";
		}else {
			return "qna/qnaWrite";
		}
	}
	
	@RequestMapping(value="/qnaWrite")
	public ModelAndView qnaView(Model model, HttpServletRequest request, @RequestParam("subject") int subject,
			@RequestParam("content") int content) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("loginUser");
		if(mvo == null) {
			mav.setViewName("member/login");
		}else {
			QnaVO qvo = new QnaVO();
			qvo.setSubject(request.getParameter("subject"));
			qvo.setContent(request.getParameter("content"));
			qs.insertQna(qvo, mvo.getId());
		}
		mav.setViewName("redirect:/qnaList");
		return mav;
	}
}
