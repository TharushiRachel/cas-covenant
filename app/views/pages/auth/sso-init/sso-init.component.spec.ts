import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SsoInitComponent } from './sso-init.component';

describe('SsoInitComponent', () => {
  let component: SsoInitComponent;
  let fixture: ComponentFixture<SsoInitComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SsoInitComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SsoInitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
