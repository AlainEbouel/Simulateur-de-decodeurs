import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {catchError, map, Observable, of, throwError} from 'rxjs';
import { UserCredentials } from '../models/user-credentials';
import {API_BASE_URL} from '../app.config';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  // authenticate(credentials: UserCredentials): Observable<any> {
  //   const headers = new HttpHeaders({
  //     Authorization: 'Basic ' + btoa(`${credentials.username}:${credentials.password}`)
  //   });
  //
  //   return this.http.get(`${this.baseUrl}/admin/clients`, { headers, observe: 'response' });
  // }
  // // src/app/services/auth.service.ts
  //
  // isAdmin(credentials: UserCredentials): Observable<boolean> {
  //   const headers = new HttpHeaders({
  //     Authorization: 'Basic ' + btoa(`${credentials.username}:${credentials.password}`)
  //   });
  //
  //   return this.http.get('http://localhost:8080/admin/clients', { headers, observe: 'response' }).pipe(
  //     map(response => response.status === 200),
  //     catchError(error => {
  //       if (error.status === 403) {
  //         return of(false);
  //       }
  //       return throwError(() => error);
  //     })
  //   );
  // }
  checkRole(credentials: UserCredentials): Observable<'ADMIN' | 'CLIENT' | null> {
    const headers = new HttpHeaders({
      Authorization: 'Basic ' + btoa(`${credentials.username}:${credentials.password}`)
    });

    return this.http.get(`${API_BASE_URL}/admin/clients`, { headers }).pipe(
      map(() => 'ADMIN' as const),
      catchError(() =>
        this.http.get(`${API_BASE_URL}/client/decoders`, { headers }).pipe(
          map(() => 'CLIENT' as const),
          catchError(() => of(null))
        )
      )
    );
  }

}
