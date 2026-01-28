import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AyuntamientosAdminComponent } from './ayuntamientos-admin-component';

describe('AyuntamientosAdminComponent', () => {
  let component: AyuntamientosAdminComponent;
  let fixture: ComponentFixture<AyuntamientosAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AyuntamientosAdminComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AyuntamientosAdminComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
