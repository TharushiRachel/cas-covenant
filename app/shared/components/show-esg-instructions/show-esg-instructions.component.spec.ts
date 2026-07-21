import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowEsgInstructionsComponent } from './show-esg-instructions.component';

describe('ShowEsgInstructionsComponent', () => {
  let component: ShowEsgInstructionsComponent;
  let fixture: ComponentFixture<ShowEsgInstructionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowEsgInstructionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowEsgInstructionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
