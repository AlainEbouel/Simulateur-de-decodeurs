import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {SessionService} from '../services/session.service';

@Injectable({ providedIn: 'root' })
export class ClientGuard implements CanActivate {
  constructor(private session: SessionService, private router: Router) {}

  canActivate(): boolean {
    if (this.session.getRole() === 'CLIENT') return true;
    this.router.navigate(['/']);
    return false;
  }
}
