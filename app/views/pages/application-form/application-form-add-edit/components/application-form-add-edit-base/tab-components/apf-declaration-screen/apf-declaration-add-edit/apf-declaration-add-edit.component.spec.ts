import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfDeclarationAddEditComponent } from './apf-declaration-add-edit.component';

describe('ApfDeclarationAddEditComponent', () => {
  let component: ApfDeclarationAddEditComponent;
  let fixture: ComponentFixture<ApfDeclarationAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfDeclarationAddEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfDeclarationAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
