export class CustomerCovenant {
customerCovenantId : number;
RequestUUID: string;
custId: string;
casReference: string;
covenant_Code: string;
covenant_Description: string;
covenant_Frequency: string;
covenant_Due_Date: Date;
disbursementType: string;

constructor(customerCovenant) {
    customerCovenant = customerCovenant || {};
    this.customerCovenantId = customerCovenant.customerCovenantId || '';
    this.RequestUUID =  customerCovenant.RequestUUID || '';
    this.custId = customerCovenant.custId || '';
    this.casReference = customerCovenant.casReference || '';
    this.covenant_Code = customerCovenant.covenant_Code || '';
    this.covenant_Description = customerCovenant.covenant_Description || '';
    this.covenant_Frequency = customerCovenant.covenant_Frequency || '';
    this.covenant_Due_Date = customerCovenant.covenant_Due_Date || '';
    this.disbursementType = customerCovenant.disbursementType || '';
}
}