import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityPaperViewTemplateComponent } from './facility-paper-view-template.component';

describe('FacilityPaperViewTemplateComponent', () => {
  let component: FacilityPaperViewTemplateComponent;
  let fixture: ComponentFixture<FacilityPaperViewTemplateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacilityPaperViewTemplateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilityPaperViewTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
