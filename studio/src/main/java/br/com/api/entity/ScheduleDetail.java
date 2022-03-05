package br.com.api.entity;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.api.enums.OftenEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "schedule_detail")
@Getter
@Setter
public class ScheduleDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "schedule_detail_id")
	private Integer identifier;
	
	@Column(name = "schedule_detail_init_date", nullable = false)
	private LocalDate initDate;
	
	@Column(name = "schedule_detail_time", nullable = false)
	private LocalTime time;
	
	@Column(name = "schedule_detail_duration_lesson", nullable = false)
	private Duration duration;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "schedule_detail_often", nullable = false)
	private OftenEnum often;

	@ManyToOne
	@JoinColumn(name = "class_id", nullable = false)
	private Class cClass;
	
	@ManyToOne
	@JoinColumn(name = "schedule_id", nullable = false)
	private Schedule schedule;
}
