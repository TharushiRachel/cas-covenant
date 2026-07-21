import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeadComprehensivePartiesComponent } from './lead-comprehensive-parties.component';

describe('LeadComprehensivePartiesComponent', () => {
  let component: LeadComprehensivePartiesComponent;
  let fixture: ComponentFixture<LeadComprehensivePartiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeadComprehensivePartiesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeadComprehensivePartiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
