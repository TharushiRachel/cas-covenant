import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GenerateCrcDialogComponent } from './generate-crc-dialog.component';

describe('GenerateCrcDialogComponent', () => {
  let component: GenerateCrcDialogComponent;
  let fixture: ComponentFixture<GenerateCrcDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GenerateCrcDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GenerateCrcDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
