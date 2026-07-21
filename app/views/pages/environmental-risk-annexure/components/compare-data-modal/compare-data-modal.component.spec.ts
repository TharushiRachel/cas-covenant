import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompareDataModalComponent } from './compare-data-modal.component';

describe('CompareDataModalComponent', () => {
  let component: CompareDataModalComponent;
  let fixture: ComponentFixture<CompareDataModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CompareDataModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompareDataModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
