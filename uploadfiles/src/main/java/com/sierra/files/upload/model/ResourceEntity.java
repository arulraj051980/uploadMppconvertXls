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
@Table(name="resource_1")
public class ResourceEntity {
	
	@Id
	@GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "RID", columnDefinition = "VARCHAR(255)")
	private String rid;
	
	@Column(name="INDICATORS")
	private String indicators;
	
	@Column(name="RESOURCE_NAME")
	private String resource_name;
	
	@Column(name="TYPE")
	private String type;
	
	@Column(name="MATERIAL_LABEL")
	private String material_label;
	
	@Column(name="INITIALS")
	private String initials;
	
	@Column(name="GROUPS")
	private String groups;
	
	@Column(name="MAXUNITS")
	private String maxunits;
	
	@Column(name="STDRATE")
	private String stdrate;
	
	@Column(name="OVTRATE")
	private String ovtrate;
	
	@Column(name="COST")
	private String cost;
	
	@Column(name="ACCRUE")
	private String accrue;
	
	@Column(name="BASECALENDAR")
	private String basecalendar;
	
	@Column(name="CODE")
	private String code;
	

}
