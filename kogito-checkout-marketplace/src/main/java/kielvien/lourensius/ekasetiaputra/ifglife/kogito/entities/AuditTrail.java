package kielvien.lourensius.ekasetiaputra.ifglife.kogito.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import kielvien.lourensius.ekasetiaputra.ifglife.kogito.constants.Constants;

@MappedSuperclass
public class AuditTrail {
	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "created_datetime")
	private LocalDateTime createdDatetime;

	@Column(name = "updated_datetime")
	private LocalDateTime updatedDatetime;

	@PrePersist
	private void onCreated() {
		this.createdDatetime = LocalDateTime.now();
		this.createdBy = Constants.SYSTEM;
	}

	@PreUpdate
	private void onUpdated() {
		this.updatedDatetime = LocalDateTime.now();
		this.createdBy = Constants.SYSTEM;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getCreatedDatetime() {
		return createdDatetime;
	}

	public void setCreatedDatetime(LocalDateTime createdDatetime) {
		this.createdDatetime = createdDatetime;
	}

	public LocalDateTime getUpdatedDatetime() {
		return updatedDatetime;
	}

	public void setUpdatedDatetime(LocalDateTime updatedDatetime) {
		this.updatedDatetime = updatedDatetime;
	}
}
