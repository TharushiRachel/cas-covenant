import { FormGroup } from "@angular/forms";
import * as _ from "lodash";
import * as moment from "moment";
import { SETTINGS } from "../core/setting/commons.settings";
import { Constants } from "../core/setting/constants";

export class SelectOptionValue {
  key: string | number;
  value: string | number;

  constructor(key, value) {
    this.key = key;
    this.value = value;
  }
}

export class AppUtils {
  /*
   * @field theForm : input form
   * @field formErrors : error object
   * */
  public static getFormErrors(theForm: FormGroup, formErrors: any): any {
    for (const field in formErrors) {
      if (!formErrors.hasOwnProperty(field)) {
        continue;
      }

      // Clear previous errors
      formErrors[field] = {};

      // Get the control
      const control = theForm.get(field);

      if (control && control.dirty && !control.valid) {
        formErrors[field] = control.errors;
      }
    }

    return formErrors;
  }

  public static generateArray(size: number, zeroIncluded: boolean) {
    const arr = [];

    if (zeroIncluded) {
      arr.push(0);
    }

    for (let i = 1; i <= size; i++) {
      arr.push(i);
    }

    return arr;
  }

  public static getArrayItem(
    itemArray: Array<any>,
    field: string,
    matchingValue: any
  ): any {
    return _.find(itemArray, (item) => {
      return item[field] == matchingValue;
    });
  }

  /**
   * constantStrObject - > {'ACT' : 'Actice'}
   * */

  public static generateSelectOptions(
    constantStrObject: any,
    isSort?: boolean,
    isFirstValueEmpty?: boolean
  ): Array<SelectOptionValue> {
    let items: Array<SelectOptionValue> = [];
    let keys = _.keys(constantStrObject);

    keys.forEach((key) => {
      items.push(new SelectOptionValue(key, constantStrObject[key]));
    });

    if (isSort) {
      items.sort((a, b) => {
        return a.value > b.value ? 1 : b.value > a.value ? -1 : 0;
      });
    }

    if (isFirstValueEmpty) {
      items.unshift(new SelectOptionValue("", "All"));
    }

    return items;
  }

  public static getActiveItems(itemList: Array<any>): Array<any> {
    let onlyActiveItems = [];

    itemList.forEach((item) => {
      if (item.status == "ACT") {
        onlyActiveItems.push(item);
      }
    });

    return onlyActiveItems;
  }

  public static getActiveItemsWithSelectedItem(
    itemList: Array<any>,
    itemKey: any,
    selectedItemId: any
  ): Array<any> {
    let onlyActiveItems = [];

    itemList.forEach((item) => {
      if (item[itemKey] == selectedItemId) {
        onlyActiveItems.push(item);
      } else {
        if (item.status == "ACT") {
          onlyActiveItems.push(item);
        }
      }
    });

    return onlyActiveItems;
  }

  public static getActiveItemsWithSelectedItems(
    itemList: Array<any>,
    itemKey: any,
    selectedItemIds: any
  ): Array<any> {
    let onlyActiveItems = [];

    itemList.forEach((item) => {
      if (_.indexOf(selectedItemIds, item[itemKey]) !== -1) {
        onlyActiveItems.push(item);
      } else {
        if (item.status == "ACT") {
          onlyActiveItems.push(item);
        }
      }
    });

    return onlyActiveItems;
  }

  public static trim(formValues: any): any {
    _.keys(formValues).forEach((key) => {
      if (_.isString(formValues[key])) {
        formValues[key] = formValues[key].trim();
      }
    });

    return formValues;
  }

  public static generateNumberArray(length: number) {
    const numberArray = [];

    for (let i = 0; i < length; i++) {
      if (i < 10) {
        numberArray.push("0" + i);
      } else {
        numberArray.push("" + i);
      }
    }

    return numberArray;
  }

  public static getDateHour(dateTimeStr) {
    if (dateTimeStr != null) {
      return dateTimeStr.split(" ")[1].split(":")[0];
    }

    return "";
  }

  public static getDateMinutes(dateTimeStr) {
    if (dateTimeStr != null) {
      return dateTimeStr.split(" ")[1].split(":")[1];
    }

    return "";
  }

  public static getTodayCombinedTime(hour: string, mins: string): string {
    return moment().format(SETTINGS.DATE_FORMAT) + " " + hour + ":" + mins;
  }

  public static isAfter(startDateTime: string, endDateTime: string): boolean {
    return moment(startDateTime, SETTINGS.DATE_TIME_FORMAT).isAfter(
      moment(endDateTime, SETTINGS.DATE_TIME_FORMAT)
    );
  }

  public static isSameOrAfter(
    startDateTime: string,
    endDateTime: string
  ): boolean {
    return moment(startDateTime, SETTINGS.DATE_TIME_FORMAT).isSameOrAfter(
      moment(endDateTime, SETTINGS.DATE_TIME_FORMAT)
    );
  }

  public static isBefore(startDateTime: string, endDateTime: string): boolean {
    return moment(startDateTime, SETTINGS.DATE_TIME_FORMAT).isBefore(
      moment(endDateTime, SETTINGS.DATE_TIME_FORMAT)
    );
  }

  public static isSameOrBefore(
    startDateTime: string,
    endDateTime: string
  ): boolean {
    return moment(startDateTime, SETTINGS.DATE_TIME_FORMAT).isSameOrBefore(
      moment(endDateTime, SETTINGS.DATE_TIME_FORMAT)
    );
  }

  public static getProfileUrl(imageName) {
    let imageUrl = SETTINGS.BASE_IMAGE_URL;
    if (imageName) {
      imageUrl += imageName;
    } else {
      imageUrl += "default.jpg";
    }

    return imageUrl;
  }

  public static getTaskImage(imageName) {
    let imageUrl = SETTINGS.BASE_IMAGE_URL;
    if (imageName) {
      imageUrl += imageName;
    } else {
      imageUrl += "default-task.jpeg";
    }

    return imageUrl;
  }

  public static getDecimalPlaces(a) {
    if (!isFinite(a)) return 0;
    let e = 1,
      p = 0;
    while (Math.round(a * e) / e !== a) {
      e *= 10;
      p++;
    }
    return p;
  }

  public static getUserEligibleDivisions(divisions, userEligibleDivisionIDs) {
    return _.filter(divisions, (division: any) => {
      return _.indexOf(userEligibleDivisionIDs, division.divisionID) !== -1;
    });
  }

  public static generateZeroArray(length: number) {
    let array = [];
    for (let i = 0; i < length; i++) {
      array[i] = 0;
    }
    return array;
  }

  public static getBranchFromBranchCode(branches, code) {
    return _.find(branches, (b) => b.branchCode == code);
  }

  public static getBranchFromBranchName(branches, branchName) {
    return _.find(branches, (b) => b.branchName == branchName);
  }

  public static getCustomerFromCustomerName(customers, customerName) {
    return _.find(
      customers,
      (customer) => customer.customerName == customerName
    );
  }

  public static getCustomerFromCustomerID(customers, customerID) {
    return _.find(customers, (customer) => customer.customerID == customerID);
  }

  public static getSupportingDocFromDocumentName(supportingDocs, documentName) {
    return _.find(supportingDocs, (doc) => doc.documentName == documentName);
  }

  public static getGlobalSupportingDocFromDocumentName(
    globalSupportingDocs,
    documentName
  ) {
    return _.find(
      globalSupportingDocs,
      (doc) => doc.documentName == documentName
    );
  }

  public static getSupportingDocFromDocumentID(
    supportingDocs,
    supportingDocID
  ) {
    return _.find(
      supportingDocs,
      (doc) => doc.supportingDocID == supportingDocID
    );
  }

  public static getApplicationFormTopicByTopic(topics, key) {
    return _.find(topics, (topic) => topic.topic == key);
  }

  public static getFacilityTypeFromFacilityName(facilityTypes, facilityName) {
    return _.find(
      facilityTypes,
      (facilityType) => facilityType.facilityTypeName == facilityName
    );
  }

  public static getFacilityPaperAssignedUserFromUserID(users, userID) {
    return _.find(users, (user) => user.userID == userID);
  }

  public static getFacilityPaperAssignedUserFromLabel(users, label) {
    return _.find(users, (user) => user.label == label);
  }

  public static getUserFromAssignUserID(users, assignUserID) {
    return _.find(users, (user) => user.assignUserID == assignUserID);
  }

  public static getApplicationFormAssignedUserFromUserID(users, userID) {
    return _.find(users, (user) => user.assignUserID == userID);
  }

  public static getUpmGroupFromUpmGroupCode(upmGroups, groupCode) {
    return _.find(upmGroups, (group) => group.groupCode == groupCode);
  }

  public static getFacilityFromFacilityID(Facilities, facilityID) {
    return _.find(Facilities, (facility) => (facility.facilityID = facilityID));
  }

  public static getCasBranchDepartment(casBranchDepartments, code) {
    return _.find(
      casBranchDepartments,
      (data) => (data.branchDepartmentCasCode = code)
    );
  }

  public static isNic(nic) {
    if (!nic) {
      return false;
    }

    let nicSize = nic.length;

    if (nicSize == 12) {
      return new RegExp("^[0-9]*$").test(nic);
    }

    if (nicSize == 10) {
      let isValidValue = new RegExp("^[0-9]*$").test(nic.substring(0, 9));
      let lastChar = nic[nicSize - 1];
      return (
        isValidValue &&
        (lastChar.toUpperCase() == "V" || lastChar.toUpperCase() == "X")
      );
    }

    return false;
  }

  public static isBRN(identificationNumber) {
    if (!identificationNumber) {
      return false;
    } else {
      if (identificationNumber.trim() == "") {
        return false;
      }
      //rest of the validations
      return true;
    }
  }

  public static isPassport(identificationNumber) {
    if (!identificationNumber) {
      return false;
    } else {
      if (identificationNumber.trim() == "") {
        return false;
      }
      //rest of the validations
      return true;
    }
  }

  public static getFloatValue(value) {
    let number = parseFloat(value);
    if (number) {
      return number;
    } else {
      return 0;
    }
  }

  public static startCaseFormat(value) {
    return _.startCase(_.kebabCase(value));
  }

  public static getMillionValue(value:any) {
    let x = Number(value);
    if (x) {
      return value / 1000000;
    } else {
      return  value != null ? value : 0;
    }
  }

  public static roundUp(num, precision) {
    precision = Math.pow(10, precision);
    return Math.ceil(num * precision) / precision;
  }

  public static keysRestricted(event, keys) {
    const keyCode = event.keyCode;
    if (Array.isArray(keys)) {
      const excludedKeys = [...keys];
      if (excludedKeys.includes(keyCode)) {
        event.preventDefault();
      }
    } else {
      console.error("invalid parameter type : " + typeof keys);
    }
  }

  public static tabPressDisabled(event) {
    const excludedKeys = [Constants.keyCodes.TAB];
    this.keysRestricted(event, excludedKeys);
  }

  public static getValue(amount) {
    if (amount) {
      if (isNaN(amount)) {
        return amount.replace(/,/g, "");
      }
    }
    return amount;
  }

  public static getMillionToRupeeValue(value: any) {
    let x = Number(value);
    if (x) {
      return value * 1000000;
    } else {
      return value;
    }
  }
}
