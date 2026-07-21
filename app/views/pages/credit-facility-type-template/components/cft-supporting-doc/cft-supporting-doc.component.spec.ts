import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CftSupportingDocComponent } from './cft-supporting-doc.component';

describe('CftSupportingDocComponent', () => {
  let component: CftSupportingDocComponent;
  let fixture: ComponentFixture<CftSupportingDocComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CftSupportingDocComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CftSupportingDocComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
