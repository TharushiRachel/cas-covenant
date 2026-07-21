import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ACAECountBoxComponent } from './acae-count-box.component';

describe('ACAECountBoxComponent', () => {
  let component: ACAECountBoxComponent;
  let fixture: ComponentFixture<ACAECountBoxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ACAECountBoxComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ACAECountBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
