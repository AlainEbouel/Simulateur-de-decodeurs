import { Injectable } from '@angular/core';
import { UserCredentials } from '../models/user-credentials';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  private credentials: UserCredentials | null = null;
  private role: 'ADMIN' | 'CLIENT' | null = null;

  setSession(credentials: UserCredentials, role: 'ADMIN' | 'CLIENT') {
    this.credentials = credentials;
    this.role = role;
  }

  getCredentials(): UserCredentials | null {
    return this.credentials;
  }

  getRole(): 'ADMIN' | 'CLIENT' | null {
    return this.role;
  }

  clear() {
    this.credentials = null;
    this.role = null;
  }

  isLoggedIn(): boolean {
    return !!this.credentials && !!this.role;
  }
}
