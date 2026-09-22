import { Component, input } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-top-bar',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './top-bar.html',
  styleUrl: './top-bar.scss',
})
export class TopBar {
  readonly usuario = input('Humberto Rueda');

  get iniciales(): string {
    return this.usuario()
      .split(' ')
      .slice(0, 2)
      .map((parte) => parte.charAt(0))
      .join('')
      .toUpperCase();
  }
}
