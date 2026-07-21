export class ShowMessageDto {

  showOnSuccessSearchWithResult: boolean;
  showOnSuccessSearchEmptyResult: boolean;
  showOnRecordSaveUpdate: boolean;
  showOnResourceSaveUpdate: boolean;

  successSearchResultMessage: string;
  successSearchEmptyResultMessage: string;
  recordSaveUpdateMessage: string;
  resourceSaveUpdateMessage: string;

  constructor(data?) {
    data = data || {};
    this.showOnSuccessSearchWithResult = data.showOnSuccessSearchWithResult || false;
    this.showOnSuccessSearchEmptyResult = data.showOnSuccessSearchEmptyResult || false;
    this.showOnRecordSaveUpdate = data.showOnRecordSaveUpdate || false;
    this.showOnResourceSaveUpdate = data.showOnResourceSaveUpdate || false;

    this.successSearchResultMessage = data.successSearchResultMessage || '';
    this.successSearchEmptyResultMessage = data.successSearchEmptyResultMessage || '';
    this.recordSaveUpdateMessage = data.recordSaveUpdateMessage || '';
    this.resourceSaveUpdateMessage = data.resourceSaveUpdateMessage || '';
  }

  getDefaultSearch() {
    return new ShowMessageDto({
      showOnSuccessSearchEmptyResult: true,
      successSearchEmptyResultMessage: 'No results found!'
    });
  }

  getDefaultAdded() {
    return new ShowMessageDto({
      showOnRecordSaveUpdate: true,
      recordSaveUpdateMessage: 'Successfully added'
    });
  }

  getDefaultUpdated() {
    return new ShowMessageDto({
      showOnRecordSaveUpdate: true,
      recordSaveUpdateMessage: 'Successfully updated'
    });
  }

  getDefaultSaveUpdate() {
    return new ShowMessageDto({
      showOnRecordSaveUpdate: true,
      recordSaveUpdateMessage: 'Successfully saved / updated'
    });
  }

  getMerchantReportGeneratedMsg() {
    return new ShowMessageDto({
      showOnRecordSaveUpdate: true,
      recordSaveUpdateMessage: 'Successfully Generated Merchant User CSV'
    });
  }

  getUserReportGeneratedMsg() {
    return new ShowMessageDto({
      showOnRecordSaveUpdate: true,
      recordSaveUpdateMessage: 'User CSV Report Generated Successfully'
    });
  }

  getDriverReportGeneratedMsg() {
    return new ShowMessageDto({
      showOnRecordSaveUpdate: true,
      recordSaveUpdateMessage: 'Successfully Generated Driver CSV'
    });
  }

  getCustomerReportGeneratedMsg() {
    return new ShowMessageDto({
      showOnRecordSaveUpdate: true,
      recordSaveUpdateMessage: 'Successfully Generated Customer CSV'
    });
  }

  getDefaultResourceSaveUpdate() {
    return new ShowMessageDto({
      showOnResourceSaveUpdate: true,
      resourceSaveUpdateMessage: 'Resource added / updated'
    });
  }

  getDefaultRemove() {
    return new ShowMessageDto({
      showOnRecordSaveUpdate: true,
      recordSaveUpdateMessage: 'Successfully removed'
    });
  }

  showBuildHarvestCycle() {
    return new ShowMessageDto({
      showOnRecordSaveUpdate: true,
      recordSaveUpdateMessage: 'Harvest round created'
    });
  }

  showEndHarvestCycle() {
    return new ShowMessageDto({
      showOnRecordSaveUpdate: true,
      recordSaveUpdateMessage: 'Harvest round ended'
    });
  }

  getSuccessfullyCopyMessage() {
    return new ShowMessageDto({
      showOnRecordSaveUpdate: true,
      recordSaveUpdateMessage: 'Successfully Copied'
    });
  }

}
