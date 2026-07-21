import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DaTableStructureComponent } from './da-table-structure.component';

describe('DaTableStructureComponent', () => {
  let component: DaTableStructureComponent;
  let fixture: ComponentFixture<DaTableStructureComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DaTableStructureComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DaTableStructureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
