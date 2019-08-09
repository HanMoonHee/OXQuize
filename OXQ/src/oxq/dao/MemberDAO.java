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

	public static MemberDAO getInstance() {
		if (instance == null) {
			synchronized (MemberDAO.class) {
				instance = new MemberDAO();
			}
		}
		return instance;
	}
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "java";
	private String password = "dkdlxl";
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public MemberDAO() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void getConnection() {
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 회원 추가
	public int insertMember(MemberDTO dto) {
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
				if (rs != null)	rs.close();
				if (pstmt != null)	pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return su;
	}

	// 회원정보 업데이트
	public int updateMember(MemberDTO dto) {
		int su = 0;
		getConnection();
		String sql = "update member set pwd = ?," + "nickname = ?," + "tel = ?," + "email = ? where id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getPwd());
			pstmt.setString(2, dto.getNickName());
			pstmt.setString(3, dto.getTel());
			pstmt.setString(4, dto.getEmail());
			pstmt.setString(5, dto.getId());

			su = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)	pstmt.close();
				if (conn != null)	conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return su;
	}

	// 회원정보 삭제
	public int deleteMember(String id) {
		int su = 0;
		getConnection();
		String sql = "delete Member where id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			su = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)	pstmt.close();
				if (conn != null)	conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return su;
	}

	// 전체 아이디 리스트
	public ArrayList<MemberDTO> getIdList() {
		ArrayList<MemberDTO> arrayList = new ArrayList<MemberDTO>();
		getConnection();
		String sql = "select id from member";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getString("id"));

				arrayList.add(dto);
			} // while
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)	rs.close();
				if (pstmt != null)	pstmt.close();
				if (conn != null)	conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arrayList;
	}

	// 비밀번호 찾기
	public String getPwd(String id) {
		String pwd = "";
		getConnection();
		String sql = "select pwd from member where id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				pwd = rs.getString("pwd");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)	rs.close();
				if (pstmt != null)	pstmt.close();
				if (conn != null)	conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return pwd;
	}

	// 회원 전체 정보
	public ArrayList<MemberDTO> getMemberList() {
		ArrayList<MemberDTO> arrayList = new ArrayList<MemberDTO>();
		getConnection();
		String sql = "select * from member";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MemberDTO dto = new MemberDTO();

				dto.setId(rs.getString("id"));
				dto.setPwd(rs.getString("pwd"));
				dto.setNickName(rs.getString("nickName"));
				dto.setTel(rs.getString("tel"));
				dto.setEmail(rs.getString("email"));
				dto.setLogin(rs.getInt("login"));
				dto.setO_cnt(rs.getInt("o_cnt"));
				dto.setX_cnt(rs.getInt("x_cnt"));
				dto.setWin_cnt(rs.getInt("win_cnt"));

				arrayList.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { // 종료
				if (rs != null)	rs.close();
				if (pstmt != null)	pstmt.close();
				if (conn != null)	conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arrayList;
	}

	// 로그인한 회원의 정보 객체
	public MemberDTO loginDTO(String id) {
		MemberDTO dto = new MemberDTO();
		getConnection();
		String sql = "select * from member where id = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto.setId(rs.getString("id"));
				dto.setPwd(rs.getString("pwd"));
				dto.setNickName(rs.getString("nickName"));
				dto.setEmail(rs.getString("email"));
				dto.setTel(rs.getString("tel"));
				dto.setLogin(rs.getInt("login"));
				dto.setO_cnt(rs.getInt("o_cnt"));
				dto.setX_cnt(rs.getInt("x_cnt"));
				dto.setWin_cnt(rs.getInt("win_cnt"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)	rs.close();
				if (pstmt != null)	pstmt.close();
				if (conn != null)	conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	// 로그인 체크 성공 1, 비밀번호 틀림 0, 아이디 없음 -1
	public int login(String id, String pwd) {
		int flag = 0;
		getConnection();
		String sql = "select pwd from member where id = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				// 패스워드 일치한다면
				if (rs.getString(1).equals(pwd)) {
					flag = 1; // 로그인 성공
				} else
					flag = 0; // 비밀번호 불일치
			} else {
				flag = -1; // 아이디 없음
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)	rs.close();
				if (pstmt != null)	pstmt.close();
				if (conn != null)	conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	// 로그인 하면 login 상태 1로 update
	public int LoginFlag(String id) {
		int su = 0;
		getConnection();
		String sql = "update member set login = ? where id = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1);
			pstmt.setString(2, id);

			su = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)	rs.close();
				if (pstmt != null)	pstmt.close();
				if (conn != null)	conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return su;
	}

	// 로그아웃 하면 login 상태 0로 update
	public int LogoutFlag(String id) {
		int su = 0;
		getConnection();
		String sql = "update member set login = ? where id = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 0);
			pstmt.setString(2, id);

			su = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)	rs.close();
				if (pstmt != null)	pstmt.close();
				if (conn != null)	conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return su;
	}

	// 로그인 상태 가지고 오기
	public int flag(String id) {
		int su = 0;
		getConnection();
		String sql = "select login from member where id = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				su = rs.getInt("login");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)	rs.close();
				if (pstmt != null)	pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return su;
	}

	// 게임에 참가한 플레이어의 지금까지 맞춘 문제 개수 틀린문제 개수
	public MemberDTO getOXHistory(String nickname) {
		MemberDTO dto = new MemberDTO();
		getConnection();
		String sql = "select o_cnt,x_cnt,win_cnt from member where nickname = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nickname);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto.setO_cnt(rs.getInt("o_cnt"));
				dto.setX_cnt(rs.getInt("x_cnt"));
				dto.setWin_cnt(rs.getInt("win_cnt"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)	rs.close();
				if (pstmt != null)	pstmt.close();
				if (conn != null)	conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	public int updateOXHistory(String nickname, MemberDTO dto) {
		int su = 0;
		getConnection(); // 연결
		String sql = "update member set o_cnt = ?, x_cnt = ? where nickname = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getO_cnt());
			pstmt.setInt(2, dto.getX_cnt());
			pstmt.setString(3, nickname);

			su = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)	rs.close();
				if (pstmt != null)	pstmt.close();
				if (conn != null)	conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return su;
	}

	// 아이디 찾기
	public ArrayList<MemberDTO> getId(String id) {
		ArrayList<MemberDTO> arrayList = new ArrayList<MemberDTO>();
		String sql = "select id from member where id = ?";
		getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getString("id"));

				arrayList.add(dto);
			} // while
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)	rs.close();
				if (pstmt != null)	pstmt.close();
				if (conn != null)	conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arrayList;
	}

	// 아이디의 정보값 가져오기
	public MemberDTO getIdLIst(MemberDTO dto) {
		getConnection();
		String sql = "select * from member where id = ?";
		System.out.println(dto.getId());
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto.setId(rs.getString("id"));
				dto.setPwd(rs.getString("pwd"));
				dto.setNickName(rs.getString("nickName"));
				dto.setEmail(rs.getString("email"));
				dto.setLogin(rs.getInt("login"));
				dto.setO_cnt(rs.getInt("o_cnt"));
				dto.setX_cnt(rs.getInt("x_cnt"));
				dto.setWin_cnt(rs.getInt("win_cnt"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)	rs.close();
				if (pstmt != null)	pstmt.close();
				if (conn != null)	conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dto;
	}
}