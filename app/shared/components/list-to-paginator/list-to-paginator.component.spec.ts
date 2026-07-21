import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListToPaginatorComponent } from './list-to-paginator.component';

describe('ListToPaginatorComponent', () => {
  let component: ListToPaginatorComponent;
  let fixture: ComponentFixture<ListToPaginatorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListToPaginatorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListToPaginatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
