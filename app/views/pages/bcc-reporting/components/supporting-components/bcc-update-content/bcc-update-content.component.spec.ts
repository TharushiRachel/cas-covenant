import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BccUpdateContentComponent } from './bcc-update-content.component';

describe('BccUpdateContentComponent', () => {
  let component: BccUpdateContentComponent;
  let fixture: ComponentFixture<BccUpdateContentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BccUpdateContentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BccUpdateContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
