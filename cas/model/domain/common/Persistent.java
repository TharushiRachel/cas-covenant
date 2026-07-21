package com.itechro.cas.model.domain.common;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * Base class for versioning of entities
 *
 * @author chamara
 */
@MappedSuperclass
public abstract class Persistent implements Serializable {

	private static final long serialVersionUID = -4528747516727555914L;
	@Version
	@Column(name = "VERSION", nullable = false)
	protected Long version = null;

	public Persistent() {
		super();
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}

