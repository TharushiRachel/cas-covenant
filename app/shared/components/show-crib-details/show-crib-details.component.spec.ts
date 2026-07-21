import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowCribDetailsComponent } from './show-crib-details.component';

describe('ShowCribDetailsComponent', () => {
  let component: ShowCribDetailsComponent;
  let fixture: ComponentFixture<ShowCribDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowCribDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowCribDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
