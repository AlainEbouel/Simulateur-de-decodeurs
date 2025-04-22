import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Client } from '../models/client.model';
import { API_BASE_URL } from '../app.config';
import { SessionService } from './session.service';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  constructor(private http: HttpClient, private session: SessionService) {}

  getClients(): Observable<Client[]> {
    const headers = this.session.getAuthHeaders();
    return this.http.get<Client[]>(`${API_BASE_URL}/admin/clients`, { headers });
  }

  getUnassignedDecoders(): Observable<string[]> {
    const headers = this.session.getAuthHeaders();
    return this.http.get<string[]>(`${API_BASE_URL}/admin/decoders/unassigned`, { headers });
  }
}
