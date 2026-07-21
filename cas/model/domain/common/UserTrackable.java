package com.itechro.cas.model.domain.common;

import java.io.Serializable;
import java.util.Date;

/**
 * UserTrackable must implement this interface
 *
 * @author chamara
 */
public interface UserTrackable extends Serializable {

	public Date getCreatedDate();

	public void setCreatedDate(Date createdDate);

	public String getCreatedBy();

	public void setCreatedBy(String createdBy);

	public Date getModifiedDate();

	public void setModifiedDate(Date modifiedDate);

	public String getModifiedBy();

	public void setModifiedBy(String createdBy);
}
