import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ACAEPaperDetailsComponent } from './acae-paper-details.component';

describe('ACAEDetailsEditComponent', () => {
  let component: ACAEPaperDetailsComponent;
  let fixture: ComponentFixture<ACAEPaperDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ACAEPaperDetailsComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ACAEPaperDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
