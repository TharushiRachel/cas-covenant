import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SaveDeviationComponent } from './save-deviation.component';

describe('SaveDeviationComponent', () => {
  let component: SaveDeviationComponent;
  let fixture: ComponentFixture<SaveDeviationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SaveDeviationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SaveDeviationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
