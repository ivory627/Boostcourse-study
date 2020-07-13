package kr.or.connect.guestbook.service.impl;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.or.connect.guestbook.config.ApplicationConfig;
import kr.or.connect.guestbook.dto.Guestbook;
import kr.or.connect.guestbook.service.GuestbookService;

public class GuestbookServiceTest {

	public static void main(String[] args) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		GuestbookService service = ac.getBean(GuestbookService.class);
		
		Guestbook book = new Guestbook();
		book.setName("sanga");
		book.setContent("test");
		book.setRegdate(new Date());
		Guestbook res = service.addGuestbook(book, "127.0.0.1");
		System.out.println(res);
	}

}
