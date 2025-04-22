import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { AdminService } from '../../services/admin.service';
import { Client } from '../../models/client.model';
import {SessionService} from '../../services/session.service';
import {Router} from '@angular/router';
import {MatAccordion, MatExpansionPanel, MatExpansionPanelTitle} from '@angular/material/expansion';
import {MatButton} from '@angular/material/button';
import {MatExpansionPanelHeader} from '@angular/material/expansion';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {UserCredentials} from '../../models/user-credentials';
import {Observable} from 'rxjs';
import {MatFormField, MatFormFieldModule, MatLabel} from '@angular/material/form-field';
import {MatOption, MatSelectModule} from '@angular/material/select';
import {MatSelect} from '@angular/material/select';
import {FormsModule} from '@angular/forms';
import {MatInput} from '@angular/material/input';
import {API_BASE_URL} from '../../app.config';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css'],
  standalone: true,
  imports: [CommonModule, MatCardModule, MatAccordion, MatExpansionPanel, MatExpansionPanelTitle, MatButton, MatExpansionPanelHeader, MatLabel, MatSelect, MatOption, FormsModule, MatFormField, MatInput],
})
export class AdminPageComponent implements OnInit {
  credentials: UserCredentials | null = null;
  clients: any[] = [];
  availableDecoders: string[] = [];
  selectedDecoderIp: string | null = null;

  newClient = {
    name: '',
    password: ''
  };

  constructor(
    private http: HttpClient,
    private session: SessionService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.credentials = this.session.getCredentials();
    if (!this.credentials) {
      this.router.navigate(['/']);
      return;
    }
    this.refreshData();
  }

  private getAuthHeaders(): HttpHeaders {
    if (!this.credentials) {
      throw new Error("Identifiants manquants");
    }
    return new HttpHeaders({
      Authorization: 'Basic ' + btoa(`${this.credentials.username}:${this.credentials.password}`)
    });
  }

  private refreshData(): void {
    const headers = this.getAuthHeaders();

    this.http.get<any[]>(`${API_BASE_URL}/admin/clients`, { headers }).subscribe({
      next: data => this.clients = data,
      error: err => {
        console.error('Erreur chargement clients', err);
        this.router.navigate(['/']);
      }
    });

    this.http.get<string[]>(`${API_BASE_URL}/admin/decoders/unassigned`, { headers }).subscribe({
      next: data => this.availableDecoders = data,
      error: err => console.error('Erreur chargement décodeurs', err)
    });
  }

  logout(): void {
    this.session.logout();
    this.router.navigate(['/']);
  }

  assignDecoder(clientId: number): void {
    if (!this.selectedDecoderIp) return;

    const headers = this.getAuthHeaders();
    const body = {
      ipAddress: this.selectedDecoderIp,
      client: { id: clientId }
    };

    this.http.post(`${API_BASE_URL}/admin/decoders`, body, { headers, responseType: 'text' }).subscribe({
      next: () => {
        this.selectedDecoderIp = null;
        this.refreshData();
      },
      error: err => console.error("Erreur assignation décodeur", err)
    });
  }

  removeDecoder(decoderId: number): void {
    const headers = this.getAuthHeaders();

    this.http.delete(`${API_BASE_URL}/admin/decoders/${decoderId}`, { headers }).subscribe({
      next: () => this.refreshData(),
      error: err => console.error("Erreur suppression décodeur", err)
    });
  }

  createClient(): void {
    const headers = this.getAuthHeaders();

    this.http.post(`${API_BASE_URL}/admin/clients`, this.newClient, { headers }).subscribe({
      next: () => {
        this.newClient = { name: '', password: '' };
        this.refreshData();
      },
      error: err => console.error("Erreur création client", err)
    });
  }

  deleteClient(clientId: number): void {
    const confirmation = confirm("Êtes-vous sûr de vouloir supprimer ce client ? Cette action est irréversible.");
    if (!confirmation) return;
    const headers = this.getAuthHeaders();

    this.http.delete(`${API_BASE_URL}/admin/clients/${clientId}`, { headers }).subscribe({
      next: () => this.refreshData(),
      error: err => console.error("Erreur suppression client", err)
    });
  }
}

