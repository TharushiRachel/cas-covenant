export class UserDelegateAuthorityUpdateDto{

  userDaID;
  userName;
  maxAmount;
  cleanAmount;
  description;
  status;
  approveStatus;
  approvedDateStr;
  approvedBy;
  createdDateStr;
  createdBy;
  modifiedDateStr;
  modifiedBy;


  constructor(userDelegatedAuthorityDto){

    userDelegatedAuthorityDto = userDelegatedAuthorityDto || {};
    this.userDaID = userDelegatedAuthorityDto.userDaID || '';
    this.userName = userDelegatedAuthorityDto.userName || '';
    this.maxAmount = userDelegatedAuthorityDto.maxAmount || '';
    this.cleanAmount= userDelegatedAuthorityDto.cleanAmount || '';
    this.description =  userDelegatedAuthorityDto.description || '';
    this.status = userDelegatedAuthorityDto.status || 'ACT';
    this.approveStatus = userDelegatedAuthorityDto.approveStatus || 'PENDING';
    this.approvedDateStr = userDelegatedAuthorityDto.approvedDateStr || '';
    this.approvedBy = userDelegatedAuthorityDto.approvedBy || '';
    this.createdDateStr = userDelegatedAuthorityDto.createdDateStr || '';
    this.createdBy = userDelegatedAuthorityDto.createdBy || '';
    this.modifiedDateStr = userDelegatedAuthorityDto.modifiedDateStr || '';
    this.modifiedBy = userDelegatedAuthorityDto.modifiedBy || ''
  }
}
