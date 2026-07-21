import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DaTableComponent } from './da-table.component';

describe('DaTableComponent', () => {
  let component: DaTableComponent;
  let fixture: ComponentFixture<DaTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DaTableComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DaTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
