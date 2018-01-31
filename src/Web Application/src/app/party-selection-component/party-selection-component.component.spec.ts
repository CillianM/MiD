import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PartySelectionComponentComponent } from './party-selection-component.component';

describe('PartySelectionComponentComponent', () => {
  let component: PartySelectionComponentComponent;
  let fixture: ComponentFixture<PartySelectionComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PartySelectionComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PartySelectionComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
