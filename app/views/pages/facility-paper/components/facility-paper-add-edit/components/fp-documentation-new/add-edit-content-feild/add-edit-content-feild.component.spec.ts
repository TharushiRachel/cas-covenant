import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditContentFeildComponent } from './add-edit-content-feild.component';

describe('AddEditContentFeildComponent', () => {
  let component: AddEditContentFeildComponent;
  let fixture: ComponentFixture<AddEditContentFeildComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddEditContentFeildComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEditContentFeildComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
