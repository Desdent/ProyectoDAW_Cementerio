import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccesiblityComponent } from './accesiblity-component';

describe('AccesiblityComponent', () => {
  let component: AccesiblityComponent;
  let fixture: ComponentFixture<AccesiblityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccesiblityComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccesiblityComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
