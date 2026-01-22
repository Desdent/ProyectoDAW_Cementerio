import { Component, signal } from '@angular/core';
import { HeaderComponent } from './header-component/header-component';
import { BodyComponent } from './body-component/body-component';
import { FooterComponent } from './footer-component/footer-component';

@Component({
  selector: 'app-root',
  imports: [HeaderComponent, BodyComponent, FooterComponent],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  protected readonly title = signal('FrontendCemetery');
}
