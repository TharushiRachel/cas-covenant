import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonalCribDetailComponent } from './personal-crib-detail.component';

describe('PersonalCribDetailComponent', () => {
  let component: PersonalCribDetailComponent;
  let fixture: ComponentFixture<PersonalCribDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PersonalCribDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonalCribDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
