import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransferFacilityPaperComponent } from './transfer-facility-paper.component';

describe('TransferFacilityPaperComponent', () => {
  let component: TransferFacilityPaperComponent;
  let fixture: ComponentFixture<TransferFacilityPaperComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransferFacilityPaperComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransferFacilityPaperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
