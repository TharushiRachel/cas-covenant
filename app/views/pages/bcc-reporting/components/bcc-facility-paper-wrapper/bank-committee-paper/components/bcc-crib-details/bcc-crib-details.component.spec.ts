import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BccCribDetailsComponent } from './bcc-crib-details.component';

describe('BccCribDetailsComponent', () => {
  let component: BccCribDetailsComponent;
  let fixture: ComponentFixture<BccCribDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BccCribDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BccCribDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
