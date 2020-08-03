package kr.or.connect.guestbook.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.connect.guestbook.argumentresolver.HeaderInfo;
import kr.or.connect.guestbook.dto.Guestbook;
import kr.or.connect.guestbook.service.GuestbookService;

@Controller
public class GuestbookController {
	@Autowired
	GuestbookService service;
	
	@GetMapping(path="/list")
	public String list(@RequestParam(name="start",required=false,defaultValue="0")int start,
			ModelMap model, @CookieValue(value="count",defaultValue = "0", required = true) String value,
			HttpServletResponse response,
			HeaderInfo headerInfo) {
		
		System.out.println("-----------------------------------------------------");
		System.out.println(headerInfo.get("user-agent"));
		System.out.println("-----------------------------------------------------");
		
		/*
		 * HttpServletRequest는 특정 이름의 Cookie를 구하는 메소드를 가지고 있지 않습니다.
			그렇기 때문에, 특정 이름의 쿠키를 구하려면 반복문을 이용해 원하는 이름의 쿠키가 있는지 찾아야 합니다.
			CookieValue애노테이션을 이용하면 쉽게 특정 이름의 쿠키 값을 구할 수 있습니다.
		 */
//		String value = null;
//		boolean find = false;
//		Cookie[] cookies = request.getCookies();
//		if(cookies != null) {
//			for(Cookie cookie : cookies) {
//				if("count".equals(cookie.getName())) {
//					find = true;
//					value = cookie.getValue();
//					break;
//				}
//			}
//		}
		
//		if(!find) {
//			value = "1";
//		}else {
			try {
				int i = Integer.parseInt(value);
				value = Integer.toString(++i);
			}catch(Exception e) {
				value = "1";
			}
//		}
		
		Cookie cookie = new Cookie("count",value);
		cookie.setMaxAge(60*60*24*365); //1년동안 유지
		cookie.setPath("/"); // / 경로이하에 모든 쿠키 적용
		response.addCookie(cookie);
		
		//start로 시작하는 방명록 목록
		List<Guestbook> list = service.getGuestbooks(start);
		
		//전체 페이지 수
		int cnt = service.getCount();
		int pageCnt = cnt / service.LIMIT;
		if(cnt % service.LIMIT > 0)
			pageCnt++;
		
		// 페이지 수만큼 start의 값을 리스트로 저장
		// 예를 들면 페이지수가 3이면
		// 0, 5, 10 이렇게 저장된다.
		// list?start=0 , list?start=5, list?start=10 으로 링크가 걸린다.
		List<Integer> pageStartList = new ArrayList<Integer>();
		for(int i=0; i<pageCnt; i++) {
			pageStartList.add(i*service.LIMIT);
		}
		
		model.addAttribute("list",list);
		model.addAttribute("cnt", cnt);
		model.addAttribute("pageStartList", pageStartList);
		model.addAttribute("cookieCount", value);
		
		return "list";
	}
	
	@PostMapping(path="/write")
	public String write(@ModelAttribute Guestbook guestbook,
			HttpServletRequest request) {
		String clientIp = request.getRemoteAddr();
		System.out.println("clientIp:"+clientIp);
		service.addGuestbook(guestbook, clientIp);
		return "redirect:list";
	}
	
	@GetMapping(path="/delete")
	public String delete(@RequestParam(name="id",required = true)Long id,
			@SessionAttribute("isAdmin")String isAdmin,
			HttpServletRequest request,
			RedirectAttributes redirectAttr) {
		if(isAdmin == null || !"true".equals(isAdmin)) {//세션값이 true가 아닐경우
			redirectAttr.addFlashAttribute("errorMessage", "로그인을 하지 않았습니다.");
			return "redirect:loginform";
		}
		String clientIp = request.getRemoteAddr();
		service.deleteGuestbook(id, clientIp);
		return "redirect:list";
	}
	
}
