import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpmGroupAddEditComponent } from './upm-group-add-edit.component';

describe('UpmGroupAddEditComponent', () => {
  let component: UpmGroupAddEditComponent;
  let fixture: ComponentFixture<UpmGroupAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpmGroupAddEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpmGroupAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
