import { Component, OnInit } from '@angular/core';
import { SmeServiceService } from '../../service/sme-service.service';

@Component({
  selector: 'app-sme-answers',
  templateUrl: './sme-answers.component.html',
  styleUrls: ['./sme-answers.component.scss']
})
export class SmeAnswersComponent implements OnInit {
  groupedAnswers: { [key: string]: any[] } = {}; // Initialize as an empty object
  facilityPaperID: string | null = sessionStorage.getItem("facilityPaperID");

  constructor(private smeService: SmeServiceService) {}

  ngOnInit() {
    this.getAnswers();
  }

  getAnswers() {
    this.smeService.getSmeAnswers(this.facilityPaperID).then((response: any) => {
      console.log('response.result:', response.result);
      if (response) {
        this.groupedAnswers = response; // Store all key-value pairs
        console.log('Grouped Answers:', this.groupedAnswers);
      } else {
        console.warn('No answers found in the response.');
      }
    }).catch((error: any) => {
      console.error('Error fetching SME answers:', error);
    });
  }

  // Helper method to safely get keys of groupedAnswers
  objectKeys(obj: any): string[] {
    return obj ? Object.keys(obj) : [];
  }

  getGroupCreatedDate(answers: any[]): string | null {
    if (!answers || answers.length === 0) return null;
      const firstDate = answers[0].createdDate;
      const allSame = answers.every(a => a.createdDate === firstDate);
      return allSame ? firstDate : null;
  }
}