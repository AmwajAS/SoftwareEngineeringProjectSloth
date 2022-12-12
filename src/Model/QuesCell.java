package Model;
public class QuesCell extends Cell{
	private Question question;
	
	public QuesCell(int x, int y, Question q) {
		super(x, y);
		this.question=q;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return "QuesCell [question=" + question + ", getX()=" + getX() + ", getY()=" + getY() + ", isOccupied()="
				+ isOccupied() + ", getName()=" + getName() + "]";
	}

}
