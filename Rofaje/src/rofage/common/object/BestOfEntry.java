package rofage.common.object;

public class BestOfEntry {
	private String gameTitle;
	private Float gameNote;
	private int nbComments;
	private int nbVotes;
	private String gameCRC;
	
	public BestOfEntry (String gameTitle, Float gameNote, int nbComments, String gameCRC, int nbVotes) {
		this.gameTitle = gameTitle;
		this.gameNote = gameNote;
		this.nbComments = nbComments;
		this.gameCRC = gameCRC;
		this.nbVotes = nbVotes;
	}

	public String getGameTitle() {
		return gameTitle;
	}

	public void setGameTitle(String gameTitle) {
		this.gameTitle = gameTitle;
	}

	public Float getGameNote() {
		return gameNote;
	}

	public void setGameNote(Float gameNote) {
		this.gameNote = gameNote;
	}

	public int getNbComments() {
		return nbComments;
	}

	public void setNbComments(int nbComments) {
		this.nbComments = nbComments;
	}

	public String getGameCRC() {
		return gameCRC;
	}

	public void setGameCRC(String gameCRC) {
		this.gameCRC = gameCRC;
	}

	public int getNbVotes() {
		return nbVotes;
	}

	public void setNbVotes(int nbVotes) {
		this.nbVotes = nbVotes;
	}
}
