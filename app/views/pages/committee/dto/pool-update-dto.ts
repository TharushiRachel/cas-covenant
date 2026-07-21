export class PoolUpdateDto {
  userId;
  poolId;
  approveStatus;
  approvedDateStr;
  approvedBy;
  parentRecordID;
  createdDateStr;
  createdBy;
  modifiedDateStr;
  modifiedBy;
  userName;
  userDisplayName;
  userStatus;

  constructor(poolDto) {
    poolDto = poolDto || {};
    this.userId = poolDto.userId || "";
    this.poolId = poolDto.poolId || "";
    this.approveStatus = poolDto.approveStatus || "PENDING";
    this.approvedDateStr = poolDto.approvedDateStr || "";
    this.approvedBy = poolDto.approvedBy || "";
    this.createdDateStr = poolDto.createdDateStr || "";
    this.createdBy = poolDto.createdBy || "";
    this.modifiedDateStr = poolDto.modifiedDateStr || "";
    this.modifiedBy = poolDto.modifiedBy || "";
    this.userName = poolDto.userName || "";
    this.userDisplayName = poolDto.userDisplayName || "";
    this.userStatus = poolDto.userStatus || "";
  }
}
