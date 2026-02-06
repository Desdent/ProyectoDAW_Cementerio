import { Component, effect, Injectable, signal } from '@angular/core';

@Component({
  selector: 'app-accesiblity-component',
  imports: [],
  templateUrl: './accesiblity-component.html',
  styleUrl: './accesiblity-component.css',
})
@Injectable({ providedIn: 'root' })
export class AccesiblityComponent {
  fontSizePercent = signal<number>(100);

  constructor() {
    effect(() => {
      document.documentElement.style.fontSize = `${this.fontSizePercent()}%`;
    });
  }

  aumentar() {
    if (this.fontSizePercent() < 150) {
      // Límite máximo
      this.fontSizePercent.update((s) => s + 10);
    }
  }

  disminuir() {
    if (this.fontSizePercent() > 80) {
      // Límite mínimo
      this.fontSizePercent.update((s) => s - 10);
    }
  }

  reset() {
    this.fontSizePercent.set(100);
  }
}
