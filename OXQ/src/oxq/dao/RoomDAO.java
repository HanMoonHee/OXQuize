package oxq.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oxq.dto.MemberDTO;
import oxq.dto.RoomDTO;

public class RoomDAO {
	// 싱글톤 처리
	private static RoomDAO instance;

	public static RoomDAO getInstance() {
		if (instance == null) {
			synchronized (RoomDAO.class) {
				instance = new RoomDAO();
			}
		}
		return instance;
	} // 싱글톤 처리 끝

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "java";
	private String password = "dkdlxl";
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public RoomDAO() {
		try {
			Class.forName(driver); // 드라이버 로딩
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void getConnection() { // 접속
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 게임방 만들기
	public int insertRoom(RoomDTO dto) {
		int su = 0;
		getConnection(); // 접속
		String sql = "INSERT INTO room values (?,?,?,?)";

		try {
			pstmt = conn.prepareStatement(sql); // 생성
			pstmt.setInt(1, dto.getRoomNumer());
			pstmt.setString(2, dto.getRoomName());
			pstmt.setString(3, dto.getRoomPwd());
			pstmt.setInt(4, dto.getPlayerCnt());

			su = pstmt.executeUpdate(); // 실행

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { // 종료
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return su;
	}

	// 게임방 번호 시퀀스로 받아오기
	public int getRoomNum() {
		int seq = 0;
		getConnection();
		String sql = "select seq_room.nextval from dual";
		try {
			pstmt = conn.prepareStatement(sql); // 생성
			rs = pstmt.executeQuery(); // 실행

			if (rs.next())
				seq = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { // 종료
				if (rs != null)	rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return seq;
	}

	// 디비에 저장된 룸 리스트
	public ArrayList<RoomDTO> getRoomList() {
		ArrayList<RoomDTO> arrayList = new ArrayList<RoomDTO>();
		getConnection();
		String sql = "select * from room order by room_no asc";

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				RoomDTO dto = new RoomDTO();
				dto.setRoomNumer(rs.getInt("room_no"));
				dto.setRoomName(rs.getString("room_name"));
				dto.setRoomPwd(rs.getString("room_pwd"));
				dto.setPlayerCnt(rs.getInt("room_playercnt"));

				arrayList.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			arrayList = null;
		} finally {
			try { // 종료
				if (rs != null)	rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arrayList;
	}

	// 디비에 저장된 회원 리스트
	public ArrayList<MemberDTO> getMemberList() {
		ArrayList<MemberDTO> arrayList = new ArrayList<MemberDTO>();
		getConnection();
		String sql = "select nickname from member where login = 1";

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setNickName(rs.getString("nickname"));

				arrayList.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			arrayList = null; // 에러 발생 했을때 arrayList를 null 해줘야됨.
		} finally {
			try { // 종료
				if (rs != null)	rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arrayList;
	}
	// 디비에 저장된 회원 이긴 횟수별로 출력
	public ArrayList<MemberDTO> getRankList() {
		ArrayList<MemberDTO> arrayList = new ArrayList<MemberDTO>();
		getConnection();
		String sql = "select nickname, win_cnt from member order by win_cnt desc";

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setNickName(rs.getString("nickname"));
				dto.setWin_cnt(rs.getInt("win_cnt"));

				arrayList.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			arrayList = null; // 에러 발생 했을때 arrayList를 null 해줘야됨.
		} finally {
			try { // 종료
				if (rs != null)	rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arrayList;
	}
}
