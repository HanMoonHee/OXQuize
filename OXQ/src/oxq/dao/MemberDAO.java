package oxq.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oxq.dto.MemberDTO;

public class MemberDAO {
	private static MemberDAO instance;
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "java";
	private String password = "dkdlxl";
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public static MemberDAO getInstance() {
		if (instance == null) {
			synchronized (MemberDAO.class) {
				instance = new MemberDAO();
			}

		}

		return instance;
	}
	
	public MemberDAO() {
		try {
			Class.forName(driver);
			System.out.println("드라이버 로딩성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("접속 성공");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int insertOXQuiz(MemberDTO dto) {
		int su = 0;
		getConnection();
		String sql = "insert into member(id, pwd, nickname, tel, email) values(?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPwd());
			pstmt.setString(3, dto.getNickName());
			pstmt.setString(4, dto.getTel());
			pstmt.setString(5, dto.getEmail());

			
			su = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return su;
		
	}
	
	public int updateOXQuiz(MemberDTO dto) {
		int su = 0;
		getConnection();
		String sql = "update member set pwd = ?"
				+ "nickname = ?,"
				+ "tel = ?,"
				+ "email = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getPwd());
			pstmt.setString(2, dto.getNickName());
			pstmt.setString(3, dto.getTel());
			pstmt.setString(4, dto.getEmail());
			
			su = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return su;
	}
	
	public int deleteOXQuiz(int id) {
		int su = 0;
		getConnection();
		String sql = "delete Member where id = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			su = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return su;
	}
	
	public ArrayList<MemberDTO> getMemberList() {
		ArrayList<MemberDTO> arrayList = new ArrayList<MemberDTO>();
		String sql = "select id from member";
		getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				
				arrayList.add(dto);
			}//while
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arrayList;
	}
	
	public ArrayList<MemberDTO> Check() { // 로그인 id,pw체크 || 회원가입 id 중복체크
		getConnection();
		
		String sql = "select * from member";
		ResultSet rs = null;
		
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String email = rs.getString("email");
				int login = rs.getInt("login");
				int o_cnt=rs.getInt("o_cnt");
				int x_cnt=rs.getInt("x_cnt");
				int win_cnt=rs.getInt("win_cnt");
				dto.setId(id);
				dto.setPwd(pwd);
				dto.setEmail(email);
				dto.setLogin(login);
				dto.setO_cnt(o_cnt);
				dto.setX_cnt(x_cnt);
				dto.setWin_cnt(win_cnt);
			
				list.add(dto);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
		
}
