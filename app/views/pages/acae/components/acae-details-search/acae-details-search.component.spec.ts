import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ACAEDetailsSearchComponent } from './acae-details-search.component';

describe('ACAEDetailsSearchComponent', () => {
  let component: ACAEDetailsSearchComponent;
  let fixture: ComponentFixture<ACAEDetailsSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ACAEDetailsSearchComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ACAEDetailsSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
