package rofage.common.object;

public class Comment {
	private String text = "";
	private Float note;
	private String login = "";
	private String crc = "";
	private boolean spoiler = false;
	private long idComment;
	
	public Comment(float note, String text) {
		this.note = new Float(note);
		this.text = text;
	}
	
	public Comment(String login, float note, String text, String crc, boolean spoiler, long idComment) {
		this.login = login;
		this.note = new Float(note);
		this.text = text;
		this.crc = crc;
		this.spoiler = spoiler;
		this.idComment = idComment;
	}
	
	public Comment() {
		this.note = new Float(9);
		this.text = "";
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Float getNote() {
		return note;
	}
	public void setNote(Float note) {
		this.note = note;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getCrc() {
		return crc;
	}

	public void setCrc(String crc) {
		this.crc = crc;
	}

	public boolean isSpoiler() {
		return spoiler;
	}

	public void setSpoiler(boolean spoiler) {
		this.spoiler = spoiler;
	}

	public long getIdComment() {
		return idComment;
	}

	public void setIdComment(long idComment) {
		this.idComment = idComment;
	}
	
	
}
