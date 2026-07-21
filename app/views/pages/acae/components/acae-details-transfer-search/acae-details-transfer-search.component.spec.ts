import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AcaeDetailsTransferSearchComponent } from './acae-details-transfer-search.component';

describe('AcaeDetailsTransferSearchComponent', () => {
  let component: AcaeDetailsTransferSearchComponent;
  let fixture: ComponentFixture<AcaeDetailsTransferSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AcaeDetailsTransferSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AcaeDetailsTransferSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
