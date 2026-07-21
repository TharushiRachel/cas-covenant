import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FromToDateDialogComponent } from './from-to-date-dialog.component';

describe('FromToDateDialogComponent', () => {
  let component: FromToDateDialogComponent;
  let fixture: ComponentFixture<FromToDateDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FromToDateDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FromToDateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
