package youhyoo;
import java.util.*;

public class Review_Dto {
	private int rv_score;
	private String rv_question;
	private String rv_id;
	private Date rv_date;
	private int rv_view;
	private String rv_photo;
	private int rv_pension;
	private String rv_answer;
	
	public Review_Dto(){}
	
	public int getRv_score() {
		return rv_score;
	}
	public void setRv_score(int rv_score) {
		this.rv_score = rv_score;
	}
	public String getRv_question() {
		return rv_question;
	}
	public void setRv_question(String rv_question) {
		this.rv_question = rv_question;
	}
	public String getRv_id() {
		return rv_id;
	}
	public void setRv_id(String rv_id) {
		this.rv_id = rv_id;
	}
	public Date getRv_date() {
		return rv_date;
	}
	public void setRv_date(Date rv_date) {
		this.rv_date = rv_date;
	}
	public int getRv_view() {
		return rv_view;
	}
	public void setRv_view(int rv_view) {
		this.rv_view = rv_view;
	}
	public String getRv_photo() {
		return rv_photo;
	}
	public void setRv_photo(String rv_photo) {
		this.rv_photo = rv_photo;
	}
	public int getRv_pension() {
		return rv_pension;
	}
	public void setRv_pension(int rv_pension) {
		this.rv_pension = rv_pension;
	}
	public String getRv_answer() {
		return rv_answer;
	}
	public void setRv_answer(String rv_answer) {
		this.rv_answer = rv_answer;
	}
}
