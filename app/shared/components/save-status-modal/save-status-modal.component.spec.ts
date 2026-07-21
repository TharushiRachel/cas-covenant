import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SaveStatusModalComponent } from './save-status-modal.component';

describe('SaveStatusModalComponent', () => {
  let component: SaveStatusModalComponent;
  let fixture: ComponentFixture<SaveStatusModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SaveStatusModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SaveStatusModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
