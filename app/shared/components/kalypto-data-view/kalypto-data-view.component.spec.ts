import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KalyptoDataViewComponent } from './kalypto-data-view.component';

describe('KalyptoDataViewComponent', () => {
  let component: KalyptoDataViewComponent;
  let fixture: ComponentFixture<KalyptoDataViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KalyptoDataViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KalyptoDataViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
