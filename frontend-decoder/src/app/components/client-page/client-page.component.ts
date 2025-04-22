import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {SessionService} from '../../services/session.service';
import {UserCredentials} from '../../models/user-credentials';
import {MatCardContent, MatCardHeader, MatCardModule} from '@angular/material/card';
import {MatButton, MatButtonModule} from '@angular/material/button';
import {CommonModule, NgForOf} from '@angular/common';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatOption, MatSelect} from '@angular/material/select';
import {FormsModule} from '@angular/forms';
import {Channel} from '../../models/channel.model';


@Component({
  selector: 'app-client-page',
  templateUrl: './client-page.component.html',
  styleUrls: ['./client-page.component.css'],
  standalone: true,
  imports: [
    MatCardHeader,
    MatCardContent,
    MatButton,
    NgForOf,
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatFormField,
    MatOption,
    MatSelect,
    FormsModule,
    MatLabel,

  ],
})

export class ClientPageComponent implements OnInit {
  credentials: UserCredentials | null = null;
  decoders: { id: number; ipAddress: string; status?: any; channels?: Channel[] }[] = [];
  allChannels: any[] = [];
  selectedChannelId: { [decoderId: number]: number } = {};
  clientName: string = '';

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
    this.clientName = this.credentials.username;
    this.loadDecoders();
    this.loadAllChannels();
  }

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      Authorization: 'Basic ' + btoa(`${this.credentials!.username}:${this.credentials!.password}`)
    });
  }

  loadDecoders(): void {
    this.http.get<any[]>('http://localhost:8080/client/decoders', { headers: this.getHeaders() }).subscribe({
      next: (data) => {
        this.decoders = data;
        for (const decoder of this.decoders) {
          this.loadDecoderStatus(decoder);
          this.loadDecoderChannels(decoder);
        }
      },
      error: () => this.router.navigate(['/'])
    });
  }

  loadDecoderStatus(decoder: any): void {
    this.http.get<any>(`http://localhost:8080/client/decoder/${decoder.id}/status`, {
      headers: this.getHeaders()
    }).subscribe({
      next: (status) => decoder.status = status
    });
  }

  loadDecoderChannels(decoder: any): void {
    this.http.get<any[]>(`http://localhost:8080/client/decoder/${decoder.id}/channels`, {
      headers: this.getHeaders()
    }).subscribe({
      next: (channels) => decoder.channels = channels
    });
  }

  loadAllChannels(): void {
    this.http.get<any[]>('http://localhost:8080/client/channels', {
      headers: this.getHeaders()
    }).subscribe({
      next: (channels) => this.allChannels = channels
    });
  }

  addChannel(decoderId: number): void {
    const channelId = this.selectedChannelId[decoderId];
    if (!channelId) return;

    this.http.post(`http://localhost:8080/client/decoder/${decoderId}/add-channel/${channelId}`, {}, {
      headers: this.getHeaders()
    }).subscribe({
      next: () => {
        this.loadDecoderChannels(this.decoders.find(d => d.id === decoderId));
        this.selectedChannelId[decoderId] = 0;
      }
    });
  }

  removeChannel(decoderId: number, channelId: number): void {
    this.http.delete(`http://localhost:8080/client/decoder/${decoderId}/remove-channel/${channelId}`, {
      headers: this.getHeaders()
    }).subscribe({
      next: () => this.loadDecoderChannels(this.decoders.find(d => d.id === decoderId))
    });
  }

  reboot(decoderId: number): void {
    this.http.post(`http://localhost:8080/client/decoder/${decoderId}/reboot`, {}, {
      headers: this.getHeaders()
    }).subscribe({
      next: () => this.pollStatusUntilActive(decoderId)
    });
  }

  pollStatusUntilActive(decoderId: number): void {
    const decoder = this.decoders.find(d => d.id === decoderId);
    if (!decoder) return;

    const check = () => {
      this.http.get<any>(`http://localhost:8080/client/decoder/${decoderId}/status`, {
        headers: this.getHeaders()
      }).subscribe({
        next: (status) => {
          decoder.status = status;
          if (status.state !== 'active') {
            setTimeout(check, 2000); // interroge à nouveau dans 2s
          }
        }
      });
    };

    check();
  }
  reinit(decoderId: number): void {
    this.http.post(`http://localhost:8080/client/decoder/${decoderId}/reinit`, {}, {
      headers: this.getHeaders()
    }).subscribe({
      next: () => this.loadDecoderStatus(this.decoders.find(d => d.id === decoderId))
    });
  }
  shutdown(decoderId: number): void {
    this.http.post(`http://localhost:8080/client/decoder/${decoderId}/shutdown`, {}, {
      headers: this.getHeaders()
    }).subscribe({
      next: () => this.loadDecoderStatus(this.decoders.find(d => d.id === decoderId))
    });
  }

  logout(): void {
    this.session.logout();
    this.router.navigate(['/']);
  }
}
