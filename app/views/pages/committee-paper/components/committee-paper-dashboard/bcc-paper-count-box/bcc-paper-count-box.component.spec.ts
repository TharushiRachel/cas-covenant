import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CountBoxComponent } from './lead-count-box.component';

describe('LeadCountBoxComponent', () => {
  let component: ApplicationFormCountBoxComponent;
  let fixture: ComponentFixture<ApplicationFormCountBoxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApplicationFormCountBoxComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationFormCountBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
