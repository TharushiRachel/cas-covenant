import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SectionSubSectionAddEditComponent } from './section-sub-section-add-edit.component';

describe('SectionSubSectionAddEditComponent', () => {
  let component: SectionSubSectionAddEditComponent;
  let fixture: ComponentFixture<SectionSubSectionAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SectionSubSectionAddEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SectionSubSectionAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
