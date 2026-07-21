import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonalDetailBasicComponent } from './personal-detail-basic.component';

describe('PersonalDetailBasicComponent', () => {
  let component: PersonalDetailBasicComponent;
  let fixture: ComponentFixture<PersonalDetailBasicComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PersonalDetailBasicComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonalDetailBasicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
