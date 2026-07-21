import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpcNotifyComponentComponent } from './upc-notify-component.component';

describe('UpcNotifyComponentComponent', () => {
  let component: UpcNotifyComponentComponent;
  let fixture: ComponentFixture<UpcNotifyComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpcNotifyComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpcNotifyComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
