export class RoleUpdateDto {
  roleID;
  roleName;
  upmPrivilegeCode;
  status;
  privileges;
  addedPrivileges;
  deletedPrivileges;
  approveStatus;
  approvedDateStr;
  approvedBy;
  createdDateStr;
  createdBy;
  modifiedDateStr;
  modifiedBy;

  constructor(roleUpdateDto) {
    roleUpdateDto = roleUpdateDto || {};
    this.roleID = roleUpdateDto.roleID || '';
    this.roleName = roleUpdateDto.roleName || '';
    this.upmPrivilegeCode = roleUpdateDto.upmPrivilegeCode || '';
    this.status = roleUpdateDto.status || 'ACT';
    this.privileges = roleUpdateDto.privileges || [];
    this.addedPrivileges = roleUpdateDto.addedPrivileges || [];
    this.deletedPrivileges = roleUpdateDto.deletedPrivileges || [];
    this.approveStatus= roleUpdateDto.approveStatus || 'PENDING';
    this.approvedDateStr= roleUpdateDto.approvedDateStr || '';
    this.approvedBy= roleUpdateDto.approvedBy || '';
    this.createdDateStr= roleUpdateDto.createdDateStr || '';
    this.createdBy= roleUpdateDto.createdBy || '';
    this.modifiedDateStr= roleUpdateDto.modifiedDateStr || '';
    this.modifiedBy= roleUpdateDto.modifiedBy || '';
  }
}
