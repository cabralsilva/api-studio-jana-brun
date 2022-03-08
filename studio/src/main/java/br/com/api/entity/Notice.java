package br.com.api.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.api.enums.NoticeTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notice")
@Getter
@Setter
public class Notice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notice_id")
	private Integer identifier;

	@Column(name = "notice_title", nullable = false)
	private String title;

	@Column(name = "notice_description", nullable = false)
	private String description;
	
	@Column(name = "notice_level", nullable = false)
	private Integer level;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "notice_type", nullable = false)
	private NoticeTypeEnum type;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "notice_start_date")
	private Calendar startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "notice_end_date")
	private Calendar endDate;
}
