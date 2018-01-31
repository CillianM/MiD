import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IdentityTypeListComponent } from './identity-type-list.component';

describe('IdentityTypeListComponent', () => {
  let component: IdentityTypeListComponent;
  let fixture: ComponentFixture<IdentityTypeListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IdentityTypeListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IdentityTypeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
