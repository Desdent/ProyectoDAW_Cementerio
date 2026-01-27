import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardAyuntamientoComponent } from './dashboard-ayuntamiento-component';

describe('DashboardAyuntamientoComponent', () => {
  let component: DashboardAyuntamientoComponent;
  let fixture: ComponentFixture<DashboardAyuntamientoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardAyuntamientoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardAyuntamientoComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
