import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ACAEDetailsMonitorComponent } from './acae-details-monitor.component';

describe('ACAEDetailsMonitorComponent', () => {
  let component: ACAEDetailsMonitorComponent;
  let fixture: ComponentFixture<ACAEDetailsMonitorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ACAEDetailsMonitorComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ACAEDetailsMonitorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
