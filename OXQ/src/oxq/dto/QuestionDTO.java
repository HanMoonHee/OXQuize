package oxq.dto;

public class QuestionDTO {
	private int quest_no;		// 문제  번호
	private String question;	// 문제 
	private String answer;		// 문제 답
	
	// getters
	public int getQuest_no() {return quest_no;}
	public String getQuestion() {return question;}
	public String getAnswer() {return answer;}
	
	// setters
	public void setQuest_no(int quest_no) {this.quest_no = quest_no;}
	public void setQuestion(String question) {this.question = question;}
	public void setAnswer(String answer) {this.answer = answer;}
}
