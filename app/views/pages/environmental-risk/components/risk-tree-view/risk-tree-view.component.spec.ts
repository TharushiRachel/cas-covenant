import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RiskTreeViewComponent } from './risk-tree-view.component';

describe('RiskTreeViewComponent', () => {
  let component: RiskTreeViewComponent;
  let fixture: ComponentFixture<RiskTreeViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RiskTreeViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RiskTreeViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
