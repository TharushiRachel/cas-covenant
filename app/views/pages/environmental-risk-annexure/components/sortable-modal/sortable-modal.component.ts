import { Component, OnInit } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";
import { Constants } from "src/app/core/setting/constants";

@Component({
  selector: "app-sortable-modal",
  templateUrl: "./sortable-modal.component.html",
  styleUrls: ["./sortable-modal.component.scss"],
})
export class SortableModalComponent implements OnInit {
  action: Subject<any> = new Subject<any>();
  heading: string = "";
  dataList: any[] = [];
  items: any[] = [];
  listType: string = "";
  constructor(private readonly mdbModalRef: MDBModalRef) {}

  ngOnInit() {
    this.items = this.dataList
      .filter((d: any) => d.actionStatus !== Constants.annexStatusConst.DELETE)
      .sort((a: any, b: any) => a.displayOrder - b.displayOrder)
      .map((d: any, i: number) => ({
        id: this.listType == "A" ? d.answerId : d.questionId,
        name: this.listType == "A" ? d.answer : d.question,
        displayOrder: i + 1,
      }));
  }

  onClose(): void {
    this.action.next(null);
    this.mdbModalRef.hide();
  }

  listOrderChanged(list: any[]) {
    this.items = list.map((l: any, i: number) => ({
      ...l,
      displayOrder: i + 1,
    }));
  }

  getDisplayOrder(id: any) {
    return this.items.some((d: any) => d.id == id)
      ? this.items.find((d: any) => d.id == id).displayOrder
      : 0;
  }

  handleSortSubmit() {
    let prop: string = this.listType == "A" ? "answerId" : "questionId";
    let sortedList: any[] = this.dataList.map((d: any) => ({
      ...d,
      displayOrder: this.getDisplayOrder(d[prop]),
      actionStatus:
        d.actionStatus === Constants.annexStatusConst.SUBMITTED &&
        this.isRowEdited(d[prop])
          ? Constants.annexStatusConst.UPDATE
          : d.actionStatus,
    }));
    this.action.next(sortedList);
    this.mdbModalRef.hide();
  }

  isRowEdited(id: any) {
    let prop: string = this.listType == "A" ? "answerId" : "questionId";

    let prevItem: any = this.dataList.find((d: any) => d[prop] == id);
    let prevOrder: number = prevItem ? prevItem.displayOrder : 0;

    return this.getDisplayOrder(id) !== prevOrder;
  }
}
