import { Component, Input, OnInit } from '@angular/core';
import { SessionStorage } from 'ngx-webstorage';
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import { FacilityPaperAddEditService } from '../../pages/facility-paper/services/facility-paper-add-edit.service';
import { UrlEncodeService } from 'src/app/core/service/application/url-encode.service';
import { Constants } from 'src/app/core/setting/constants';
import { Session } from 'protractor';
import { ViewCovenantAccountDetailsComponent } from '../../pages/customer-360/components/customer-base/components/customer-covenant/view-covenant-account-details/view-covenant-account-details.component';
import { MDBModalService } from 'ng-uikit-pro-standard';

@Component({
  selector: 'app-preview-existing-covenants',
  templateUrl: './preview-existing-covenants.component.html',
  styleUrls: ['./preview-existing-covenants.component.scss']
})
export class PreviewExistingCovenantsComponent implements OnInit {

  private toSentenceCase(value: any): any {
    if (typeof value !== 'string') {
      return value;
    }

    const lower = value.toLowerCase();
    return lower.charAt(0).toUpperCase() + lower.slice(1);
  }
 
  @Input("facilityPaper") facilityPaper: any = {};
  @SessionStorage(SETTINGS.STORAGE.SELECTED_CUSTOMER_ID)
  selectedCIFID: any;

  @SessionStorage('facilityPaperID') facilityPaperId: any;

  covTypCCompStN: any[] = [];
  covTypCCompStY: any[] = [];
  covTypACompStN: any[] = [];
  covTypACompStY: any[] = [];

  covenantFrequencyOptions = Constants.covenantFreaquencyTypes;
  groupedCovTypACompStN: any[] = []; // <-- Initialize as empty array
  groupedCovTypACompStY: any[] = []; // <-- Initialize as empty array
  existingFacilityCovenants: any = {};
  specialComment: any;
  facilityDisplayOrderByAcctId: any = {};

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private urlEncodeService: UrlEncodeService,
    private readonly mdbModalService: MDBModalService,
  ) { }

  ngOnInit() {
    this.getCovenantDetailsFromFinacle();
    this.checkMatch();
  }

  ngOnDestroy(): void {}

  getCovenantDetailsFromFinacle() {  
    let custId = this.urlEncodeService.decode(this.selectedCIFID);
    let facilityPaperId = this.facilityPaperId;
    this.facilityPaperAddEditService.getCovenantDetailsFromFinacle(custId, facilityPaperId)
      .then((response) => {
        const allCovenants: any[] = [];
  
        response.covenant.forEach((covenantItem: any) => {
          if (
            covenantItem.covenantInq &&
            Array.isArray(covenantItem.covenantInq)
          ) {
            allCovenants.push(
              ...covenantItem.covenantInq.map((item: any) => ({
                ...item,
                covRem: this.toSentenceCase(item.covRem),
              })),
            );
          }
        });
  
        this.covTypCCompStN = allCovenants.filter(
          (item) => item.covTyp === "C" && item.compSt === "N"
        );
        this.covTypCCompStY = allCovenants.filter(
          (item) => item.covTyp === "C" && item.compSt === "Y"
        );
        this.covTypACompStN = allCovenants.filter(
          (item) => item.covTyp === "A" && item.compSt === "N"
        );
        this.covTypACompStY = allCovenants.filter(
          (item) => item.covTyp === "A" && item.compSt === "Y"
        );


        this.groupCovTypACompStNByAcctId();
        this.groupCovTypACompStYByAcctId();

        this.specialComment = response.specialComment;
      })
      .catch((error) => {
        console.error("Error fetching covenant details:", error);
      });
  }

  getCovenantFrequencyLabel(frequencyValue: any) {
    const frequency = this.covenantFrequencyOptions.find(
      (item) => item.value === frequencyValue
    );
    return frequency ? frequency.label : "Unknown";
  }

  groupCovTypACompStNByAcctId() {
    const map = new Map<string, any>();

    this.covTypACompStN.forEach((item) => {
      const acctId = item.acctId;

      const selectedFacility = this.existingFacilityCovenants[acctId];

      const displayOrder =
        selectedFacility && selectedFacility.displayOrder != null
          ? selectedFacility.displayOrder
          : 9999;

      if (!map.has(acctId)) {
        map.set(acctId, {
          acctId,
          facility: selectedFacility
            ? `Facility ${selectedFacility.displayOrder}`
            : null,
          displayOrder,
          items: [],
        });
      }

      map.get(acctId).items.push({
        covRem: item.covRem,
        covFrq: item.covFrq,
        covDue: item.covDue,
        srlNum: item.srlNum,
        nonComplianceCovenantDTO: item.nonComplianceCovenantDTO,
      });
    });

    this.groupedCovTypACompStN = Array.from(map.values()).sort(
      (a, b) => a.displayOrder - b.displayOrder,
    ); // IMPORTANT FOR ANGULAR 8 CHANGE DETECTION

    this.groupedCovTypACompStN = [...this.groupedCovTypACompStN];
  }

  groupCovTypACompStYByAcctId() {
    const map = new Map<string, any>();

    this.covTypACompStY.forEach((item) => {
      const acctId = item.acctId;

      const selectedFacility = this.existingFacilityCovenants[acctId];

      const displayOrder =
        selectedFacility && selectedFacility.displayOrder != null
          ? selectedFacility.displayOrder
          : 9999;
          
      if (!map.has(acctId)) {
        map.set(acctId, {
          acctId,
          facility: selectedFacility
            ? `Facility ${selectedFacility.displayOrder}`
            : null,
          displayOrder,
          items: [],
        });
      }

      map.get(acctId).items.push({
        covRem: item.covRem,
        covFrq: item.covFrq,
        covDue: item.covDue,
      });
    });

    this.groupedCovTypACompStY = Array.from(map.values()).sort(
      (a, b) => a.displayOrder - b.displayOrder,
    ); // IMPORTANT FOR ANGULAR 8 CHANGE DETECTION

    this.groupedCovTypACompStY = [...this.groupedCovTypACompStY];
  }

      checkMatch() {
    if (this.facilityPaper && this.facilityPaper.facilityPaperID) {
      this.facilityPaperAddEditService
        .getAllExistingFacilityCovenants(this.facilityPaper.facilityPaperID)
        .then((covenantData: any[]) => {
          this.facilityDisplayOrderByAcctId =
            this.facilityDisplayOrderByAcctId || {};
          if (Array.isArray(covenantData)) {
            this.facilityPaperAddEditService
              .getFacilityList()
              .then((facilityList: any[]) => {
                if (Array.isArray(facilityList)) {
                  covenantData.forEach((item) => {
                    const key = item.acctId || item.accountId;

                    if (key && item.facilityId) {
                      const matchedFacility = facilityList.find(
                        (fac) => fac.facilityID === item.facilityId,
                      );

                      if (matchedFacility) {
                        const displayOrder = matchedFacility.displayOrder;
                        const amountMillion =
                          matchedFacility.facilityAmountMillion ||
                          (matchedFacility.facilityAmount
                            ? typeof matchedFacility.facilityAmount === "number"
                              ? (
                                  matchedFacility.facilityAmount / 1000000
                                ).toFixed(3)
                              : matchedFacility.facilityAmount
                            : "");

                        this.existingFacilityCovenants[key] = {
                          ...matchedFacility,
                          facilityAmountMillion: amountMillion,
                          label:
                            `${matchedFacility.creditFacilityName} - ` +
                            `${matchedFacility.facilityCurrency} ` +
                            `${amountMillion} Mn`,
                        };
                        this.facilityDisplayOrderByAcctId[key] = displayOrder;
                      } else {
                        this.existingFacilityCovenants[key] = item;
                      }
                    }
                  }); 

                  this.groupCovTypACompStYByAcctId();
                  this.groupCovTypACompStNByAcctId();
                }
              });
          }
        });
    }
  }

  
  openBankDetails(acctId: string): void {
      const modalRef = this.mdbModalService.show(
        ViewCovenantAccountDetailsComponent,
        {
          backdrop: true,
          keyboard: true,
          focus: true,
          show: false,
          ignoreBackdropClick: true,
          class: "modal-width-30-p modal-margin-center",
          containerClass: "",
          animated: true,
          data: {
            content: {
              acctId: acctId,
            },
          },
        },
      );
      //modalRef.componentInstance.acctId = acctId;
    }
}

