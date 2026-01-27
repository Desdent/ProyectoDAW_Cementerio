import { Component, signal } from '@angular/core';
import { HeaderComponent } from './shared/components/header-component/header-component';
import { BodyComponent } from './layout/body-component/body-component';
import { FooterComponent } from './shared/components/footer-component/footer-component';

@Component({
  selector: 'app-root',
  imports: [HeaderComponent, BodyComponent, FooterComponent],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  protected readonly title = signal('FrontendCemetery');
}
