package oxq.action.game;

public class QuestionsDTO {
	private int qNum; // 문제번호 1,2,3...
	private String question; // 문제 
	private int answer; // 답: 1이면 O 0이면 X
	public int getqNum() {
		return qNum;
	}
	public void setqNum(int qNum) {
		this.qNum = qNum;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public int getAnswer() {
		return answer;
	}
	public void setAnswer(int answer) {
		this.answer = answer;
	}

}
