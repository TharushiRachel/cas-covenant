import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GenerateBccDialogComponent } from './generate-bcc-dialog.component';

describe('GenerateBccDialogComponent', () => {
  let component: GenerateBccDialogComponent;
  let fixture: ComponentFixture<GenerateBccDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GenerateBccDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GenerateBccDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
