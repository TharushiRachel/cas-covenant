import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DaTableChangeOrderComponent } from './da-table-change-order.component';

describe('DaTableChangeOrderComponent', () => {
  let component: DaTableChangeOrderComponent;
  let fixture: ComponentFixture<DaTableChangeOrderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DaTableChangeOrderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DaTableChangeOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
