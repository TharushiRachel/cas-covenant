export class FacilityReviewSearchDetails {
  dateRange;
  fromDate;
  toDate;
  upmGroupCode;
  userName;
  paperReviewStatusList;
  approvedUser;
  loginUpmAccessLevel;



  constructor(dto) {
    dto = dto || {};
    this.dateRange = dto.dateRange || null;
    this.fromDate = dto.fromDate || null;
    this.toDate = dto.toDate || null;
    this.upmGroupCode = dto.upmGroupCode || null;
    this.userName = dto.userName || null;
    this.paperReviewStatusList = dto.paperReviewStatusList || null;
    this.approvedUser = dto.approvedUser || null;
    this.loginUpmAccessLevel = dto.loginUpmAccessLevel || null;
  }
}
