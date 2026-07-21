import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SaveDeviationTypeComponent } from './save-deviation-type.component';

describe('SaveDeviationTypeComponent', () => {
  let component: SaveDeviationTypeComponent;
  let fixture: ComponentFixture<SaveDeviationTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SaveDeviationTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SaveDeviationTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
