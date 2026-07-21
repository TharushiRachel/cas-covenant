import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpMdAssistanceCmntViewComponent } from './fp-md-assistance-cmnt-view.component';

describe('FpMdAssistanceCmntViewComponent', () => {
  let component: FpMdAssistanceCmntViewComponent;
  let fixture: ComponentFixture<FpMdAssistanceCmntViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpMdAssistanceCmntViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpMdAssistanceCmntViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
