package oxq.dto;

public class QuestionDTO {
	private int quest_no;
	private String question;
	private String answer;
	// getter
	public int getQuest_no() {return quest_no;}
	public String getQuestion() {return question;}
	public String getAnswer() {return answer;}
	// setter
	public void setQuest_no(int quest_no) {this.quest_no = quest_no;}
	public void setQuestion(String question) {this.question = question;}
	public void setAnswer(String answer) {this.answer = answer;}
}
