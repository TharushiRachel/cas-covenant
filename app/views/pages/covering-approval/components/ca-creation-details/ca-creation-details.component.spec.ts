import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CaCreationDetailsComponent } from './ca-creation-details.component';

describe('CaCreationDetailsComponent', () => {
  let component: CaCreationDetailsComponent;
  let fixture: ComponentFixture<CaCreationDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CaCreationDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CaCreationDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
