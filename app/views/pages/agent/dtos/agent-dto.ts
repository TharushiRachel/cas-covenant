export class AgentDto {
  agentID;
  mobileNumber;
  supervisorADUserID;
  nic;
  remark;
  status;
  userDTO: any;

  constructor(agetDTO) {
    agetDTO = agetDTO || {};
    this.agentID = agetDTO.agentID || '';
    this.mobileNumber = agetDTO.mobileNumber || '';
    this.supervisorADUserID = agetDTO.supervisorADUserID || '';
    this.nic = agetDTO.nic || '';
    this.remark = agetDTO.remark || '';
    this.status = agetDTO.status || 'ACT';

    this.userDTO = agetDTO.userDTO || {};
  }
}
