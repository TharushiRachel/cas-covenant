import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KaliptoDetailComponent } from './kalipto-detail.component';

describe('KaliptoDetailComponent', () => {
  let component: KaliptoDetailComponent;
  let fixture: ComponentFixture<KaliptoDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KaliptoDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KaliptoDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
