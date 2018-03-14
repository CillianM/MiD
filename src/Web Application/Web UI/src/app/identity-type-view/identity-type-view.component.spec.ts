import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IdentityTypeViewComponent } from './identity-type-view.component';

describe('IdentityTypeViewComponent', () => {
  let component: IdentityTypeViewComponent;
  let fixture: ComponentFixture<IdentityTypeViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IdentityTypeViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IdentityTypeViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
