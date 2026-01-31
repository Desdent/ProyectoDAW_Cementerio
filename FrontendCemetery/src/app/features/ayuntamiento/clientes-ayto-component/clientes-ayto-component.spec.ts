import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientesAytoComponent } from './clientes-ayto-component';

describe('ClientesAytoComponent', () => {
  let component: ClientesAytoComponent;
  let fixture: ComponentFixture<ClientesAytoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClientesAytoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClientesAytoComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
