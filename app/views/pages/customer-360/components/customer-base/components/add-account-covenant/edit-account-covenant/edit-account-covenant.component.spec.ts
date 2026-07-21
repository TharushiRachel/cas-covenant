import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditAccountCovenantComponent } from './edit-account-covenant.component';

describe('EditAccountCovenantComponent', () => {
  let component: EditAccountCovenantComponent;
  let fixture: ComponentFixture<EditAccountCovenantComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditAccountCovenantComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditAccountCovenantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
