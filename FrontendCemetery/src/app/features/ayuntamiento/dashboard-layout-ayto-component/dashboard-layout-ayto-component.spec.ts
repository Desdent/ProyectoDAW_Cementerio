import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardLayoutAytoComponent } from './dashboard-layout-ayto-component';

describe('DashboardLayoutAytoComponent', () => {
  let component: DashboardLayoutAytoComponent;
  let fixture: ComponentFixture<DashboardLayoutAytoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardLayoutAytoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardLayoutAytoComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
