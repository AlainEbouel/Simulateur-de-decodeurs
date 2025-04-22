import { Injectable } from '@angular/core';
import { UserCredentials } from '../models/user-credentials';
import {HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  private readonly USER_KEY = 'auth-user';
  private readonly ROLE_KEY = 'auth-role';
  private credentials: UserCredentials | null = null;
  setSession(credentials: UserCredentials, role: 'ADMIN' | 'CLIENT') {
    sessionStorage.setItem(this.USER_KEY, JSON.stringify(credentials));
    sessionStorage.setItem(this.ROLE_KEY, role);
  }

  getCredentials(): UserCredentials  {
    const data = sessionStorage.getItem(this.USER_KEY);
    return data ? JSON.parse(data) : null;
  }

  getRole(): 'ADMIN' | 'CLIENT' | null {
    return sessionStorage.getItem(this.ROLE_KEY) as 'ADMIN' | 'CLIENT' | null;
  }

  isLoggedIn(): boolean {
    return !!sessionStorage.getItem(this.USER_KEY) && !!sessionStorage.getItem(this.ROLE_KEY);
  }

  logout() {
    sessionStorage.removeItem(this.USER_KEY);
    sessionStorage.removeItem(this.ROLE_KEY);
  }

  getAuthHeaders(): HttpHeaders {
    if (!this.credentials) {
      throw new Error('Aucun identifiant disponible');
    }

    const credentials = this.getCredentials();
    return new HttpHeaders({
      Authorization: 'Basic ' + btoa(`${credentials.username}:${credentials.password}`)
    });
  }
}
