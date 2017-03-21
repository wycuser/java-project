package com.shanlin.p2p.app.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shanlin.p2p.app.model.Letter;

public class LetterContentVO {

	private Letter letter;
	
	private String content;
	
	public void setLetter(Letter letter) {
		this.letter = letter;
	}
	
	@JsonIgnore
	public Letter getLetter() {
		return letter;
	}

	public Long getId() {
		return letter.getId();
	}

	public String getTitle() {
		return letter.getTitle();
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getSendTime() {
		return letter.getSendTime();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
