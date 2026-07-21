import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EsgConfirmScoreComponent } from './esg-confirm-score.component';

describe('EsgConfirmScoreComponent', () => {
  let component: EsgConfirmScoreComponent;
  let fixture: ComponentFixture<EsgConfirmScoreComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EsgConfirmScoreComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EsgConfirmScoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
