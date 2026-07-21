import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BccDirectorDetailsComponent } from './bcc-director-details.component';

describe('BccDirectorDetailsComponent', () => {
  let component: BccDirectorDetailsComponent;
  let fixture: ComponentFixture<BccDirectorDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BccDirectorDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BccDirectorDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
