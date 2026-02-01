import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardLayoutClienteComponent } from './dashboard-layout-cliente-component';

describe('DashboardLayoutClienteComponent', () => {
  let component: DashboardLayoutClienteComponent;
  let fixture: ComponentFixture<DashboardLayoutClienteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardLayoutClienteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardLayoutClienteComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
