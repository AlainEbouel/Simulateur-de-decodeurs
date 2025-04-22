import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Client } from '../models/client.model';
import { Decoder } from '../models/decoder.model';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../app.config';

@Injectable({ providedIn: 'root' })
export class AdminService {
  constructor(private http: HttpClient) {}

  getClients(): Observable<Client[]> {
    return this.http.get<Client[]>(`${API_BASE_URL}/admin/clients`);
  }

  getAvailableDecoders(): Observable<string[]> {
    return this.http.get<string[]>(`${API_BASE_URL}/admin/decoders/unassigned`);
  }
}
