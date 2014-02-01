package org.kesler.fiastester.dao;

import java.util.UUID;

import javax.persistence.MappedSuperclass;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;

import org.hibernate.annotations.GenericGenerator;

/**
* Определяет свойства и методы для классов, сохраняемых в базу данных
*/
@MappedSuperclass
public abstract class AbstractEntity {

	@Id
	@GeneratedValue
	@Column(name="ID")
	private Long id;



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


}