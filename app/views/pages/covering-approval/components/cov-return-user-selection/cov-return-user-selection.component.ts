import { Component, Input, OnInit } from '@angular/core';
import { MDBModalRef, MDBModalService } from 'ng-uikit-pro-standard';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-cov-return-user-selection',
  templateUrl: './cov-return-user-selection.component.html',
  styleUrls: ['./cov-return-user-selection.component.scss']
})
export class CovReturnUserSelectionComponent implements OnInit {

  @Input() users: any[] = [];  
  selectedUserID: string | null = null; 
  userComment: string = ''; 

  // Define the action Subject to emit selected user and comment
  public action: Subject<any> = new Subject();

  constructor(public modalRef: MDBModalRef) {}

  ngOnInit(): void {
    // Sort users by `assignUserUpmGroupCode` in ascending order
    this.users.sort((a, b) => {
      return Number(a.assignUserUpmGroupCode) - Number(b.assignUserUpmGroupCode);
    });
    //console.log("users",this.users)
  }

  confirmSelection(): void {
    if (this.selectedUserID) {
      const selectedUser = this.users.find(user => user.assignUserID === this.selectedUserID);
      const dataToEmit = {
        assignedUser: selectedUser,
        remarkData: {
          comment: this.userComment,
          createdUserId: selectedUser.assignUserID,
          createdUserDisplayName: selectedUser.assignUserDisplayName,
          createdUserUpmCode: selectedUser.assignUserUpmGroupCode,
        }
      };

      this.action.next(dataToEmit); // Emit selected user and comment data
      this.action.complete();
      this.modalRef.hide();
    } else {
      console.warn('No user selected.');
    }
  }
  closeModal(): void {
    this.modalRef.hide();
  }
}


