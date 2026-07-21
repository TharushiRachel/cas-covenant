import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CaCreationDetailsDraftComponent } from './ca-creation-details-draft.component';

describe('CaCreationDetailsDraftComponent', () => {
  let component: CaCreationDetailsDraftComponent;
  let fixture: ComponentFixture<CaCreationDetailsDraftComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CaCreationDetailsDraftComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CaCreationDetailsDraftComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
