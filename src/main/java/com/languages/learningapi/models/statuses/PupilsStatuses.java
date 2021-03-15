package com.languages.learningapi.models.statuses;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.languages.learningapi.models.Pupils;

@Entity
public class PupilsStatuses extends LearningStatuses{

	
    private Long pupils_id;

    @ManyToOne
    @JoinColumn(name = "pupils_id", insertable = false, updatable = false)
    private Pupils pupils;

	public Long getPupils_id() {
		return pupils_id;
	}

	public void setPupils_id(Long pupils_id) {
		this.pupils_id = pupils_id;
	}
    
    
}
