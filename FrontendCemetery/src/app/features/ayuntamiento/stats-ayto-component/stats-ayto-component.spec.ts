import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StatsAytoComponent } from './stats-ayto-component';

describe('StatsAytoComponent', () => {
  let component: StatsAytoComponent;
  let fixture: ComponentFixture<StatsAytoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StatsAytoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StatsAytoComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
