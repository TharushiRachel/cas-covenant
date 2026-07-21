import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyFacilityPapersComponent } from './my-facility-papers.component';

describe('MyFacilityPapersComponent', () => {
  let component: MyFacilityPapersComponent;
  let fixture: ComponentFixture<MyFacilityPapersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyFacilityPapersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyFacilityPapersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
