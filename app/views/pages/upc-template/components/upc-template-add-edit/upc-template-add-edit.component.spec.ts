import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpcTemplateAddEditComponent } from './upc-template-add-edit.component';

describe('UpcTemplateAddEditComponent', () => {
  let component: UpcTemplateAddEditComponent;
  let fixture: ComponentFixture<UpcTemplateAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpcTemplateAddEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpcTemplateAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
