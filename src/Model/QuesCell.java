package Model;
public class QuesCell extends Cell{
	private Question question;

	public QuesCell(int row, int column,Question question) {
		super(row, column);
		this.question=question;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
}
