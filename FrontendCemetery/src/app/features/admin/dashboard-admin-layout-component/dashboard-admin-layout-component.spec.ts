import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardAdminLayoutComponent } from './dashboard-admin-layout-component';

describe('DashboardLayoutComponent', () => {
  let component: DashboardAdminLayoutComponent;
  let fixture: ComponentFixture<DashboardAdminLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardAdminLayoutComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DashboardAdminLayoutComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
