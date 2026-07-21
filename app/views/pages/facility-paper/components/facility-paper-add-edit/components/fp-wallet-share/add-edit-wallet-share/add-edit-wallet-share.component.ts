import { CurrencyPipe } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";
import { Constants } from "src/app/core/setting/constants";

@Component({
  selector: "app-add-edit-wallet-share",
  templateUrl: "./add-edit-wallet-share.component.html",
  styleUrls: ["./add-edit-wallet-share.component.scss"],
})
export class AddEditWalletShareComponent implements OnInit {
  modalRef: MDBModalRef;
  action: Subject<any> = new Subject<any>();
  facilityPaper: any = {};

  formData: any = {
    walletShareId: 0,
    bank: "",
    facilities: [],
    commonSecurity: "",
    recordStatus: Constants.recordStatusConst.NEW,
    createdDate: "",
    commonSecurities: [],
  };

  selectedFacilities: any[] = [];

  newFacility: any = {
    facilityId: 0,
    facility: "",
    limitAmount: "",
    osAmount: "",
    security: "",
    recordStatus: Constants.recordStatusConst.NEW,
    createdDate: "",
    facilitySecurities: [],
  };

  formErrors: any = {
    bank: "",
    facility: "",
    limitAmount: "",
    osAmount: "",
    facilities: "",
    security: "",
    commonSecurity: "",
  };

  isFacilityEdit: boolean = false;
  selectedItem: any;
  prevDataList: any[] = [];
  prevFacilites: any[] = [];

  constructor(
    public mdbModalRef: MDBModalRef,
    public currencyPipe: CurrencyPipe
  ) {}

  ngOnInit() {
    if (this.selectedItem) {
      this.setFormData(this.selectedItem);
    }
  }

  isBankSelected() {
    return this.formData.bank !== "" && this.formData.bank !== null;
  }

  setFormData(item: any) {
    item.facilities.forEach((element: any) => {
      if (
        !this.selectedFacilities.some(
          (sf: any) => sf.facilityId == element.facilityId
        )
      ) {
        this.selectedFacilities.push({
          ...element,
          security:
            element.facilitySecurities && element.facilitySecurities.length > 0
              ? element.facilitySecurities[0].securityDetail
              : "",
        });
      }
    });

    this.prevFacilites = item.facilities;
    this.formData = {
      ...this.formData,
      walletShareId: item.walletShareId,
      bank: item.bank,
      commonSecurity:
        item.commonSecurities && item.commonSecurities.length > 0
          ? item.commonSecurities[0].securityDetail
          : "",
      commonSecurities: item.commonSecurities ? item.commonSecurities : [],
      recordStatus: item.createdDate
        ? Constants.recordStatusConst.UPDATE
        : Constants.recordStatusConst.NEW,
    };
  }

  handleBankChange(event: any) {
    let value = event && event.target ? event.target.value : "";
    if (value) {
      let prevData: any = this.prevDataList.find(
        (d: any) =>
          d.bank.replace(/\s+/g, "").toLowerCase() ===
          value.replace(/\s+/g, "").toLowerCase()
      );
      if (prevData) {
        this.setFormData(prevData);
      }
    }
  }

  setCurrencyFormatValue(prop: any) {
    let propValue: any = this.newFacility[prop]
      ? parseFloat(this.newFacility[prop].replace(/,/g, ""))
      : 0;

    if (propValue && !this.isValidAmount(propValue)) {
      this.formErrors = {
        ...this.formErrors,
        [prop]: "Invalid amount!",
      };

      setTimeout(() => {
        this.formErrors = {
          ...this.formErrors,
          [prop]: "",
        };
      }, 1500);
    }
    if (propValue !== null) {
      this.newFacility = {
        ...this.newFacility,
        [prop]: this.currencyPipe.transform(propValue, "", "", "1.3-3"),
      };
    }
  }

  getFormattedMillionValue(amount: any) {
    if (amount != null) {
      return this.currencyPipe.transform(amount, "", "", "1.3-3");
    }
  }

  close() {
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  isValidAmount(value: any) {
    let validRegex: RegExp = RegExp(/^[0-9]\d*(\.\d+)?$/);
    return validRegex.test(value);
  }

  validateNewFacility() {
    this.formErrors = {
      ...this.formErrors,
      facility: !this.newFacility.facility ? "Facility is required." : "",
      limitAmount: !this.newFacility.limitAmount
        ? "Amount is required."
        : !this.isValidAmount(parseFloat(this.newFacility.limitAmount))
        ? "Invalid amount!"
        : "",
      osAmount: !this.newFacility.osAmount
        ? "Amount is required."
        : !this.isValidAmount(parseFloat(this.newFacility.osAmount))
        ? "Invalid amount!"
        : "",
      security: this.isCharactorExceeded(this.newFacility.security)
        ? "Charactor limit exceeded."
        : "",
    };

    setTimeout(() => {
      this.formErrors = {
        ...this.formErrors,
        facility: "",
        limitAmount: "",
        osAmount: "",
        security: "",
      };
    }, 1500);
  }

  saveFacility() {
    if (
      this.newFacility.facility &&
      this.isValidAmount(
        this.removeCommaSeperation(this.newFacility.limitAmount)
      ) &&
      this.isValidAmount(
        this.removeCommaSeperation(this.newFacility.osAmount)
      ) &&
      !this.isCharactorExceeded(this.newFacility.security)
    ) {
      if (
        !this.selectedFacilities.some(
          (sf: any) => sf.facilityId == this.newFacility.facilityId
        )
      ) {
        this.selectedFacilities.push({
          ...this.newFacility,
          limitAmount: this.removeCommaSeperation(this.newFacility.limitAmount),
          osAmount: this.removeCommaSeperation(this.newFacility.osAmount),
          facilitySecurities: this.newFacility.security
            ? [
                {
                  ...this.generateSecurity(
                    this.newFacility.security,
                    Constants.yesNoConst.N
                  ),
                },
              ]
            : [],
          facilityId: this.generateRandomNumber(),
        });
      } else {
        this.selectedFacilities = this.selectedFacilities.map((sf: any) =>
          sf.facilityId == this.newFacility.facilityId
            ? {
                ...sf,
                facility: this.newFacility.facility,
                limitAmount: this.removeCommaSeperation(
                  this.newFacility.limitAmount
                ),
                osAmount: this.removeCommaSeperation(this.newFacility.osAmount),
                security: this.newFacility.security,
                facilitySecurities: sf.facilitySecurities
                  ? sf.facilitySecurities.map((fs: any) => ({
                      ...this.generateSecurity(
                        this.newFacility.security,
                        Constants.yesNoConst.N,
                        fs
                      ),
                    }))
                  : [
                      {
                        ...this.generateSecurity(
                          this.newFacility.security,
                          Constants.yesNoConst.N
                        ),
                      },
                    ],
                recordStatus: sf.createdDate
                  ? Constants.recordStatusConst.UPDATE
                  : Constants.recordStatusConst.NEW,
              }
            : sf
        );
      }
      this.clearFacilityData();
    } else {
      this.validateNewFacility();
    }
  }

  editFacility(item: any) {
    let facilitySecurities: any[] =
      item.facilitySecurities && item.facilitySecurities.length > 0
        ? item.facilitySecurities
        : [];

    this.newFacility = {
      facilityId: item.facilityId,
      facility: item.facility,
      limitAmount: this.getFormattedMillionValue(item.limitAmount),
      osAmount: this.getFormattedMillionValue(item.osAmount),
      security:
        facilitySecurities.length > 0
          ? facilitySecurities[0].securityDetail
          : [],
      facilitySecurities: facilitySecurities,
    };

    this.isFacilityEdit = true;
  }

  removeFacility(item: any) {
    if (item.recordStatus === Constants.recordStatusConst.NEW) {
      this.selectedFacilities = this.selectedFacilities.filter(
        (sf: any) => sf.facilityId != item.facilityId
      );
    } else {
      this.selectedFacilities = this.selectedFacilities.map((sf: any) =>
        sf.facilityId === item.facilityId
          ? {
              ...sf,
              recordStatus: Constants.recordStatusConst.DELETE,
            }
          : sf
      );
    }
  }

  clearFacilityData() {
    this.isFacilityEdit = false;
    this.newFacility = {
      facilityId: 0,
      facility: "",
      limitAmount: "",
      osAmount: "",
      security: "",
      recordStatus: Constants.recordStatusConst.NEW,
      createdDate: "",
      facilitySecurities: [],
    };
  }

  saveWalletShare() {
    let prevCommonSecurity: any = this.formData.commonSecurities
      ? this.formData.commonSecurities[0]
      : null;
    this.formData = {
      ...this.formData,
      walletShareId: this.formData.walletShareId
        ? this.formData.walletShareId
        : this.generateRandomNumber(),
      facilities: this.selectedFacilities,
      commonSecurities:
        this.formData.commonSecurity || prevCommonSecurity
          ? [
              {
                ...this.generateSecurity(
                  this.formData.commonSecurity,
                  Constants.yesNoConst.Y,
                  prevCommonSecurity
                ),
              },
            ]
          : [],
    };
    if (
      this.formData.bank &&
      this.filterRemoveData(this.selectedFacilities).length > 0 &&
      !this.isCharactorExceeded(this.formData.commonSecurity)
    ) {
      this.action.next(this.formData);
      this.mdbModalRef.hide();
    } else {
      this.formErrors = {
        ...this.formErrors,
        bank: !this.formData.bank ? "Bank is required." : "",
        facilities:
          this.filterRemoveData(this.selectedFacilities).length == 0
            ? "Facilities are required."
            : "",
        commonSecurity: this.isCharactorExceeded(this.formData.commonSecurity)
          ? "Charactor limit exceeded."
          : "",
      };

      setTimeout(() => {
        this.formErrors = {
          ...this.formErrors,
          bank: "",
          facilities: "",
          commonSecurity: "",
        };
      }, 1500);
    }
  }

  generateRandomNumber() {
    let epoch = new Date().getTime();
    return epoch % 100000;
  }

  filterRemoveData(data: any[]) {
    return data.filter(
      (d: any) => d.recordStatus !== Constants.recordStatusConst.DELETE
    );
  }

  clearData() {
    this.clearFacilityData();
    this.selectedFacilities = [];
    this.formData = {
      walletShareId: 0,
      bank: "",
      facilities: [],
      commonSecurity: "",
      recordStatus: Constants.recordStatusConst.NEW,
      createdDate: "",
      commonSecurities: [],
    };
  }

  isSubmittedItem() {
    return (
      this.selectedItem &&
      this.selectedItem.walletShareId > 0 &&
      this.selectedItem.recordStatus !== Constants.recordStatusConst.NEW
    );
  }

  generateSecurity(
    securityDetail: string,
    isCommonSecurity: string,
    security?: any
  ) {
    if (security !== undefined && security !== null) {
      return {
        securityId: security.securityId,
        facilityId: security.facilityId,
        walletShareId: security.walletShareId,
        isCommonSecurity: isCommonSecurity,
        securityDetail: securityDetail,
        recordStatus: securityDetail
          ? Constants.recordStatusConst.UPDATE
          : Constants.recordStatusConst.DELETE,
      };
    }

    return {
      securityId: 0,
      facilityId: 0,
      walletShareId: 0,
      isCommonSecurity: isCommonSecurity,
      securityDetail: securityDetail,
      recordStatus: Constants.recordStatusConst.NEW,
    };
  }

  removeCommaSeperation(value: string) {
    return parseFloat(value.replace(/,/g, ""));
  }

  isCharactorExceeded(value: string) {
    return value.length > 3900;
  }
}
