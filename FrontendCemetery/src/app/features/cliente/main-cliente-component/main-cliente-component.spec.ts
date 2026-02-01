import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainClienteComponent } from './main-cliente-component';

describe('MainClienteComponent', () => {
  let component: MainClienteComponent;
  let fixture: ComponentFixture<MainClienteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MainClienteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MainClienteComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
