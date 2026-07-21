import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowCribHistoryComponent } from './show-crib-history.component';

describe('ShowCribHistoryComponent', () => {
  let component: ShowCribHistoryComponent;
  let fixture: ComponentFixture<ShowCribHistoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowCribHistoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowCribHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
