import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SectionSubSectionComponent } from './section-sub-section.component';

describe('SectionSubSectionComponent', () => {
  let component: SectionSubSectionComponent;
  let fixture: ComponentFixture<SectionSubSectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SectionSubSectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SectionSubSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
