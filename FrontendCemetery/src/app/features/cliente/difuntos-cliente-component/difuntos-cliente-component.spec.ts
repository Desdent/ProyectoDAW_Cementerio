import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DifuntosClienteComponent } from './difuntos-cliente-component';

describe('DifuntosClienteComponent', () => {
  let component: DifuntosClienteComponent;
  let fixture: ComponentFixture<DifuntosClienteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DifuntosClienteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DifuntosClienteComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
