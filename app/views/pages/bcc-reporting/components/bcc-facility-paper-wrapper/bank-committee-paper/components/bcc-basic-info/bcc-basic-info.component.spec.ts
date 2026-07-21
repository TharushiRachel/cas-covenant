import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BccBasicInfoComponent } from './bcc-basic-info.component';

describe('BccBasicInfoComponent', () => {
  let component: BccBasicInfoComponent;
  let fixture: ComponentFixture<BccBasicInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BccBasicInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BccBasicInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
