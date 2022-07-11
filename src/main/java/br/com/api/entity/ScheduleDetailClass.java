package br.com.api.entity;

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
@Table(name = "schedule_detail_class")
@Getter
@Setter
public class ScheduleDetailClass {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "schedule_detail_class_id")
	private Integer identifier;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "schedule_detail_class_often")
	private OftenEnum often;

	@Column(name = "schedule_detail_class_often_day")
	private String oftenDay;
	
	@Column(name = "schedule_detail_class_init_time")
	private LocalTime initTime;

	@Column(name = "schedule_detail_class_end_time")
	private LocalTime endTime;
	
	@ManyToOne
	@JoinColumn(name = "classroom_id")
	private Classroom classroom;
	
	@ManyToOne
	@JoinColumn(name = "class_id")
	private Class clazz;

}
