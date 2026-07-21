import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FinacleDataStructureComponent } from './finacle-data-structure.component';

describe('FinacleDataStructureComponent', () => {
  let component: FinacleDataStructureComponent;
  let fixture: ComponentFixture<FinacleDataStructureComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FinacleDataStructureComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FinacleDataStructureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
