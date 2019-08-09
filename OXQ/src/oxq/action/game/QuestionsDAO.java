package oxq.action.game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuestionsDAO {
	private static QuestionsDAO instance; // SingleTon
	private String driver = "oracle.jdbc.driver.OracleDriver"; // driverLoading
	private Connection conn; // connection
	private PreparedStatement pstmt; // statement
	private ResultSet rs;
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "java";
	private String password = "dkdlxl";
	
	public QuestionsDAO() {
		try {
			Class.forName(driver); // ����
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static QuestionsDAO getInstance() { // SingleTon
		if (instance == null) { // static���� ��Ƴ��ұ� ������ ó�� ������ �� �� �ѹ� null
			synchronized (QuestionsDAO.class) {
				instance = new QuestionsDAO();
			}
		}
		return instance;
	}

	public void getConnection() {
		try {
			conn = DriverManager.getConnection(url, user, password); // jabc:oracle:����̹���:����:��Ʈ:�����ͺ��̽���
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<QuestionsDTO> getQuestionList(){ // 문제
		getConnection();
		ArrayList<QuestionsDTO> questionList = new ArrayList<QuestionsDTO>();
		try {
			String sql = "select * from questions";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				QuestionsDTO dto = new QuestionsDTO();
				dto.setqNum(rs.getInt("qnum"));
				dto.setQuestion(rs.getString("question"));
				dto.setAnswer(rs.getInt("answer"));
				questionList.add(dto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) { 
					rs.close();
				}
				if (pstmt != null) { 
					pstmt.close();
				}
				if (conn != null) { 
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return questionList;
	}
}
