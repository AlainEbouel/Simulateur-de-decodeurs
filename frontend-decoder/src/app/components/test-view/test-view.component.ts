import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-test-view',
  standalone: true,
  imports: [CommonModule],
  template: `<h1 style="color: green">✅ Composant Test affiché</h1>`
})
export class TestViewComponent {}
