import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PartyViewComponentComponent } from './party-view-component.component';

describe('PartyViewComponentComponent', () => {
  let component: PartyViewComponentComponent;
  let fixture: ComponentFixture<PartyViewComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PartyViewComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PartyViewComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
