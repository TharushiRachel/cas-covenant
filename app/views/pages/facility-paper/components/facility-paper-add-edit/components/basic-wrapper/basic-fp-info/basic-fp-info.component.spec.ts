import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BasicFpInfoComponent } from './basic-fp-info.component';

describe('BasicFpInfoComponent', () => {
  let component: BasicFpInfoComponent;
  let fixture: ComponentFixture<BasicFpInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BasicFpInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BasicFpInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
