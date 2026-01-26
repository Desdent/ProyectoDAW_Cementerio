import { AfterViewInit, Component, OnDestroy } from '@angular/core';

@Component({
  selector: 'app-home-component',
  imports: [],
  templateUrl: './home-component.html',
  styleUrl: './home-component.css',
})
export class HomeComponent implements AfterViewInit, OnDestroy {
  currentSlide = 0;
  slides: NodeListOf<Element> | null = null;
  dots: NodeListOf<Element> | null = null;
  sliderWrapper: HTMLElement | null = null;
  autoplayInterval: any;

  ngAfterViewInit() {
    // Pequeño delay para asegurar que el DOM está completamente renderizado
    setTimeout(() => {
      this.initializeSlider();
    }, 100);
  }

  ngOnDestroy() {
    this.stopAutoplay();
  }

  private initializeSlider() {
    this.slides = document.querySelectorAll('.profile-slide');
    this.dots = document.querySelectorAll('.dot');
    this.sliderWrapper = document.querySelector('.slider-wrapper');

    console.log('Slides encontrados:', this.slides?.length);
    console.log('Dots encontrados:', this.dots?.length);

    const prevBtns = document.querySelectorAll('.prev-btn');
    const nextBtns = document.querySelectorAll('.next-btn');

    console.log('Botones prev encontrados:', prevBtns.length);
    console.log('Botones next encontrados:', nextBtns.length);

    prevBtns.forEach((btn) => {
      btn.addEventListener('click', (e) => {
        e.preventDefault();
        console.log('Click en prev');
        this.prevSlide();
      });
    });

    nextBtns.forEach((btn) => {
      btn.addEventListener('click', (e) => {
        e.preventDefault();
        console.log('Click en next');
        this.nextSlide();
      });
    });

    // Configuracion dots
    this.dots?.forEach((dot, index) => {
      dot.addEventListener('click', (e) => {
        e.preventDefault();
        console.log('Click en dot', index);
        this.currentSlide = index;
        this.updateSlider();
        this.resetAutoplay();
      });
    });

    // Iniciar autoplay
    this.startAutoplay();

    // Pausar autoplay al hover
    const sliderContainer = document.querySelector('.slider-container');
    if (sliderContainer) {
      sliderContainer.addEventListener('mouseenter', () => {
        this.stopAutoplay();
      });

      sliderContainer.addEventListener('mouseleave', () => {
        this.startAutoplay();
      });
    }
  }

  private updateSlider() {
    if (this.sliderWrapper) {
      this.sliderWrapper.style.transform = `translateX(-${this.currentSlide * 100}%)`;
    }

    this.dots?.forEach((dot, index) => {
      if (index === this.currentSlide) {
        dot.classList.add('active');
      } else {
        dot.classList.remove('active');
      }
    });
  }

  private nextSlide() {
    if (this.slides) {
      this.currentSlide = (this.currentSlide + 1) % this.slides.length;
      this.updateSlider();
    }
  }

  private prevSlide() {
    if (this.slides) {
      this.currentSlide = (this.currentSlide - 1 + this.slides.length) % this.slides.length;
      this.updateSlider();
    }
  }

  private startAutoplay() {
    this.autoplayInterval = setInterval(() => {
      this.nextSlide();
    }, 6000);
  }

  private stopAutoplay() {
    if (this.autoplayInterval) {
      clearInterval(this.autoplayInterval);
    }
  }

  private resetAutoplay() {
    this.stopAutoplay();
    this.startAutoplay();
  }
}
