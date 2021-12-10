package com.sierra.files.upload.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain=true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="Task")
public class TaskTable {
		
	@Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private String ID;	
	
	@Column(name="ACTIVE")
	private String Active;
	
	@Column(name="TASKMODE")
	private String TaskMode;
	
	@Column(name="NAME")
	private String Name;
	
	@Column(name="DURATION")
	private String Duration;
	
	@Column(name="STARTTIME")
	private String Start;
	
	@Column(name="FINISH")
	private String Finish;
	
	@Column(name="PREDECESSORS")
	private String Predecessors;
	
	@Column(name="OUTLINELEVEL")
	private String OutlineLevel;
	
	@Column(name="NOTES")
	private String Notes;

}
