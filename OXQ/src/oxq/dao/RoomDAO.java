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
	private String url = "jdbc:oracle:thin:@192.168.0.46:1521:xe"; // 192.168.0.46 팀장님 ip
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
	public int insertRoom(RoomDTO dto, String nickName) {
		int su = 0;
		getConnection(); // 접속
		String sql = "insert into room(room_no, room_name, room_pwd, room_playercnt, player1) values (?,?,?,?,?)";

		try {
			pstmt = conn.prepareStatement(sql); // 생성
			pstmt.setInt(1, dto.getRoomNumer());
			pstmt.setString(2, dto.getRoomName());
			pstmt.setString(3, dto.getRoomPwd());
			pstmt.setInt(4, 1);
			pstmt.setString(5, nickName);

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

	// 게임방 삭제
	public int deleteRoom(String roomName) {
		int su = 0;
		getConnection();
		String sql = "delete from room where room_Name = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, roomName);
			su = pstmt.executeUpdate();
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

	// 게임방 이름 가지고 오기
	public String getRoomName(String nickName) {
		String roomName = "";
		getConnection();

		String sql = "select room_name from room where player1 = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nickName);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				roomName = rs.getString(1);
			}
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
		return roomName;
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
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return seq;
	}

	// 게임방 번호 가져오기
	public int getCurrentRoomNum(String roomName) {
		int no = 0;
		getConnection();
		String sql = "select room_no from room where room_name = ?";
		try {
			pstmt = conn.prepareStatement(sql); // 생성
			pstmt.setString(1, roomName);
			rs = pstmt.executeQuery(); // 실행

			if (rs.next())
				no = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { // 종료
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return no;
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
				dto.setPlayer1(rs.getString("player1"));
				dto.setPlayer2(rs.getString("player2"));
				dto.setPlayer1_score(rs.getInt("player1_score"));
				dto.setPlayer2_score(rs.getInt("player2_score"));

				arrayList.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			arrayList = null;
		} finally {
			try { // 종료
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arrayList;
	}

	// 로그인 한 상태인 회원 닉네임 리스트
	public ArrayList<MemberDTO> getLoginMemberList() {
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
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
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
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arrayList;
	}

	// 해당 방에 있는 플레이어의 닉네임을 디비에서 불러와서 리스트에 담는다
	public ArrayList<String> getNicksss(String room_name) {
		ArrayList<String> list = new ArrayList<String>();
		getConnection();
		String sql = "select player1,player2 from room where room_name = ?"; // room_name으로

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, room_name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				list.add(rs.getString("player1"));
				list.add(rs.getString("player2"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { // 종료
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	// 해당 방에 있는 플레이어의 수를 구한다
	public int getPlayerCnt(String roomName) {
		int playerCnt = 0;

		getConnection();
		String sql = "select ROOM_PLAYERCNT from room where ROOM_NAME = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, roomName);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				playerCnt = rs.getInt("ROOM_PLAYERCNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { // 종료
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return playerCnt;
	}

	// 플레이어가 게임방에 들어가면/나가면 그 게임방의 플에이어 수를 바꾼다
	public void updatePlayCnt(int playerCnt, String roomName) {
		String sql = "update room set ROOM_PLAYERCNT=? where ROOM_NAME=?";
		getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, playerCnt);
			pstmt.setString(2, roomName);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { // 종료
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

//	public void updatePlayCnt(PlayInfoDTO dto, String roomName) { // 플레이어가 게임방에 들어가면/나가면 그 게임방의 플에이어 수를 바꾼다
//		String sql = "update room set ROOM_PLAYERCNT=? where ROOM_NAME=?";
//		getConnection();
//		try {
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, dto.getPlayerCnt());
//			pstmt.setString(2, roomName);
//			pstmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try { // 종료
//				if (rs != null)
//					rs.close();
//				if (pstmt != null)
//					pstmt.close();
//				if (conn != null)
//					conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}

	// 1번 플레이어가 방을 나가면 room 테이블에서 닉네임 삭제
	public void updatePlayer1(String nickname) {
		getConnection();
		String sql = "update room set player1=null where player1=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nickname);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { // 종료
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 2번 플레이어가 방을 나가면 room 테이블에서 닉네임 삭제
	public void updatePlayer2(String nickname) {
		getConnection();
		String sql = "update room set player2=null where player2=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nickname);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { // 종료
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void updatePlayer01Score(String nickname, int correctCnt) {
		getConnection();
		String sql = "update room set player1_score=? where player1=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, correctCnt);
			pstmt.setString(2, nickname);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { // 종료
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void updatePlayer02Score(String nickname, int correctCnt) {
		getConnection();
		String sql = "update room set player2_score=? where player2=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, correctCnt);
			pstmt.setString(2, nickname);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { // 종료
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public int getP1Score(String room_name) {
		int p1Score = 0;
		getConnection();
		String sql = "select player1_score from room where room_name =?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, room_name);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				p1Score = rs.getInt("player1_score");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { // 종료
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return p1Score;
	}

	public int getP2Score(String room_name) {
		int p2Score = 0;
		getConnection();
		String sql = "select player2_score from room where room_name =?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, room_name);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				p2Score = rs.getInt("player2_score");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { // 종료
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return p2Score;
	}

	// player 2가 들어오면 룸 테이블 업데이트 playercnt 2로 player2 닉네임 넣어주기
	public int updateRoom(String nickname, String room_name) {
		int su = 0;
		getConnection();
		String sql = "update room set room_playercnt = ?, player2 = ? where room_name = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 2);
			pstmt.setString(2, nickname);
			pstmt.setString(3, room_name);

			su = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { // 종료
				if (rs != null)
					rs.close();
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

	public RoomDTO getRoomInfo(String roomName) {
		RoomDTO dto = new RoomDTO();
		getConnection();
		String sql = "select * from room where ROOM_NAME = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, roomName);
			rs = pstmt.executeQuery();

			if (rs.next())
				;
			{
				dto.setRoomNumer(rs.getInt("ROOM_NO"));
				dto.setRoomName(rs.getString("ROOM_NAME"));
				dto.setRoomPwd(rs.getString("ROOM_PWD"));
				dto.setPlayer1(rs.getString("PLAYER1"));
				dto.setPlayer1(rs.getString("PLAYER2"));
				dto.setPlayer1_score(rs.getInt("PLAYER1_SCORE"));
				dto.setPlayer2_score(rs.getInt("PLAYER2_SCORE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { // 종료
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dto;
	}
}
