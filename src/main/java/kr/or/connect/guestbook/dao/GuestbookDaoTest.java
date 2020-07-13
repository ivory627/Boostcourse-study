package kr.or.connect.guestbook.dao;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.or.connect.guestbook.config.ApplicationConfig;
import kr.or.connect.guestbook.dto.Guestbook;
import kr.or.connect.guestbook.dto.Log;

public class GuestbookDaoTest {

	public static void main(String[] args) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
//		GuestbookDao dao = ac.getBean(GuestbookDao.class);
//		
//		Guestbook guestbook = new Guestbook();
//		guestbook.setName("상아쓰");
//		guestbook.setContent("테스트중!");
//		guestbook.setRegdate(new Date());
//		Long id = dao.insert(guestbook);
//		System.out.println("id: "+id);
		
		LogDao dao = ac.getBean(LogDao.class);
		Log log = new Log();
		log.setIp("127.0.0.1");
		log.setMethod("insert");
		log.setRegdate(new Date());
		dao.insert(log);
	}

}
