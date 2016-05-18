package youhyoo;
import java.util.*;

public class Review_Dto {
	private int rv_num;
	private int rv_score;
	private String rv_title;
	private String rv_content;
	private String rv_id;
	private Date rv_date;
	private int rv_view;
	private String rv_photo;
	private int rv_pension;
	
	public Review_Dto(){}

	public int getRv_num() {
		return rv_num;
	}

	public void setRv_num(int rv_num) {
		this.rv_num = rv_num;
	}

	public int getRv_score() {
		return rv_score;
	}

	public void setRv_score(int rv_score) {
		this.rv_score = rv_score;
	}

	public String getRv_title() {
		return rv_title;
	}

	public void setRv_title(String rv_title) {
		this.rv_title = rv_title;
	}

	public String getRv_content() {
		return rv_content;
	}

	public void setRv_content(String rv_content) {
		this.rv_content = rv_content;
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
	
}
