import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CaDetailsAboutComponent } from './ca-details-about.component';

describe('CaDetailsAboutComponent', () => {
  let component: CaDetailsAboutComponent;
  let fixture: ComponentFixture<CaDetailsAboutComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CaDetailsAboutComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CaDetailsAboutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
