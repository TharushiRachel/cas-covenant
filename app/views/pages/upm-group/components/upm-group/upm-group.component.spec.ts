import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpmGroupComponent } from './upm-group.component';

describe('UpmGroupComponent', () => {
  let component: UpmGroupComponent;
  let fixture: ComponentFixture<UpmGroupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpmGroupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpmGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
