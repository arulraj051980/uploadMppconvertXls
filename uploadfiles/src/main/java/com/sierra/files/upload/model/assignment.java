package com.sierra.files.upload.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="ASSIGNMENT_1")

public class assignment {
	
	@Id
	@GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "aid", columnDefinition = "VARCHAR(255)")
	private String aid;
	
	@Column(name="taskname")
	private String taskname;
	
	@Column(name="resourcename")
	private String resourcename;
	
	@Column(name="work")
	private String work;
	
	@Column(name="duration")
	private String duration;

}
