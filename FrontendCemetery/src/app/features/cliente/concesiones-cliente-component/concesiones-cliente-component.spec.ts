import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConcesionesClienteComponent } from './concesiones-cliente-component';

describe('ConcesionesClienteComponent', () => {
  let component: ConcesionesClienteComponent;
  let fixture: ComponentFixture<ConcesionesClienteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConcesionesClienteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConcesionesClienteComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
