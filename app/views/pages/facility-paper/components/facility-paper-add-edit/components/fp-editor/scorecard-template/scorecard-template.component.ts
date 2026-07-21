import { Component, Input, OnInit } from '@angular/core';
import { FacilityPaperAddEditService } from 'src/app/views/pages/facility-paper/services/facility-paper-add-edit.service';

@Component({
  selector: 'app-scorecard-template',
  templateUrl: './scorecard-template.component.html',
  styleUrls: ['./scorecard-template.component.scss']
})
export class ScorecardTemplateComponent implements OnInit {

  evaluations = [];
  @Input() facilityPaperID: number;
  score: any;
  dataLoaded: boolean = false;
  customerEvaluationId: number;

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService
  ) {}

  ngOnInit() {
    this.getCustomerEvaluation();
  }

  getCustomerEvaluation() {
    this.facilityPaperAddEditService
      .getCustomerMaxEvaluationForm(this.facilityPaperID)
      .then((data: any) => {
        this.evaluations = data;

        this.facilityPaperAddEditService
          .getCustomerMaxEvaluationId(this.facilityPaperID)
          .then((data1: any) => {
            this.customerEvaluationId = data1.customerEvaluationId;

            const val = localStorage.getItem("id");

            if(this.customerEvaluationId != null){
              this.facilityPaperAddEditService
              .getEvaluationScore(this.customerEvaluationId)
              .then((data1: any) => {
                this.score = data1;
              });
            }
            
          });
      });
  }
}
