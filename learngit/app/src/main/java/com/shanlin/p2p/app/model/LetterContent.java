package com.shanlin.p2p.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 站内信内容
 * @author zheng xin
 * @createDate 2015年1月28日
 */
@Entity
@Table(name="T6124", schema="S61")
public class LetterContent implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="F01")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="F01")
	private Letter letter;
	
	@Column(name="F02")
	private String content;

	public Letter getLetter() {
		return letter;
	}

	public void setLetter(Letter letter) {
		this.letter = letter;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "LetterContent [letter=" + letter + ", content=" + content + "]";
	}
	
}
