import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrackebleInfoComponent } from './trackeble-info.component';

describe('TrackebleInfoComponent', () => {
  let component: TrackebleInfoComponent;
  let fixture: ComponentFixture<TrackebleInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrackebleInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrackebleInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
