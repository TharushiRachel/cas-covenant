import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ACAEDetailsComponent } from './acae-details.component';

describe('AcaeDetailsSearchComponent', () => {
  let component: ACAEDetailsComponent;
  let fixture: ComponentFixture<ACAEDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ACAEDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ACAEDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
