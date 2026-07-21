import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewCompStatementComponent } from './preview-comp-statement.component';

describe('PreviewCompStatementComponent', () => {
  let component: PreviewCompStatementComponent;
  let fixture: ComponentFixture<PreviewCompStatementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviewCompStatementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewCompStatementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
