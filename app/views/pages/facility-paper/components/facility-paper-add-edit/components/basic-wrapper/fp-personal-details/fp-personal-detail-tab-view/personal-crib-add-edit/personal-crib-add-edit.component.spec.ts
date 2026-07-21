import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonalCribAddEditComponent } from './personal-crib-add-edit.component';

describe('PersonalCribAddEditComponent', () => {
  let component: PersonalCribAddEditComponent;
  let fixture: ComponentFixture<PersonalCribAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PersonalCribAddEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonalCribAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
