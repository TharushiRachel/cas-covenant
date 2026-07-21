import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DaTableAddEditComponent } from './da-table-add-edit.component';

describe('DaTableAddEditComponent', () => {
  let component: DaTableAddEditComponent;
  let fixture: ComponentFixture<DaTableAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DaTableAddEditComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DaTableAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
