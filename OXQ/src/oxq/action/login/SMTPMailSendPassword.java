package oxq.action.login;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
 
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import oxq.dao.MemberDAO;
import oxq.dto.MemberDTO;

public class SMTPMailSendPassword {

	private MemberDAO dao = MemberDAO.getInstance();
	private ArrayList<MemberDTO> list;
	private String searchPassword;
	
	public SMTPMailSendPassword (String id, String email) {
		Properties p = System.getProperties();
		p.put("mail.smtp.starttls.enable", "true"); //gmail은 무조건 true 고정
		p.put("mail.smtp.host", "smtp.gmail.com"); //smtp 서버 주소
		p.put("mail.smtp.auth", "true"); //gmail은 무조건 true 고정
		p.put("mail.smtp.port", "587"); //gmail 포트
		//p에다 서버주소,포트 등을던진다.
		
		
		Authenticator auth = new MyAuthentication();
		//auth에 담은 MyAuthentication클래스는 인증정보 폼을 가져와 아이디와 비밀번호를 인증하는 클래스이다.
		
        //session 생성 및  MimeMessage생성
        Session session = Session.getDefaultInstance(p, auth);
        MimeMessage msg = new MimeMessage(session);
        
        list = dao.getMemberList();

		for(MemberDTO dto : list) {
			if(id.equals(dto.getId()) && dto.getEmail().equals(email)) {
				searchPassword = dto.getPwd();
			}
		}
   
        try{
            //편지보낸시간
            msg.setSentDate(new Date());
            
            // 이메일 수신자
            InternetAddress to = new InternetAddress(email);
            msg.setRecipient(Message.RecipientType.TO, to);
             
            // 이메일 제목
            msg.setSubject("비밀번호를 확인하세요.", "UTF-8");
             
            // 이메일 내용
            msg.setText("패스워드는 : " + searchPassword + "입니다." , "UTF-8");
             
            // 이메일 헤더
            msg.setHeader("content-Type", "text/html");
            
            //메일보내기
            javax.mail.Transport.send(msg);
             
        }catch (AddressException addr_e) {
            addr_e.printStackTrace();
        }catch (MessagingException msg_e) {
            msg_e.printStackTrace();
        }
	}
}
