import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  Output,
  SimpleChanges
} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-paginator',
  templateUrl: './paginator.component.html',
  styleUrls: ['./paginator.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PaginatorComponent implements OnInit, OnChanges, OnDestroy {

  @Input('pageIndex') pageIndex = 0;
  @Input('length') length;
  @Input('pageSize') pageSize = 10;
  @Input('pageSizeOptions') pageSizeOptions;

  @Output('page') page = new EventEmitter();

  searchForm: FormGroup;

  valueChangesSubs = new Subscription();

  optionsSelect = [
    {value: 5, label: '5'},
    {value: 10, label: '10'},
    {value: 25, label: '25'},
    {value: 100, label: '100'},
  ];

  constructor(private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.searchForm = this.formBuilder.group({
      size: [this.pageSize]
    });

    this.valueChangesSubs = this.searchForm.valueChanges
      .subscribe((formValues: any) => {
        this.onValueUpdate(0);
      });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['pageSize'] && changes['pageSize'].currentValue) {
      if (this.searchForm) {
        this.searchForm.controls.size.setValue(this.pageSize, {onlySelf: true, emitEvent: false});
      }
    }
  }

  ngOnDestroy(): void {
    this.valueChangesSubs.unsubscribe();
  }

  getDisplayString() {
    let index = this.pageIndex + 1;
    let max = index * this.pageSize;
    let min = (max - this.pageSize) + 1;
    max = max <= this.length ? max : this.length;

    let str = `${min}-${max} of ${this.length}`;
    return str;
  }

  prvClick(event) {
    if (event) {
      event.preventDefault();
      event.stopPropagation();
    }

    if (this.pageIndex > 0) {
      this.onValueUpdate(this.pageIndex - 1);
    }
  }

  nextClick(event) {
    if (event) {
      event.preventDefault();
      event.stopPropagation();
    }
    let maxPages = Math.ceil(this.length / this.pageSize);
    if (this.pageIndex < (maxPages - 1)) {
      this.onValueUpdate(this.pageIndex + 1);
    }
  }

  onValueUpdate(pageIndex: any) {
    let formValues = this.searchForm.getRawValue();
    let data: any = {
      pageSize: formValues.size,
      pageIndex: pageIndex
    };

    this.page.emit(data);
  }

}
