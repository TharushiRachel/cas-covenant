import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SortableModalComponent } from './sortable-modal.component';

describe('SortableModalComponent', () => {
  let component: SortableModalComponent;
  let fixture: ComponentFixture<SortableModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SortableModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SortableModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
