package oxq.action.login;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
 
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import oxq.dao.MemberDAO;
import oxq.dto.MemberDTO;


public class SMTPMailSendManager {

	private int randomNumber; //인증번호 발생
	private ArrayList<MemberDTO> list;
	private MemberDAO dao = MemberDAO.getInstance();
	
	
	public SMTPMailSendManager(String emailAll) {
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
			if(dto.getEmail().equals(emailAll)) {
				randomNumber = (int)(Math.random()*8999)+1000;
		        System.out.println(randomNumber);
			}
		}
        
        
        try{
            //편지보낸시간
            msg.setSentDate(new Date());
             
            InternetAddress from = new InternetAddress() ;
             
            
            from = new InternetAddress("<kimhum11@gmail.com>");
             
            // 이메일 발신자
            msg.setFrom(from);
           
            // 이메일 수신자
           
            InternetAddress to = new InternetAddress(emailAll);
            
            msg.setRecipient(Message.RecipientType.TO, to);
             
            // 이메일 제목
            msg.setSubject("인증번호를 확인하세요.", "UTF-8");
             
            // 이메일 내용
            msg.setText("인증번호는 : " + randomNumber + "입니다." , "UTF-8");
             
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
	
	public int getRandomNumber() {
		return randomNumber;
	}
	
	public void setRandomNumber(int randomNumber) {
		this.randomNumber = randomNumber;
	}

	public static void main(String[] args, String emailAll) {
		
		SMTPMailSendManager smtpMailSendManager = new SMTPMailSendManager(emailAll);

		
	}
}

class MyAuthentication extends Authenticator {
	
    PasswordAuthentication pa;
    
    public MyAuthentication(){
 
        String id = "kimhum100@gmail.com";       // 구글 ID
        String pw = "Bit10000!";          // 구글 비밀번호
        
        // ID와 비밀번호를 입력한다.
        pa = new PasswordAuthentication(id, pw);
      
    }
 
    // 시스템에서 사용하는 인증정보
    public PasswordAuthentication getPasswordAuthentication() {
        return pa;
    }
}

